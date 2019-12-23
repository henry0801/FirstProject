package com.controller;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.common.MapList;
import com.common.WorkTimePrint;
import com.dto.EmployeeDto;
import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;
import com.form.WorkStatusForm;
import com.service.EmployeeService;
import com.service.WorkdailyService;
import com.service.WorkmonthlyService;

@Controller
@RequestMapping("workStatus")
@SessionAttributes(value={"employeeDtoList","workyear","workmonth","message"})
public class WorkStatusController {

	Logger logger = LoggerFactory.getLogger(WorkStatusController.class);

	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private WorkdailyService workdailyService;
	@Autowired
    private WorkmonthlyService workmonthlyService;

	private MapList mapList = new MapList();

	private WorkTimePrint worktimeprint = new WorkTimePrint();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {

        String workyear = workStatusForm.getWorkYear();
        String workmonth = workStatusForm.getWorkMonth();

        if (workyear == null || workmonth == null) {
           Calendar cal = Calendar.getInstance();
           workyear = String.valueOf(cal.get(1));
           workmonth = String.format("%02d", cal.get(2) + 1);
        }

        workStatusForm.setWorkYear(workyear);
        workStatusForm.setWorkMonth(workmonth);

    	return showlist(workStatusForm, request,model,map);
    }

    //戻るボタン
    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute WorkStatusForm workStatusForm, Model model, ModelMap map) {

        return "menu";
    }

    //検索ボタン
    @RequestMapping(value = "", params = "search", method = RequestMethod.POST)
    public String showlist(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {

    	String workyear = workStatusForm.getWorkYear();
    	String workmonth =  workStatusForm.getWorkMonth();

    	List<EmployeeDto> employeeDtoList = employeeService.getUserWorkmonthly(workyear,workmonth);

    	for (EmployeeDto employeeDto : employeeDtoList) {
    		String userid = employeeDto.getUserid();
    		WorkmonthlyDto workmonthlyDto = workmonthlyService.getWorkmonthlyById(userid, workyear, workmonth);

    		employeeDto.setWorkmonthlyDto(workmonthlyDto);
		}

        model.addAttribute("workStatusForm", workStatusForm);
        model.addAttribute("employeeDtoList", employeeDtoList);

        map.put("workyear", workyear);
        map.put("workmonth", workmonth);
        map.put("employeeDtoList", employeeDtoList);

        return "workStatus";
    }


    //一括ダウンロード
    @RequestMapping(value = "", params = "multiDownLoad", method = RequestMethod.POST)
    public String test1(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, HttpServletResponse response,Model model,ModelMap map) {

    	List<EmployeeDto> employees = (List<EmployeeDto>) map.get("employeeDtoList");

    	String workyear = workStatusForm.getWorkYear();
		String workmonth =workStatusForm.getWorkMonth();
		String[] taisyo = workStatusForm.getTaisyo();

		try {

			Workbook workbook = null;

			ByteArrayOutputStream  bufferdOutpuStream = new ByteArrayOutputStream();
			ZipOutputStream zipOutPutStream = new ZipOutputStream(bufferdOutpuStream);

			for (int i = 0; i < employees.size(); i++) {
				EmployeeDto employeeDto = employees.get(i);
				String userid = employeeDto.getUserid();
				String username = employeeDto.getUsername();

				WorkmonthlyDto workmonthlyDto = workmonthlyService.getWorkmonthlyById(userid, workyear, workmonth);
				List<WorkdailyDto> workdailyDtoList = workdailyService.getWorkdailyById(userid, workyear, workmonth);
				workmonthlyDto.setWorkdailyDtoList(workdailyDtoList);
				workmonthlyDto.setUsername(username);

				//画面CheckBoxでチェックしないと、対象外にする。
				if (!Arrays.asList(taisyo).contains(String.valueOf(i)) || workmonthlyDto==null || workdailyDtoList==null)
					continue;

				workbook = worktimeprint.print(workmonthlyDto);

				String outputfileNm = workyear + "年" + workmonth + "月_勤务表_" + username+".xls";
				zipOutPutStream.putNextEntry(new ZipEntry(outputfileNm));
				workbook.write(zipOutPutStream);
				zipOutPutStream.write(bufferdOutpuStream.toByteArray(), 0, bufferdOutpuStream.toByteArray().length);

				zipOutPutStream.closeEntry();
			}
			zipOutPutStream.close();

			String zipFIleName = workyear + "年" + workmonth + "月_勤務表.zip";
			response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(zipFIleName, "UTF-8")+"\"");
			response.setHeader("Content-Type", "application/zip");
			response.getOutputStream().write(bufferdOutpuStream.toByteArray());
			response.flushBuffer();
			bufferdOutpuStream.close();

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

        return null;
    }

    @ModelAttribute("yearList")
	public Map<String, String> getYearList() {
		return mapList.getYearList();
	}

	@ModelAttribute("monthList")
	public Map<String, String> getMonthList() {
		return mapList.getMonthList();
	}

}
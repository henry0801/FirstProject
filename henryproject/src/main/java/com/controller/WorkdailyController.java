package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
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
import com.common.WorkTimeCal;
import com.common.WorkTimePrint;
import com.dto.EmployeeDto;
import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;
import com.form.WorkStatusForm;
import com.form.WorkdailyForm;
import com.service.EmployeeService;
import com.service.WorkdailyService;
import com.service.WorkmonthlyService;

@Controller
@RequestMapping("/workInput")
@SessionAttributes({"employeename", "workyear", "workmonth","employeeDtoList","workmonthlyDto"})
public class WorkdailyController {

	Logger logger = LoggerFactory.getLogger(WorkdailyController.class);

	private MapList mapList = new MapList();

	@Autowired
    private EmployeeService employeeService;

	@Autowired
    private WorkdailyService workdailyService;

	@Autowired
    private WorkmonthlyService workmonthlyService;

	private WorkTimeCal workTimeCal = new WorkTimeCal();

	private WorkTimePrint workTimePrint = new WorkTimePrint();

	//他の画面でリンク経由
	@RequestMapping(value = "link", method = RequestMethod.GET)
	public String link(HttpServletRequest request, WorkdailyForm workdailyForm, Model model, ModelMap map) {

		String userid = request.getParameter("userid");
		String employeename = request.getParameter("employeename");
		workdailyForm.setUserid(userid);

		map.put("employeename", employeename);
		return viewBuilder(workdailyForm, model, map);
	}

	//初期表示
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String show(@ModelAttribute Model model,ModelMap map) {

        return viewBuilder(new WorkdailyForm(), model, map);
    }

    //戻るボタン
    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute WorkStatusForm workStatusForm, Model model,ModelMap map) {
        String workyear = (String) map.get("workyear");
        String workmonth = (String) map.get("workmonth");

        workStatusForm.setWorkYear(workyear);
        workStatusForm.setWorkMonth(workmonth);

        List<EmployeeDto> employeeDtoList = employeeService.getUserWorkmonthly(workyear,workmonth);
        model.addAttribute("employeeDtoList", employeeDtoList);

    	return "workStatus";
    }

    //検索ボタン
	@RequestMapping(value = "", params = "serach", method = RequestMethod.POST)
	public String viewBuilder(@ModelAttribute WorkdailyForm workdailyForm, Model model,ModelMap map) {

		String userid = workdailyForm.getUserid();
		String workyear = workdailyForm.getWorkYear();
		String workmonth =workdailyForm.getWorkMonth();
		if (workyear == null || workmonth == null) {
			workyear = (String) map.get("workyear");
	        workmonth = (String) map.get("workmonth");
	        workdailyForm.setWorkYear(workyear);
	    	workdailyForm.setWorkMonth(workmonth);
		}

		WorkmonthlyDto workmonthlyDto = workmonthlyService.getWorkmonthlyById(userid, workyear, workmonth);
		List<WorkdailyDto> workdailyDtoList = workdailyService.getWorkdailyById(userid,workyear,workmonth);

		if (workdailyDtoList!=null && workdailyDtoList.size()>0) {

			String[] start_h = new String[workdailyDtoList.size()];
			String[] start_m = new String[workdailyDtoList.size()];
			String[] end_h = new String[workdailyDtoList.size()];
			String[] end_m = new String[workdailyDtoList.size()];

			for (int i = 0; i < workdailyDtoList.size(); i++) {
				WorkdailyDto workdailyDto = workdailyDtoList.get(i);
				String dateStr = workdailyDto.getWorkyear()+workdailyDto.getWorkmonth()+workdailyDto.getWorkday();

				try {
					SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
					Date date = fmt.parse(dateStr);
					workdailyDto.setDate(date);

				} catch (ParseException e) {
					logger.debug("ParseException: "+e.getMessage());
				}

				start_h[i] = workdailyDto.getStart_h();
				start_m[i] = workdailyDto.getStart_m();
				end_h[i] = workdailyDto.getEnd_h();
				end_m[i] = workdailyDto.getEnd_m();
			}

			workdailyForm.setStart_harry(start_h);
			workdailyForm.setStart_marry(start_m);
			workdailyForm.setEnd_harry(end_h);
			workdailyForm.setEnd_marry(end_m);

		}

		if (workmonthlyDto==null) {
			workmonthlyDto = new WorkmonthlyDto();
		}else {
			workmonthlyDto.setWorkdailyDtoList(workdailyDtoList);
		}

		map.put("workyear", workyear);
		map.put("workmonth", workmonth);
		map.put("workmonthlyDto", workmonthlyDto);

        model.addAttribute(workdailyForm);
        model.addAttribute("employeename",map.get("employeename"));
        model.addAttribute("workmonthlyDto", workmonthlyDto);

		return "workdaily";
	}

	//保存ボタン
	@RequestMapping(value = "", params = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute WorkdailyForm form, Model model,ModelMap map) {

		//セッションから取得
		WorkmonthlyDto workmonthlyDto = (WorkmonthlyDto) map.get("workmonthlyDto");
		List<WorkdailyDto> workdailDtoList_after = new ArrayList<>();

		String[] userid = form.getUseridarry();
		String[] start_h = form.getStart_harry();
		String[] start_m = form.getStart_marry();
		String[] end_h = form.getEnd_harry();
		String[] end_m = form.getEnd_marry();
		String[] workyear = form.getWorkyeararry();
		String[] workmonth = form.getWorkmontharry();
		String[] day = form.getWorkdayarry();
		String[] biko1 = form.getBiko1arry();
		String[] biko2 = form.getBiko2arry();

		if (userid!=null) {
			for (int i = 0; i < userid.length; i++) {

				WorkdailyDto workdailDto = new WorkdailyDto();
				workdailDto.setUserid(userid[i]);
				workdailDto.setWorkyear(workyear[i]);
				workdailDto.setWorkmonth(workmonth[i]);
				workdailDto.setWorkday(day[i]);
				workdailDto.setStart_h(start_h[i]);
				workdailDto.setStart_m(start_m[i]);
				workdailDto.setEnd_h(end_h[i]);
				workdailDto.setEnd_m(end_m[i]);
				workdailDto.setBiko1(biko1[i]);
				workdailDto.setBiko2(biko2[i]);

				workdailDtoList_after.add(workdailDto);
			}
			workmonthlyDto.setWorkdailyDtoList(workdailDtoList_after);

			//工数計算
			workTimeCal.calWorkmonthData(workmonthlyDto);

			workmonthlyService.updateWorkMonthDayById(workmonthlyDto);
		}

		return viewBuilder(form, model, map);
	}

	//新規作成ボタン
	@RequestMapping(value = "", params = "recreate", method = RequestMethod.POST)
	public String recreate(@ModelAttribute WorkdailyForm workdailyForm, Model model,ModelMap map) {

		//キー取得
		String userid = workdailyForm.getUserid();
		String workyear = workdailyForm.getWorkYear();
		String workmonth =workdailyForm.getWorkMonth();
		if (workyear == null || workmonth == null) {
			workyear = (String) map.get("workyear");
	        workmonth = (String) map.get("workmonth");
	        workdailyForm.setWorkYear(workyear);
	    	workdailyForm.setWorkMonth(workmonth);
		}

		//新規WorkdailyDtoリスト作成
		List<WorkdailyDto> workdailyDtoList_New = workTimeCal.setNewWorkmonthData(userid, workyear, workmonth);

		//新規WorkmonthlyDto作成
		WorkmonthlyDto workmonthlyDto_New = new WorkmonthlyDto();
		workmonthlyDto_New.setUserid(userid);
		workmonthlyDto_New.setWorkyear(workyear);
		workmonthlyDto_New.setWorkmonth(workmonth);
		workmonthlyDto_New.setStatus("1");
		workmonthlyDto_New.setWorkdailyDtoList(workdailyDtoList_New);

		//工数計算
		workTimeCal.calWorkmonthData(workmonthlyDto_New);

		//新規データ作成
		workmonthlyService.makeNewWorkMonthDayById(workmonthlyDto_New);

		return viewBuilder(workdailyForm, model, map);
	}

	//印刷ボタン
    @RequestMapping(value = "", params = "print", method = RequestMethod.POST)
    public String print(HttpServletResponse response,WorkdailyForm workdailyForm, Model model,ModelMap map) throws IOException {

    	response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");

    	//セッションから取得
    	WorkmonthlyDto workmonthlyDto = (WorkmonthlyDto) map.get("workmonthlyDto");

		String workyear = (String) map.get("workyear");
        String workmonth = (String) map.get("workmonth");
        String username = (String)map.get("employeename");

        workmonthlyDto.setUsername(username);
		String outputfileNm = workyear+"-"+workmonth+"勤务表";
		workmonth = String.valueOf(Integer.valueOf(workmonth));

		Workbook workbook = null;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook = workTimePrint.print(workmonthlyDto);

			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(content);

			response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(outputfileNm+".xls", "UTF-8")+"\"");

			ServletOutputStream out = response.getOutputStream();

			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;

			while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
				bufferedOutputStream.write(buff, 0, bytesRead);
			}

		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
		} catch (EncryptedDocumentException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if (bufferedInputStream != null)
				bufferedInputStream.close();
			if (bufferedOutputStream != null)
				bufferedOutputStream.close();
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

	@ModelAttribute("hourList")
	public Map<String, String> getHourList() {
		return mapList.getHourList();
	}

	@ModelAttribute("miniteList")
	public Map<String, String> getMiniteList() {
		return mapList.getMiniteList();
	}


}
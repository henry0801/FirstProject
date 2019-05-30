package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import com.common.WorkTimeCal;
import com.dto.WorkmonthDto;
import com.form.WorkStatusForm;
import com.form.WorkmonthForm;
import com.service.WorkmonthService;

@Controller
@RequestMapping("/workInput")
@SessionAttributes({"workmonths", "name", "year", "month"})
public class WorkmonthController {

	Logger logger = LoggerFactory.getLogger(WorkmonthController.class);

	@Autowired
    private WorkmonthService workmonthService;

	//他の画面でリンク経由
	@RequestMapping(value = "link", method = RequestMethod.GET)
	public String link(HttpServletRequest request, WorkmonthForm workmonthForm, Model model, ModelMap map) {

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		workmonthForm.setId(id);

		map.put("name", name);
		return viewBuilder(workmonthForm, model, map);
	}

	//初期表示
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String show(@ModelAttribute Model model,ModelMap map) {

        return viewBuilder(new WorkmonthForm(), model, map);
    }

    //戻るボタン
    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute WorkStatusForm workStatusForm, Model model,ModelMap map) {
        String year = (String) map.get("year");
        String month = (String) map.get("month");

        workStatusForm.setWorkYear(year);
        workStatusForm.setWorkMonth(month);

    	return "workStatus";
    }

    //検索ボタン
	@RequestMapping(value = "", params = "serach", method = RequestMethod.POST)
	public String viewBuilder(@ModelAttribute WorkmonthForm workmonthForm, Model model,ModelMap map) {

		String workYear = workmonthForm.getWorkYear();
		String workMonth =workmonthForm.getWorkMonth();
		if (workYear == null || workMonth == null) {
			workYear = (String) map.get("year");
	        workMonth = (String) map.get("month");
	        workmonthForm.setWorkYear(workYear);
	    	workmonthForm.setWorkMonth(workMonth);
		}

		String id = workmonthForm.getId();

		List<WorkmonthDto> workmonths = workmonthService.getWorkmonthById(id,workYear,workMonth);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");


		if (workmonths!=null && workmonths.size()>0) {

			String[] start_h = new String[workmonths.size()];
			String[] start_m = new String[workmonths.size()];
			String[] end_h = new String[workmonths.size()];
			String[] end_m = new String[workmonths.size()];

			double totalworkHours = 0.00;
			double addOverHours = 0.00;

			for (int i = 0; i < workmonths.size(); i++) {
				WorkmonthDto dto = workmonths.get(i);
				String year = dto.getYear();
				String month = dto.getMonth();
				String day = dto.getDay();

				try {
					Date date = fmt.parse(year+month+day);
					dto.setDate(date);

					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
						dto.setWeekendflg("1");
			        }else {
			        	dto.setWeekendflg("0");
					}

				} catch (ParseException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				start_h[i] = dto.getStart_h();
				start_m[i] = dto.getStart_m();
				end_h[i] = dto.getEnd_h();
				end_m[i] = dto.getEnd_m();


				//工数計算
				WorkTimeCal workTimeCal = new WorkTimeCal();
				double workHours = workTimeCal.calWorkTime(dto.getStart_h()+":"+dto.getStart_m(), dto.getEnd_h()+":"+dto.getEnd_m());
				double overHours = workTimeCal.calOverTime(dto.getStart_h()+":"+dto.getStart_m(), dto.getEnd_h()+":"+dto.getEnd_m());

				dto.setWorkHours(String.format("%.2f", workHours));
				dto.setOverHours(String.format("%.2f", overHours));

				addOverHours = addOverHours + overHours;
				totalworkHours = totalworkHours + workHours;
				dto.setAddOverHours(String.format("%.2f", addOverHours));

			}

			workmonthForm.setStart_h(start_h);
			workmonthForm.setStart_m(start_m);
			workmonthForm.setEnd_h(end_h);
			workmonthForm.setEnd_m(end_m);

			workmonthForm.setTotalWorkHours(String.format("%.2f", totalworkHours));
			workmonthForm.setTotalOverHours(String.format("%.2f", addOverHours));
		}


		map.put("year", workYear);
		map.put("month", workMonth);
		map.put("workmonths", workmonths);

        model.addAttribute(workmonthForm);
        model.addAttribute("name",map.get("name"));
        model.addAttribute("workmonths", workmonths);

		return "workmonth";
	}

	//保存ボタン
	@RequestMapping(value = "", params = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute WorkmonthForm form, Model model,ModelMap map) {

		List<WorkmonthDto> workmonths_before = (List<WorkmonthDto>) map.get("workmonths");

		List<WorkmonthDto> workmonths_after = new ArrayList<>();

		List<WorkmonthDto> workmonths_u= new ArrayList<>();

		String[] userid = form.getUserid();
		String[] start_h = form.getStart_h();
		String[] start_m = form.getStart_m();
		String[] end_h = form.getEnd_h();
		String[] end_m = form.getEnd_m();
		String[] year = form.getYear();
		String[] month = form.getMonth();
		String[] day = form.getDay();
		String[] biko1 = form.getBiko1();
		String[] biko2 = form.getBiko2();

		for (int i = 0; i < userid.length; i++) {

			WorkmonthDto workmonthDto = new WorkmonthDto();
			workmonthDto.setUserid(userid[i]);
			workmonthDto.setYear(year[i]);
			workmonthDto.setMonth(month[i]);
			workmonthDto.setDay(day[i]);
			workmonthDto.setStart_h(start_h[i]);
			workmonthDto.setStart_m(start_m[i]);
			workmonthDto.setEnd_h(end_h[i]);
			workmonthDto.setEnd_m(end_m[i]);
			workmonthDto.setBiko1(biko1[i]);
			workmonthDto.setBiko2(biko2[i]);

			workmonths_after.add(workmonthDto);
		}


		//セッション比較
		if (workmonths_before !=null && workmonths_before.size()>0) {
			for (int i = 0; i < workmonths_before.size(); i++) {
				WorkmonthDto workmonth_before = workmonths_before.get(i);

				for (int j = 0; j < workmonths_after.size(); j++) {
					WorkmonthDto workmonth_after = workmonths_after.get(j);

					if (workmonth_before.toKeyString().equals(workmonth_after.toKeyString())) {
						if (!workmonth_before.toString().equals(workmonth_after.toString())) {
							workmonths_u.add(workmonth_after);
						}
					}
				}


			}
		}

		workmonthService.updateWorkmonthById(workmonths_u);


		return viewBuilder(form, model, map);
	}

	//新規作成ボタン
	@RequestMapping(value = "", params = "recreate", method = RequestMethod.POST)
	public String recreate(@ModelAttribute WorkmonthForm workmonthForm, Model model,ModelMap map) {

		String workYear = workmonthForm.getWorkYear();
		String workMonth =workmonthForm.getWorkMonth();
		if (workYear == null || workMonth == null) {
			workYear = (String) map.get("year");
	        workMonth = (String) map.get("month");
	        workmonthForm.setWorkYear(workYear);
	    	workmonthForm.setWorkMonth(workMonth);
		}

		String id = workmonthForm.getId();

		List<WorkmonthDto> workmonths = (List<WorkmonthDto>) map.get("workmonths");

		if (workmonths == null) {
			workmonths = workmonthService.getWorkmonthById(id,workYear,workMonth);
		}

		//既存のデータがあれば、削除する
		if (workmonths != null && workmonths.size()>0) {
			workmonthService.deleteWorkmonthById(id,workYear,workMonth);
		}

		int year = Integer.parseInt(workYear);
	    int month = Integer.parseInt(workMonth);
	    int day = 1;

		Calendar cal = Calendar.getInstance();

		cal.set(year, month-1, 1);

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<WorkmonthDto> workmonths_new = new ArrayList<>();

		for (int i = 0; i < lastDay; i++) {
			WorkmonthDto workmonthDto = new WorkmonthDto();

			workmonthDto.setUserid(id);
			workmonthDto.setYear(String.valueOf(year));
			workmonthDto.setMonth(String.format("%02d", month));
			workmonthDto.setDay(String.format("%02d", day));

			workmonthDto.setBiko1("");
			workmonthDto.setBiko2("");

			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				workmonthDto.setStart_h("");
				workmonthDto.setStart_m("");
				workmonthDto.setEnd_h("");
				workmonthDto.setEnd_m("");
			}else {
				workmonthDto.setStart_h("09");
				workmonthDto.setStart_m("00");
				workmonthDto.setEnd_h("18");
				workmonthDto.setEnd_m("00");
			}

			workmonths_new.add(workmonthDto);

			day++;
			cal.add(Calendar.DATE, 1);
		}

		workmonthService.insertWorkmonthById(workmonths_new);

		return viewBuilder(workmonthForm, model, map);
	}

	//印刷ボタン
    @RequestMapping(value = "", params = "print", method = RequestMethod.POST)
    public String print(HttpServletResponse response,WorkmonthForm workmonthForm, Model model,ModelMap map) throws IOException {

    	response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");


		String year = (String) map.get("year");
        String month = (String) map.get("month");

		String sheetNm = "協力会社社員勤休表";
		String outputfileNm = year+"-"+month+"勤务表";
		month = String.valueOf(Integer.valueOf(month));

		Workbook workbook = null;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			String filePath = Thread.currentThread().getContextClassLoader().getResource("work.xls").getFile();

//			String filePath = getClass().getResource("/work.xls").getPath();
//			filePath = filePath.substring(1);
			FileInputStream fileInputStream = new FileInputStream(new File(filePath));

			workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheet(sheetNm);


			List<WorkmonthDto> workmonths = (List<WorkmonthDto>) map.get("workmonths");
			if (workmonths !=null && workmonths.size()>0) {
				for (int i = 0; i < workmonths.size(); i++) {
					WorkmonthDto workmonth = workmonths.get(i);

					Cell cell_start = sheet.getRow(4+i).getCell(2);
					Cell cell_end = sheet.getRow(4+i).getCell(3);

					Cell cell_workHours = sheet.getRow(4+i).getCell(4);
					Cell cell_overHours = sheet.getRow(4+i).getCell(5);
					Cell cell_addOverHours = sheet.getRow(4+i).getCell(6);

					Cell cell_biko1 = sheet.getRow(4+i).getCell(7);
					Cell cell_biko2 = sheet.getRow(4+i).getCell(8);

					String start = workmonth.getStart_h()+":"+workmonth.getStart_m();
					String end = workmonth.getEnd_h()+":"+workmonth.getEnd_m();

					if (!":".equals(start)) {
						double time = DateUtil.convertTime(start);
						cell_start.setCellValue(time);
					}

					if (!":".equals(end)) {
						double time = DateUtil.convertTime(end);
						cell_end.setCellValue(time);
					}

					cell_workHours.setCellValue(workmonth.getWorkHours());
					cell_overHours.setCellValue(workmonth.getOverHours());
					cell_addOverHours.setCellValue(workmonth.getAddOverHours());
					cell_biko1.setCellValue(workmonth.getBiko1());
					cell_biko1.setCellValue(workmonth.getBiko2());


				}

				sheet.getRow(1).getCell(0).setCellValue(year+"年");
				sheet.getRow(1).getCell(2).setCellValue(month+"月");
				sheet.getRow(3).getCell(0).setCellValue(month+"月");
				sheet.getRow(2).getCell(7).setCellValue("作業者氏名　："+map.get("name"));

				sheet.getRow(35).getCell(4).setCellValue(workmonthForm.getTotalWorkHours());
				sheet.getRow(35).getCell(8).setCellValue(workmonthForm.getTotalOverHours());
			}



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
		} catch (InvalidFormatException e) {
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


	@ModelAttribute("hourList")
	public Map<String, String> getHourList() {
		Map<String, String> hourList = new TreeMap<String, String>();
		hourList.put("", "");
		hourList.put("00", "00");
		hourList.put("01", "01");
		hourList.put("02", "02");
		hourList.put("03", "03");
		hourList.put("04", "04");
		hourList.put("05", "05");
		hourList.put("06", "06");
		hourList.put("07", "07");
		hourList.put("08", "08");
		hourList.put("09", "09");
		hourList.put("10", "10");
		hourList.put("11", "11");
		hourList.put("12", "12");
		hourList.put("13", "13");
		hourList.put("14", "14");
		hourList.put("15", "15");
		hourList.put("16", "16");
		hourList.put("17", "17");
		hourList.put("18", "18");
		hourList.put("19", "19");
		hourList.put("21", "21");
		hourList.put("22", "22");
		hourList.put("23", "23");
		hourList.put("24", "24");
		return hourList;
	}

	@ModelAttribute("miniteList")
	public Map<String, String> getMiniteList() {
		Map<String, String> miniteList = new TreeMap<String, String>();
		miniteList.put("", "");
		miniteList.put("00", "00");
		miniteList.put("15", "15");
		miniteList.put("30", "30");
		miniteList.put("45", "45");

		return miniteList;
	}




}
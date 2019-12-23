package com.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;

public class WorkTimeCal {

	Logger logger = LoggerFactory.getLogger(WorkTimeCal.class);

	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	final long min_15 = 15 * 60 * 1000;
	String[] restStr = {"12:15","12:30","12:45","13:00","18:00"};

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

	public double calWorkTime(String start, String end) {
		double workHours = calTimePassing(start, end);

		return workHours;
	}

	public double calOverTime(String start, String end) {
		double overHours = calTimePassing("18:00", end);

		return overHours;
	}

	public double calTimePassing(String start, String end) {

		double worktime_double = 0.00;

		try {
			long worktime_long = 0;

			long time1 = format.parse(start).getTime();
			long time2 = format.parse(end).getTime();

			while (time1 < time2) {
				time1 += min_15;

				if (ifNotContain(restStr, time1)) {
					worktime_long += min_15;
				}
			}

			worktime_double = longToDouble(worktime_long);

		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return worktime_double;
	}

	public boolean ifNotContain(String[] arr, long targetValue) throws ParseException {
		for (String str : arr) {
			long l = format.parse(str).getTime();
			if (l == targetValue)
				return false;
		}
		return true;
	}

	public double longToDouble(long longValue) {
		double worktime_double = longValue % (24 * 3600 * 1000);

		return worktime_double / (3600 * 1000);
	}

	//工数計算
	public void calWorkmonthData(WorkmonthlyDto workmonthlyDto) {

		List<WorkdailyDto> workdailyDtoList = workmonthlyDto.getWorkdailyDtoList();

		if (workdailyDtoList!=null && workdailyDtoList.size()>0) {

			double totalworkHours = 0.00;
			double addOverHours = 0.00;

			for (int i = 0; i < workdailyDtoList.size(); i++) {
				WorkdailyDto workdailyDto = workdailyDtoList.get(i);

				//工数計算
				double workHours = calWorkTime(workdailyDto.getStart_h()+":"+workdailyDto.getStart_m(), workdailyDto.getEnd_h()+":"+workdailyDto.getEnd_m());
				double overHours = calOverTime(workdailyDto.getStart_h()+":"+workdailyDto.getStart_m(), workdailyDto.getEnd_h()+":"+workdailyDto.getEnd_m());

				workdailyDto.setWorkHours(String.format("%.2f", workHours));
				workdailyDto.setOverHours(String.format("%.2f", overHours));

				totalworkHours = totalworkHours + workHours;
				addOverHours = addOverHours + overHours;
				workdailyDto.setAddOverHours(String.format("%.2f", addOverHours));

				workdailyDto.setWorkhoursday(new BigDecimal(workHours));
				workdailyDto.setOverhoursday(new BigDecimal(overHours));
				workdailyDto.setAddoverhoursday(new BigDecimal(addOverHours));

			}

			workmonthlyDto.setWorkhoursmonth(new BigDecimal(totalworkHours));
			workmonthlyDto.setOverhoursmonth(new BigDecimal(addOverHours));
		}
	}

	//新規WorkdailyDtoリスト作成
	public List<WorkdailyDto> setNewWorkmonthData(String userid,String workyear,String workmonth) {

		int workyearInt = Integer.parseInt(workyear);
	    int workmonthInt = Integer.parseInt(workmonth);
	    int day = 1;

		Calendar cal = Calendar.getInstance();
		cal.set(workyearInt, workmonthInt-1, 1);

		List<WorkdailyDto> workdailyDtoList = new ArrayList<>();

		//Holidayリソース取得
		Properties holidayProperties = null;
		try {
			holidayProperties = new CrunchifyGetPropertyValues().getPropValue();
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.debug("IOException: "+e1.getMessage());
		}

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 0; i < lastDay; i++) {
			WorkdailyDto workdailyDto = new WorkdailyDto();
			workdailyDto.setUserid(userid);

			String workyearStr = String.valueOf(workyearInt);
			String workmonthStr = String.format("%02d", workmonthInt);
			String workdayStr = String.format("%02d", day);

			String dateStr = workyearStr+workmonthStr+workdayStr;

			workdailyDto.setWorkyear(workyearStr);
			workdailyDto.setWorkmonth(workmonthStr);
			workdailyDto.setWorkday(workdayStr);

			String biko1 = "";
			String biko2 = "";

			boolean holidayFlg = holidayProperties != null &&  holidayProperties.get(dateStr)!=null;
			boolean weekendFlg = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			workdailyDto.setWeekendflag(String.valueOf(weekendFlg ? 1 : 0));
			workdailyDto.setHolidayflag(String.valueOf(holidayFlg ? 1 : 0));

			//出勤時間設定
			if(holidayFlg || weekendFlg){
				workdailyDto.setStart_h("");
				workdailyDto.setStart_m("");
				workdailyDto.setEnd_h("");
				workdailyDto.setEnd_m("");
			}else {
				workdailyDto.setStart_h("09");
				workdailyDto.setStart_m("00");
				workdailyDto.setEnd_h("18");
				workdailyDto.setEnd_m("00");
			}

			if (holidayFlg) {
				biko2 = holidayProperties.get(dateStr).toString();
			}

			workdailyDto.setBiko1(biko1);
			workdailyDto.setBiko2(biko2);

			workdailyDtoList.add(workdailyDto);

			day++;
			cal.add(Calendar.DATE, 1);
		}
		return workdailyDtoList;
	}

}

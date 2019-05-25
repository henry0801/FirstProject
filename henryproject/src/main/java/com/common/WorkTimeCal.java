package com.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkTimeCal {

	Logger logger = LoggerFactory.getLogger(WorkTimeCal.class);



	public double calWorkTime(String start,String end) {

		double workHours_am = suboftime(start,"12:00");
        double workHours_pm = suboftime("13:00",end);

        if(!"".equals(end) && suboftime("17:45",end)>0){
        	workHours_pm = workHours_pm - 0.25;
        }

        double workHours = workHours_am+workHours_pm;

		return workHours;

	}

	public double calOverTime(String start,String end) {

		double overHours = suboftime("18:00", end);

		return overHours;

	}

	//time2-time1
	public double suboftime(String start,String end) {
		//工数計算
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		double workhours = 0.00;
		try {

			Date d1 = timeformat.parse("1990/01/01 "+start);
			Date d2 = timeformat.parse("1990/01/01 "+end);

		    workhours=(d2.getTime() - d1.getTime())%(24*3600*1000);
		    workhours = workhours/(3600*1000);

		    if (workhours<0) {
		    	workhours = 0.00;
			}

		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return workhours;
	}

}

package com.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkTimeCal {

	Logger logger = LoggerFactory.getLogger(WorkTimeCal.class);

	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	final long min_15 = 15 * 60 * 1000;
	String[] restStr = {"12:15","12:30","12:45","13:00","18:00"};

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

}

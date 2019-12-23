package com.common;

import java.util.Map;
import java.util.TreeMap;

public class MapList {

	public Map<String, String> getYearList() {

		Map<String, String> yearList = new TreeMap<String, String>();
		yearList.put("", "");
		yearList.put("2018", "2018");
		yearList.put("2019", "2019");
		yearList.put("2020", "2020");
		return yearList;
	}

	public Map<String, String> getMonthList() {
		Map<String, String> monthList = new TreeMap<String, String>();
		monthList.put("", "");
		monthList.put("01", "01");
		monthList.put("02", "02");
		monthList.put("03", "03");
		monthList.put("04", "04");
		monthList.put("05", "05");
		monthList.put("06", "06");
		monthList.put("07", "07");
		monthList.put("08", "08");
		monthList.put("09", "09");
		monthList.put("10", "10");
		monthList.put("11", "11");
		monthList.put("12", "12");
		return monthList;
	}

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
		hourList.put("20", "20");
		hourList.put("21", "21");
		hourList.put("22", "22");
		hourList.put("23", "23");
		hourList.put("24", "24");
		return hourList;
	}

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

package com.dto;

import java.io.Serializable;
import java.util.Date;

public class WorkmonthDto implements Serializable{

	private String userid;
	private String year;
    private String month;
    private String day;
    private String start_h;
    private String start_m;
    private String end_h;
    private String end_m;
    private String biko1;
    private String biko2;
    private Date creation_time;
    private Date modification_time;

    private Date date;

    private String weekendflg;

    private String workHours;
    private String overHours;
    private String addOverHours;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStart_h() {
		return start_h;
	}
	public void setStart_h(String start_h) {
		this.start_h = start_h;
	}
	public String getStart_m() {
		return start_m;
	}
	public void setStart_m(String start_m) {
		this.start_m = start_m;
	}
	public String getEnd_h() {
		return end_h;
	}
	public void setEnd_h(String end_h) {
		this.end_h = end_h;
	}
	public String getEnd_m() {
		return end_m;
	}
	public void setEnd_m(String end_m) {
		this.end_m = end_m;
	}
	public String getBiko1() {
		return biko1;
	}
	public void setBiko1(String biko1) {
		this.biko1 = biko1;
	}
	public String getBiko2() {
		return biko2;
	}
	public void setBiko2(String biko2) {
		this.biko2 = biko2;
	}
	public Date getCreation_time() {
		return creation_time;
	}
	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}
	public Date getModification_time() {
		return modification_time;
	}
	public void setModification_time(Date modification_time) {
		this.modification_time = modification_time;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWeekendflg() {
		return weekendflg;
	}
	public void setWeekendflg(String weekendflg) {
		this.weekendflg = weekendflg;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	public String getOverHours() {
		return overHours;
	}
	public void setOverHours(String overHours) {
		this.overHours = overHours;
	}
	public String getAddOverHours() {
		return addOverHours;
	}
	public void setAddOverHours(String addOverHours) {
		this.addOverHours = addOverHours;
	}
	public String toKeyString() {
		return "WorkmonthDto [userid=" + userid + ", year=" + year + ", month=" + month + ", day=" + day + "]";
	}

	public String toString() {
		return "WorkmonthDto [userid=" + userid + ", year=" + year + ", month=" + month + ", day=" + day + ", start_h=" + start_h + ", start_m=" + start_m + ", end_h=" + end_h + ", end_m=" + end_m
				+ ", biko1=" + biko1 + ", biko2=" + biko2 + "]";
	}




}

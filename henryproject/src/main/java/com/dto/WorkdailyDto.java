package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorkdailyDto implements Serializable{

	private String userid;
	private String workyear;
    private String workmonth;
    private String workday;
    private String start_h;
    private String start_m;
    private String end_h;
    private String end_m;
    private String biko1;
    private String biko2;
    private Date creation_time;
    private Date modification_time;

    private Date date;

    private String weekendflag;
    private String holidayflag;
	private String workHours;
    private String overHours;
    private String addOverHours;

    private BigDecimal workhoursday;
    private BigDecimal overhoursday;
    private BigDecimal addoverhoursday;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public String getWorkmonth() {
		return workmonth;
	}

	public void setWorkmonth(String workmonth) {
		this.workmonth = workmonth;
	}

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
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

	public String getWeekendflag() {
		return weekendflag;
	}

	public void setWeekendflag(String weekendflag) {
		this.weekendflag = weekendflag;
	}

	public String getHolidayflag() {
		return holidayflag;
	}

	public void setHolidayflag(String holidayflag) {
		this.holidayflag = holidayflag;
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


	public BigDecimal getWorkhoursday() {
		return workhoursday;
	}

	public void setWorkhoursday(BigDecimal workhoursday) {
		this.workhoursday = workhoursday;
	}

	public BigDecimal getOverhoursday() {
		return overhoursday;
	}

	public void setOverhoursday(BigDecimal overhoursday) {
		this.overhoursday = overhoursday;
	}

	public BigDecimal getAddoverhoursday() {
		return addoverhoursday;
	}

	public void setAddoverhoursday(BigDecimal addoverhoursday) {
		this.addoverhoursday = addoverhoursday;
	}

	public String toKeyString() {
		return "WorkdailyDto [userid=" + userid + ", workyear=" + workyear + ", workmonth=" + workmonth + ", workday=" + workday + "]";
	}

	public String toString() {
		return "WorkdailyDto [userid=" + userid + ", workyear=" + workyear + ", workmonth=" + workmonth + ", workday=" + workday + ", start_h=" + start_h + ", start_m=" + start_m + ", end_h=" + end_h + ", end_m=" + end_m
				+ ", biko1=" + biko1 + ", biko2=" + biko2 + "]";
	}




}

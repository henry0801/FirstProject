package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WorkmonthlyDto implements Serializable{

	private String userid;
	private String workyear;
    private String workmonth;
    private String status;
    private String deleteflag;
    private BigDecimal workhoursmonth;
    private BigDecimal overhoursmonth;
    private Date creation_time;
    private Date modification_time;

    private String username;

    private List<WorkdailyDto> workdailyDtoList;

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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public BigDecimal getWorkhoursmonth() {
		return workhoursmonth;
	}
	public void setWorkhoursmonth(BigDecimal workhoursmonth) {
		this.workhoursmonth = workhoursmonth;
	}
	public BigDecimal getOverhoursmonth() {
		return overhoursmonth;
	}
	public void setOverhoursmonth(BigDecimal overhoursmonth) {
		this.overhoursmonth = overhoursmonth;
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
	public List<WorkdailyDto> getWorkdailyDtoList() {
		return workdailyDtoList;
	}
	public void setWorkdailyDtoList(List<WorkdailyDto> workdailyDtoList) {
		this.workdailyDtoList = workdailyDtoList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


}

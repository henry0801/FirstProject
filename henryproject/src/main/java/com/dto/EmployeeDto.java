package com.dto;

import java.io.Serializable;

public class EmployeeDto implements Serializable{

    private String userid;
    private String username;
    private String password;
    private String biko;
    private String genba;
    private String place;
    private String status;

    private WorkmonthlyDto workmonthlyDto;

    private int countOfWorkInput;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCountOfWorkInput() {
		return countOfWorkInput;
	}
	public void setCountOfWorkInput(int countOfWorkInput) {
		this.countOfWorkInput = countOfWorkInput;
	}
	public String getBiko() {
		return biko;
	}
	public void setBiko(String biko) {
		this.biko = biko;
	}
	public String getGenba() {
		return genba;
	}
	public void setGenba(String genba) {
		this.genba = genba;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@Override
	public String toString() {
		return "EmployeeDto [userid=" + userid + ", username=" + username + ", biko=" + biko + ", genba=" + genba + ", place=" + place + "]";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public WorkmonthlyDto getWorkmonthlyDto() {
		return workmonthlyDto;
	}
	public void setWorkmonthlyDto(WorkmonthlyDto workmonthlyDto) {
		this.workmonthlyDto = workmonthlyDto;
	}




}

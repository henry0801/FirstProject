package com.dto;

import java.io.Serializable;

public class EmployeeDto implements Serializable{

    private String userid;
    private String username;
    private String password;

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

}

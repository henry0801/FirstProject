package com.entity;

import java.util.Date;

public class Statistic {

	private String id;
	private String name;
    private String status;
    private String biko;
    private Date creation_time;
    private Date modification_time;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBiko() {
		return biko;
	}
	public void setBiko(String biko) {
		this.biko = biko;
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



}

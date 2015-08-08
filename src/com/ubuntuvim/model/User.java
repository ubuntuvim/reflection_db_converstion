package com.ubuntuvim.model;

import java.util.Date;


public class User {
	
	private int id;
	private String username;
	private Date birth;
	private Date detail_time;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getDetail_time() {
		return detail_time;
	}
	public void setDetail_time(Date detail_time) {
		this.detail_time = detail_time;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", birth=" + birth
				+ ", detail_time=" + detail_time + "]";
	}
}

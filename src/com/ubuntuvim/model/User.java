package com.ubuntuvim.model;

import java.sql.Date;


public class User {
	
	private int id;
	private String username;
	private Date brith;
	private Date dateil_time;
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
	public Date getBrith() {
		return brith;
	}
	public void setBrith(Date brith) {
		this.brith = brith;
	}
	public Date getDateil_time() {
		return dateil_time;
	}
	public void setDateil_time(Date dateil_time) {
		this.dateil_time = dateil_time;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", brith=" + brith
				+ ", dateil_time=" + dateil_time + "]";
	}
}

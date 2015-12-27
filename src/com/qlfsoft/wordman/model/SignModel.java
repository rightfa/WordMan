package com.qlfsoft.wordman.model;

import java.util.Date;

import org.litepal.crud.DataSupport;

public class SignModel extends DataSupport {

	private int id;
	private String account;
	private Date day;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

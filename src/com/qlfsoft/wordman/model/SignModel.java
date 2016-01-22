package com.qlfsoft.wordman.model;

import java.util.Date;

import org.litepal.crud.DataSupport;

/**
 * ´ò¿¨ÐÅÏ¢
 * @author qlf
 *
 */
public class SignModel extends DataSupport {

	private String account;
	private String day;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}	
}

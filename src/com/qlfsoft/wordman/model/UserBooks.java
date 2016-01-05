package com.qlfsoft.wordman.model;

import org.litepal.crud.DataSupport;

public class UserBooks extends DataSupport{
	private String account;//账号
	private int bookId;//单词本序号
	private boolean inUser;//是否在使用中
	private int haveStudy;//已经学习的单词数
	private int totalDay;//计划学习的天数
	private int remainDay;//剩余学习的天数
	private int dailyword;//每日学习单词数
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public boolean isInUser() {
		return inUser;
	}
	public void setInUser(boolean inUser) {
		this.inUser = inUser;
	}
	public int getHaveStudy() {
		return haveStudy;
	}
	public void setHaveStudy(int haveStudy) {
		this.haveStudy = haveStudy;
	}
	public int getTotalDay() {
		return totalDay;
	}
	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}
	public int getRemainDay() {
		return remainDay;
	}
	public void setRemainDay(int remainDay) {
		this.remainDay = remainDay;
	}
	public int getDailyword() {
		return dailyword;
	}
	public void setDailyword(int dailyword) {
		this.dailyword = dailyword;
	}
}

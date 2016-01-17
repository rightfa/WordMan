package com.qlfsoft.wordman.model;

import java.util.Date;

import org.litepal.crud.DataSupport;

/**
 * @author hyn
 *
 */
public class UserWords extends DataSupport{
	private int bookId;//单词本序号
	private int wordId;//单词序号
	private int orderNo;//单词排序
	private String account;//账号
	private Date date;//首次学习日期
	private int repeat;//重复次数
	private Date upateDate;//更新日期
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRepeat() {
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Date getUpateDate() {
		return upateDate;
	}
	public void setUpateDate(Date upateDate) {
		this.upateDate = upateDate;
	}
	
	
}

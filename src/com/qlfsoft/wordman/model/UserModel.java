package com.qlfsoft.wordman.model;

import org.litepal.crud.DataSupport;
/**
 * 用户信息
 * @author hyn
 *
 */
public class UserModel extends DataSupport {
	private String nickname;//昵称
	private String account;//账号
	private String password;//密码
	private String avatar;//头像
	private String significances;//签名
	private int loginState;//登录状态,1为登录，0为登出
	private int selBook;//选择的单词本
	private int haveStudy;//已经学习的单词数
	private int totalDay;//计划学习的天数
	private int remainDay;//剩余学习的天数
	private int dailyword;//每日学习单词数
	private int wordSize;//单词本的单词量
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getSelBook() {
		return selBook;
	}
	public void setSelBook(int selBook) {
		this.selBook = selBook;
	}
	public String getSignificances() {
		return significances;
	}
	public void setSignificances(String significances) {
		this.significances = significances;
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
	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}
	public int getWordSize() {
		return wordSize;
	}
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}
	public int getLoginState() {
		return loginState;
	}
	
}

package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	private Context mContext;
	private SharedPreferences sp;
	private String userBookId = "CURRENT_BOOK_ID";
	private String firstopen = "FIRSTOPEN";//第一次打开应用
	private String userAccount = "USERACCOUNT";//用户账号
	private String loginState = "LOGINSTATE";//时候登录状态
	private String nickname = "NICKNAME";//昵称
	private String significance = "SIGNIFICANCE";//签名
	private String password = "PASSWORD";//密码
	private String defaultImage = "DEFAULTIMAGE";//是否默认头像
	private String haveStudy = "HAVESTUDY";//已经学习的单词数
	private String totalDay = "TOTALDAY";//计划学习的天数
	private String remainDay = "REMAINDAY";//剩余学习的天数
	private String dailyWord = "DAILYWORD";//每日学习的单词数
	
	private String temp_nickname = "TEMP_NICKNAME";
	private String temp_account = "TEMP_ACCOUNT";
	private String temp_significance = "TEMP_SIGNIFICANCE";
	private String temp_orginPwd = "TEMP_ORGINPWD";
	private String temp_newPwd = "TEMP_NEWPWD";
	private String temp_rePwd = "TEMP_REPWD";
	
	private SharePreferenceUtils()
	{
		mContext = BaseApplication.sContext;
		sp = mContext.getSharedPreferences("com.qlfsoft.wordman", Context.MODE_PRIVATE);
	}
	
	private static SharePreferenceUtils sharedPreferenceUtil;
	
	public synchronized static SharePreferenceUtils getInstnace()
	{
		if(null == sharedPreferenceUtil)
			sharedPreferenceUtil = new SharePreferenceUtils();
		return sharedPreferenceUtil;
	}
	
	/**
	 * 获取已经学习过的单词数
	 * @return
	 */
	public int getHaveStudy()
	{
		return sp.getInt(haveStudy, 0);
	}
	
	/**
	 * 设置已经学习过的单词数
	 * @param value
	 */
	public void setHaveStudy(int value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(haveStudy, value);
		ed.commit();
	}
	
	/**
	 * 获取计划学习的天数
	 * @return
	 */
	public int getTotalDay()
	{
		return sp.getInt(totalDay, 0);
	}
	
	/**
	 * 设置计划学习的天数
	 * @param value
	 */
	public void setTotalDay(int value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(totalDay, value);
		ed.commit();
	}
	
	/**
	 * 获取剩余学习的天数
	 * @return
	 */
	public int getRemainDay()
	{
		return sp.getInt(remainDay, 0);
	}
	
	/**
	 * 设置剩余学习的天数
	 * @param value
	 */
	public void setRemainDay(int value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(remainDay, value);
		ed.commit();
	}
	
	/**
	 * 获取每日学习的单词数
	 * @return
	 */
	public int getDailyWord()
	{
		return sp.getInt(dailyWord, 0);
	}
	
	/**
	 * 设置每日学习的单词数
	 * @param value
	 */
	public void setDailyWord(int value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(dailyWord, value);
		ed.commit();
	}
	
	/**
	 * 当前用户是否已经选择了背诵单词本
	 * @return
	 */
	public boolean hasSelBook()
	{
		int bookId = sp.getInt(userBookId, 0);
		return bookId != 0;
	}
	
	public boolean getFirstOpen()
	{
		boolean flag = sp.getBoolean(firstopen, true);
		return flag;
	}
	
	public void setFirstOpen()
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putBoolean(firstopen, false);
		ed.commit();
	}
	
	
	/**
	 * 设置选中的单词本序号
	 * @param bookId
	 */
	public void setBookId(int bookId)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(userBookId, bookId);
		ed.commit();
	}
	
	/**
	 * 获取当前用户正在背诵的单词本序号
	 * @return
	 */
	public int getSelBookId()
	{
		return sp.getInt(userBookId, 0);
	}
	
	public String getUserAccount()
	{
		String account = sp.getString(userAccount, "");
		return account;
	}
	
	public void setUserAccount(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(userAccount, value);
		ed.commit();
	}
	
	/**
	 * 获取登录状态
	 * @return
	 */
	public boolean getLoginState()
	{
		return sp.getBoolean(loginState, true);
	}
	
	/**
	 * 设置登录状态
	 * @param value
	 */
	public void setLoginState(boolean value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putBoolean(loginState, value);
		ed.commit();
	}
	
	/**
	 * 获取昵称
	 * @return
	 */
	public String getNickName()
	{
		return sp.getString(nickname, "单词君");
	}
	
	/**
	 * 设置昵称
	 * @param value
	 */
	public void setNickName(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(nickname,value);
		ed.commit();
	}
	
	/**
	 * 设置签名
	 * @param value
	 */
	public void setSignificance(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(significance,value);
		ed.commit();
	}
	
	public String getSignificance()
	{
		return sp.getString(significance, "你有多努力无需多谈,重要的是你完成了多少计划之中的事");
	}
	
	public String getPassword()
	{
		return sp.getString(password, "");
	}
	
	/**
	 * 设置密码
	 * @param value
	 */
	public void setPassword(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(password,value);
		ed.commit();
	}
	
	/**
	 * 设置是否默认头像
	 * @param value
	 */
	public void setAvatarImage(boolean value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putBoolean(defaultImage, value);
		ed.commit();
	}
	
	public boolean getAvatarImage()
	{
		return sp.getBoolean(defaultImage, true);
	}
	
	/**
	 * 设置临时用户信息
	 * @param nickname 用户昵称
	 * @param account 用户账号
	 * @param significances 用户签名
	 * @param orginPwd 原密码
	 * @param newPwd 新密码
	 * @param rePwd 确认密码
	 */
	public void setTempUserInfo(String nickname,String account,String significances,String orginPwd,String newPwd,String rePwd)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(temp_nickname, nickname);
		ed.putString(temp_account, account);
		ed.putString(temp_significance, significances);
		ed.putString(temp_orginPwd, orginPwd);
		ed.putString(temp_newPwd,newPwd);
		ed.putString(temp_rePwd, rePwd);
		ed.commit();
	}
	
	public String getTemp_nickname()
	{
		return sp.getString(temp_nickname, "单词君");
	}
	
	public String getTemp_account()
	{
		return sp.getString(temp_account, "");
	}
	
	public String getTemp_significance()
	{
		return sp.getString(temp_significance, "");
	}
	
	public String getTemp_orginPwd()
	{
		return sp.getString(temp_orginPwd, "");
	}
	
	public String getTemp_newPwd()
	{
		return sp.getString(temp_newPwd, "");
	}
	
	public String getTemp_rePwd()
	{
		return sp.getString(temp_rePwd, "");
	}
	
}

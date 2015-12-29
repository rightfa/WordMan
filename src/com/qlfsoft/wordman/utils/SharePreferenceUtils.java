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
	
	public SharePreferenceUtils()
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
	
}

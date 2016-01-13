package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	private Context mContext;
	private SharedPreferences sp;
	private String firstopen = "FIRSTOPEN";//第一次打开应用
	private String defaultImage = "DEFAULTIMAGE";//是否默认头像
	
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

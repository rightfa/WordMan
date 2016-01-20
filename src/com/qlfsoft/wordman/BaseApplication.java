package com.qlfsoft.wordman;

import org.litepal.LitePalApplication;

import android.content.Context;
import android.view.WindowManager;

public class BaseApplication extends LitePalApplication {

	public static Context sContext;
	public static String book_db_Path;
	public static String dic_db_Path;
	public static String dictionary_db_Path;
	public static int screenWidth;
	public static int screenHeight;
	/**
	 * 当前选择的单词本的序号
	 */
	public static int curBookId;
	/**
	 * 用户账号
	 */
	public static String userAccount;
	/**
	 * 昵称
	 */
	public static String nickName;
	/**
	 * 签名
	 */
	public static String significance;
	/**
	 * 已学习的单词数
	 */
	public static int haveStudy;
	/**
	 * 单词本词汇量
	 */
	public static int wordSize;
	/**
	 * 计划学习的天数
	 */
	public static int totalDay;
	/**
	 * 剩余学习的天数
	 */
	public static int remainDay;
	/**
	 * 每日需要学习的单词量
	 */
	public static int dailyWord;
	
	/* 头像名称 */
	public static final String FACEIMAGE_FILE_NAME = "faceImage.jpg";
	@Override
	public void onCreate()
	{
		super.onCreate();
		sContext = getApplicationContext();
		WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
	}
	
	public static Context getContext()
	{
		return sContext;
	}
}

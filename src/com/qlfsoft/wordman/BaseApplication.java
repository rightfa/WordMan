package com.qlfsoft.wordman;

import org.litepal.LitePalApplication;

import android.content.Context;

public class BaseApplication extends LitePalApplication {

	public static Context sContext;
	public static String book_db_Path;
	public static String dic_db_Path;
	public static String dictionary_db_Path;
	@Override
	public void onCreate()
	{
		super.onCreate();
		sContext = getApplicationContext();
	}
	
	public static Context getContext()
	{
		return sContext;
	}
}

package com.qlfsoft.wordman;

import org.litepal.LitePalApplication;

import android.content.Context;

public class BaseApplication extends LitePalApplication {

	public static Context sContext;
	
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

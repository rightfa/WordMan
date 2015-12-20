package com.qlfsoft.wordman.utils;

import android.util.Log;

public class LogUtils {

	private static boolean DEBUG = true;
	public static void Logv(String msg)
	{
		if(DEBUG)
			Log.v("myLog", msg);
	}
	
	public static void Logv(int msg)
	{
		if(DEBUG)
			Log.v("myLog", String.valueOf(msg));
	}
}

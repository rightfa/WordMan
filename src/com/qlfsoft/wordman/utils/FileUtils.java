package com.qlfsoft.wordman.utils;

public class FileUtils {

	public static boolean ExistSDCard()
	{
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			return true;
		}else
			return false;
	}
}

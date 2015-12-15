package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;

public class DictionaryDBHelper {

	private Context mContext;
	public DictionaryDBHelper()
	{
		this.mContext = BaseApplication.getContext();
	}
	
	public boolean CopyDataBase()
	{	
		String basePath = mContext.getExternalFilesDir(null).getAbsolutePath();
		if(null == basePath)
		{
			return false;
		}
		
		
		return true;
	}
	
	 
}

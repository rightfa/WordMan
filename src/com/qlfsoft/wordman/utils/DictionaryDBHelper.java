package com.qlfsoft.wordman.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.os.Environment;

public class DictionaryDBHelper {

	private Context mContext;
	public DictionaryDBHelper()
	{
		this.mContext = BaseApplication.getContext();
	}
	
	public boolean CopyDataBase()
	{	
		String basePath = "";
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable())
		{
			basePath = mContext.getExternalFilesDir(null).getAbsolutePath();
		}else
		{
			basePath = mContext.getFilesDir().getAbsolutePath();
		}
		if("".equals(basePath))
		{
			return false;
		}
		try{
			BaseApplication.book_db_Path = basePath + "/book.db";
			BaseApplication.dic_db_Path = basePath + "/dic.db";
			BaseApplication.dictionary_db_Path = basePath + "/dictionary.db";
			CopyFile("book.db",BaseApplication.book_db_Path);
			CopyFile("dic.db",BaseApplication.dic_db_Path);
			CopyFile("dictionary.db",BaseApplication.dictionary_db_Path);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 复制文件
	 * @param srcFile assert中源文件路径
	 * @param destFile 目标文件路径
	 * @throws IOException
	 */
	private void CopyFile(String srcFile,String destFile) throws IOException
	{
		InputStream is = mContext.getResources().getAssets().open(srcFile);
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(destFile);
		int length;
		while((length = is.read()) !=-1 )
		{
			fos.write(buffer,0,length);
		}
		is.close();
		fos.close();
	}
	 
}

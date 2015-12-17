package com.qlfsoft.wordman.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.model.BookCategory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DictionaryDBHelper {

	private Context mContext;
	public DictionaryDBHelper()
	{
		this.mContext = BaseApplication.getContext();
	}
	
	/**
	 * 从assert文件夹中复制数据库到程序的Data目录下
	 * @return
	 */
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
	
	private SQLiteDatabase getbookDB()
	{	
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.book_db_Path, Context.MODE_PRIVATE,null);
		return db;
	}
	
	private SQLiteDatabase getdicDB()
	{
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.dic_db_Path, Context.MODE_PRIVATE, null);
		return db;
	}
	
	private SQLiteDatabase getdictionaryDB()
	{
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.dictionary_db_Path, Context.MODE_PRIVATE, null);
		return db;
	}
	
	/**
	 * 获取所有字典分类
	 * @return
	 */
	public List<BookCategory> getAllBookCats()
	{
		List<BookCategory> bookCategorys = new ArrayList<BookCategory>();
		String sql = "select CateID,CateName,CateOrder from tbCate";
		SQLiteDatabase db = getbookDB();
		Cursor cur = db.rawQuery(sql, null);
		BookCategory category = null;
		while(cur.moveToNext())
		{
			category = new BookCategory();
			category.setCateID(cur.getInt(cur.getColumnIndex("CateID")));
			category.setCateName(cur.getString(cur.getColumnIndex("CateName")));
			category.setCateOrder(cur.getInt(cur.getColumnIndex("CateOrder")));
			bookCategorys.add(category);
		}
		db.close();
		return bookCategorys;
	}
	 
}

package com.qlfsoft.wordman.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.model.BookBook;
import com.qlfsoft.wordman.model.BookCategory;
import com.qlfsoft.wordman.model.WordModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DictionaryDBHelper {

	private Context mContext;
	private DictionaryDBHelper()
	{
		this.mContext = BaseApplication.getContext();
	}
	
	private static DictionaryDBHelper instance;
	
	public synchronized static DictionaryDBHelper getInstance()
	{
		if(null == instance)
			instance = new DictionaryDBHelper();
		return instance;
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
			File file1 = new File(BaseApplication.book_db_Path);
			LogUtils.Logv("开始复制数据");
			if(!file1.exists())
			{
				CopyFile("book.db",BaseApplication.book_db_Path);
				LogUtils.Logv("book.db复制完成");
			}
			File file2 = new File(BaseApplication.dic_db_Path);
			if(!file2.exists())
			{
				CopyFile("dic.db",BaseApplication.dic_db_Path);
				LogUtils.Logv("dic.db复制完成");
			}
			File file3 = new File(BaseApplication.dictionary_db_Path);
			if(!file3.exists())
			{
				CopyFile("dictionary.db",BaseApplication.dictionary_db_Path);
			}
			LogUtils.Logv("dictionary.db复制完成");
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
		LogUtils.Logv(srcFile);
		InputStream is = mContext.getResources().getAssets().open(srcFile);
		
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(destFile);
		int length;
		while((length = is.read(buffer)) !=-1 )
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
		String sql = "select CateID,CateName,CateOrder from tbCate order by CateOrder asc";
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
	 
	/**
	 * 获取字典分类下的单词本
	 * @param cateId
	 * @return
	 */
	public List<BookBook> getBooksByCateId(int cateId)
	{
		List<BookBook> books = new ArrayList<BookBook>();
		String sql = "select BookID,CateID,BookName,BookCount,BookOrder from tbBook where CateID=" + cateId + " order by BookOrder asc";
		SQLiteDatabase db = getbookDB();
		Cursor cursor = db.rawQuery(sql, null);
		BookBook book = null;
		while(cursor.moveToNext())
		{
			book = new BookBook();
			book.setBookId(cursor.getInt(cursor.getColumnIndex("BookID")));
			book.setCateId(cursor.getInt(cursor.getColumnIndex("CateID")));
			book.setBookName(cursor.getString(cursor.getColumnIndex("BookName")));
			book.setBookCount(cursor.getInt(cursor.getColumnIndex("BookCount")));
			book.setBookOrder(cursor.getInt(cursor.getColumnIndex("BookOrder")));
			books.add(book);
		}
		db.close();
		return books;
	}
	
	/**
	 * 根据单词本序号获取单词本信息
	 * @param bookId
	 * @return
	 */
	public BookBook getBookById(int bookId)
	{
		BookBook book = null;
		String sql = "select BookID,CateID,BookName,BookCount,BookOrder from tbBook where BookID=" + bookId;
		SQLiteDatabase db = getbookDB();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			book = new BookBook();
			book.setBookId(cursor.getInt(cursor.getColumnIndex("BookID")));
			book.setCateId(cursor.getInt(cursor.getColumnIndex("CateID")));
			book.setBookName(cursor.getString(cursor.getColumnIndex("BookName")));
			book.setBookCount(cursor.getInt(cursor.getColumnIndex("BookCount")));
			book.setBookOrder(cursor.getInt(cursor.getColumnIndex("BookOrder")));
		}
		db.close();
		return book;
	}
	
	/**
	 * 获取单词信息
	 * @param wordId
	 * @return
	 */
	public WordModel getWordById(int wordId)
	{
		WordModel wordModel = null;
		String sql = "select WordID,Word,Description,Phonetic,Sentence from tbWord where WordID=" + wordId;
		SQLiteDatabase db = getdicDB();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			wordModel = new WordModel();
			wordModel.setWordId(cursor.getInt(cursor.getColumnIndex("WordID")));
			wordModel.setWord(cursor.getString(cursor.getColumnIndex("Word")));
			wordModel.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
			wordModel.setPhonetic(cursor.getString(cursor.getColumnIndex("Phonetic")));
			wordModel.setSentence(cursor.getString(cursor.getColumnIndex("Sentence")));
		}
		return wordModel;
	}
	
	/**
	 * 获取单词序号
	 * @param selBook  单词本
	 * @param orderNo  排序
	 * @return
	 */
	public int getWordId(int selBook,int orderNo)
	{
		int wordId = 0;
		String sql = "select WordID from tbData where BookID=" + selBook + " and OrderNo=" + orderNo;
		SQLiteDatabase db = getbookDB();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			wordId = cursor.getInt(cursor.getColumnIndex("WordID"));
		}
		return wordId;
	}
	
	/**
	 * 获取4个解释
	 * @param wordId
	 * @return
	 */
	public List<String> get4Selectors(int wordId)
	{
		List<String> results = new ArrayList<String>();
		SQLiteDatabase db = getdicDB();
		Random random = new Random(System.currentTimeMillis());
		int word1 =  random.nextInt(ConstantsUtil.MAXWORDID);
		while(word1 == wordId)
			word1 = random.nextInt(ConstantsUtil.MAXWORDID);
		int word2 = random.nextInt(ConstantsUtil.MAXWORDID);
		while(word2== word1 || word2== wordId)
			word2 = random.nextInt(ConstantsUtil.MAXWORDID);
		int word3 = random.nextInt(ConstantsUtil.MAXWORDID);
		while(word3 == word2 || word3== word1 || word3 == wordId)
			word3 =random.nextInt(ConstantsUtil.MAXWORDID);
		String sql = "select Description from tbWord where WordID in(" + wordId+ "," + word1 + "," + word2 + "," + word3 + ")";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			String description = cursor.getString(cursor.getColumnIndex("Description"));
			results.add(description);
		}
		
		for(int i = 0; i < 10; i++)
		{
			int intFlag = random.nextInt(4);
			String temp = results.get(intFlag);
			results.set(intFlag, results.get(0));
			results.set(0,temp);
		}
		return results;
	}
}

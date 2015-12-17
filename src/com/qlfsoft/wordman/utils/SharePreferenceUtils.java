package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	private Context mContext;
	private SharedPreferences sp;
	private String userBookId = "CURRENT_BOOK_ID";
	
	public SharePreferenceUtils()
	{
		mContext = BaseApplication.sContext;
		sp = mContext.getSharedPreferences("com.qlfsoft.wordman", Context.MODE_PRIVATE);
	}
	
	/**
	 * 当前用户是否已经选择了背诵单词本
	 * @return
	 */
	public boolean hasSelBook()
	{
		int bookId = sp.getInt(userBookId, 0);
		return bookId != 0;
	}
	
	/**
	 * 设置选中的单词本序号
	 * @param bookId
	 */
	public void setBookId(int bookId)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(userBookId, bookId);
		ed.commit();
	}
}

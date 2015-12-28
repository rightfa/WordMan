package com.qlfsoft.wordman.utils;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.qlfsoft.wordman.model.UserModel;

import android.database.sqlite.SQLiteDatabase;

public class WordManDB {

	private SQLiteDatabase db;
	private static WordManDB wordmanDB;
	
	public WordManDB()
	{
		db = Connector.getDatabase();
	}
	
	public synchronized static WordManDB getInstance()
	{
		if(wordmanDB == null)
		{
			wordmanDB = new WordManDB();
		}
		return wordmanDB;
	}
	
	public void saveUserInfo(UserModel user)
	{
		if(user!= null)
			user.save();
	}
	
	public List<UserModel> loadUserInfos()
	{
		List<UserModel> list = DataSupport.findAll(UserModel.class);
		return list;
	}
	
}

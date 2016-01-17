package com.qlfsoft.wordman;

import java.util.ArrayList;
import java.util.List;

public abstract class UserInfoSubject {
	private List<IUserInfoObserver> list = new ArrayList<IUserInfoObserver>();
	
	public void attach(IUserInfoObserver object)
	{
		list.add(object);
	}
	
	public void detach(IUserInfoObserver object)
	{
		list.remove(object);
	}
	
	public void nodifyObservers()
	{
		for(IUserInfoObserver objs: list)
		{
			objs.userInfoUpdate();
		}
	}
}

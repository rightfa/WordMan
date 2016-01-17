package com.qlfsoft.wordman;

import java.util.ArrayList;
import java.util.List;

public abstract class PlanSubject {

	private List<IPlanObserver> list = new ArrayList<IPlanObserver>();
	
	public void attach(IPlanObserver object)
	{
		list.add(object);
	}
	
	public void detach(IPlanObserver object)
	{
		list.remove(object);
	}
	
	public void notifyObservers()
	{
		for(IPlanObserver obj: list)
		{
			obj.planUpdate();
		}
	}
}

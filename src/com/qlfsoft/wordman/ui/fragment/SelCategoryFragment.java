package com.qlfsoft.wordman.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelCategoryFragment extends Fragment {

	int mNum;
	public static SelCategoryFragment newInstance(int num)
	{
		SelCategoryFragment fragment = new SelCategoryFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ?  getArguments().getInt("num") : 1;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	
}

package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private Context mContext;
	
	public static void showShort(String msg)
	{
		Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
	}
}

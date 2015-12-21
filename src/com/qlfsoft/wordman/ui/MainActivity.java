package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.widget.FlipperLayout;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends BaseActivity implements OnOpenListener {

	/**
	 * 当前显示内容的容器(继承于ViewGroup)
	 */
	private FlipperLayout mRoot;
	
	/*
	 * 当前显示的View的编号
	 */
	private int mViewPosition;
	/*
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;
	
	private static Activity mInstance;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 创建容器,并设置全屏大小
		 */
		mRoot = new FlipperLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRoot.setLayoutParams(params);
		/**
		 * 创建菜单界面和内容首页界面,并添加到容器中,用于初始显示
		 */
		setContentView(mRoot);
		setListener();
		mInstance=this;
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}
	}

}

package com.qlfsoft.wordman.ui;

import java.io.File;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.menu.Desktop;
import com.qlfsoft.wordman.menu.Desktop.onChangeViewListener;
import com.qlfsoft.wordman.menu.Home;
import com.qlfsoft.wordman.menu.MyPlan;
import com.qlfsoft.wordman.menu.UserInfo;
import com.qlfsoft.wordman.utils.ActivityForResultUtil;
import com.qlfsoft.wordman.utils.ViewUtil;
import com.qlfsoft.wordman.widget.FlipperLayout;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnOpenListener {

	/**
	 * 当前显示内容的容器(继承于ViewGroup)
	 */
	private FlipperLayout mRoot;
	
	private Desktop mDesktop;
	private Home mHome;
	private UserInfo mUserInfo;
	private MyPlan mPlan;
	/*
	 * 当前显示的View的编号
	 */
	private int mViewPosition;
	/*
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;
	/**
	 * 退出时间
	 */
	private long mExitTime;
	
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
		mDesktop = new Desktop(this,this);
		mHome = new Home(this,this);
		mRoot.addView(mDesktop.getView(),params);
		mRoot.addView(mHome.getView(),params);
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
		mHome.setOnOpenListener(this);
		mDesktop.setOnChangeViewListener(new onChangeViewListener(){

			@Override
			public void onChangeView(int arg0) {
				mViewPosition = arg0;
				switch(arg0)
				{
				case ViewUtil.HOME:
					mRoot.close(mHome.getView());
					break;
				case ViewUtil.USER:
					if(mUserInfo == null)
					{
						mUserInfo = new UserInfo(MainActivity.this,MainActivity.this);
						mUserInfo.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mUserInfo.getView());
					break;
				case ViewUtil.MYPLAN:
					if(mPlan == null)
					{
						mPlan = new MyPlan(MainActivity.this,MainActivity.this);
						mPlan.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mPlan.getView());
					break;
				default:
						break;
				}
				
			}
			
		});
		}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 返回键监听
	 */
	public void onBackPressed() {
		exit();
	}

	/**
	 * 判断两次返回时间间隔,小于两秒则退出程序
	 */
	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回键,可直接退出程序", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	public void open() {
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ActivityForResultUtil.REQUESTCODE_USERINFO_CAMERA:
		case ActivityForResultUtil.REQUESTCODE_USERINFO_IMAGE:
		case ActivityForResultUtil.REQUESTCODE_USERINFO_RESULT:
			if(mUserInfo == null)
			{
				mUserInfo = new UserInfo(MainActivity.this,MainActivity.this);
				mUserInfo.setOnOpenListener(MainActivity.this);
			}
			mRoot.close(mUserInfo.getView());
			mUserInfo.doActivityResult(requestCode, resultCode, data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

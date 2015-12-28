package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.utils.LogUtils;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.utils.TaskUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		TaskUtils.executeAsyncTask(new AsyncTask<Object,Object,Object>(){

			boolean dbExist = true;
			@Override
			protected Object doInBackground(Object... params) {
				DictionaryDBHelper dbHelper = new DictionaryDBHelper();
				dbExist = dbHelper.CopyDataBase();
				return null;
			}
			
			@Override
			protected void onPostExecute(Object o)
			{
				super.onPostExecute(o);
				if(dbExist)
				{
					new Handler().postDelayed(new Runnable(){
	
						@Override
						public void run() {
							SharePreferenceUtils spHelper = new SharePreferenceUtils();
							if(spHelper.getFirstOpen())
							{
								Intent intent = new Intent(SplashActivity.this,SelCategoryActivity.class);
								SplashActivity.this.startActivity(intent);

							}else if(!spHelper.getLoginState())
							{
								//转入登录界面
								Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
								startActivity(intent);
							}else
							{
								Intent intent = new Intent(SplashActivity.this,MainActivity.class);
								SplashActivity.this.startActivity(intent);
							}
							
							SplashActivity.this.finish();
						}
						
					}, 700);
				}else
				{
					System.exit(0);
				}
			}
		});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}

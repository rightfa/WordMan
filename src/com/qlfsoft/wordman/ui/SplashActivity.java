package com.qlfsoft.wordman.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.model.UserModel;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {

	private TextView splash_tv_firstLoading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		splash_tv_firstLoading = (TextView) findViewById(R.id.splash_tv_firstLoading);
		//获取数据
		final List<UserModel> users = DataSupport.where("loginState=?",String.valueOf(1)).find(UserModel.class);
		final List<UserModel> existUsers = DataSupport.findAll(UserModel.class);
		if(existUsers.size()<=0)//第一次打开程序
			splash_tv_firstLoading.setVisibility(View.VISIBLE);
		TaskUtils.executeAsyncTask(new AsyncTask<Object,Object,Object>(){

			boolean dbExist = true;
			@Override
			protected Object doInBackground(Object... params) {
				DictionaryDBHelper dbHelper = DictionaryDBHelper.getInstance();
				dbExist = dbHelper.CopyDataBase();
				if(existUsers.size() <= 0)
				{
					UserModel user = new UserModel();
					user.setAccount("");
					user.setNickname("单词君");
					user.setPassword("");
					user.setSignificances("你有多努力无需多谈重要的是你完成了多少计划之中的事");
					user.setLoginState(1);
					user.setSelBook(0);
					user.setLoginState(1);
					user.save();
					BaseApplication.nickName="单词君";
					BaseApplication.significance = user.getSignificances();
					BaseApplication.userAccount = user.getAccount();
					BaseApplication.curBookId = 0;
				}else if(users.size() > 0)
				{
					UserModel user = users.get(0);
					BaseApplication.curBookId = user.getSelBook();
					BaseApplication.nickName = user.getNickname();
					BaseApplication.significance = user.getSignificances();
					BaseApplication.totalDay = user.getTotalDay();
					BaseApplication.userAccount = user.getAccount();
					BaseApplication.wordSize = user.getWordSize();
					BaseApplication.haveStudy = user.getHaveStudy();
					SharePreferenceUtils sp = SharePreferenceUtils.getInstnace();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String date = format.format(new Date());
					int repeat_size = sp.getRepeat_Size();
					String repeat_date = sp.getRepeat_Date();
					if(repeat_date.equals(date))
					{
						repeat_size = repeat_size;
					}else
					{
						repeat_size = 1;
					}
					BaseApplication.dailyWord = user.getDailyword() * repeat_size;
					BaseApplication.remainDay= (int)Math.ceil(((float)(BaseApplication.wordSize-BaseApplication.haveStudy))/user.getDailyword());
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Object o)
			{
				super.onPostExecute(o);
				if(dbExist)
				{
					new Handler().post(new Runnable(){
	
						@Override
						public void run() {
							SharePreferenceUtils spHelper = SharePreferenceUtils.getInstnace();
							if(existUsers.size()>0 && users.size() <=0)
							{
								//转入登录界面
								Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
								startActivity(intent);
							}else if(BaseApplication.curBookId == 0)
							{
								Intent intent = new Intent(SplashActivity.this,SelCategoryActivity.class);
								SplashActivity.this.startActivity(intent);

							}else
							{
								Intent intent = new Intent(SplashActivity.this,MainActivity.class);
								SplashActivity.this.startActivity(intent);
							}
							
							SplashActivity.this.finish();
						}
						
					});
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

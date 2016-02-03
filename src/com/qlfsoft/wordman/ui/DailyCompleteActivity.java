package com.qlfsoft.wordman.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class DailyCompleteActivity extends BaseActivity {

	private ImageButton ib_menu;
	private Button btn_back;
	private Button btn_rePlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dailycomplete);
		initView();
		initData();
		setListener();
	}
	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.dailycomplete_menu);
		btn_back = (Button) findViewById(R.id.dailycomplete_btn_back);
		btn_rePlay = (Button) findViewById(R.id.dailycomplete_btn_replay);
		
	}
	private void initData() {
		// TODO Auto-generated method stub
		
	}
	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(DailyCompleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DailyCompleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		btn_rePlay.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				SharePreferenceUtils sp = SharePreferenceUtils.getInstnace();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(new Date());
				int repeat_size = sp.getRepeat_Size();
				String repeat_date = sp.getRepeat_Date();
				if(repeat_date.equals(date))
				{
					repeat_size = repeat_size + 1;
				}else
				{
					repeat_size = 2;
				}
				sp.setRepeat_Date(date);
				sp.setRepeat_Size(repeat_size);
				BaseApplication.dailyWord = BaseApplication.dailyWord / (repeat_size - 1) * repeat_size;
				Intent intent = new Intent(DailyCompleteActivity.this,WordActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}

package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestComleteActivity extends BaseActivity {

	private ImageButton ib_menu;
	private TextView tv_score;
	private TextView tv_estimate;
	private Button btn_back;
	private Button btn_rePlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcomplete);
		initView();
		initData();
		setListener();
	}
	private void initData() {
		Intent intent = getIntent();
		int score = intent.getIntExtra("SCORE", 0);
		tv_score.setText(String.valueOf(score));
		String[] estimates = getResources().getStringArray(R.array.test_estimate);
		if(score <30)
			tv_estimate.setText(estimates[0]);
		else if(score <60)
		{
			tv_estimate.setText(estimates[1]);
		}else if(score < 70)
		{
			tv_estimate.setText(estimates[2]);
		}else if(score< 80)
		{
			tv_estimate.setText(estimates[3]);
		}else if(score< 90)
		{
			tv_estimate.setText(estimates[4]);
		}else if(score< 99)
		{
			tv_estimate.setText(estimates[5]);
		}else
		{
			tv_estimate.setText(estimates[6]);
		}
		
	}
	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.testComplete_menu);
		tv_score = (TextView) findViewById(R.id.testComplete_tv_score);
		tv_estimate = (TextView) findViewById(R.id.testComplete_tv_estimate);
		btn_back = (Button) findViewById(R.id.testComplete_btn_back);
		btn_rePlay = (Button) findViewById(R.id.testComplete_btn_replay);
		
	}
	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(TestComleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(TestComleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_rePlay.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				setResult(1);
				finish();
			}
		});
	}

}

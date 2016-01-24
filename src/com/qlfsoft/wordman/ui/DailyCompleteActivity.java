package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;

import android.os.Bundle;
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
		// TODO Auto-generated method stub
		
	}

}

package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WordActivity extends BaseActivity {

	private ImageButton ib_menu;
	private TextView tv_log;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private RelativeLayout rl_new;
	private TextView tv_description;
	private Button btn_next1;
	private Button btn_know;
	private Button btn_unknow;
	private LinearLayout ll_review;
	private ListView lv_selectors;
	private Button btn_next2;
	private int type;//1是新词，2是复习
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		Intent intent = getIntent();
		type = intent.getIntExtra("Type", 1);
		initView();
		setListener();
		initData();
	}

	private void initData() {
		switch(type)
		{
		case 1://新词
			rl_new.setVisibility(View.VISIBLE);
			ll_review.setVisibility(View.INVISIBLE);
			btn_next1.setVisibility(View.INVISIBLE);
			btn_know.setVisibility(View.VISIBLE);
			btn_unknow.setVisibility(View.VISIBLE);
			
			break;
		case 2://复习
			rl_new.setVisibility(View.INVISIBLE);
			ll_review.setVisibility(View.VISIBLE);
			btn_next2.setVisibility(View.INVISIBLE);
			break;
		}
		
	}

	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_sound.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_next1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_next2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_know.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_unknow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.word_menu);
		tv_log = (TextView) findViewById(R.id.word_tv_log);
		btn_sound = (Button) findViewById(R.id.word_btn_sound);
		tv_word = (TextView) findViewById(R.id.word_tv_word);
		tv_phonetic = (TextView) findViewById(R.id.word_tv_phonetic);
		rl_new = (RelativeLayout) findViewById(R.id.word_rl_new);
		tv_description = (TextView) findViewById(R.id.word_tv_description);
		btn_next1 = (Button) findViewById(R.id.word_btn_next1);
		btn_know = (Button) findViewById(R.id.word_btn_konw);
		btn_unknow = (Button) findViewById(R.id.word_btn_unkonw);
		ll_review = (LinearLayout) findViewById(R.id.word_ll_review);
		lv_selectors = (ListView) findViewById(R.id.word_lv_selectors);
		btn_next2 = (Button) findViewById(R.id.word_btn_next2);

	}

}

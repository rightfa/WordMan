package com.qlfsoft.wordman.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;

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
	private String word;//单词
	private String phonetic;//音标
	private String description;//解释
	private List<String> selectors;//选项
	
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
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateformat.format(new Date());
		List<UserWords> todayWords = DataSupport.where("account=? and bookId=? and date=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).find(UserWords.class);
		int todaySize = todayWords.size();//今日已经学习的单词数
		List<UserWords> beforeWords = DataSupport.where("account=? and bookId=? and date<>? and repeat<=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,"4").find(UserWords.class);
		int beforeSize = beforeWords.size();//前面已经学习的单词数
		int reviewSize = beforeSize / 3 * 2;
		String strLog = String.format("今日需新学%d//%d  今日需复习%d",BaseApplication.dailyWord - todaySize,BaseApplication.dailyWord,reviewSize);
		tv_log.setText(strLog);
		int before_maxOrder = DataSupport.where("account=? and bookId=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId)).max(UserWords.class, "orderNo", int.class);
		DictionaryDBHelper db = DictionaryDBHelper.getInstance();
		WordModel wordModel = db.getWordById(db.getWordId(BaseApplication.curBookId, before_maxOrder));
		word = wordModel.getWord();
		phonetic = wordModel.getPhonetic();
		description = wordModel.getDescription();
		tv_word.setText(word);
		tv_phonetic.setText((null == phonetic)?"": phonetic); 
		tv_description.setText(description);
		tv_description.setVisibility(View.INVISIBLE);
		
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

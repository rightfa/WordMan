package com.qlfsoft.wordman.ui;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.utils.ToastUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class BookCompleteActivity extends BaseActivity {

	private ImageButton ib_menu;
	private Button btn_back;
	private Button btn_rePlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookcomplete);
		initView();
		initData();
		setListener();
	}
	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.bookcomplete_menu);
		btn_back = (Button) findViewById(R.id.bookcomplete_btn_back);
		btn_rePlay = (Button) findViewById(R.id.bookcomplete_btn_replay);
	}
	private void initData() {
		// TODO Auto-generated method stub
		
	}
	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(BookCompleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BookCompleteActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		btn_rePlay.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				DataSupport.deleteAll(UserWords.class, "account=? and bookId=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId));
				ContentValues values = new ContentValues();
				values.put("haveStudy", 0);
				DataSupport.updateAll(UserModel.class, values,  "account=? and selBook=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId));
				BaseApplication.haveStudy = 0;
				BaseApplication.remainDay = BaseApplication.totalDay;
				ToastUtils.showShort("单词本重置完成！");
				Intent intent = new Intent(BookCompleteActivity.this,WordActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		
	}

}

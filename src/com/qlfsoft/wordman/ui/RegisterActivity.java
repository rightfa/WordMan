package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity {

	private Button btn_back;
	private EditText et_account;
	private EditText et_pwd;
	private EditText et_nickname;
	private EditText et_rePwd;
	private Button btn_submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		setListener();
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		
	}
	private void setListener() {
		btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
				
			}
			
		});
		btn_submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	private void initView() {
		btn_back = (Button) this.findViewById(R.id.register_back);
		et_account = (EditText) this.findViewById(R.id.register_et_account);
		et_pwd = (EditText) this.findViewById(R.id.register_et_pwd);
		et_nickname = (EditText) this.findViewById(R.id.register_et_nickname);
		et_rePwd = (EditText) this.findViewById(R.id.register_et_repwd);
		btn_submit = (Button) this.findViewById(R.id.register_activity_submit);
		
	}

}

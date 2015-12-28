package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

	private EditText et_account;
	private EditText et_pwd;
	private Button btn_login;
	private TextView tv_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		setListener();
		init();
	}

	private void init() {
		SharePreferenceUtils spHelper = new SharePreferenceUtils();
		String userAccount = spHelper.getUserAccount();
		if(userAccount != null && (!userAccount.equals("")))
		{
			et_account.setText(userAccount);
		}
		
	}

	private void setListener() {
		btn_login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		tv_register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);	
			}
			
		});
	}

	private void initView()
	{
		et_account = (EditText) this.findViewById(R.id.login_et_account);
		et_pwd = (EditText) this.findViewById(R.id.login_et_pwd);
		btn_login = (Button) this.findViewById(R.id.login_activity_login);
		tv_register = (TextView) this.findViewById(R.id.login_tv_register);
	}
}

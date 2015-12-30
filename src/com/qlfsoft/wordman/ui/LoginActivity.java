package com.qlfsoft.wordman.ui;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private EditText et_account;
	private EditText et_pwd;
	private Button btn_login;
	private TextView tv_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		initView();
		setListener();
		init();
	}

	private void init() {
		SharePreferenceUtils spHelper =SharePreferenceUtils.getInstnace();
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
				String userAccount = et_account.getText().toString();
				String userPwd = et_pwd.getText().toString();
				if(TextUtils.isEmpty(userAccount))
				{
					Toast.makeText(LoginActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				List<UserModel> list = DataSupport.where("account=? and password=?",userAccount,userPwd).find(UserModel.class);
				if(list.size() > 0)
				{
					UserModel item = list.get(0);
					SharePreferenceUtils spHelper = new SharePreferenceUtils();
					spHelper.setBookId(item.getSelBook());
					spHelper.setNickName(item.getNickname());
					spHelper.setUserAccount(item.getAccount());
					spHelper.setPassword(item.getPassword());
					spHelper.setLoginState(true);
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();		
				}
				Toast.makeText(LoginActivity.this, "用户名或账号错误！", Toast.LENGTH_SHORT).show();
				et_pwd.setText("");
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

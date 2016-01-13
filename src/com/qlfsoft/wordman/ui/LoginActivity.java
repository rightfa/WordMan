package com.qlfsoft.wordman.ui;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.utils.ToastUtils;

import android.app.Activity;
import android.content.ContentValues;
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
		String userAccount = BaseApplication.userAccount;
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
					ToastUtils.showShort( "账号不能为空！");
					return;
				}
				List<UserModel> list = DataSupport.where("account=? and password=?",userAccount,userPwd).find(UserModel.class);
				if(list.size() > 0)
				{
					UserModel item = list.get(0);
					BaseApplication.curBookId = item.getSelBook();
					BaseApplication.nickName = item.getNickname();
					BaseApplication.userAccount = item.getAccount();
					ContentValues values = new ContentValues();
					values.put("loginState", 0);
					DataSupport.updateAll(UserModel.class, values, null);
					item.setLoginState(1);
					item.updateAll("account=?",userAccount);
					SharePreferenceUtils spHelper = SharePreferenceUtils.getInstnace();
					spHelper.setAvatarImage(true);
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();		
				}else
				{
					ToastUtils.showShort( "用户名或账号错误！");
					et_pwd.setText("");
				}
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

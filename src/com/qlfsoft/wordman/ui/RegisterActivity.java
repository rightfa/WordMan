package com.qlfsoft.wordman.ui;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
				String account = et_account.getText().toString();
				String nickname = et_nickname.getText().toString();
				String pwd = et_pwd.getText().toString();
				String rePwd = et_rePwd.getText().toString();
				
				if(TextUtils.isEmpty(account))
				{
					showToast("账号不能为空！");
					return;
				}
				if(TextUtils.isEmpty(nickname))
				{
					showToast("昵称不能为空！");
					return;
				}
				if(!pwd.equals(rePwd))
				{
					showToast("两次输入的密码不一致！");
					return;
				}
				
				List<UserModel> list = DataSupport.where("account=?",account).find(UserModel.class);
				if(list.size()>0)
				{
					showToast("账号已经存在！");
					return;
				}
				UserModel userModel = new UserModel();
				userModel.setAccount(account);
				userModel.setPassword(pwd);
				userModel.setNickname(nickname);
				userModel.save();
				
				SharePreferenceUtils spHelper = new SharePreferenceUtils();
				spHelper.setBookId(0);
				spHelper.setNickName(nickname);
				spHelper.setUserAccount(account);
				spHelper.setLoginState(true);
				
				Intent intent = new Intent(RegisterActivity.this,SelCategoryActivity.class);
				startActivity(intent);
				finish();
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
	
	private void showToast(String text)
	{
		Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_SHORT).show();
	}

}

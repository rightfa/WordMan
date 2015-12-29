package com.qlfsoft.wordman.menu;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.ui.LoginActivity;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfo {

	private Context mContext;
	private Activity mActivity;
	private View mUserInfo;
	private OnOpenListener mOnOpenListener;
	
	private TextView tv_title;
	private ImageButton btn_back;
	private Button btn_submit;
	private ImageButton ib_avatar;
	private Button btn_avatarchg;
	private TextView tv_nickname;
	private TextView tv_account;
	private TextView tv_significances;
	private LinearLayout ll_orginPwd;
	private TextView tv_orginPwd;
	private LinearLayout ll_newPwd;
	private LinearLayout ll_rePwd;
	private TextView tv_newPwd;
	private TextView tv_rePwd;
	private Button btn_logout;
	private boolean modify_flag;
	
	public UserInfo(Context context,Activity activity)
	{
		mContext = context;
		mActivity = activity;
		mUserInfo = LayoutInflater.from(context).inflate(R.layout.about_activity, null);
		findViewById();
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
				if(null != mOnOpenListener)
					mOnOpenListener.open();
				
			}
			
		});
		btn_submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_avatarchg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
				if(!(null == spu.getUserAccount() || "".equals(spu.getUserAccount())))
				{
					spu.setLoginState(false);
				}
				Intent intent = new Intent(mContext,LoginActivity.class);
				mActivity.startActivity(intent);
				mActivity.finish();
				
			}
			
		});
	}

	private void findViewById() {
		tv_title = (TextView) mUserInfo.findViewById(R.id.about_title);
		btn_back = (ImageButton) mUserInfo.findViewById(R.id.about_back);
		btn_submit = (Button) mUserInfo.findViewById(R.id.about_submit);
		ib_avatar = (ImageButton) mUserInfo.findViewById(R.id.about_avatar);
		btn_avatarchg = (Button) mUserInfo.findViewById(R.id.about_avatar_change);
		tv_nickname = (TextView) mUserInfo.findViewById(R.id.about_tv_nickname);
		tv_account = (TextView) mUserInfo.findViewById(R.id.about_tv_account);
		tv_significances = (TextView) mUserInfo.findViewById(R.id.about_tv_significances);
		ll_orginPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_orginpwd);
		tv_orginPwd = (TextView) mUserInfo.findViewById(R.id.about_tv_orginpwd);
		ll_newPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_newpwd);
		tv_newPwd = (TextView) mUserInfo.findViewById(R.id.about_tv_newpwd);
		ll_rePwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_repwd);
		tv_rePwd = (TextView) mUserInfo.findViewById(R.id.about_tv_repwd);
		btn_logout = (Button) mUserInfo.findViewById(R.id.about_btn_logout);
	}
	
	public View getView() {
		return mUserInfo;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}

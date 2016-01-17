package com.qlfsoft.wordman.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.UserInfoSubject;
import com.qlfsoft.wordman.model.UserBooks;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.ui.LoginActivity;
import com.qlfsoft.wordman.utils.ActivityForResultUtil;
import com.qlfsoft.wordman.utils.FileUtils;
import com.qlfsoft.wordman.utils.PhotoUtil;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.utils.ToastUtils;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo extends UserInfoSubject {

	private Context mContext;
	private Activity mActivity;
	private View mUserInfo;
	private OnOpenListener mOnOpenListener;
	
	private TextView tv_title;
	private ImageButton btn_back;
	private Button btn_submit;
	private ImageButton ib_avatar;
	private Button btn_avatarchg;
	private EditText tv_nickname;
	private EditText tv_account;
	private EditText tv_significances;
	private LinearLayout ll_orginPwd;
	private EditText tv_orginPwd;
	private LinearLayout ll_newPwd;
	private LinearLayout ll_rePwd;
	private EditText tv_newPwd;
	private EditText tv_rePwd;
	private Button btn_logout;
	private boolean modify_flag;
	
	private String[] items = new String[] { "选择本地图片", "拍照" };
	private String tempImage = "temp.jpg";
	
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
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		tv_nickname.setText(BaseApplication.nickName);
		tv_account.setText(BaseApplication.userAccount);
		tv_significances.setText(BaseApplication.significance);
		boolean defaultImage = spu.getAvatarImage();
		ib_avatar.setImageResource(R.drawable.head);
		File file = new File(mActivity.getFilesDir(),BaseApplication.FACEIMAGE_FILE_NAME);
		if(!defaultImage && file.exists())
		{
			Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
			ib_avatar.setImageBitmap(bmp);	
		}
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
				if(btn_submit.getText().toString().equals(mContext.getResources().getString(R.string.about_btn_modify_str)))
				{
					//进行修改
					tv_account.setEnabled(true);
					tv_nickname.setEnabled(true);
					tv_significances.setEnabled(true);
					btn_submit.setText(R.string.about_btn_complete_str);
					ll_orginPwd.setVisibility(View.VISIBLE);
					ll_newPwd.setVisibility(View.VISIBLE);
					ll_rePwd.setVisibility(View.VISIBLE);
					btn_avatarchg.setVisibility(View.VISIBLE);
				}else
				{
					//修改完成
					String account = tv_account.getText().toString().trim();
					if(TextUtils.isEmpty(account))
					{
						ToastUtils.showShort("账号不能为空！");
						return;
					}
					String nickname = tv_nickname.getText().toString().trim();
					if(TextUtils.isEmpty(nickname))
					{
						ToastUtils.showShort("昵称不能为空！");
						return;
					}
					
					String newPwd = tv_newPwd.getText().toString().trim();
					String rePwd = tv_rePwd.getText().toString().trim();
					if(!newPwd.equals(rePwd))
					{
						ToastUtils.showShort("两次密码输入不一致");
						return;
					}
					String orginPwd = tv_orginPwd.getText().toString().trim();
					String psd = DataSupport.where("account=?",BaseApplication.userAccount).find(UserModel.class).get(0).getPassword();
					if(!orginPwd.equals(psd))
					{
						ToastUtils.showShort("原密码不正确");
						return;
					}
					String significances = tv_significances.getText().toString();
					
					UserModel myUser = new UserModel();
					myUser.setAccount(account);
					myUser.setNickname(nickname);
					myUser.setPassword(newPwd);
					myUser.setSignificances(significances);
					boolean updateFlag = false;
					
					UserBooks myBook = new UserBooks();
					myBook.setAccount(account);
					myBook.setBookId(BaseApplication.curBookId);
					myBook.setDailyword(BaseApplication.dailyWord);
					myBook.setHaveStudy(BaseApplication.haveStudy);
					myBook.setRemainDay(BaseApplication.remainDay);
					myBook.setTotalDay(BaseApplication.totalDay);
					
					List<UserModel> list = DataSupport.where("account=?",account).find(UserModel.class);
					if(list.size() > 0)
					{
						if(list.get(0).getPassword().equals(orginPwd))
						{
							myUser.updateAll("account=?",account);
							updateFlag = true;
						}else
						{
							ToastUtils.showShort("账号已经存在，不能再创建！");
							return;
						}
					}else
					{
						myUser.updateAll("account=?",BaseApplication.userAccount);
						myBook.save();
						updateFlag = true;
					}
					
					if(updateFlag)
					{
						BaseApplication.userAccount = account;
						BaseApplication.nickName = nickname;
						BaseApplication.significance = significances;
						nodifyObservers();
					}
					
					btn_submit.setText(R.string.about_btn_modify_str);
					ll_orginPwd.setVisibility(View.GONE);
					ll_newPwd.setVisibility(View.GONE);
					ll_rePwd.setVisibility(View.GONE);
					btn_avatarchg.setVisibility(View.INVISIBLE);
					tv_account.setEnabled(false);
					tv_nickname.setEnabled(false);
					tv_significances.setEnabled(false);
				}
				
			}
			
		});
		btn_avatarchg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String account = tv_account.getText().toString().trim();
				String nickname = tv_nickname.getText().toString().trim();
				String newPwd = tv_newPwd.getText().toString().trim();
				String rePwd = tv_rePwd.getText().toString().trim();
				String orginPwd = tv_orginPwd.getText().toString().trim();
				String significances = tv_significances.getText().toString();
				SharePreferenceUtils.getInstnace().setTempUserInfo(nickname, account, significances, orginPwd, newPwd, rePwd);
				showDialog();
				
			}
			
		});
		btn_logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
				if(!(null == BaseApplication.userAccount || "".equals(BaseApplication.userAccount)))
				{
					ContentValues values = new ContentValues();
					values.put("loginState", 0);
					DataSupport.updateAll(UserModel.class, values, "account=?",BaseApplication.userAccount);
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
		tv_nickname = (EditText) mUserInfo.findViewById(R.id.about_tv_nickname);
		tv_account = (EditText) mUserInfo.findViewById(R.id.about_tv_account);
		tv_significances = (EditText) mUserInfo.findViewById(R.id.about_tv_significances);
		ll_orginPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_orginpwd);
		tv_orginPwd = (EditText) mUserInfo.findViewById(R.id.about_tv_orginpwd);
		ll_newPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_newpwd);
		tv_newPwd = (EditText) mUserInfo.findViewById(R.id.about_tv_newpwd);
		ll_rePwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_repwd);
		tv_rePwd = (EditText) mUserInfo.findViewById(R.id.about_tv_repwd);
		btn_logout = (Button) mUserInfo.findViewById(R.id.about_btn_logout);
	}
	
	public View getView() {
		return mUserInfo;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(mContext)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							mActivity.startActivityForResult(intentFromGallery,
									ActivityForResultUtil.REQUESTCODE_USERINFO_IMAGE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(mActivity.getFilesDir(),tempImage)));

							mActivity.startActivityForResult(intentFromCapture,
									ActivityForResultUtil.REQUESTCODE_USERINFO_CAMERA);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	
	public void doActivityResult(int requestCode, int resultCode, Intent data) {
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		tv_nickname.setText(spu.getTemp_nickname());
		tv_account.setText(spu.getTemp_account());
		tv_significances.setText(spu.getTemp_significance());
		tv_orginPwd.setText(spu.getTemp_orginPwd());
		tv_newPwd.setText(spu.getTemp_newPwd());
		tv_rePwd.setText(spu.getTemp_rePwd());
		
		//结果码不等于取消时候
		switch (requestCode) {
			case ActivityForResultUtil.REQUESTCODE_USERINFO_IMAGE:
				startPhotoZoom(data.getData());
				break;
			case ActivityForResultUtil.REQUESTCODE_USERINFO_CAMERA:
				startPhotoZoom(data.getData());
				break;
			case ActivityForResultUtil.REQUESTCODE_USERINFO_RESULT:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		mActivity.startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			File file = new File(mActivity.getFilesDir(),BaseApplication.FACEIMAGE_FILE_NAME);
			PhotoUtil.saveBitmap(photo,file);
			Drawable drawable = new BitmapDrawable(photo);
			ib_avatar.setImageDrawable(drawable);
			SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
			spu.setAvatarImage(false);
		}
	}
	

}

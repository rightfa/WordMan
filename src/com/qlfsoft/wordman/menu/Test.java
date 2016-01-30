package com.qlfsoft.wordman.menu;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Test {

	private Context mContext;
	private Activity mActivity;
	private View mTest;
	private OnOpenListener mOnOpenListener;
	
	private LinearLayout ll_top;
	private ImageButton ib_menu;
	private TextView tv_remainSize;
	private TextView tv_correct;
	private TextView tv_wrong;
	private ProgressBar pb_haveTest;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private ListView lv_selectors;
	public Test(Context context,Activity activity)
	{
		mContext = context;
		mActivity = activity;
		mTest = LayoutInflater.from(mContext).inflate(R.layout.activity_test, null);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	public View getView() {
		return mTest;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}

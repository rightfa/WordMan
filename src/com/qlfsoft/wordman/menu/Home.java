package com.qlfsoft.wordman.menu;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Home {

	private Context mContext;
	private Activity mActivity;
	private View mHome;
	
	private ImageButton btn_menu;
	private ImageButton btn_search;
	private LinearLayout top_layout;
	private TextView tv_username;
	private TextView tv_sign;
	private TextView tv_chgPlan;
	private TextView tv_remainDay;
	private TextView tv_dayCount;
	private Button btn_start;
	private TextView tv_needStudy;
	private TextView tv_haveStudy;
	private ProgressBar pb_haveStudy;
	private TextView tv_dailyword_en;
	private TextView tv_dailyword_cn;
	private TextView tv_dailyword_from;
	
	private OnOpenListener mOnOpenListener;
	
	public Home(Context context, Activity activity) {
		mContext = context;
		mActivity = activity;
		mHome = LayoutInflater.from(context).inflate(R.layout.home, null);
		findViewById();
		setListener();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}

	private void setListener() {
		btn_menu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
			
		});
		btn_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		tv_sign.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		tv_chgPlan.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_start.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void findViewById() {
		btn_menu = (ImageButton) mHome.findViewById(R.id.home_menu);
		btn_search = (ImageButton) mHome.findViewById(R.id.home_search);
		top_layout= (LinearLayout) mHome.findViewById(R.id.home_top_layout);
		tv_username = (TextView) mHome.findViewById(R.id.home_user_name);
		tv_sign = (TextView) mHome.findViewById(R.id.home_tv_sign);
		tv_chgPlan = (TextView) mHome.findViewById(R.id.home_tv_chgplan);
		tv_remainDay = (TextView) mHome.findViewById(R.id.home_remain_day);
		tv_dayCount = (TextView) mHome.findViewById(R.id.home_day_count);
		btn_start = (Button) mHome.findViewById(R.id.home_btn_start);
		tv_needStudy = (TextView) mHome.findViewById(R.id.home_tv_needstudy);
		tv_haveStudy = (TextView) mHome.findViewById(R.id.home_tv_havestudy);
		pb_haveStudy = (ProgressBar) mHome.findViewById(R.id.home_pb_havestudy);
		tv_dailyword_en = (TextView) mHome.findViewById(R.id.home_tv_dailyword_en);
		tv_dailyword_cn = (TextView) mHome.findViewById(R.id.home_tv_dailyword_ch);
		tv_dailyword_from = (TextView) mHome.findViewById(R.id.home_tv_dailyword_from);
		
	}
	
	public View getView() {
		return mHome;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}

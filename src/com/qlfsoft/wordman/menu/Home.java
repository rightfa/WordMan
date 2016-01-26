package com.qlfsoft.wordman.menu;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.IPlanObserver;
import com.qlfsoft.wordman.IUserInfoObserver;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.ui.BookCompleteActivity;
import com.qlfsoft.wordman.ui.WordActivity;
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
import android.widget.ProgressBar;
import android.widget.TextView;

public class Home implements IPlanObserver,IUserInfoObserver{

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
		tv_username.setText(BaseApplication.nickName);
		tv_remainDay.setText(String.valueOf(BaseApplication.remainDay));
		tv_dayCount.setText(String.valueOf(BaseApplication.dailyWord));
		tv_haveStudy.setText("已完成 " + BaseApplication.haveStudy + "/" + BaseApplication.wordSize);
		pb_haveStudy.setMax(BaseApplication.wordSize);
		pb_haveStudy.setProgress(BaseApplication.haveStudy);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateformat.format(new Date());
		List<UserWords> todayWords = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).find(UserWords.class);
		int todaySize = todayWords.size();//今日已经学习的单词数
		List<UserWords> beforeWords = DataSupport.where("account=? and bookId=? and date([date])<>date(?) and repeat<=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,"4").order("upateDate asc").order("repeat desc").find(UserWords.class);
		int beforeSize = beforeWords.size();//前面还未学习完全的单词数
		final int reviewSize = beforeSize / 3 * 2;//今日需要复习的单词数
		List<UserWords> reviewedWords = DataSupport.where("account=? and bookId=? and date(upateDate)=date(?) and date([date])<>date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,today).find(UserWords.class);
		final int reviewedSize = reviewedWords.size();//今日已经复习的单词数
		String strLog = String.format("今日需新学%d/%d  今日需复习%d/%d",BaseApplication.dailyWord - todaySize,BaseApplication.dailyWord,reviewedSize,reviewSize);
		tv_needStudy.setText(strLog);
		
		btn_start.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(BaseApplication.wordSize <= BaseApplication.haveStudy)//如果已经学习完成
				{
					intent.setClass(mContext, BookCompleteActivity.class);
				}else
				{
					intent.setClass(mContext, WordActivity.class);
				}
				mContext.startActivity(intent);
				mActivity.finish();
			}
			
		});
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

	@Override
	public void userInfoUpdate() {
		init();
		
	}

	@Override
	public void planUpdate() {
		init();
	}
}

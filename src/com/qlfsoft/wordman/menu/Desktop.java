package com.qlfsoft.wordman.menu;

import java.io.File;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.utils.PhotoUtil;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.utils.ViewUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Desktop {

	private Context mContext;
	private Activity mActivity;
	
	/*
	 * 当前界面的View
	 */
	private View mDesktop;
	
	private LinearLayout mWallpaper;
	private RelativeLayout mTopLayout;
	private ImageView mAvatar;
	private TextView mName;
	private TextView mSig;
	private ListView mDisplay;
	private ImageView mSetUp;
	
	public interface onChangeViewListener{
		public abstract void onChangeView(int arg0);
	}
	private DesktopAdapter mAdapter;
	
	private onChangeViewListener mOnChangeViewListener;
	
	public Desktop(Context context,Activity activity)
	{
		mContext = context;
		mActivity = activity;
		mDesktop = LayoutInflater.from(context).inflate(R.layout.desktop, null);
		findViewById();
		setListener();
		init();
	}
	
	private void findViewById()
	{
		mWallpaper = (LinearLayout) mDesktop.findViewById(R.id.desktop_wallpager);
		mTopLayout = (RelativeLayout) mDesktop.findViewById(R.id.desktop_top_layout);
		mAvatar = (ImageView) mDesktop.findViewById(R.id.desktop_avatar);
		mName = (TextView) mDesktop.findViewById(R.id.desktop_name);
		mSig = (TextView) mDesktop.findViewById(R.id.desktop_sig);
		mDisplay = (ListView) mDesktop.findViewById(R.id.desktop_display);
		mSetUp = (ImageView) mDesktop.findViewById(R.id.desktop_set_up);
		
	}
	
	private void setListener()
	{
		mTopLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(null != mOnChangeViewListener)
				{
					mOnChangeViewListener.onChangeView(ViewUtil.USER);
					mAdapter.setChoose(-1);
					mAdapter.notifyDataSetChanged();
				}
				
			}
			
		});
		
		mSetUp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
	}
	
	private void init()
	{
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		mName.setText(spu.getNickName());
		mSig.setText(spu.getSignificance());
		mAvatar.setImageBitmap(PhotoUtil.toRoundCorner(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.head), 15));
		File file = new File(mActivity.getFilesDir(),BaseApplication.FACEIMAGE_FILE_NAME);
		if(!spu.getAvatarImage() && file.exists())
		{
			Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
			mAvatar.setImageBitmap(PhotoUtil.toRoundCorner(bmp,15));	
		}
		mAdapter = new DesktopAdapter(mContext);
		mDisplay.setAdapter(mAdapter);
	}
	
	public void setOnChangeViewListener(onChangeViewListener listener)
	{
		mOnChangeViewListener = listener;
	}
	
	/**
	 * 获取菜单界面
	 * @return
	 */
	public View getView()
	{
		return mDesktop;
	}
	
	
	public class DesktopAdapter extends BaseAdapter{

		private Context dContext;
		private String[] mName = {"首页","我的计划","进度","词库","单词量测试"};
		private int[] mIcon = {R.drawable.sidebar_icon_dynamic,R.drawable.sidebar_icon_myplan,R.drawable.sidebar_icon_process,
				R.drawable.sidebar_icon_library,R.drawable.sidebar_icon_test};
		private int[] mIconPressed = {R.drawable.sidebar_icon_dynamic_pressed,R.drawable.sidebar_icon_myplan_pressed,R.drawable.sidebar_icon_process_pressed,
				R.drawable.sidebar_icon_library_pressed,R.drawable.sidebar_icon_test_pressed};
		
		private int mChoose = 0;
		
		public DesktopAdapter(Context context)
		{
			dContext = context;
		}
		
		@Override
		public int getCount() {
			
			return mName.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setChoose(int choose)
		{
			mChoose = choose;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView == null)
			{
				convertView = LayoutInflater.from(dContext).inflate(R.layout.desktop_item, null);
				holder = new ViewHolder();
				holder.layout = (LinearLayout) convertView.findViewById(R.id.desktop_item_layout);
				holder.icon = (ImageView) convertView.findViewById(R.id.desktop_item_icon);
				holder.name = (TextView) convertView.findViewById(R.id.desktop_item_name);
				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mName[position]);
			if(position == mChoose)
			{
				holder.name.setTextColor(Color.parseColor("#ffffffff"));
				holder.icon.setImageResource(mIconPressed[position]);
				holder.layout.setBackgroundColor(Color.parseColor("#20000000"));
			}else
			{
				holder.name.setTextColor(Color.parseColor("#7fffffff"));
				holder.icon.setImageResource(mIcon[position]);
				holder.layout.setBackgroundColor(Color.parseColor("#00000000"));
			}
			convertView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(null != mOnChangeViewListener)
					{
						switch(position)
						{
						case ViewUtil.HOME:
							mOnChangeViewListener.onChangeView(ViewUtil.HOME);
							break;
						case ViewUtil.MYPLAN:
							mOnChangeViewListener.onChangeView(ViewUtil.MYPLAN);
							break;
						case ViewUtil.PROCESS:
							mOnChangeViewListener.onChangeView(ViewUtil.PROCESS);
							break;
						case ViewUtil.LIBRARY:
							mOnChangeViewListener.onChangeView(ViewUtil.LIBRARY);
							break;
						case ViewUtil.TEST:
							mOnChangeViewListener.onChangeView(ViewUtil.TEST);
							break;
						default:
							mOnChangeViewListener.onChangeView(ViewUtil.HOME);
							break;
						}
						mChoose = position;
						notifyDataSetChanged();
					}
					
				}
				
			});
			
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout layout;
			ImageView icon;
			TextView name;
		}
		
	}
	
}

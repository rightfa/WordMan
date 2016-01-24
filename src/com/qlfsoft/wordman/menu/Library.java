package com.qlfsoft.wordman.menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.ui.WordInfoActivity;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

public class Library {

	private Context mContext;
	private Activity mActivity;
	private View mLibrary;
	private View mPopView;
	private ListView popup_lv;
	
	private OnOpenListener mOnOpenListener;
	private PopupWindow mPopupWindow;
	private ImageButton ib_menu;
	private LinearLayout ll_top;
	private TextView tv_top;
	private ListView lv_words;
	private String[] mPopupWindowItems = new String[]{"今日单词","已学单词","单词本"};
	private int wordsLength = 0;
	private String today= "";
	private DictionaryDBHelper dbHelper = DictionaryDBHelper.getInstance();
	private ListViewAdapter lvAdapter;
	private int itemFlag = 0;//当前的选项
	
	public Library(Context context,Activity activity)
	{
		this.mContext = context;
		mActivity = activity;
		mLibrary = LayoutInflater.from(mContext).inflate(R.layout.activity_library, null);
		initView();
		initData();
		setListener();
	}
	
	
	
	private void initView() {
		ib_menu = (ImageButton) mLibrary.findViewById(R.id.library_menu);
		ll_top = (LinearLayout) mLibrary.findViewById(R.id.library_top_layout);
		tv_top = (TextView) mLibrary.findViewById(R.id.library_tv_top);
		lv_words = (ListView) mLibrary.findViewById(R.id.library_lv_words);
		mPopView = LayoutInflater.from(mContext).inflate(R.layout.library_popupwindow, null);
		popup_lv = (ListView) mPopView.findViewById(R.id.library_popupwindow_display);
	}



	private void initData() {
		PopupAdapter popupAdapter = new PopupAdapter();
		popup_lv.setAdapter(popupAdapter);
		mPopupWindow = new PopupWindow(mPopView,ll_top.getWidth(),LayoutParams.WRAP_CONTENT,true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		today = dateformat.format(new Date());
		wordsLength = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).count(UserWords.class);
		lvAdapter = new ListViewAdapter();
		lv_words.setAdapter(lvAdapter);
	}



	private void setListener() {
		ll_top.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				if(mPopupWindow.isShowing())
				{
					mPopupWindow.dismiss();
				}else
				{
					mPopupWindow.showAsDropDown(ll_top,0,-10);
				}
			}
		});
		popup_lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				mPopupWindow.dismiss();
				tv_top.setText(mPopupWindowItems[position]);
				itemFlag = position;
				switch(position)
				{
				case 0:
					wordsLength = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).count(UserWords.class);
					break;
				case 1:
					wordsLength = DataSupport.where("account=? and bookId=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId)).count(UserWords.class);
					break;
				case 2:
					wordsLength = dbHelper.getBookById(BaseApplication.curBookId).getBookCount();
					break;
				}
				lvAdapter.notifyDataSetChanged();
			}
			
		});
		ib_menu.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mOnOpenListener.open();
			}
		});
		
		lv_words.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				int wordId = 0;
				switch(itemFlag)
				{
					case 0:
					{
						List<UserWords> words = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).limit(1).offset(position).find(UserWords.class);
						wordId = words.get(0).getWordId();
					}
					break;
					case 1:
					{
						List<UserWords> words = DataSupport.where("account=? and bookId=? ",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId)).limit(1).offset(position).find(UserWords.class);
						wordId = words.get(0).getWordId();
					}
					break;
					case 2:
						wordId = dbHelper.getWordIdByBookOrder(BaseApplication.curBookId, position);
					break;
				}
				
				WordModel word = dbHelper.getWordById(wordId);
				Intent intent = new Intent(mContext,WordInfoActivity.class);
				intent.putExtra("WORDMODEL", word);
				mActivity.startActivity(intent);
				
			}
		});
	}



	public View getView() {
		return mLibrary;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
	private class PopupAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPopupWindowItems.length;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PopupHolder holder = null;
			if(null == convertView)
			{
				convertView = LayoutInflater.from(mContext).inflate(R.layout.library_popupwindow_item, null);
				holder = new PopupHolder();
				holder.tv_name = (TextView) convertView.findViewById(R.id.library_popupwindow_item_name);
				convertView.setTag(holder);
			}else{
				holder = (PopupHolder) convertView.getTag();
			}
			holder.tv_name.setText(mPopupWindowItems[position]);
			return convertView;
		}
		
	}
	
	class PopupHolder{
		TextView tv_name;
	}
	
	private class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return wordsLength;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ListViewHolder holder = null;
			if(null == convertView)
			{
				holder = new ListViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.library_listview_item, null);
				holder.item_bg = (LinearLayout) convertView.findViewById(R.id.library_lv_item_ll_bg);
				holder.tv_word = (TextView) convertView.findViewById(R.id.library_lv_item_word);
				holder.tv_phonetic = (TextView) convertView.findViewById(R.id.library_lv_item_phonetic);
				holder.tv_description = (TextView) convertView.findViewById(R.id.library_lv_item_description);
				convertView.setTag(holder);
			}else
			{
				holder = (ListViewHolder) convertView.getTag();
			}
			int wordId = 0;
			switch(itemFlag)
			{
				case 0:
				{
					List<UserWords> words = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).limit(1).offset(position).find(UserWords.class);
					wordId = words.get(0).getWordId();
				}
				break;
				case 1:
				{
					List<UserWords> words = DataSupport.where("account=? and bookId=? ",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId)).limit(1).offset(position).find(UserWords.class);
					wordId = words.get(0).getWordId();
				}
				break;
				case 2:
					wordId = dbHelper.getWordIdByBookOrder(BaseApplication.curBookId, position);
				break;
			}
			
			WordModel word = dbHelper.getWordById(wordId);
			holder.tv_word.setText(word.getWord());
			holder.tv_phonetic.setText(word.getPhonetic());
			holder.tv_description.setText(word.getDescription());
			
			if(position%2 == 0)
			{
				holder.item_bg.setBackgroundColor(mContext.getResources().getColor(R.color.app_color));
				holder.tv_word.setTextColor(Color.parseColor("#ffffff"));
				holder.tv_phonetic.setText(Color.parseColor("#ffffff"));
				holder.tv_description.setText(Color.parseColor("#ffffff"));
			}else
			{
				holder.item_bg.setBackgroundColor(mContext.getResources().getColor(R.color.light_app_color));
				holder.tv_word.setTextColor(Color.parseColor("#000000"));
				holder.tv_phonetic.setText(Color.parseColor("#000000"));
				holder.tv_description.setText(Color.parseColor("#000000"));
			}
			return convertView;
		}
		
	}
	
	class ListViewHolder
	{
		LinearLayout item_bg;
		TextView tv_word;
		TextView tv_phonetic;
		TextView tv_description;
	}
}

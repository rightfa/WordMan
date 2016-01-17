package com.qlfsoft.wordman.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class WordActivity extends BaseActivity {

	private ImageButton ib_menu;
	private TextView tv_log;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private RelativeLayout rl_new;
	private TextView tv_description;
	private Button btn_next1;
	private Button btn_know;
	private Button btn_unknow;
	private LinearLayout ll_review;
	private ListView lv_selectors;
	private Button btn_next2;
	private int type;//1是新词，2是复习
	private String word;//单词
	private String phonetic;//音标
	private String description;//解释
	private List<String> selectors;//选项
	private int select_index = -1;//默认listview没有选中
	private TextToSpeech tts;
	private int orderNo;//排序数
	private WordModel wordModel;
	private Animator animFadeOut = null;
	private Animator animFadeIn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		animFadeOut = AnimatorInflater.loadAnimator(this, R.animator.objectfadeout);
		animFadeIn = AnimatorInflater.loadAnimator(this, R.animator.objectfadein);
		initView();
		initData();
		setListener();
	}

	@SuppressLint("SimpleDateFormat")
	private void initData() {
		animFadeIn.setTarget(tv_word);
		animFadeIn.start();
		lv_selectors.setClickable(true);
		select_index = -1;
		final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		final String today = dateformat.format(new Date());
		List<UserWords> todayWords = DataSupport.where("account=? and bookId=? and date=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).find(UserWords.class);
		List<UserWords> beforeWords = DataSupport.where("account=? and bookId=? and date<>? and repeat<=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,"4").order("upateDate asc").order("repeat desc").find(UserWords.class);
		final int beforeSize = beforeWords.size();//前面还未学习完全的单词数
		final int reviewSize = beforeSize / 3 * 2;//今日需要复习的单词数
		List<UserWords> reviewedWords = DataSupport.where("account=? and bookId=? and upateDate=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).find(UserWords.class);
		final int reviewedSize = reviewedWords.size();//今日已经复习的单词数
		String strLog = String.format("今日需新学%d/%d  今日需复习%d/%d",BaseApplication.dailyWord - todayWords.size(),BaseApplication.dailyWord,reviewedSize,reviewSize);
		tv_log.setText(strLog);
		DictionaryDBHelper db = DictionaryDBHelper.getInstance();
		int wordId = 0;
		if(reviewedSize >= reviewSize)
			type = 1;
		else
			type = 2;
		switch(type)
		{
		case 1://新词
			rl_new.setVisibility(View.VISIBLE);
			ll_review.setVisibility(View.INVISIBLE);
			btn_next1.setVisibility(View.INVISIBLE);
			btn_know.setVisibility(View.VISIBLE);
			btn_unknow.setVisibility(View.VISIBLE);
			tv_description.setVisibility(View.INVISIBLE);
			
			int before_maxOrder = DataSupport.where("account=? and bookId=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId)).max(UserWords.class, "orderNo", int.class);
			orderNo = before_maxOrder + 1;
			wordId = db.getWordId(BaseApplication.curBookId, orderNo);
		
			break;
		case 2://复习
			rl_new.setVisibility(View.INVISIBLE);
			ll_review.setVisibility(View.VISIBLE);
			btn_next2.setVisibility(View.INVISIBLE);

			final UserWords userWord = beforeWords.get(0);
			wordId = userWord.getWordId();
			selectors = db.get4Selectors(wordId,0);
			final DescriptionsAdapter adapter = new DescriptionsAdapter();
			lv_selectors.setAdapter(adapter);
			lv_selectors.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					lv_selectors.setClickable(false);
					try {
						userWord.setUpateDate(dateformat.parse(today));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(selectors.get(position).equals(description))
					{
						userWord.setRepeat(userWord.getRepeat() + 1);
						userWord.updateAll("account=? and bookId=? and wordId=?",userWord.getAccount(),String.valueOf(userWord.getBookId()),String.valueOf(userWord.getWordId()));
						if((reviewedSize + 1) >=  reviewSize)
						{
							type = 1;
						}else
						{
							type = 2;
						}
						FadeOut();
						initData();
						
						
					}else
					{
						select_index = position;
						btn_next2.setVisibility(View.VISIBLE);						
						userWord.updateAll("account=? and bookId=? and wordId=?",userWord.getAccount(),String.valueOf(userWord.getBookId()),String.valueOf(userWord.getWordId()));
						adapter.notifyDataSetChanged();
					}
					
				}
				
			});
			break;
		}
		wordModel = db.getWordById(wordId);
		word = wordModel.getWord();
		phonetic = wordModel.getPhonetic();
		description = wordModel.getDescription();
		tv_word.setText(word);
		tv_phonetic.setText((null == phonetic)?"": phonetic); 
		tv_description.setText(description);
	}

	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WordActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				
			}
			
		});
		tts = new TextToSpeech(WordActivity.this,new OnInitListener()
		{

			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS)
				{
					int result = tts.setLanguage(Locale.US);
				}
				
			}
			
		});
		btn_sound.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);
				
			}
			
		});
		btn_next1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				type = 1;
				
				FadeOut();
				initData();
			}
			
		});
		btn_next2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				type = 2;
				
				FadeOut();
				initData();
			}
			
		});
		btn_know.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tv_description.setVisibility(View.VISIBLE);
				btn_next1.setVisibility(View.VISIBLE);
				UserWords userWord = new UserWords();
				userWord.setAccount(BaseApplication.userAccount);
				userWord.setBookId(BaseApplication.curBookId);
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				String today = dateformat.format(new Date());
				try {
					userWord.setDate(dateformat.parse(today));
					userWord.setUpateDate(dateformat.parse(today));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				userWord.setOrderNo(orderNo);
				userWord.setRepeat(1);
				userWord.setWordId(wordModel.getWordId());
				userWord.save();
				btn_know.setVisibility(View.INVISIBLE);
				btn_unknow.setVisibility(View.INVISIBLE);
			}
			
		});
		btn_unknow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {		
				UserWords userWord = new UserWords();
				userWord.setAccount(BaseApplication.userAccount);
				userWord.setBookId(BaseApplication.curBookId);
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				String today = dateformat.format(new Date());
				try {
					userWord.setDate(dateformat.parse(today));
					userWord.setUpateDate(dateformat.parse(today));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				userWord.setOrderNo(orderNo);
				userWord.setRepeat(0);
				userWord.setWordId(wordModel.getWordId());
				userWord.save();
				Intent intent = new Intent(WordActivity.this,WordInfoActivity.class);
				intent.putExtra("WORDMODEL", wordModel);
				startActivity(intent);
				finish();
			}
			
		});
	}

	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.word_menu);
		tv_log = (TextView) findViewById(R.id.word_tv_log);
		btn_sound = (Button) findViewById(R.id.word_btn_sound);
		tv_word = (TextView) findViewById(R.id.word_tv_word);
		tv_phonetic = (TextView) findViewById(R.id.word_tv_phonetic);
		rl_new = (RelativeLayout) findViewById(R.id.word_rl_new);
		tv_description = (TextView) findViewById(R.id.word_tv_description);
		btn_next1 = (Button) findViewById(R.id.word_btn_next1);
		btn_know = (Button) findViewById(R.id.word_btn_konw);
		btn_unknow = (Button) findViewById(R.id.word_btn_unkonw);
		ll_review = (LinearLayout) findViewById(R.id.word_ll_review);
		lv_selectors = (ListView) findViewById(R.id.word_lv_selectors);
		btn_next2 = (Button) findViewById(R.id.word_btn_next2);

	}
	
	private void FadeOut()
	{
		animFadeOut.setTarget(tv_word);
		animFadeOut.start();
	}
	
	class DescriptionsAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return selectors.size();
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
			ViewHolder holder = null;
			if(null == convertView)
			{
				convertView = LayoutInflater.from(WordActivity.this).inflate(R.layout.word_lv_selectors_item, null);
				holder = new ViewHolder();
				holder.tv_item = (TextView) convertView.findViewById(R.id.word_lv_selectors_item_tv);
				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_item.setText(selectors.get(position));
			
			if(select_index == position)
			{
				holder.tv_item.setBackgroundColor(Color.parseColor("#f8c3c9"));
			}
			
			return convertView;
		}
		
	}
	
	class ViewHolder 
	{
		TextView tv_item;
	}
}

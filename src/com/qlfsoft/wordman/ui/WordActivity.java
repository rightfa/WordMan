package com.qlfsoft.wordman.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.litepal.crud.DataSupport;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserModel;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
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
	private LinearLayout rl_new;
	private TextView tv_description;
	private Button btn_next1;
	private Button btn_know;
	private Button btn_unknow;
	private LinearLayout ll_review;
	private ListView lv_selectors;
	private Button btn_next2;
	private int type;//1是新词，2是复习
	private List<String> selectors;//选项
	private int select_index = -1;//默认listview没有选中
	private TextToSpeech tts;
	private int orderNo;//排序数
	private WordModel wordModel;
	private Animator animFadeOut1 = null;
	private Animator animFadeIn1 = null;
	private Animator animFadeOut2 = null;
	private Animator animFadeIn2 = null;
	private String today= "";//今日的日期格式
	private int todayWordsSize;//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		animFadeOut1 = AnimatorInflater.loadAnimator(this, R.animator.objectfadeout);
		animFadeIn1 = AnimatorInflater.loadAnimator(this, R.animator.objectfadein);
		animFadeOut2 = AnimatorInflater.loadAnimator(this, R.animator.objectfadeout);
		animFadeIn2 = AnimatorInflater.loadAnimator(this, R.animator.objectfadein);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		today = dateformat.format(new Date());
		initView();
		initData();
		setListener();
	}

	private void initData() {
		animFadeIn1.setTarget(tv_word);
		animFadeIn1.start();
		animFadeIn2.setTarget(tv_phonetic);
		animFadeIn2.start();
		lv_selectors.setClickable(true);
		select_index = -1;
		
		todayWordsSize = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).count(UserWords.class);
		
		if(todayWordsSize == BaseApplication.dailyWord)
		{
			Intent intent = new Intent(WordActivity.this,DailyCompleteActivity.class);
			startActivity(intent);
			finish();
		}
		
		List<UserWords> beforeWords = DataSupport.where("account=? and bookId=? and date([date])<>date(?) and repeat<=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,"4").order("upateDate asc").order("repeat desc").find(UserWords.class);
		final UserWords userWord = beforeWords.get(0);//前面第一个还未学习完全的单词
		final int beforeSize = beforeWords.size();//前面还未学习完全的单词数
		final int reviewSize = beforeSize / 3 * 2;//今日需要复习的单词数
		final int reviewedSize =DataSupport.where("account=? and bookId=? and date(upateDate)=date(?) and date([date])<>date(?) ",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,today).count(UserWords.class);//今日已经复习的单词数
		String strLog = String.format("今日需新学%d/%d  今日需复习%d/%d",BaseApplication.dailyWord - todayWordsSize,BaseApplication.dailyWord,reviewedSize,reviewSize);
		tv_log.setText(strLog);
		DictionaryDBHelper db = DictionaryDBHelper.getInstance();
		int wordId = 0;
		if(reviewedSize >= reviewSize)//如果已经复习完成则显示新词否则还是复习旧单词
			type = 1;
		else
			type = 2;
		final DescriptionsAdapter adapter = new DescriptionsAdapter();
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
			wordId = userWord.getWordId();
			selectors = db.get4Selectors(wordId,0);
			lv_selectors.setAdapter(adapter);
			break;
		}
		wordModel = db.getWordById(wordId);
		tv_word.setText(wordModel.getWord());
		tv_phonetic.setText((null == wordModel.getPhonetic())?"": wordModel.getPhonetic()); 
		tv_description.setText(wordModel.getDescription());
		
		lv_selectors.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lv_selectors.setClickable(false);
				userWord.setUpateDate(today);
				if(selectors.get(position).equals(wordModel.getDescription()))
				{
					userWord.setRepeat(userWord.getRepeat() + 1);
					userWord.updateAll("account=? and bookId=? and wordId=?",userWord.getAccount(),String.valueOf(userWord.getBookId()),String.valueOf(userWord.getWordId()));
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
					int result = tts.setLanguage(Locale.UK);
				}
				
			}
			
		});
		btn_sound.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tts.speak(wordModel.getWord(), TextToSpeech.QUEUE_FLUSH, null);
				
			}
			
		});
		btn_next1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				FadeOut();
				initData();
			}
			
		});
		btn_next2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
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
				userWord.setDate(today);
				userWord.setUpateDate(today);
				userWord.setOrderNo(orderNo);
				userWord.setRepeat(1);
				userWord.setWordId(wordModel.getWordId());
				userWord.save();
				BaseApplication.haveStudy = BaseApplication.haveStudy + 1;
				ContentValues values = new ContentValues();
				values.put("haveStudy", BaseApplication.haveStudy);
				DataSupport.updateAll(UserModel.class, values, "account=? and selBook=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId));
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
				userWord.setDate(today);
				userWord.setUpateDate(today);
				userWord.setOrderNo(orderNo);
				userWord.setRepeat(0);
				userWord.setWordId(wordModel.getWordId());
				userWord.save();
				BaseApplication.haveStudy = BaseApplication.haveStudy + 1;
				ContentValues values = new ContentValues();
				values.put("haveStudy", BaseApplication.haveStudy);
				DataSupport.updateAll(UserModel.class, values, "account=? and selBook=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId));
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
		rl_new = (LinearLayout) findViewById(R.id.word_rl_new);
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
		animFadeOut1.setTarget(tv_word);
		animFadeOut1.start();
		animFadeOut2.setTarget(tv_phonetic);
		animFadeOut2.start();
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
			if(selectors.get(position).equals(wordModel.getDescription()))
			{
				holder.tv_item.setBackgroundResource(R.color.app_color);
			}
			return convertView;
		}
		
	}
	
	class ViewHolder 
	{
		TextView tv_item;
	}
}

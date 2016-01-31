package com.qlfsoft.wordman.menu;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.ui.TestComleteActivity;
import com.qlfsoft.wordman.utils.ActivityForResultUtil;
import com.qlfsoft.wordman.utils.ConstantsUtil;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

public class Test {

	private Context mContext;
	private Activity mActivity;
	private View mTest;
	private OnOpenListener mOnOpenListener;
	
	private LinearLayout ll_top;
	private ImageButton ib_menu;
	private TextView tv_remainSize;//剩余测试的单词量
	private TextView tv_correct;
	private TextView tv_wrong;
	private ProgressBar pb_haveTest;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private ListView lv_selectors;//选项列表
	private List<WordModel> words = null;//随机选择的4个单词
	private int testWordSize = 100;//一次测试单词的总量
	private int testCorrectSize = 0;//测试正确的单词数
	private int testWrongSize = 0;//测试错误的单词数
	private int curIndex = 0;//当前选择的4个单词中的一个
	private TextToSpeech tts;
	
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
		ll_top = (LinearLayout) mTest.findViewById(R.id.test_top_layout);
		ib_menu = (ImageButton) mTest.findViewById(R.id.test_menu);
		tv_remainSize = (TextView) mTest.findViewById(R.id.test_tv_remainSize);
		tv_correct = (TextView) mTest.findViewById(R.id.test_tv_correct);
		tv_wrong = (TextView) mTest.findViewById(R.id.test_tv_wrong);
		pb_haveTest = (ProgressBar) mTest.findViewById(R.id.test_pb_haveTest);
		btn_sound = (Button) mTest.findViewById(R.id.test_btn_sound);
		tv_word = (TextView) mTest.findViewById(R.id.test_tv_word);
		tv_phonetic = (TextView) mTest.findViewById(R.id.test_tv_phonetic);
		lv_selectors = (ListView) mTest.findViewById(R.id.test_lv_selectors);
	}

	private void initData() {
		if((testCorrectSize + testWrongSize) >= testWordSize)
		{
			Intent intent = new Intent(mContext,TestComleteActivity.class);
			intent.putExtra("SCORE", testCorrectSize);
			mActivity.startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_TESTCOMPLETE);
			mActivity.finish();
		}
		Random r = new Random(System.currentTimeMillis() + testCorrectSize + testWrongSize);
		curIndex = r.nextInt(4);
		words = DictionaryDBHelper.getInstance().getRandom4Words();
		WordModel word = words.get(curIndex);
		tv_remainSize.setText(String.valueOf(testWordSize - testCorrectSize - testWrongSize));
		tv_correct.setText(String.valueOf(testCorrectSize));
		tv_wrong.setText(String.valueOf(testWrongSize));
		pb_haveTest.setMax(testCorrectSize + testWrongSize);
		pb_haveTest.setProgress(testWrongSize);
		tv_word.setText(word.getWord());
		tv_phonetic.setText(word.getPhonetic());
	}

	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mOnOpenListener.open();
			}
		});
		tts = new TextToSpeech(mContext,new OnInitListener(){

			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS)
				{
					tts.setLanguage(Locale.UK);
				}
			}
			
		});
		btn_sound.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				tts.speak(tv_word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		lv_selectors.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(position == curIndex)
				{
					testCorrectSize += 1;
				}else
				{
					testWrongSize += 1;
				}
				initData();
				
			}
		});
	}
	
	public View getView() {
		return mTest;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
	class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			words.size();
			return 0;
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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.word_lv_selectors_item, null);
				holder = new ViewHolder();
				holder.tv_item = (TextView) convertView.findViewById(R.id.word_lv_selectors_item_tv);
				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_item.setText(words.get(position).getWord());
			return convertView;
		}
		
	}
	
	class ViewHolder{
		TextView tv_item;
	}
}

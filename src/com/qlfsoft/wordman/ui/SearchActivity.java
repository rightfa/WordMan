package com.qlfsoft.wordman.ui;

import java.util.Locale;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.WordModel;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.utils.ToastUtils;
import com.qlfsoft.wordman.widget.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class SearchActivity extends BaseActivity {

	private ImageButton ib_menu;
	private ClearEditText et_input;
	private ScrollView sv_search;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private TextView tv_description;
	private TextToSpeech tts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initView();
		initData();
		setListener();
		
	}


	private void initView() {
		ib_menu = (ImageButton) findViewById(R.id.search_menu);
		et_input = (ClearEditText) findViewById(R.id.search_et);
		sv_search = (ScrollView) findViewById(R.id.search_sv);
		btn_sound = (Button) findViewById(R.id.search_btn_sound);
		tv_word = (TextView) findViewById(R.id.search_tv_word);
		tv_phonetic = (TextView) findViewById(R.id.search_tv_phonetic);
		tv_description = (TextView) findViewById(R.id.search_tv_description);
	}

	private void initData() {
		sv_search.setVisibility(View.INVISIBLE);
		tts = new TextToSpeech(this,new OnInitListener(){

			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS)
					tts.setLanguage(Locale.UK);
				
			}
			
		});
	}

	private void setListener() {
		ib_menu.setOnClickListener(new android.view.View.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		et_input.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch(event.getAction())
				{
				case KeyEvent.ACTION_UP:
					if(KeyEvent.KEYCODE_ENTER == keyCode)
					{
						String in = et_input.getText().toString().trim();
						WordModel wordModel = DictionaryDBHelper.getInstance().searchWord(in);
						if(null != wordModel)
						{
							sv_search.setVisibility(View.VISIBLE);
							tv_word.setText(wordModel.getWord());
							tv_phonetic.setText(wordModel.getPhonetic());
							tv_description.setText(wordModel.getDescription());
							et_input.setText("");
						}else
						{
							ToastUtils.showShort("请输入合法单词！");
						}
					}
					break;
				case KeyEvent.ACTION_DOWN:
					break;
				}
				return false;
			}
			
		});
		
		btn_sound.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tts.speak(tv_word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
			
		});
	}

}

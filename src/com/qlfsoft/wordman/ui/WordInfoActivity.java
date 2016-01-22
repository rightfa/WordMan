package com.qlfsoft.wordman.ui;

import java.util.Locale;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.WordModel;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WordInfoActivity extends BaseActivity {

	private WordModel wordModel;
	private ImageButton wordInfo_menu;
	private Button btn_sound;
	private TextView tv_word;
	private TextView tv_phonetic;
	private TextView tv_description;
	private TextView tv_sentence;
	private Button btn_next;
	private TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordinfo);
		wordModel = getIntent().getParcelableExtra("WORDMODEL");
		initView();
		initData();
		setListener();
	}

	private void initView() {
		wordInfo_menu = (ImageButton) findViewById(R.id.wordinfo_menu);
		btn_sound = (Button) findViewById(R.id.wordinfo_btn_sound);
		tv_word = (TextView) findViewById(R.id.wordinfo_tv_word);
		tv_phonetic = (TextView) findViewById(R.id.wordinfo_tv_phonetic);
		tv_description = (TextView) findViewById(R.id.wordinfo_tv_description);
		tv_sentence = (TextView) findViewById(R.id.wordinfo_tv_sentence);
		btn_next = (Button) findViewById(R.id.wordinfo_btn_next);
	}

	private void setListener() {
		wordInfo_menu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WordInfoActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
		btn_sound.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v)
			{
				tts.speak(wordModel.getWord(),TextToSpeech.QUEUE_FLUSH , null);
			}
		});
		btn_next.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(WordInfoActivity.this,WordActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void initData() {
		tv_word.setText(wordModel.getWord());
		tv_phonetic.setText(wordModel.getPhonetic());
		tv_description.setText(wordModel.getDescription());
		String sentence = wordModel.getSentence();
		if(null == sentence)
			sentence = "";
		tv_sentence.setText(Html.fromHtml(sentence));
		tts = new TextToSpeech(WordInfoActivity.this,new OnInitListener(){

			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS)
				{
					int result = tts.setLanguage(Locale.UK);
				}
			}
			
		});
	}

	
}

package com.qlfsoft.wordman.ui;

import java.util.ArrayList;
import java.util.List;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.model.BookCategory;
import com.qlfsoft.wordman.ui.fragment.SelCategoryFragment;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SelCategoryActivity extends FragmentActivity {

	private List<BookCategory> indicators;
	private TabPageIndicator tpi_indicator;
	private ViewPager vp_pager;
	private int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sel_category);
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		initData();
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sel_category, menu);
		return true;
	}
	
	private void initData()
	{
		DictionaryDBHelper dbHelper = DictionaryDBHelper.getInstance();
		indicators = new ArrayList<BookCategory>();
		indicators = dbHelper.getAllBookCats();
	}
	
	private void initView()
	{
		tpi_indicator = (TabPageIndicator) findViewById(R.id.activity_sel_category_indicator);
		vp_pager = (ViewPager) findViewById(R.id.activity_sel_category_pager);
		PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
		vp_pager.setAdapter(pageAdapter);
		tpi_indicator.setViewPager(vp_pager);
	}
	
	class PageAdapter extends FragmentPagerAdapter
	{

		@Override
		public CharSequence getPageTitle(int position) {
			return indicators.get(position).getCateName();
		}

		public PageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return SelCategoryFragment.newInstance(indicators.get(arg0).getCateID(),type);
		}

		@Override
		public int getCount() {
			return indicators.size();
		}
		
	}

}

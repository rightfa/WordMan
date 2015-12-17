package com.qlfsoft.wordman.ui;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.ui.fragment.SelCategoryFragment;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class SelCategoryActivity extends BaseActivity {

	private String[] indicators;
	private TabPageIndicator tpi_indicator;
	private ViewPager vp_pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sel_category);
		
		tpi_indicator = (TabPageIndicator) findViewById(R.id.activity_sel_category_indicator);
		vp_pager = (ViewPager) findViewById(R.id.activity_sel_category_pager);
		tpi_indicator.setViewPager(vp_pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sel_category, menu);
		return true;
	}
	
	class PageAdapter extends FragmentPagerAdapter
	{

		@Override
		public CharSequence getPageTitle(int position) {
			return indicators[position];
		}

		public PageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return SelCategoryFragment.newInstance(arg0);
		}

		@Override
		public int getCount() {
			return indicators.length;
		}
		
	}

}

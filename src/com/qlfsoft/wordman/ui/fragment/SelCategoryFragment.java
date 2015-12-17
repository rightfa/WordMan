package com.qlfsoft.wordman.ui.fragment;

import com.qlfsoft.wordman.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class SelCategoryFragment extends Fragment {

	int mNum;
	public static SelCategoryFragment newInstance(int num)
	{
		SelCategoryFragment fragment = new SelCategoryFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ?  getArguments().getInt("num") : 1;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container);
		GridView gv = (GridView) view.findViewById(R.id.frg_category_gv);
		CategoryAdapter adapter = new CategoryAdapter(inflater);
		gv.setAdapter(adapter);
		
		return view;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	class CategoryAdapter extends BaseAdapter
	{

		private LayoutInflater minflater;
		public CategoryAdapter(LayoutInflater inflater)
		{
			minflater = inflater;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}

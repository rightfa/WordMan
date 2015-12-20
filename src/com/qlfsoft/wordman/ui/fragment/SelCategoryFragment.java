package com.qlfsoft.wordman.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.BookBook;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.utils.LogUtils;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SelCategoryFragment extends Fragment {

	int mNum;
	private List<BookBook> books;
	private SharePreferenceUtils sp;
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
		books = new ArrayList<BookBook>();
		DictionaryDBHelper dbHelper = new DictionaryDBHelper();
		books = dbHelper.getBooksByCateId(mNum);
		sp = new SharePreferenceUtils();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, null);
		GridView gv = (GridView) view.findViewById(R.id.frg_category_gv);
		final CategoryAdapter adapter = new CategoryAdapter(getActivity());
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				int selBookId = books.get(position).getBookId();
				sp.setBookId(selBookId);
				adapter.notifyDataSetChanged();
			}
			
		});
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

		private Context mContext;
		public CategoryAdapter(Context ctx)
		{
			mContext = ctx;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return books.size();
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
			if(null != convertView)
			{
				holder = (ViewHolder) convertView.getTag();
			}else
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_category_item, null);
				holder.tv_name = (TextView) convertView.findViewById(R.id.frg_category_item_tv);
				holder.tv_count = (TextView) convertView.findViewById(R.id.frg_category_item_tv_count);
				holder.iv_sel = (ImageView) convertView.findViewById(R.id.frg_category_item_img_sel);
				convertView.setTag(holder);
			}
			BookBook book = books.get(position);
			holder.tv_name.setText(book.getBookName());
			//holder.tv_count.setText(book.getBookCount());
			if(sp.getSelBookId() == book.getBookId())
			{
				holder.iv_sel.setVisibility(View.VISIBLE);
			}else
			{
				holder.iv_sel.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}
		
	}
	
	class ViewHolder
	{
		public TextView tv_name;
		public TextView tv_count;
		public ImageView iv_sel;
	}
}

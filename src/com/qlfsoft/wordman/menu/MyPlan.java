package com.qlfsoft.wordman.menu;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import kankan.wheel.widget.WheelView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.BookBook;
import com.qlfsoft.wordman.model.UserBooks;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;
import com.qlfsoft.wordman.widget.HorizontalListView;

public class MyPlan {

	private Context mContext;
	private Activity mActivity;
	private View mMyPlan;
	
	private LinearLayout ll_top_layout;
	private ImageButton ib_menu;
	private TextView tv_bookedit;
	private HorizontalListView hlv_books;
	private Button btn_addbook;
	private TextView tv_submit_chgPlan;
	private WheelView wheel_dailyword;
	private WheelView wheel_needDay;
	
	private OnOpenListener mOnOpenListener;
	private List<UserBooks> myBooks;
	
	public MyPlan(Context ctx,Activity act)
	{
		this.mContext = ctx;
		this.mActivity = act;
		mMyPlan = LayoutInflater.from(ctx).inflate(R.layout.activity_plan, null);
		initView();
		setListener();
		init();
	}

	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(null != mOnOpenListener)
					mOnOpenListener.open();
			}
			
		});
		tv_bookedit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_addbook.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		tv_submit_chgPlan.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		hlv_books.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void init() {
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		int selBookId = spu.getSelBookId();
		String account = spu.getUserAccount();
		if(!("".equals(account)))
		{
			myBooks = DataSupport.where("account=?",account).find(UserBooks.class);
		}else{
			myBooks = new ArrayList<UserBooks>();
			UserBooks book = new UserBooks();
			book.setBookId(selBookId);
		}
		
		
		
	}

	private void initView() {
		ll_top_layout = (LinearLayout) mMyPlan.findViewById(R.id.plan_top_layout);
		ib_menu = (ImageButton) mMyPlan.findViewById(R.id.plan_menu);
		tv_bookedit = (TextView) mMyPlan.findViewById(R.id.plan_tv_bookedit);
		hlv_books = (HorizontalListView) mMyPlan.findViewById(R.id.plan_hlv_books);
		btn_addbook = (Button) mMyPlan.findViewById(R.id.plan_btn_addbook);
		tv_submit_chgPlan = (TextView) mMyPlan.findViewById(R.id.plan_tv_submit_chgPlan);
		wheel_dailyword = (WheelView) mMyPlan.findViewById(R.id.plan_wheel_dailyword);
		wheel_needDay = (WheelView) mMyPlan.findViewById(R.id.plan_wheel_needDay);
	}
	
	public View getView() {
		return mMyPlan;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
	class HLVAdapter extends BaseAdapter{

		private Context m_ctx;
		public HLVAdapter(Context ctx)
		{
			m_ctx = ctx;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myBooks.size();
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
				convertView = LayoutInflater.from(m_ctx).inflate(R.layout.activity_plan_item, null);
				holder.tv_name = (TextView) convertView.findViewById(R.id.plan_item_tv);
				holder.tv_account = (TextView) convertView.findViewById(R.id.plan_item_tv_count);
				holder.iv_sel = (ImageView) convertView.findViewById(R.id.plan_item_img_sel);
				convertView.setTag(holder);
			}
			int selBookId = SharePreferenceUtils.getInstnace().getSelBookId();
			int curBookId = myBooks.get(position).getBookId();
			if(selBookId == curBookId)
				holder.iv_sel.setVisibility(View.VISIBLE);
			else
				holder.iv_sel.setVisibility(View.INVISIBLE);
			
			BookBook book = DictionaryDBHelper.getInstance().getBookById(curBookId);
			holder.tv_name.setText(book.getBookName());
			holder.tv_account.setText(book.getBookCount());
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public TextView tv_name;
		public TextView tv_account;
		public ImageView iv_sel;
	}
}

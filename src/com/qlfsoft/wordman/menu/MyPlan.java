package com.qlfsoft.wordman.menu;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.BookBook;
import com.qlfsoft.wordman.model.UserBooks;
import com.qlfsoft.wordman.utils.DictionaryDBHelper;
import com.qlfsoft.wordman.utils.LogUtils;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.utils.ToastUtils;
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
	private boolean w_d1 = false;//标记每日单词量是否在滚动
	private boolean w_d2=false;//标记计划学习天数是否在滚动
	
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
				if(tv_bookedit.getText().equals("编辑"))//编辑状态
				{
					tv_bookedit.setText("完成");
					
				}else//保存内容
				{
					tv_bookedit.setText("编辑");
				}
				
			}
			
		});
		btn_addbook.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				
			}
			
		});
		tv_submit_chgPlan.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
				int tmp_dailyword = wheel_dailyword.getCurrentItem() + 10;
				int tmp_needDay = wheel_needDay.getCurrentItem() + 10;
				spu.setRemainDay((spu.getWordSize()-spu.getHaveStudy())/tmp_dailyword);
				spu.setTotalDay(tmp_needDay);
				spu.setDailyWord(tmp_dailyword);
			}
			
		});
	}

	private void init() {
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		int selBookId = spu.getSelBookId();
		int curBookSize = spu.getWordSize();//当前选中的单词本词汇量
		String account = spu.getUserAccount();
		if(!("".equals(account)))
		{
			myBooks = DataSupport.where("account=?",account).find(UserBooks.class);
		}
		if(null == myBooks || myBooks.size() == 0){
			myBooks = new ArrayList<UserBooks>();
			UserBooks book = new UserBooks();
			book.setBookId(selBookId);
			myBooks.add(book);
		}
		
		float hlv_dimen_item = mContext.getResources().getDimension(R.dimen.x100); 
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(hlv_dimen_item * myBooks.size()), (int)hlv_dimen_item);
		hlv_books.setLayoutParams(params);
		
		final HLVAdapter adapter = new HLVAdapter(mContext);
		hlv_books.setAdapter(adapter);
		hlv_books.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
				DictionaryDBHelper dbHelper = DictionaryDBHelper.getInstance();
				String account = spu.getUserAccount();
				if("".equals(account))
				{
					ToastUtils.showShort("请先去设置账号！");
					return;
				}
				int selBookId = myBooks.get(position).getBookId();
				//保存前一本单词本的内容
				int preBookId = spu.getSelBookId();
				UserBooks preBook = new UserBooks();
				preBook.setBookId(preBookId);
				preBook.setAccount(account);
				preBook.setDailyword(spu.getDailyWord());
				preBook.setHaveStudy(spu.getHaveStudy());
				preBook.setRemainDay(spu.getRemainDay());
				preBook.setTotalDay(spu.getTotalDay());
				if(DataSupport.where("account=? and bookId=?",account,String.valueOf(preBookId)).find(UserBooks.class).size() > 0)
				{
					preBook.updateAll("account=? and bookId=?",account,String.valueOf(preBookId));
				}else
				{
					preBook.save();
				}
				
				spu.setBookId(selBookId);
				BookBook bookInfo = dbHelper.getBookById(selBookId);
				int bookwords = bookInfo.getBookCount();
				spu.setWordSize(bookwords);
				List<UserBooks> tmpBooks = DataSupport.where("account=? and bookId=?",account,String.valueOf(selBookId)).find(UserBooks.class);
				if(tmpBooks.size() > 0)
				{
					spu.setDailyWord(tmpBooks.get(0).getDailyword());
					spu.setHaveStudy(tmpBooks.get(0).getHaveStudy());
					spu.setRemainDay(tmpBooks.get(0).getRemainDay());
					spu.setTotalDay(tmpBooks.get(0).getTotalDay());
					
				}else
				{
					spu.setDailyWord(50);
					spu.setHaveStudy(0);
					spu.setRemainDay((int)Math.ceil((float)bookwords / 50));
					spu.setTotalDay((int)Math.ceil((float)bookwords / 50));
				}
				adapter.notifyDataSetChanged();
				
			}
			
		});
		
		wheel_dailyword.setViewAdapter(new NumericWheelAdapter(mContext,10,300));
		wheel_needDay.setViewAdapter(new NumericWheelAdapter(mContext,1,365));
		resetWheels();
		
		wheel_dailyword.addChangingListener(new OnWheelChangedListener(){

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
//				if(!w_d1)
//				{
//					int tmp_dailyword = wheel_dailyword.getCurrentItem() + 10;
//					int tmp_w_item = (int)Math.ceil((float)(SharePreferenceUtils.getInstnace().getWordSize() / tmp_dailyword)) + 1;
//					wheel_needDay.setCurrentItem(tmp_w_item,true);
//				}
				
			}
			
		});
		wheel_dailyword.addScrollingListener(new OnWheelScrollListener(){

			@Override
			public void onScrollingStarted(WheelView wheel) {
				w_d1 = true;
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				w_d1 = false;
				LogUtils.Logv("dailyword:" + wheel_dailyword.getCurrentItem());
				int tmp_dailyword = wheel_dailyword.getCurrentItem() + 10;
				int tmp_w_item = (int)Math.ceil((float)(SharePreferenceUtils.getInstnace().getWordSize() / tmp_dailyword));
				if(tmp_w_item < 0 || tmp_w_item > 365)
				{
					ToastUtils.showShort("不切实际的计划不利于学单词哦！");
					resetWheels();
					return;
				}
				wheel_needDay.setCurrentItem(tmp_w_item,false);
			}
			
		});
		wheel_needDay.addScrollingListener(new OnWheelScrollListener(){

			@Override
			public void onScrollingStarted(WheelView wheel) {
				w_d2 = true;
				
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				w_d2 = false;	
				LogUtils.Logv("need_day:" + wheel_needDay.getCurrentItem());
				int tmp_needDay = wheel_needDay.getCurrentItem() + 1;
				int tmp_w_item = (int)Math.ceil((float)(SharePreferenceUtils.getInstnace().getWordSize() / tmp_needDay)) - 9;
				if(tmp_w_item < 0 || tmp_w_item > 290)
				{
					ToastUtils.showShort("不切实际的计划不利于学单词哦！");
					resetWheels();
					return;
				}
				wheel_dailyword.setCurrentItem(tmp_w_item,false);
			}
			
		});
		wheel_needDay.addChangingListener(new OnWheelChangedListener(){

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
//				if(!w_d2)
//				{
//					int tmp_needDay = wheel_needDay.getCurrentItem() + 1;
//					wheel_dailyword.setCurrentItem(SharePreferenceUtils.getInstnace().getWordSize() / tmp_needDay,true);
//				}
				
			}
			
		});
		
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
	
	private void resetWheels()
	{
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		wheel_dailyword.setCurrentItem(spu.getDailyWord() - 10);
		wheel_needDay.setCurrentItem(spu.getTotalDay() -1);
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
				holder.btn_delete = (Button) convertView.findViewById(R.id.plan_btn_delete);
				holder.btn_refresh = (Button) convertView.findViewById(R.id.plan_btn_refresh);
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
			holder.tv_account.setText(String.valueOf(book.getBookCount()));
			
			//删除单词本,如果是当前正在学习的单词本则不能删除
			holder.btn_delete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
				
			});
			//重置单词本学习内容
			holder.btn_refresh.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public TextView tv_name;
		public TextView tv_account;
		public ImageView iv_sel;
		public Button btn_delete;
		public Button btn_refresh;
	}
}

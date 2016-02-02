package com.qlfsoft.wordman.widget;

import com.qlfsoft.wordman.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher{

	private Drawable mClearDrawable;
	private boolean hasFocus;
	
	public ClearEditText(Context context) {
		this(context,null);
		
	}

	
	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}


	private void init() {
		mClearDrawable = getCompoundDrawables()[2];
		if(mClearDrawable == null)
		{
			mClearDrawable = getResources().getDrawable(R.drawable.edit_reset_bg);
		}
		mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);//默认设置隐藏图标
		setOnFocusChangeListener(this);//设置焦点改变的监听
		addTextChangedListener(this);
		
	}
	
   
	@Override 
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				
				if (touchable) {
					//里面写上自己想做的事情，也就是DrawableRight的触发事件
					this.setText("");
				}
			}
		}

		return super.onTouchEvent(event);
	}


	private void setClearIconVisible(boolean b) {
		Drawable right = b ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
		
	}


	public ClearEditText(Context context, AttributeSet attrs) {
		this(context,attrs,android.R.attr.editTextStyle);
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFocus = hasFocus;
		if(hasFocus)
		{
			setClearIconVisible(getText().length() > 0);
		}else
		{
			setClearIconVisible(false);
		}
	}
	
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
            	if(hasFocus){
            		setClearIconVisible(s.length() > 0);
            	}
    } 

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

}

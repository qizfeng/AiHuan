package com.zipingfang.aihuan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 禁止左右滑动的viewpager
 * 
 * @author qizfeng
 * @since 2015年9月25日11:12:02
 */
public class ViewPager extends android.support.v4.view.ViewPager {

	public ViewPager(Context context) {

		super(context);

	}

	public ViewPager(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// return false;
	// }
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v != this && v instanceof ViewPager) {
			return true;
		}
		return super.canScroll(v, checkV, dx, x, y);
	}
}

package com.zipingfang.aihuan.test;

import android.os.Handler;
import android.os.Message;

import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;

public class MyListener implements PullToRefreshLayout.OnRefreshListener ,PullToRefreshLayout.OnLoadMoreListener{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// 下拉刷新操作
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// 加载操作
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}

}

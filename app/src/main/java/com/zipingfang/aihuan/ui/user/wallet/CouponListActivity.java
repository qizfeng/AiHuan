package com.zipingfang.aihuan.ui.user.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Coupon;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.CouponListServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.CouponAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/22.
 */

public class CouponListActivity extends BaseActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{
    private PullableListView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<Coupon> mData = new ArrayList<>();
    private CouponAdapter mAdapter;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_coupon);
        lv_container =(PullableListView)findViewById(R.id.lv_container);
        refreshLayout =(PullToRefreshLayout)findViewById(R.id.refresh_view);
        lv_container.pullable = false;
        refreshLayout.setOnRefreshListener(this);
        initActionBar();
        loadData();
    }


    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("我的优惠券");
        btn_back.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void loadData(){
        final CouponListServerDao serverDao = new CouponListServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if(serverDao.isSucc){
                    mData = serverDao.coupons;
                    mAdapter = new CouponAdapter(CouponListActivity.this,mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else {
                    ToastUtils.show(CouponListActivity.this,serverDao.desc);
                }
            }
        });
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        loadData();

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}

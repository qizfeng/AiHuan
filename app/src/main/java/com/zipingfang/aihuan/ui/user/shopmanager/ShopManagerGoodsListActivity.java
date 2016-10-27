package com.zipingfang.aihuan.ui.user.shopmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.ShopGoods;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ShopGoodsServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.ShopGoodsAdapter;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/27.
 */

public class ShopManagerGoodsListActivity extends BaseActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{

    private PullableListView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<ShopGoods> mData = new ArrayList<>();
    private ShopGoodsAdapter mAdapter;
    private int pageIndex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_shop_goods);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initActionBar();
        refreshLayout.setOnRefreshListener(this);
        loadData();
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("店铺拍品管理");
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
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

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex=1;
        loadData();
    }

    private void loadData() {
        final ShopGoodsServerDao serverDao = new ShopGoodsServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.pageIndex=pageIndex+"";
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    if (serverDao.isSucc) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        if (!StringUtils.isEmpty(serverDao.desc))
                            ToastUtils.show(ShopManagerGoodsListActivity.this, serverDao.desc);
                        if (pageIndex == 1)
                            mData = new ArrayList<>();
                        else{
                            if(serverDao.shopGoodses.size()==0){
                                ToastUtils.show(ShopManagerGoodsListActivity.this,"没有更多数据");
                                return;
                            }
                        }
                        mData.addAll(serverDao.shopGoodses);
                        mAdapter = new ShopGoodsAdapter(ShopManagerGoodsListActivity.this, mData);
                        lv_container.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                        ToastUtils.show(ShopManagerGoodsListActivity.this, serverDao.desc);
                    }
                }else
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex++;
        loadData();
    }
}

package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Order;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.OrderListServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.OrderListAdapter;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private PullableListView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<Order> mData = new ArrayList<>();
    private OrderListAdapter mAdapter;
    private RadioGroup radioGroup;
    public static String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_order_list);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lv_container.pullable = false;
        refreshLayout.setOnRefreshListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        initActionBar();
        loadData(type);
        lv_container.setOnItemClickListener(this);
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("订单列表");
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order_id", mData.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_all:
                type = "";
                loadData(type);
                break;
            case R.id.rb_pay:
                type = "101";
                loadData(type);
                break;
            case R.id.rb_send_goods:
                type = "102";
                loadData(type);
                break;
            case R.id.rb_shou_goods:
                type = "201";
                loadData(type);
                break;
            case R.id.rb_complete:
                type = "401";
                loadData(type);
                break;
        }
    }

    private void loadData(String type) {
        final OrderListServerDao serverDao = new OrderListServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = type;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
//                    if (serverDao.isSucc) {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
//                        if (serverDao.mData.size() == 0) {
////                            ToastUtils.show(DepositActivity.this, "没有更多数据");
//                        } else {
                    if (!StringUtils.isEmpty(serverDao.desc))
                        ToastUtils.show(OrderListActivity.this, serverDao.desc);
                    mData = new ArrayList<Order>();
                    mData.addAll(serverDao.mData);
                    mAdapter = new OrderListAdapter(OrderListActivity.this, mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
//                        }
//                    } else {
//                        ToastUtils.show(DepositActivity.this, serverDao.desc);
//                    }
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        loadData(type);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}

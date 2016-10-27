package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.DepositServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.DepositAdapter;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class DepositActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener {
    private PullableListView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<Deposit> mData = new ArrayList<>();
    private DepositAdapter mAdapter;
    private RadioGroup radioGroup;
    public static String type = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_deposit);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lv_container.pullable = false;
        refreshLayout.setOnRefreshListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        initActionBar();
        loadData(type);
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("保证金");
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_all:
                type = "4";
                loadData(type);
                break;
            case R.id.rb_already_pay:
                type = "2";
                loadData(type);
                break;
            case R.id.rb_already_deduct:
                type = "3";
                loadData(type);
                break;
            case R.id.rb_back:
                type = "1";
                loadData(type);
                break;
        }
    }

    private void loadData(String type) {
        final DepositServerDao serverDao = new DepositServerDao(this);
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
                        ToastUtils.show(DepositActivity.this, serverDao.desc);
                    mData = new ArrayList<Deposit>();
                    mData.addAll(serverDao.mData);
                    mAdapter = new DepositAdapter(DepositActivity.this, mData);
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

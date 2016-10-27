package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.JoinPartyServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.JoinPartyAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class JoinPartyActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private PullableListView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<Party> mData = new ArrayList<>();
    private JoinPartyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_join);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initActionBar();
        lv_container.pullable = false;
        refreshLayout.setOnRefreshListener(this);
        loadData();
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("参拍列表");
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
        loadData();
    }

    private void loadData() {
        final JoinPartyServerDao serverDao = new JoinPartyServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    if (serverDao.isSucc) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        if (serverDao.mData.size() == 0) {
                            ToastUtils.show(JoinPartyActivity.this, "没有更多数据");
                        } else {
                            mData =new ArrayList<Party>();
                            mData.addAll(serverDao.mData);
                            mAdapter = new JoinPartyAdapter(JoinPartyActivity.this, mData);
                            lv_container.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }else {
                        ToastUtils.show(JoinPartyActivity.this, serverDao.desc);
                    }
                }
            }
        });
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}

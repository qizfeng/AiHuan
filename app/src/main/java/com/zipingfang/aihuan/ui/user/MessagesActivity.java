package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Message;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.MessageListServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.AddressListAdapter;
import com.zipingfang.aihuan.ui.adapter.MessageAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/29.
 */
public class MessagesActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private PullToRefreshLayout refreshLayout;
    private PullableListView lv_container;
    private List<Message> mData = new ArrayList<>();
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_messages);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        lv_container.pullable = false;
        initActionBar();
        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setCanPullUp(false);
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("消息中心");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        loadData();
    }

    private void loadData() {
        final MessageListServerDao serverDao = new MessageListServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (serverDao.isSucc) {
                    mData = new ArrayList<>();
                    mData.addAll(serverDao.mData);
                    mAdapter = new MessageAdapter(MessagesActivity.this, mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else
                    ToastUtils.show(MessagesActivity.this, serverDao.desc);
            }
        });
    }


    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}

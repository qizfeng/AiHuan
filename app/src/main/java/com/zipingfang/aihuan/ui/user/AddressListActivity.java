package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Address;
import com.zipingfang.aihuan.dao.AddressServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.ui.adapter.AddressListAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/9.
 */
public class AddressListActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_container;
    private List<Address> mData = new ArrayList<>();
    private AddressListAdapter mAdapter;
    private TextView tv_add_new_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_addresses);
        lv_container = (ListView) findViewById(R.id.lv_container);
        tv_add_new_addr = (TextView) findViewById(R.id.tv_add_new_addr);
        tv_add_new_addr.setOnClickListener(this);
        initActionBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 200);
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("收货地址管理");
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_add_new_addr:
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void loadData() {
        final AddressServerDao serverDao = new AddressServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    mData = new ArrayList<>();
                    mData.addAll(serverDao.mData);
                    mAdapter = new AddressListAdapter(AddressListActivity.this, mData, getUserId(AddressListActivity.this));
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else
                    ToastUtils.show(AddressListActivity.this, serverDao.desc);
            }
        });
    }

}

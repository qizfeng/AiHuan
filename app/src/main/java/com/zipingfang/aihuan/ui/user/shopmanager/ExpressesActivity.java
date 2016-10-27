package com.zipingfang.aihuan.ui.user.shopmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Express;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ExpressesServerDao;
import com.zipingfang.aihuan.ui.adapter.ExpressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/26.
 */

public class ExpressesActivity extends BaseActivity implements View.OnClickListener{
    private ListView lv_container;
    private List<Express> mData = new ArrayList<>();
    private ExpressAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_expresses);
        lv_container=(ListView)findViewById(R.id.lv_container);
        initActionBar();
        loadData();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("选择快递公司");
        btn_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void loadData(){
        final ExpressesServerDao serverDao = new ExpressesServerDao(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if(serverDao.isSucc){
                    mData = serverDao.expresses;
                    mAdapter = new ExpressAdapter(ExpressesActivity.this,mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}

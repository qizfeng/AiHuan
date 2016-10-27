package com.zipingfang.aihuan.ui.user.wallet;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.ValueCard;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ValueCardMyServerDao;
import com.zipingfang.aihuan.ui.adapter.ValueCardAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/21.
 */

public class ValueCardListActivity extends BaseActivity implements View.OnClickListener{
    private List<ValueCard>  valueCards = new ArrayList<>();
    private ListView lv_container;
    private ValueCardAdapter mAdapter;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_value_card);
        lv_container =(ListView)findViewById(R.id.lv_container);
        initActionBar();
//        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("储值卡");
        TextView tv_title_right = (TextView)actionBar.findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("购买");
        btn_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.tv_title_right:
                Intent intent = new Intent(ValueCardListActivity.this,ValueCardBuyActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loadData(){
        final ValueCardMyServerDao serverDao = new ValueCardMyServerDao(this);
        serverDao.mid= getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if(serverDao.isSucc){
                    valueCards = serverDao.valueCards;
                    mAdapter = new ValueCardAdapter(ValueCardListActivity.this,valueCards,false,getUserId(ValueCardListActivity.this));
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else
                    ToastUtils.show(ValueCardListActivity.this,serverDao.desc);
            }
        });
    }
}

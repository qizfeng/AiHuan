package com.zipingfang.aihuan.ui.user.shopmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ShopCountServerDao;
import com.zipingfang.aihuan.dao.ShopOrderServerDao;

/**
 * Created by zipingfang on 16/10/26.
 */

public class ShopManagerIndexActivity extends BaseActivity implements View.OnClickListener{
    private String orderCount="0";
    private String goodsCount="0";
    private TextView tv_order_count;
    private TextView tv_goods_count;
    private LinearLayout ll_order_manager;
    private LinearLayout ll_goods_manager;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_shop_manager);
        tv_goods_count = (TextView)findViewById(R.id.tv_goods_count);
        tv_order_count=(TextView)findViewById(R.id.tv_order_count);
        ll_goods_manager=(LinearLayout)findViewById(R.id.ll_goods_manager);
        ll_order_manager=(LinearLayout)findViewById(R.id.ll_order_manager);
        ll_goods_manager.setOnClickListener(this);
        ll_order_manager.setOnClickListener(this);
        initActionBar();
        loadData();
    }


    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("店铺管理");
        btn_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_goods_manager:
                if(!"0".equals(goodsCount)){
                    intent.setClass(ShopManagerIndexActivity.this, ShopManagerGoodsListActivity.class);
                    startActivity(intent);
                }else {

                }
                break;
            case R.id.ll_order_manager:

//                if(!"0".equals(orderCount)){
//                    intent.setClass(ShopManagerIndexActivity.this, ShopManagerOrderListActivity.class);
//                    startActivity(intent);
//                }else {
//
//                }
                intent.setClass(ShopManagerIndexActivity.this, ShopManagerOrderListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        final ShopCountServerDao serverDao = new ShopCountServerDao(this);
        serverDao.mid=getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if(serverDao.isSucc){
                    goodsCount= serverDao.goodsCount;
                    orderCount= serverDao.orderCount;
                    tv_goods_count.setText(serverDao.goodsCount);
                    tv_order_count.setText(serverDao.orderCount);
                }
            }
        });
    }
}

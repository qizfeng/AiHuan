package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Order;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.OrderDetailServerDao;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;

/**
 * Created by 峰 on 2016/10/9.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_buyer;
    private LinearLayout ll_send;
    private TextView tv_order_status;
    private TextView tv_order_no;
    private TextView tv_order_time;
    private TextView tv_pay_time;
    private TextView tv_send_time;
    private TextView tv_logistics_no;

    private TextView tv_buyer_name;
    private TextView tv_buyer_phone;
    private TextView tv_buyer_address;
    private ImageView iv_img;
    private TextView tv_goods_name;
    private TextView tv_price;
    private TextView tv_price_type;
    private TextView tv_all_price;
    private TextView tv_deposit;
    private TextView tv_last_price;
    private TextView tv_real_price;
    private Button btn_send;

    private String order_id;
    private Order order = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_order_detail);
        order_id = getIntent().getStringExtra("order_id");
        initView();
        initActionBar();
        loadData();
    }

    private void initView() {
        ll_buyer = (LinearLayout) findViewById(R.id.ll_buyer);
        ll_send = (LinearLayout) findViewById(R.id.ll_send);
        iv_img = (ImageView) findViewById(R.id.iv_image);
        btn_send = (Button) findViewById(R.id.btn_send);
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_no = (TextView) findViewById(R.id.tv_order_no);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);
        tv_pay_time = (TextView) findViewById(R.id.tv_pay_time);
        tv_send_time = (TextView) findViewById(R.id.tv_send_time);
        tv_logistics_no = (TextView) findViewById(R.id.tv_logistics_no);
        tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);
        tv_buyer_phone = (TextView) findViewById(R.id.tv_buyer_phone);
        tv_buyer_address = (TextView) findViewById(R.id.tv_buyer_address);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price_type = (TextView) findViewById(R.id.tv_price_type);
        tv_all_price = (TextView) findViewById(R.id.tv_all_price);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        tv_last_price = (TextView) findViewById(R.id.tv_last_price);
        tv_real_price = (TextView) findViewById(R.id.tv_real_price);
        ll_send.setVisibility(View.GONE);
        ll_buyer.setVisibility(View.GONE);
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("订单详情");
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

    private void loadData() {
        final OrderDetailServerDao serverDao = new OrderDetailServerDao(this);
        serverDao.order_id = order_id;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    order = serverDao.order;
                    tv_order_status.setText(order.getStatus_txt());
                    tv_order_no.setText("订单编号:"+order.getId());
                    try {
                        tv_order_time.setText("订单生成时间:"+DateUtils.getCurrDate(Long.parseLong(order.getCreate_time())));
                        tv_pay_time.setText("付款时间:"+DateUtils.getCurrDate(Long.parseLong(order.getPay_success_time())));
                        tv_send_time.setText("发货时间:"+DateUtils.getCurrDate(Long.parseLong(order.getSend_time())));
                    } catch (Exception e) {

                    }
//                    tv_logistics_no.setText("物流单号:"+order.getlogi);
                    ImageLoader.getInstance().displayImage(order.getGoods_img(), iv_img, ImageLoaderConfig.normal);
                    tv_goods_name.setText(order.getGoods_name());
                    tv_price.setText("￥"+order.getTransaction_price());
                    tv_all_price.setText("￥"+order.getTransaction_price());
                    tv_deposit.setText("￥"+order.getDeposit());
                    tv_last_price.setText("￥"+order.getLast_money());
                    tv_real_price.setText("￥"+order.getLast_money());
                } else
                    ToastUtils.show(OrderDetailActivity.this, serverDao.desc);
            }
        });
    }
}

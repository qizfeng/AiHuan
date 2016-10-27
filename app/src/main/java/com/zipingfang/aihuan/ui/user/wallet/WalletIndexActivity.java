package com.zipingfang.aihuan.ui.user.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Wallet;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.WalletIndexServerDao;
import com.zipingfang.aihuan.utils.ToastUtils;

/**
 * Created by qizfeng on 16/10/20.
 */

public class WalletIndexActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_mingxi;
    private LinearLayout ll_chongzhi;
    private LinearLayout ll_tixian;
    private TextView tv_money;
    private TextView tv_score, tv_card, tv_conpon;
    private RelativeLayout rl_score, rl_card, rl_conpon;
    private Wallet wallet = new Wallet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_wallet_index);
        initView();
        initActionBar();
//        loadData();
    }

    private void initView() {
        tv_money = (TextView) findViewById(R.id.tv_money);

        tv_mingxi = (TextView) findViewById(R.id.tv_mingxi);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_conpon = (TextView) findViewById(R.id.tv_conpon);

        ll_chongzhi = (LinearLayout) findViewById(R.id.ll_chongzhi);
        ll_tixian = (LinearLayout) findViewById(R.id.ll_tixian);

        rl_score = (RelativeLayout) findViewById(R.id.rl_score);
        rl_card = (RelativeLayout) findViewById(R.id.rl_card);
        rl_conpon = (RelativeLayout) findViewById(R.id.rl_conpon);
        tv_mingxi.setOnClickListener(this);
        ll_tixian.setOnClickListener(this);
        ll_chongzhi.setOnClickListener(this);
        rl_card.setOnClickListener(this);
        rl_conpon.setOnClickListener(this);
        rl_score.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("我的钱包");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_mingxi:
                intent.setClass(WalletIndexActivity.this,IncomeAndOutgoingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_chongzhi:
                intent.setClass(WalletIndexActivity.this,RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_tixian:
                break;
            case R.id.rl_score:
                intent.setClass(WalletIndexActivity.this,PointDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_card:
                intent.setClass(WalletIndexActivity.this,ValueCardListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_conpon:
                intent.setClass(WalletIndexActivity.this,CouponListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loadData() {
        final WalletIndexServerDao serverDao = new WalletIndexServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    wallet = serverDao.wallet;
                    bindData(wallet);
                } else {
                    ToastUtils.show(WalletIndexActivity.this, serverDao.desc);
                }
            }
        });

    }

    private void bindData(Wallet wallet) {
        tv_money.setText(wallet.getBalance());
        tv_score.setText(wallet.getPoint());
        tv_card.setText(wallet.getPaycard_num()+"张");
        tv_conpon.setText(wallet.getCoupon_num()+"张");
    }
}

package com.zipingfang.aihuan.ui.user.wallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.ValueCard;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ValueCardBuyServerDao;
import com.zipingfang.aihuan.dao.ValueCardListServerDao;
import com.zipingfang.aihuan.dao.ValueCardMyServerDao;
import com.zipingfang.aihuan.ui.adapter.ValueCardAdapter;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/21.
 */

public class ValueCardBuyActivity extends BaseActivity implements View.OnClickListener {
    private List<ValueCard> valueCards = new ArrayList<>();
    private ListView lv_container;
    private ValueCardAdapter mAdapter;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_value_card);
        lv_container = (ListView) findViewById(R.id.lv_container);
        initActionBar();
        loadData();
        onBuy();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("储值卡购买");
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
        final ValueCardListServerDao serverDao = new ValueCardListServerDao(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    valueCards = serverDao.valueCards;
                    mAdapter = new ValueCardAdapter(ValueCardBuyActivity.this, valueCards, true, getUserId(ValueCardBuyActivity.this));
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else
                    ToastUtils.show(ValueCardBuyActivity.this, serverDao.desc);
            }
        });
    }

    void onBuy() {
        lv_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ValueCard valueCard = valueCards.get(position);
                if (Integer.parseInt(getUser(ValueCardBuyActivity.this).getBalance()) < Integer.parseInt(valueCards.get(position).getCard_money())){
                    ToastUtils.show(ValueCardBuyActivity.this,"余额不足,请先充值");
                    return;
                }
                DeleteDialog.Builder builder = new DeleteDialog.Builder(ValueCardBuyActivity.this);
                builder.setTitle("提醒");
                builder.setMessage("将从您的余额中扣除¥" + valueCard.getCard_money());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final ValueCardBuyServerDao serverDao = new ValueCardBuyServerDao(ValueCardBuyActivity.this);
                        serverDao.mid = getUserId(ValueCardBuyActivity.this);
                        serverDao.card_id = valueCard.getCard_id();
                        serverDao.loadData(new BaseDao.IDaoCallback() {
                            @Override
                            public void exec(boolean success, Object obj) {
                                if (serverDao.isSucc) {
                                    ToastUtils.show(ValueCardBuyActivity.this, "购买成功");
                                } else {
                                    ToastUtils.show(ValueCardBuyActivity.this, serverDao.desc);
                                }

                            }
                        });
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }
}

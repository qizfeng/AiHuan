package com.zipingfang.aihuan.ui.user.wallet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.IncomeDetail;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.IncomeDetailServerDao;
import com.zipingfang.aihuan.ui.adapter.IncomeDetailAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/20.
 */

public class IncomeAndOutgoingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_category;
    private RelativeLayout rl_all, rl_income, rl_outgoing;
    private ImageView iv_all, iv_income, iv_outgoing;
    private ListView lv_container;
    private int category = 0;//0.全部 1.收入 2.支出
    private TextView tv_title;
    private List<IncomeDetail> mData = new ArrayList<>();
    private IncomeDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_income_outgoing);
        initView();
        initActionBar();
        loadData();
    }

    private void initView() {
        ll_category = (LinearLayout) findViewById(R.id.ll_category);
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);
        rl_income = (RelativeLayout) findViewById(R.id.rl_income);
        rl_outgoing = (RelativeLayout) findViewById(R.id.rl_outgoing);
        iv_all = (ImageView) findViewById(R.id.iv_all);
        iv_income = (ImageView) findViewById(R.id.iv_income);
        iv_outgoing = (ImageView) findViewById(R.id.iv_outgoing);
        lv_container = (ListView) findViewById(R.id.lv_container);
        ll_category.setOnClickListener(this);
        rl_income.setOnClickListener(this);
        rl_outgoing.setOnClickListener(this);
        rl_all.setOnClickListener(this);
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("收支明细");
        Drawable drawable = getResources().getDrawable(R.mipmap.incomedetails_down);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
        tv_title.setCompoundDrawables(null, null, drawable, null);//画在左边
        tv_title.setCompoundDrawablePadding(5);
        tv_title.setOnClickListener(this);
        btn_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title:
                if (ll_category.getVisibility() == View.VISIBLE) {
                    ll_category.setVisibility(View.GONE);
                    Drawable drawable = getResources().getDrawable(R.mipmap.incomedetails_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
                    tv_title.setCompoundDrawables(null, null, drawable, null);//画在左边
                } else if (ll_category.getVisibility() == View.GONE) {
                    ll_category.setVisibility(View.VISIBLE);
                    Drawable drawable = getResources().getDrawable(R.mipmap.incomedetails_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
                    tv_title.setCompoundDrawables(null, null, drawable, null);//画在左边
                    if ((category == 0)) {
                        iv_all.setVisibility(View.VISIBLE);
                        iv_income.setVisibility(View.GONE);
                        iv_outgoing.setVisibility(View.GONE);
                    } else if (category == 1) {
                        iv_all.setVisibility(View.GONE);
                        iv_income.setVisibility(View.VISIBLE);
                        iv_outgoing.setVisibility(View.GONE);
                    } else if (category == 2) {
                        iv_all.setVisibility(View.GONE);
                        iv_income.setVisibility(View.GONE);
                        iv_outgoing.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.rl_all:
                category = 0;
                ll_category.setVisibility(View.GONE);
                loadData();
                break;
            case R.id.rl_income:
                category = 1;
                ll_category.setVisibility(View.GONE);
                loadData();
                break;
            case R.id.rl_outgoing:
                category = 2;
                ll_category.setVisibility(View.GONE);
                loadData();
                break;
        }
    }

    private void loadData() {
        final IncomeDetailServerDao serverDao = new IncomeDetailServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = category;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    if (serverDao.incomeDetailList.size() <= 0) {
                        ToastUtils.show(IncomeAndOutgoingActivity.this, "没有数据");
                        mData = new ArrayList<IncomeDetail>();

                    } else {
                        mData = new ArrayList<IncomeDetail>();
                        mData.addAll(serverDao.incomeDetailList);
                    }
                    mAdapter = new IncomeDetailAdapter(IncomeAndOutgoingActivity.this, mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(IncomeAndOutgoingActivity.this, serverDao.desc);
                    mData = new ArrayList<IncomeDetail>();
                    mAdapter = new IncomeDetailAdapter(IncomeAndOutgoingActivity.this, mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

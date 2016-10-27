package com.zipingfang.aihuan.ui.trade;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.dao.ActivePartyServerDao;
import com.zipingfang.aihuan.dao.AttentionServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.ui.adapter.ActivePartyGoodsAdapter;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.GridViewWithHeaderAndFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class GoodsListActivity extends BaseActivity implements View.OnClickListener {
    private GridViewWithHeaderAndFooter lv_container;
    private View header;
    private ImageView iv_cover;
    private TextView tv_time;
    private LinearLayout ll_time;
    private TextView tv_hour, tv_min, tv_sec;
    private TextView tv_name, tv_hot, tv_price;
    private Party partyInfo = new Party();
    private List<Goods> goodsList = new ArrayList<>();
    private String party_type;
    private String party_id;
    private ActivePartyGoodsAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_goods_list);
        party_id = getIntent().getStringExtra("party_id");
        party_type = getIntent().getStringExtra("party_type");
        initView();
        initActionBar();
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title_right:
                doAttention();
                break;
        }

    }

    private void initView() {
        lv_container = (GridViewWithHeaderAndFooter) findViewById(R.id.lv_container);
        header = LayoutInflater.from(this).inflate(R.layout.zpf_item_active, null);
        iv_cover = (ImageView) header.findViewById(R.id.iv_image);
        tv_time = (TextView) header.findViewById(R.id.tv_time);
        tv_hour = (TextView) header.findViewById(R.id.tv_hour);
        tv_min = (TextView) header.findViewById(R.id.tv_min);
        tv_sec = (TextView) header.findViewById(R.id.tv_sec);
        tv_name = (TextView) header.findViewById(R.id.tv_name);
        tv_hot = (TextView) header.findViewById(R.id.tv_hot);
        tv_price = (TextView) header.findViewById(R.id.tv_price);
        ll_time = (LinearLayout) header.findViewById(R.id.ll_time);
        lv_container.addHeaderView(header);


    }

    private ImageView iv_title_right;
    private TextView tv_title_right;

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("商品列表");

        iv_title_right = (ImageView) actionBar.findViewById(R.id.iv_title_right);
        tv_title_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        iv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setVisibility(View.VISIBLE);
        iv_title_right.setImageResource(R.mipmap.icon_share);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
        tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        btn_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);

    }

    private void doAttention() {
        final AttentionServerDao serverDao = new AttentionServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = party_type;
        serverDao.pid = party_id;
        serverDao.ptitle = partyInfo.getTitle();
        serverDao.cover = partyInfo.getCover();
        serverDao.starttime = partyInfo.getParty_start_time();
        serverDao.overtime = partyInfo.getParty_end_time();
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc){
                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect_s);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
                    tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
                    ToastUtils.show(GoodsListActivity.this, "关注成功");
                }
                else
                    ToastUtils.show(GoodsListActivity.this, serverDao.desc);
            }
        });
    }

    private void loadData() {
        final ActivePartyServerDao serverDao = new ActivePartyServerDao(this);
        serverDao.party_type = party_type;
        serverDao.party_id = party_id;
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    partyInfo = serverDao.partyInfo;
                    bindData(partyInfo);
                    goodsList = serverDao.goodsList;
                    mAdapter = new ActivePartyGoodsAdapter(GoodsListActivity.this, goodsList);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private long time;

    private void bindData(Party partyInfo) {
        if ("0".equals(partyInfo.getAttention())) {
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        } else if ("1".equals(partyInfo.getAttention())) {
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect_s);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        }

        ImageLoader.getInstance().displayImage(partyInfo.getCover(), iv_cover, ImageLoaderConfig.normal);
        tv_name.setText(partyInfo.getTitle());
        time = Long.parseLong(partyInfo.getLast_time());
//        tv_hot.setText(partyInfo.get);
        if (time > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(time);
                    String[] split = formatLongToTimeStr.split(":");
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            tv_hour.setText("距离结束还有" + split[0] + ":");
                        }
                        if (i == 1) {
                            tv_min.setText(split[1] + ":");
                        }
                        if (i == 2) {
                            tv_sec.setText(split[2] + "");
                        }

                    }
                    if (time > 0) {
                        mHandler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        } else if (time < 0) {
            time = Math.abs(time);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(time);
                    String[] split = formatLongToTimeStr.split(":");
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            tv_hour.setText("距离结束还有" + split[0] + ":");
                        }
                        if (i == 1) {
                            tv_min.setText(split[1] + ":");
                        }
                        if (i == 2) {
                            tv_sec.setText(split[2] + "");
                        }

                    }
                    if (time > 0) {
                        mHandler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }
}

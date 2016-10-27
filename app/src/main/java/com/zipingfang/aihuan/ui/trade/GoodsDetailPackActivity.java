package com.zipingfang.aihuan.ui.trade;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.GoodsDetail;
import com.zipingfang.aihuan.bean.PriceRecord;
import com.zipingfang.aihuan.cycleviewpager.CycleViewPager;
import com.zipingfang.aihuan.dao.AttentionServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.GoodsDetailPackServerDao;
import com.zipingfang.aihuan.dao.PriceRecordServerDao;
import com.zipingfang.aihuan.ui.index.GoodsDetailSecKillActivity;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizfeng on 16/10/19.
 */

public class GoodsDetailPackActivity extends BaseActivity implements View.OnClickListener {
    private WebView wv_goods_info;
    private String goods_id;
    private String party_id;
    private String party_type;
    private GoodsDetail goodsDetail = new GoodsDetail();
    private CycleViewPager headerCycleViewPager;
    private List<ImageView> headerViews = new ArrayList<ImageView>();
    private ImageView iv_cover;
    private TextView tv_auction_status;
    private TextView tv_hour, tv_min, tv_sec;
    private TextView tv_goods_name, tv_goods_now_price, tv_apply, tv_hot, tv_price_count;
    private TextView tv_start_price, tv_retain_price, tv_deposit, tv_add_scope, tv_delay_time, tv_type;
    private LinearLayout ll_offer_record;
    private Handler mHandler = new Handler();
    private TextView tv_title;
    private List<PriceRecord> recordList = new ArrayList<>();
    private View il_record_one, il_record_two, il_record_three;
    private LinearLayout ll_deposit, ll_pay;
    private View il_deposit, il_pay;
    private TextView tv_bottom_depoist;
    private TextView tv_bottom_pay_deposit;
    private EditText et_price;
    private ImageView iv_minus, iv_add;
    private TextView tv_bottom_pay_price;
    private TextView tv_price_type;
    private LinearLayout ll_param;
    private LinearLayout ll_goods_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_goods_detail);
        initView();
        initActionBar();
        loadData();
    }

    private void initView() {
        wv_goods_info = (WebView) findViewById(R.id.wv_goods_info);
        wv_goods_info.getSettings().setJavaScriptEnabled(true);
        wv_goods_info.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = wv_goods_info.getSettings();
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAllowFileAccess(true);
//        webSettings.setBuiltInZoomControls(true);
        tv_price_type=(TextView)findViewById(R.id.tv_price_type);
        iv_cover = (ImageView) findViewById(R.id.iv_goods_big);
        tv_auction_status = (TextView) findViewById(R.id.tv_auction_status);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_min = (TextView) findViewById(R.id.tv_min);
        tv_sec = (TextView) findViewById(R.id.tv_sec);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_goods_now_price = (TextView) findViewById(R.id.tv_goods_now_price);
        tv_apply = (TextView) findViewById(R.id.tv_apply);
        tv_hot = (TextView) findViewById(R.id.tv_hot);
        tv_price_count = (TextView) findViewById(R.id.tv_price_count);
        tv_start_price = (TextView) findViewById(R.id.tv_start_price);
        tv_retain_price = (TextView) findViewById(R.id.tv_retain_price);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        tv_add_scope = (TextView) findViewById(R.id.tv_add_scope);
        tv_delay_time = (TextView) findViewById(R.id.tv_delay_time);
        tv_type = (TextView) findViewById(R.id.tv_type);
        il_record_one = (View) findViewById(R.id.il_offer_record_one);
        il_record_two = (View) findViewById(R.id.il_offer_record_two);
        il_record_three = (View) findViewById(R.id.il_offer_record_three);
        ll_offer_record = (LinearLayout) findViewById(R.id.ll_offer_record);

        ll_deposit = (LinearLayout) findViewById(R.id.ll_deposit);
        ll_pay = (LinearLayout) findViewById(R.id.ll_pay);
        il_deposit = (View) findViewById(R.id.il_deposit);
        il_pay = (View) findViewById(R.id.il_pay);

        tv_bottom_depoist = (TextView) il_deposit.findViewById(R.id.tv_deposit);
        tv_bottom_pay_deposit = (TextView) il_deposit.findViewById(R.id.tv_pay_deposit);
        tv_bottom_pay_price = (TextView) il_pay.findViewById(R.id.tv_pay);
        iv_minus = (ImageView) il_pay.findViewById(R.id.iv_minus);
        iv_add = (ImageView) il_pay.findViewById(R.id.iv_add);
        ll_param =(LinearLayout)findViewById(R.id.ll_param);
        ll_param.setVisibility(View.GONE);
        tv_bottom_pay_price.setText("立即抢购");
        ll_goods_time =(LinearLayout)findViewById(R.id.ll_goods_time);
        ll_goods_time.setVisibility(View.GONE);



    }

    private ImageView iv_title_right;
    private TextView tv_title_right;
    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_title_right = (ImageView) view.findViewById(R.id.iv_title_right);
        TextView tv_title_right = (TextView) view.findViewById(R.id.tv_title_right);
        iv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setVisibility(View.VISIBLE);
        iv_title_right.setImageResource(R.mipmap.icon_share);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
        tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
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


    private void doAttention() {
        final AttentionServerDao serverDao = new AttentionServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = "goods";
        serverDao.agid=goods_id;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    ToastUtils.show(GoodsDetailPackActivity.this, "关注成功");
                } else {
                    ToastUtils.show(GoodsDetailPackActivity.this, serverDao.desc);
                }
            }
        });
    }


    private void loadData() {
        goods_id = getIntent().getStringExtra("goods_id");
        party_id = getIntent().getStringExtra("party_id");
        party_type=getIntent().getStringExtra("party_type");
        final GoodsDetailPackServerDao serverDao = new GoodsDetailPackServerDao(this);
        serverDao.goods_id = goods_id;
        serverDao.party_id = party_id;
///       serverDao.mid = getUserId(this);
        serverDao.party_type= party_type;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    goodsDetail = serverDao.goodsDetail;
                    wv_goods_info.loadDataWithBaseURL(null, goodsDetail.getRules_item(), "text/html", "utf-8", "");
                    initHeaderBanner(serverDao.bannerInfoList);
//                    initBottomImage(goodsDetail.getCover());
                    bindData(goodsDetail);
                }
            }
        });
    }

    private void loadRecord() {
        final PriceRecordServerDao serverDao = new PriceRecordServerDao(this);
        serverDao.goods_id = getIntent().getStringExtra("agid");
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    PriceRecord record = new PriceRecord();
                    if (serverDao.recordList.size() > 0) {
                        record = serverDao.recordList.get(0);
                        bindRecordData(record, il_record_one, true);
                    }
                    if (serverDao.recordList.size() > 1) {
                        record = serverDao.recordList.get(1);
                        bindRecordData(record, il_record_two, false);
                    }
                    if (serverDao.recordList.size() > 2) {
                        record = serverDao.recordList.get(2);
                        bindRecordData(record, il_record_three, false);
                    }
                }
            }
        });
    }

    private void bindRecordData(PriceRecord record, View view, boolean isFirst) {
        TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        if (isFirst) {
            tv_status.setText("领先");
            tv_status.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            tv_status.setText("......");
            tv_status.setBackgroundColor(getResources().getColor(R.color.zpf_offer_first));
        }
        tv_name.setText(record.getUsername());
        tv_price.setText(record.getPrice());
        tv_time.setText(DateUtils.getCurrDate(Long.parseLong(record.getAdd_time())));
    }

    private long time;

    private void bindData(GoodsDetail goodsDetail) {
        if("0".equals(goodsDetail.getAttention())){
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        }else if("1".equals(goodsDetail.getAttention())){
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_collect_s);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_title_right.setCompoundDrawables(drawable, null, null, null);//画在左边
        }
//        time = Long.parseLong(goodsDetail.getLast_time());
        tv_auction_status.setText(goodsDetail.getStatus_describe());
        tv_title.setText(goodsDetail.getGoods_name());
        tv_goods_name.setText(goodsDetail.getGoods_name());
        tv_price_type.setText("当前价");
        tv_goods_now_price.setText("￥" + goodsDetail.getMax_price());
        tv_apply.setText("报名 " + goodsDetail.getApply_count());
        tv_apply.setVisibility(View.GONE);
        tv_hot.setText("热度 " + goodsDetail.getCnt());
        tv_hot.setVisibility(View.GONE);
        tv_start_price.setText("起拍价 ￥" + goodsDetail.getStarting_price());

        tv_retain_price.setText("保留价 ￥" + goodsDetail.getReserve_price());
        tv_deposit.setText("保证金 ￥" + goodsDetail.getDeposit());
        tv_add_scope.setText("加价幅度 ￥" + goodsDetail.getBid_increments());
        tv_delay_time.setText("延时周期 " + goodsDetail.getDelay_minutes());
        tv_type.setText("拍卖类型 " + goodsDetail.getAuction_type());
        tv_price_count.setText("库存 " + goodsDetail.getInventory());
        tv_price_count.setVisibility(View.GONE);
        tv_bottom_depoist.setText("保证金:￥"+goodsDetail.getDeposit());
        ll_pay.setVisibility(View.VISIBLE);

    }

    private void initBottomImage(String url) {
        iv_cover.getLayoutParams().width = DeviceUtil.getScreenWidth(this);
        iv_cover.getLayoutParams().height = DeviceUtil.getScreenWidth(this) / 3 * 2;
        ImageLoader.getInstance().displayImage(url, iv_cover, ImageLoaderConfig.normal);
    }

    @SuppressLint("NewApi")
    private void initHeaderBanner(List<BannerInfo> headerBanners) {
        headerCycleViewPager = (CycleViewPager) getSupportFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_header);
        // 将最后一个ImageView添加进来
        headerViews.add(ViewFactory.getImageView(this, headerBanners.get(headerBanners.size() - 1).getImg()));
        for (int i = 0; i < headerBanners.size(); i++) {
            headerViews.add(ViewFactory.getImageView(this, headerBanners.get(i).getImg()));
        }
        // 将第一个ImageView添加进来
        headerViews.add(ViewFactory.getImageView(this, headerBanners.get(0).getImg()));
        // 设置循环，在调用setData方法前调用
        headerCycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        headerCycleViewPager.setData(headerViews, headerBanners, mHeaderCycleViewListener);
        //设置轮播
        headerCycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        headerCycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        headerCycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mHeaderCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(BannerInfo info, int position, View imageView) {
            if (headerCycleViewPager.isCycle()) {
                position = position - 1;
            }
        }
    };
}
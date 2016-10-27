package com.zipingfang.aihuan.ui.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.IncomeDetail;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.cycleviewpager.CycleViewPager;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.IndexBannerServerDao;
import com.zipingfang.aihuan.dao.IndexServerDao;
import com.zipingfang.aihuan.ui.SearchActivity;
import com.zipingfang.aihuan.ui.adapter.RecyclerAuctionAdapter;
import com.zipingfang.aihuan.ui.adapter.RecyclerJiangjiaAdapter;
import com.zipingfang.aihuan.ui.adapter.RecyclerMiaoshaAdapter;
import com.zipingfang.aihuan.ui.adapter.SuggestAdapter;
import com.zipingfang.aihuan.ui.trade.ActiveListActivity;
import com.zipingfang.aihuan.ui.trade.GoodsListActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.AutoTextView;
import com.zipingfang.aihuan.view.GridViewWithHeaderAndFooter;
import com.zipingfang.aihuan.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 峰 on 2016/9/13.
 * 首页-用户商家
 */
public class IndexFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private List<BannerInfo> headerBanners = new ArrayList<BannerInfo>();
    private List<BannerInfo> atyBanners = new ArrayList<BannerInfo>();
    private List<BannerInfo> toutiaos = new ArrayList<>();
    private List<ImageView> headerViews = new ArrayList<ImageView>();
    private List<ImageView> atyViews = new ArrayList<ImageView>();
    private CycleViewPager headerCycleViewPager;
    private CycleViewPager atyCycleViewPager;

    private List<Goods> msList = new ArrayList<Goods>();

    private List<Party> jjList = new ArrayList<>();

    private List<Party> aucList = new ArrayList<Party>();

    private GridViewWithHeaderAndFooter gv_suggest;
    private SuggestAdapter sugAdapter;
    private List<Goods> sugList = new ArrayList<Goods>();

    private View header;
    private View footer;
    View activity;
    View auction;
    private LinearLayout ll_toutiao;

    private LinearLayout ll_miaosha, ll_jiangjia, ll_auction;
    private View view, suggest;

    // 线程标志
    private boolean isStop = false;
    private boolean isUser = true;//用户还是商家

    private ToggleButton tb_index_title;
    private AutoTextView tv_toutiao;
    private RecyclerView rv_miaosha;
    private RecyclerView rv_auction;
    private RecyclerView rv_jiangjia;
    private StaggeredGridLayoutManager layoutManager;
    private View headerBanner;
    private ImageView iv_title_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_index, null);
        iv_title_right =(ImageView)view.findViewById(R.id.iv_title_right);
        tb_index_title = (ToggleButton) view.findViewById(R.id.tb_index_title);
        tb_index_title.setOnCheckedChangeListener(this);
        header = inflater.inflate(R.layout.zpf_include_index_header, null);
        footer = inflater.inflate(R.layout.zpf_include_index_footer, null);
        gv_suggest = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_suggest);
        gv_suggest.addHeaderView(header);
        gv_suggest.addFooterView(footer);
        footer.findViewById(R.id.loadmore_view).setOnClickListener(this);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),GoodsAllActivity.class);
                startActivity(intent);
            }
        });
        suggest = view.findViewById(R.id.il_suggest);
        ll_jiangjia = (LinearLayout) header.findViewById(R.id.ll_jiangjia);
        ll_miaosha = (LinearLayout) header.findViewById(R.id.ll_miaosha);
        ll_miaosha.getLayoutParams().height = ((DeviceUtil.getScreenWidth(getActivity()) - 70) / 2);

        ll_miaosha.setVisibility(View.VISIBLE);
        ll_jiangjia.setVisibility(View.GONE);
        headerBanner = header.findViewById(R.id.il_header_banner);
        activity = header.findViewById(R.id.il_act_banner);
        auction = header.findViewById(R.id.il_auction);
        loadBannerData();
        loadData(1);
        bindListener();
        return view;
    }

    private void loadBannerData() {
        final IndexBannerServerDao serverDao = new IndexBannerServerDao(getActivity());
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    if (serverDao.headerLine.size() > 0) {
                        toutiaos = new ArrayList<BannerInfo>();
                        toutiaos.addAll(serverDao.headerLine);
                        initToutiao(header);
                    }
                    if (serverDao.headerBanner.size() > 0) {
                        headerBanners = new ArrayList<BannerInfo>();
                        headerBanners.addAll(serverDao.headerBanner);
                        headerViews = new ArrayList<ImageView>();
                        initHeaderBanner();
                    }

                    if (serverDao.middleBanner.size() > 0) {
                        atyBanners = new ArrayList<BannerInfo>();
                        atyBanners.addAll(serverDao.middleBanner);
                        atyViews = new ArrayList<ImageView>();
                        initAtyBanner();
                    }
                }
            }
        });
    }


    private void bindListener() {
        iv_title_right.setOnClickListener(this);
//        ll_toutiao.setOnClickListener(this);
//        iv_user.setOnClickListener(this);
//        iv_merchant.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ll_miaosha.setVisibility(View.VISIBLE);
            ll_jiangjia.setVisibility(View.GONE);
            isUser = true;
            TextView tv_title = (TextView) header.findViewById(R.id.tv_suggest_title);
            tv_title.setText("精品推荐");
            if (isUser)
                loadData(1);
            else
                loadData(2);
//                tv_title.setText("打包推荐");
        } else {
            ll_miaosha.setVisibility(View.GONE);
            ll_jiangjia.setVisibility(View.VISIBLE);
            isUser = false;
            TextView tv_title = (TextView) header.findViewById(R.id.tv_suggest_title);
            tv_title.setText("精品推荐");
            if (isUser)
                loadData(1);
            else
                loadData(2);
//                tv_title.setText("打包推荐");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_toutiao:
                ToastUtils.show(getActivity(), "头条>>>" + toutiaoIndex);
                break;
            case R.id.iv_title_right:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

        }
    }

    private int toutiaoIndex = 0;

    private void initToutiao(View view) {
        tv_toutiao = (AutoTextView) view.findViewById(R.id.tv_toutiao);
        ll_toutiao = (LinearLayout) view.findViewById(R.id.ll_toutiao);
        if (toutiaos.size() == 0) {
            tv_toutiao.setText("暂无消息");
            return;
        }
        tv_toutiao.setText(toutiaos.get(toutiaoIndex).getTitle());
        try {
            timer = new Timer(true);
            timer.schedule(task, 5000, 5 * 1000);
        } catch (Exception e) {

        }
    }

    private Timer timer = new Timer(true);

    // 任务
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                tv_toutiao.next();
                toutiaoIndex++;
                if (toutiaoIndex == 3) {
                    toutiaoIndex = 0;
                }
                tv_toutiao.setText(toutiaos.get(toutiaoIndex).getTitle());
            }
        }
    };


    /**
     * 用户-秒杀
     *
     * @param view
     */
    private void initMiaoshaData(View view) {
        RecyclerMiaoshaAdapter recyclerAdapter = new RecyclerMiaoshaAdapter(msList, R.layout.zpf_item_miaosha, getActivity());
        rv_miaosha = (RecyclerView) view.findViewById(R.id.rv_miaosha);
        rv_miaosha.setHasFixedSize(true);

        int spanCount = 1; // 只显示一行
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_miaosha.setLayoutManager(layoutManager);

        rv_miaosha.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    /**
     * 商家-降价拍
     *
     * @param view
     */
    private void initJiangjiaData(View view) {
        rv_jiangjia = (RecyclerView) view.findViewById(R.id.rv_jiangjia);
        RecyclerJiangjiaAdapter adapter = new RecyclerJiangjiaAdapter(jjList, R.layout.zpf_item_jiangjia, getActivity());
        rv_jiangjia.setHasFixedSize(true);

        int spanCount = 1; // 只显示一行
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_jiangjia.setLayoutManager(layoutManager);
        rv_jiangjia.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 拍卖
     *
     * @param view
     */
    private void initAuctionData(View view) {
        TextView tv_auction_title = (TextView) view.findViewById(R.id.tv_auction_title);
        if (isUser) {
            tv_auction_title.setText("拍卖");
            Drawable drawable = getResources().getDrawable(R.mipmap.home_icon_paimai);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_auction_title.setCompoundDrawables(drawable, null, null, null);//画在左边
        } else {
            tv_auction_title.setText("升价拍");
            Drawable drawable = getResources().getDrawable(R.mipmap.home_icon_priceup);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            //setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            tv_auction_title.setCompoundDrawables(drawable, null, null, null);//画在左边
        }
        ll_auction = (LinearLayout) view.findViewById(R.id.ll_auction);
        ll_auction.getLayoutParams().height = ((DeviceUtil.getScreenWidth(getActivity()) - 160) / 2);

        RecyclerAuctionAdapter adapter = new RecyclerAuctionAdapter(aucList, R.layout.zpf_item_jiangjia, getActivity());
        rv_auction = (RecyclerView) view.findViewById(R.id.rv_auction);
        rv_auction.setHasFixedSize(true);
        int spanCount = 1; // 只显示一行
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_auction.setLayoutManager(layoutManager);
        rv_auction.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    private void initHeaderBanner() {
        headerCycleViewPager = (CycleViewPager) getChildFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_header);
        // 将最后一个ImageView添加进来
        headerViews.add(ViewFactory.getImageView(getActivity(), headerBanners.get(headerBanners.size() - 1).getImg()));
        for (int i = 0; i < headerBanners.size(); i++) {
            headerViews.add(ViewFactory.getImageView(getActivity(), headerBanners.get(i).getImg()));
        }
        // 将第一个ImageView添加进来
        headerViews.add(ViewFactory.getImageView(getActivity(), headerBanners.get(0).getImg()));
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
            Intent intent = new Intent();
            if(info.getType_id().equals("1")){
                intent.setClass(getActivity(), GoodsListActivity.class);
                intent.putExtra("party_id",info.getParty_id());
                intent.putExtra("party_type",info.getParty_type());
            }else if("2".equals(info.getType_id())){
                intent.setClass(getActivity(), ActiveListActivity.class);
                intent.putExtra("party_id",info.getParty_id());
                intent.putExtra("party_type",info.getParty_type());
            }
        }
    };


    @SuppressLint("NewApi")
    private void initAtyBanner() {
        atyCycleViewPager = (CycleViewPager) getChildFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_activity);
        // 将最后一个ImageView添加进来
        atyViews.add(ViewFactory.getImageView(getActivity(), atyBanners.get(atyBanners.size() - 1).getImg()));
        for (int i = 0; i < atyBanners.size(); i++) {
            atyViews.add(ViewFactory.getImageView(getActivity(), atyBanners.get(i).getImg()));
        }
        // 将第一个ImageView添加进来
        atyViews.add(ViewFactory.getImageView(getActivity(), atyBanners.get(0).getImg()));

        // 设置循环，在调用setData方法前调用
        atyCycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环

        atyCycleViewPager.setData(atyViews, atyBanners, mAtyCycleViewListener);
        //设置轮播
        atyCycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        atyCycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        atyCycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAtyCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerInfo info, int position, View imageView) {
            if (atyCycleViewPager.isCycle()) {
                position = position - 1;
            }
        }
    };

    private void loadData(final int type) {
        final IndexServerDao serverDao = new IndexServerDao(getActivity());
        serverDao.type = type;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (type == 1) {
                    if (serverDao.isSucc) {
                        if (serverDao.secKillList.size() > 0) {
                            msList = new ArrayList<>();
                            msList.addAll(serverDao.secKillList);
                            initMiaoshaData(header);
                        }
                    }

                    if (serverDao.isSucc) {
                        if (serverDao.aucList.size() > 0) {
                            aucList = new ArrayList<Party>();
                            aucList.addAll(serverDao.aucList);
                            initAuctionData(auction);
                        }
                    }

                    if (serverDao.isSucc) {
                        if (serverDao.secKillList.size() > 0) {
                            sugList = new ArrayList<Goods>();
                            sugList.addAll(serverDao.suggestList);
                            initSuggestData(suggest);
                        }
                    }
                } else if (type == 2) {
                    if (serverDao.downPartList.size() > 0) {
                        jjList = new ArrayList<Party>();
                        jjList.addAll(serverDao.downPartList);
                        initJiangjiaData(header);
                    }

                    if (serverDao.upPartList.size() > 0) {
                        aucList = new ArrayList<Party>();
                        aucList.addAll(serverDao.upPartList);
                        initAuctionData(auction);
                    }
                }
            }
        });
    }


    private void initSuggestData(View sugView) {
        gv_suggest = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_suggest);
        TextView tv_title = (TextView) header.findViewById(R.id.tv_suggest_title);
        if (isUser)
            tv_title.setText("精品推荐");
        else
            tv_title.setText("打包推荐");


        sugAdapter = new SuggestAdapter(getActivity(), sugList);
        gv_suggest.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();

        gv_suggest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
                intent.putExtra("party_id",sugList.get(position).getPid());
                intent.putExtra("agid",sugList.get(position).getGoods_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 关闭定时器
        isStop = true;
//        try{
//            timer.cancel();
//            task.cancel();
//        }catch (Exception e){
//
//        }
        gv_suggest.removeFooterView(footer);
        gv_suggest.removeHeaderView(header);
    }
}

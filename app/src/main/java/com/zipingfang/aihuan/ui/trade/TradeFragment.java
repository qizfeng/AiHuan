package com.zipingfang.aihuan.ui.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.Trade;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.TradeIndexServerDao;
import com.zipingfang.aihuan.gallery.FancyCoverFlow;
import com.zipingfang.aihuan.ui.SearchActivity;
import com.zipingfang.aihuan.ui.adapter.GalleryAdapter;
import com.zipingfang.aihuan.ui.adapter.HotSaleAdapter;
import com.zipingfang.aihuan.ui.adapter.RecyclerTradeAdapter;
import com.zipingfang.aihuan.ui.adapter.TradeAdapter;
import com.zipingfang.aihuan.ui.index.GoodsAllActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.view.GridViewWithHeaderAndFooter;
import com.zipingfang.aihuan.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易大厅
 * Created by 峰 on 2016/9/19.
 */
public class TradeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private View header;
    private View footer;
    private View auction;
    private View miaosha;
    private View manjian;

    private FancyCoverFlow gallery;
    private GalleryAdapter galleryAdapter;
    private List<BannerInfo> bannerInfos = new ArrayList<BannerInfo>();

    private GridViewWithHeaderAndFooter gv_suggest;
    private HotSaleAdapter hosSaleAdapter;
    private List<Goods> sugList = new ArrayList<Goods>();

    private HorizontalListView hLv_auction;
    private HorizontalListView hLv_miaosha;
    private HorizontalListView hLv_manjian;
    private List<Trade> auctionList = new ArrayList<Trade>();
    private List<Trade> miaoshaList = new ArrayList<Trade>();
    private List<Trade> manjianList = new ArrayList<Trade>();
    private TradeAdapter mAuctionAdapter, mMiaoshaAdapter, mManjianAdapter;
    private LinearLayout ll_trade;
    private RecyclerView rv_auction, rv_miaosha, rv_manjian;
    private StaggeredGridLayoutManager layoutManager;
    private int spanCount = 1;//只显示一行 recyclerView
    private ImageView iv_title_right;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_trade, null);
        header = inflater.inflate(R.layout.zpf_include_trade_header, null);
        footer = inflater.inflate(R.layout.zpf_include_index_footer, null);
        auction = header.findViewById(R.id.il_auction);
        miaosha = header.findViewById(R.id.il_miaosha);
        manjian = header.findViewById(R.id.il_manjian);
        iv_title_right =(ImageView)view.findViewById(R.id.iv_title_right);
        iv_title_right.setOnClickListener(this);
//        initHeaderBanner(header);
        loadData();
//        initSuggestData(view);
//        initAuctionData(auction);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.loadmore_view:
                 intent = new Intent(getActivity(), GoodsAllActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_title_right:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loadData() {
        final TradeIndexServerDao serverDao = new TradeIndexServerDao(getActivity());
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    if (serverDao.bannerInfos.size() > 0) {
                        bannerInfos = new ArrayList<BannerInfo>();
                        bannerInfos.addAll(serverDao.bannerInfos);
                        initHeaderBanner(header, bannerInfos);
                    }

                    if (serverDao.suggestList.size() > 0) {
                        sugList = new ArrayList<Goods>();
                        sugList.addAll(serverDao.suggestList);
                        initSuggestData(view, sugList);
                    }

                    if (serverDao.partyList.size() > 0) {
                        auctionList = new ArrayList<Trade>();
                        auctionList.addAll(serverDao.partyList);
                        initAuctionData(auction, auctionList);
                    } else {
                        auction.setVisibility(View.GONE);
                    }
                    if (serverDao.secKillList.size() > 0) {
                        miaoshaList = new ArrayList<Trade>();
                        miaoshaList.addAll(serverDao.secKillList);
                        initMiaoshaData(miaosha, miaoshaList);
                    } else {
                        miaosha.setVisibility(View.GONE);
                    }
                    if (serverDao.saleList.size() > 0) {
                        manjianList = new ArrayList<Trade>();
                        manjianList.addAll(serverDao.saleList);
                        initManjianData(manjian, manjianList);
                    } else {
                        manjian.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    private void initHeaderBanner(View view, final List<BannerInfo> bannerInfos) {
        gallery = (FancyCoverFlow) view.findViewById(R.id.gallery);
        // 让默认间距更小一点
        gallery.setSpacing(-DeviceUtil.dip2px(getActivity(), 50));
        galleryAdapter = new GalleryAdapter(getActivity(), bannerInfos);
        gallery.setAdapter(galleryAdapter);
        // 如果列表有很多项 默认选中第二项
        if (galleryAdapter.getCount() > 2) {
            gallery.setSelection(1);
        }
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailPackActivity.class);
                intent.putExtra("party_type", bannerInfos.get(position).getParty_type());
                intent.putExtra("goods_id", bannerInfos.get(position).getAuction_id());
                intent.putExtra("party_id", bannerInfos.get(position).getParty_id());
                startActivity(intent);

            }
        });
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                galleryAdapter.setSelectItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSuggestData(View view, final List<Goods> sugList) {
        gv_suggest = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_suggest);
        gv_suggest.addHeaderView(header);
        gv_suggest.addFooterView(footer);
        footer.setOnClickListener(this);
        hosSaleAdapter = new HotSaleAdapter(getActivity(), sugList);
        gv_suggest.setAdapter(hosSaleAdapter);
        hosSaleAdapter.notifyDataSetChanged();
        gv_suggest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailPackActivity.class);
                intent.putExtra("party_type", sugList.get(position).getParty_type());
                intent.putExtra("goods_id", sugList.get(position).getAuction_id());
                intent.putExtra("party_id", sugList.get(position).getParty_id());
                startActivity(intent);
            }
        });
    }

    /**
     * 拍卖专场
     *
     * @param view
     */
    private void initAuctionData(View view, List<Trade> auctionList) {
        ll_trade = (LinearLayout) view.findViewById(R.id.ll_trade);
        ll_trade.getLayoutParams().height = ((DeviceUtil.getScreenWidth(getActivity()) - 150) / 2);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("拍卖专场");

        RecyclerTradeAdapter tradeAdapter = new RecyclerTradeAdapter(auctionList, R.layout.zpf_item_trade, getActivity());
        rv_auction = (RecyclerView) view.findViewById(R.id.rv_trade);
        rv_auction.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_auction.setLayoutManager(layoutManager);
        rv_auction.setAdapter(tradeAdapter);
        tradeAdapter.notifyDataSetChanged();
    }


    /**
     * 限时秒杀
     *
     * @param view
     */
    private void initMiaoshaData(View view, List<Trade> miaoshaList) {
        ll_trade = (LinearLayout) view.findViewById(R.id.ll_trade);
        ll_trade.getLayoutParams().height = ((DeviceUtil.getScreenWidth(getActivity()) - 150) / 2);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("限时秒杀");

        RecyclerTradeAdapter tradeAdapter = new RecyclerTradeAdapter(miaoshaList, R.layout.zpf_item_trade, getActivity());
        rv_miaosha = (RecyclerView) view.findViewById(R.id.rv_trade);
        rv_miaosha.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_miaosha.setLayoutManager(layoutManager);
        rv_miaosha.setAdapter(tradeAdapter);
        tradeAdapter.notifyDataSetChanged();
    }

    /**
     * 满减活动
     *
     * @param view
     */
    private void initManjianData(View view, List<Trade> manjianList) {
        ll_trade = (LinearLayout) view.findViewById(R.id.ll_trade);
        ll_trade.getLayoutParams().height = ((DeviceUtil.getScreenWidth(getActivity()) - 150) / 2);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("满减活动");

        RecyclerTradeAdapter tradeAdapter = new RecyclerTradeAdapter(manjianList, R.layout.zpf_item_trade, getActivity());
        rv_manjian = (RecyclerView) view.findViewById(R.id.rv_trade);
        rv_manjian.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_manjian.setLayoutManager(layoutManager);
        rv_manjian.setAdapter(tradeAdapter);
        tradeAdapter.notifyDataSetChanged();
    }
}

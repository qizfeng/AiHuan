package com.zipingfang.aihuan.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.dao.AllGoodsServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableGridView;
import com.zipingfang.aihuan.ui.adapter.AttentionGoodsAdapter;
import com.zipingfang.aihuan.ui.adapter.GoodsAllAdapter;
import com.zipingfang.aihuan.ui.adapter.SuggestAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.GridViewWithHeaderAndFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/29.
 */
public class GoodsAllFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {
    private String type;
    private View view;
    private PullableGridView lv_container;
    private PullToRefreshLayout refreshLayout;
    private List<Goods> mData = new ArrayList<>();
    private GoodsAllAdapter mAdapter;
    public int pageIndex = 1;
    private BaseActivity base;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_goods_all, null);
        base = (BaseActivity) getActivity();
        base.showLoading(getActivity(), "加载中...");
        initView();
        loadData();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
        intent.putExtra("agid",mData.get(position).getGoods_id());
        intent.putExtra("party_id",mData.get(position).getPid());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        lv_container = (PullableGridView) view.findViewById(R.id.lv_container);
        refreshLayout.setOnRefreshListener(this);
        lv_container.setOnItemClickListener(this);
    }

    private void loadData() {
        final AllGoodsServerDao serverDao = new AllGoodsServerDao(getActivity());
        serverDao.pageIndex = pageIndex;
        type = GoodsAllActivity.categoryList.get(GoodsAllActivity.viewPager.getCurrentItem()).getId();
        serverDao.category_id = type;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                base.hideLoading();
                if (serverDao.isSucc) {
                    if (pageIndex == 1) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        mData = new ArrayList<>();
                    } else {
                        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        if (serverDao.goodsList.size() == 0) {
                            ToastUtils.show(getActivity(), "没有更多数据");
                            return;
                        }
                    }
                    mData.addAll(serverDao.goodsList);
                    mAdapter = new GoodsAllAdapter(getActivity(), mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (pageIndex == 1) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    } else {
                        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                    ToastUtils.show(getActivity(), "没有更多数据");
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex = 1;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex++;
        loadData();
    }
}

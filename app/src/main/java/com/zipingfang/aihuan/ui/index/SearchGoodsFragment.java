package com.zipingfang.aihuan.ui.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.SearchServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableGridView;
import com.zipingfang.aihuan.ui.adapter.AttentionGoodsAdapter;
import com.zipingfang.aihuan.ui.adapter.SearchGoodsAdapter;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/22.
 */

public class SearchGoodsFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private PullableGridView lv_container;
    private PullToRefreshLayout refreshLayout;
    private View view;
    private SearchGoodsAdapter goodsAdapter;
    private BaseActivity base;
    private List<Attention> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_attention_goods, null);
        lv_container = (PullableGridView) view.findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        base = (BaseActivity) getActivity();
        refreshLayout.setOnRefreshListener(this);
        lv_container.pullable=false;
        loadData();
        return view;
    }

    private void loadData() {
        final SearchServerDao serverDao = new SearchServerDao(getActivity());
        serverDao.type = "goods";
        serverDao.keyword=getActivity().getIntent().getStringExtra("search");
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {

                if (success) {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if (serverDao.isSucc) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        if (serverDao.goodsList.size() == 0) {
                            ToastUtils.show(getActivity(), "没有更多数据");
                        } else {
                            mData =new ArrayList<>();
                            mData.addAll(serverDao.goodsList);
                            goodsAdapter = new SearchGoodsAdapter(getActivity(), mData,base.getUserId(getActivity()));
                            lv_container.setAdapter(goodsAdapter);
                            goodsAdapter.notifyDataSetChanged();
                        }
                    }else {
                        ToastUtils.show(getActivity(), serverDao.desc);
                    }
                } else {
                    refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
    }
}

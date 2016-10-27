package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.dao.AttentionMyListServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableGridView;
import com.zipingfang.aihuan.ui.adapter.AttentionGoodsAdapter;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class AttentionGoodsFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private PullableGridView lv_container;
    private PullToRefreshLayout refreshLayout;
    private View view;
    private AttentionGoodsAdapter goodsAdapter;
    private BaseActivity base;
    private List<Attention> mData = new ArrayList<>();
    private int pageIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_attention_goods, null);
        lv_container = (PullableGridView) view.findViewById(R.id.lv_container);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        base = (BaseActivity) getActivity();
        refreshLayout.setOnRefreshListener(this);
        loadData();
        return view;
    }

    private void loadData() {
        final AttentionMyListServerDao serverDao = new AttentionMyListServerDao(getActivity());
        serverDao.type = "goods";
        serverDao.mid = base.getUserId(getActivity());
        serverDao.pageIndex = pageIndex;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if (!StringUtils.isEmpty(serverDao.desc))
                        ToastUtils.show(getActivity(), serverDao.desc);
                    if (pageIndex == 1)
                        mData = new ArrayList<>();
                    else{
                        if(serverDao.goodsList.size()==0){
                            ToastUtils.show(getActivity(),"没有更多数据");
                            return;
                        }
                    }
                    mData.addAll(serverDao.goodsList);
                    goodsAdapter = new AttentionGoodsAdapter(getActivity(), mData,base.getUserId(getActivity()));
                    lv_container.setAdapter(goodsAdapter);
                    goodsAdapter.notifyDataSetChanged();
                } else {
                    refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
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

package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.gallery.FancyCoverFlow;
import com.zipingfang.aihuan.gallery.FancyCoverFlowAdapter;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/19.
 */
public class GalleryAdapter extends FancyCoverFlowAdapter {

    private int selectItem;
    private Context mContext;
    private List<BannerInfo> mData = new ArrayList<BannerInfo>();

    public GalleryAdapter(Context mContext, List<BannerInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setSelectItem(int selectItem) {

        if (this.selectItem != selectItem) {
            this.selectItem = selectItem;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView,
                                 ViewGroup viewGroup) {
        if (reuseableView == null) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            reuseableView = inflater
                    .inflate(R.layout.item_view_group, null);
            // 需要设置宽高 这个地方也是关键点
            reuseableView.setLayoutParams(new FancyCoverFlow.LayoutParams(
                    DeviceUtil.dip2px(context, 320), DeviceUtil.dip2px(
                    context, 200)));
        }
        ImageView ivCover = (ImageView) reuseableView.findViewById(R.id.ivCover);
//        android.widget.Gallery.LayoutParams params = new android.widget.Gallery.LayoutParams(DeviceUtil.dip2px(mContext, 250), DeviceUtil.dip2px(mContext, 200));
//        ivCover.setLayoutParams(params);
//        ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        BannerInfo item = mData.get(i);

        ImageLoader.getInstance().displayImage(item.getCover(), ivCover, ImageLoaderConfig.normal);
        if (selectItem == i) {
            ivCover.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(DeviceUtil.dip2px(mContext, 320), DeviceUtil.dip2px(
                    mContext, 200)));
        } else {
            //未选中
            ivCover.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(DeviceUtil.dip2px(mContext, 320), DeviceUtil.dip2px(
                    mContext, 160)));
        }
//        ivCover.setImageResource(R.mipmap.icon_error);
        return reuseableView;
    }
}
package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhiyu on 16/10/19.
 */

public class ActivePartyGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Goods> mData = new ArrayList<Goods>();
    private LayoutInflater mInflater;

    public ActivePartyGoodsAdapter(Context mContext, List<Goods> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_active_party_goods, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_name =(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
            holder.tv_type=(TextView)convertView.findViewById(R.id.tv_type);
            holder.tv_apply=(TextView)convertView.findViewById(R.id.tv_apply);
            holder.tv_hot=(TextView)convertView.findViewById(R.id.tv_hot);
            holder.tv_price_count=(TextView)convertView.findViewById(R.id.tv_price_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Goods item = mData.get(position);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 40) / 2,
                        (DeviceUtil.getScreenWidth(mContext) - 40) /2)));
        params.setMargins(20, 20, 20, 0);
        holder.iv_image.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_name.setText(item.getGoods_name());
        holder.tv_price.setText("￥"+item.getStarting_price());
        holder.tv_type.setText("优惠价");
        holder.tv_hot.setText("热度"+item.getCnt());
        holder.tv_price_count.setText("出价"+item.getBid());
        holder.tv_apply.setText("报名"+item.getApply_count());
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_image;
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_type;
        private TextView tv_hot;
        private TextView tv_apply;
        private TextView tv_price_count;
    }
}

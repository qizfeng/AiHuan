package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
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
import com.zipingfang.aihuan.bean.Trade;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/20.
 */
public class TradeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Trade> mData = new ArrayList<Trade>();
    private LayoutInflater mInflater;

    public TradeAdapter(Context mContext, List<Trade> mData) {
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

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_trade, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                new LinearLayout.LayoutParams((DeviceUtil
//                        .getScreenWidth(mContext) - 120) / 3,
//                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4));

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 120) /3,
                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4)));
        params1.setMargins(20, 0, 20, 0);
        holder.iv_image.setLayoutParams(params1);
        Trade item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getImg(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_goods_name.setText(item.getCname());
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_goods_name;
    }
}
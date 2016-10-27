package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Coupon;
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/22.
 */

public class CouponAdapter extends BaseAdapter {
    private Context mContext;
    private List<Coupon>mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public CouponAdapter(Context mContext, List<Coupon> mData) {
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

    long time;

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_coupon, null);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_scope = (TextView) convertView.findViewById(R.id.tv_scope);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_valid = (TextView) convertView.findViewById(R.id.tv_valid);
            holder.rl_bg =(RelativeLayout)convertView.findViewById(R.id.rl_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Coupon item = mData.get(position);
        holder.tv_price.setText("￥"+item.getMoney());
        //1普通，2普通商品，3拍卖专场，4秒杀专场，5满减专场
        if("1".equals(item.getScope()))
            holder.tv_scope.setText("全场通用");
        else if("2".equals(item.getScope()))
            holder.tv_scope.setText("普通商品");
        else if("3".equals(item.getScope()))
            holder.tv_scope.setText("拍卖专场");
        else if("4".equals(item.getScope()))
            holder.tv_scope.setText("秒杀专场");
        else if("5".equals(item.getScope()))
            holder.tv_scope.setText("满减专场");
        holder.tv_valid.setText(item.getValid());
        holder.tv_status.setText(item.getStatus());
        if("未使用".equals(item.getStatus())){
            holder.rl_bg.setBackgroundResource(R.mipmap.tickets_normal);
        }else if("已使用".equals(item.getStatus())){
            holder.rl_bg.setBackgroundResource(R.mipmap.tickets_used);
        }else if("已过期".equals(item.getStatus())){
            holder.rl_bg.setBackgroundResource(R.mipmap.tickets_used);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_price;
        private TextView tv_scope;
        private TextView tv_status;
        private TextView tv_valid;
        private RelativeLayout rl_bg;
    }
}

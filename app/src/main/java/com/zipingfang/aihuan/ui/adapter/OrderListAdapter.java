package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Order;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class OrderListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public OrderListAdapter(Context mContext, List<Order> mData) {
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
            convertView = mInflater.inflate(R.layout.zpf_item_deposit, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time_desc = (TextView) convertView.findViewById(R.id.tv_time_desc);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_order_no = (TextView) convertView.findViewById(R.id.tv_order_no);
            holder.tv_pay_time = (TextView) convertView.findViewById(R.id.tv_pay_time);
            holder.tv_pay_status = (TextView) convertView.findViewById(R.id.tv_pay_status);
            holder.tv_deposit = (TextView) convertView.findViewById(R.id.tv_deposit);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_price_type = (TextView) convertView.findViewById(R.id.tv_price_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_price_type.setText("成交价");
        Order item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getGoods_img(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_order_no.setText(item.getId());

        holder.tv_pay_status.setText(item.getStatus_txt());
        if ("101".equals(item.getStatus())) {
            holder.tv_time.setText(DateUtils.getCurrDate(Long.parseLong(item.getCreate_time())));
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_time_desc.setText("生成时间:");
        } else if ("102".equals(item.getStatus())) {
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_time.setText(DateUtils.getCurrDate(Long.parseLong(item.getCreate_time())));
            holder.tv_time_desc.setText("付款时间:");
        } else if ("201".equals(item.getStatus())) {
            holder.tv_time.setVisibility(View.GONE);
            holder.tv_status.setText("已结束");
            holder.tv_time_desc.setText("发货时间:");
        } else if ("202".equals(item.getStatus())) {

        } else if ("301".equals(item.getStatus())) {

        } else if ("302".equals(item.getStatus())) {
            holder.tv_time.setVisibility(View.GONE);
            holder.tv_status.setText("已结束");
            holder.tv_time_desc.setText("发货时间:");
        } else if ("303".equals(item.getStatus())) {

        } else if ("401".equals(item.getStatus())) {
            holder.tv_time.setVisibility(View.GONE);
            holder.tv_status.setText("已结束");
            holder.tv_time_desc.setText("结束时间:");
        } else if ("501".equals(item.getStatus())) {

        } else if ("502".equals(item.getStatus())) {

        }
        try{
            holder.tv_pay_time.setText(DateUtils.getCurrDate(Long.parseLong(item.getTime())));
        }catch (Exception e){

        }
        holder.tv_time_desc.setText(item.getTime_text() + ":");
        holder.tv_price.setText("￥" + item.getTransaction_price());
        holder.tv_deposit.setVisibility(View.INVISIBLE);
        holder.tv_title.setText(item.getGoods_name());
        return convertView;
    }

    class ViewHolder {
        private TextView tv_order_no;
        private TextView tv_pay_time;
        private TextView tv_pay_status;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_time_desc;
        private TextView tv_deposit;
        private TextView tv_time;
        private TextView tv_status;
        private TextView tv_price_type;
    }
}


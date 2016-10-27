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
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class DepositAdapter extends BaseAdapter {
    private Context mContext;
    private List<Deposit> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public DepositAdapter(Context mContext, List<Deposit> mData) {
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
            holder.tv_price_type = (TextView) convertView.findViewById(R.id.tv_price_type);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_order_no=(TextView)convertView.findViewById(R.id.tv_order_no);
            holder.tv_pay_time=(TextView)convertView.findViewById(R.id.tv_pay_time);
            holder.tv_pay_status=(TextView)convertView.findViewById(R.id.tv_pay_status);
            holder.tv_deposit=(TextView)convertView.findViewById(R.id.tv_deposit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Deposit item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getConver(),holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_order_no.setText(item.getOrder_id());
        holder.tv_pay_status.setText(item.getStatus_txt());
        holder.tv_pay_time.setText(DateUtils.getCurrDate(Long.parseLong(item.getPayment_time())));
        holder.tv_price.setText("￥"+item.getInsureMoney());
        holder.tv_deposit.setText("￥"+item.getCash_deposit());
        holder.tv_time.setText(item.getProcceed_time());
        holder.tv_title.setText(item.getAuction_name());
        return convertView;
    }

    class ViewHolder {
        private TextView tv_order_no;
        private TextView tv_pay_time;
        private TextView tv_pay_status;
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_price_type;
        private TextView tv_deposit;
        private TextView tv_time;
    }
}

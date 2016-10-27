package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Active;
import com.zipingfang.aihuan.bean.IncomeDetail;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/20.
 */

public class IncomeDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<IncomeDetail> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public IncomeDetailAdapter(Context mContext, List<IncomeDetail> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_income, null);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_balance = (TextView) convertView.findViewById(R.id.tv_balance);
            holder.tv_income_type = (TextView) convertView.findViewById(R.id.tv_income_type);
            holder.tv_money_detail = (TextView) convertView.findViewById(R.id.tv_moeny_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IncomeDetail item = mData.get(position);
        holder.tv_balance.setText("余额:"+item.getCurrent_balance());
        holder.tv_income_type.setText(item.getComment());
        holder.tv_time.setText(item.getAdd_time());
        if("1".equals(item.getType())){
            holder.tv_money_detail.setText("+"+item.getIncrease_money());
        }else if("2".equals(item.getType())) {
            holder.tv_money_detail.setText("-"+item.getDecrease_money());
        }
        return convertView;
    }


    class ViewHolder {
        private TextView tv_time;
        private TextView tv_income_type;
        private TextView tv_balance;
        private TextView tv_money_detail;
    }



}

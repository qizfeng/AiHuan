package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Express;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/26.
 */

public class ExpressAdapter extends BaseAdapter {
    private Context mContext;
    private List<Express> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public ExpressAdapter(Context mContext, List<Express> mData) {
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_express, null);
            holder.tv_express = (TextView) convertView.findViewById(R.id.tv_express);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Express item = mData.get(position);
        holder.tv_express.setText(item.getName());
        return convertView;
    }

    class ViewHolder {

        private TextView tv_express;
    }
}


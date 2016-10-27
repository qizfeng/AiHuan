package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Jiangjia;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/14.
 */
public class JiangjiaAdapter extends BaseAdapter {
    private Context mContext;
    private List<Jiangjia> mData = new ArrayList<Jiangjia>();
    private LayoutInflater mInflater;

    public JiangjiaAdapter(Context mContext, List<Jiangjia> mData) {
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
            convertView = mInflater.inflate(R.layout.zpf_item_jiangjia, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_end_time = (TextView) convertView.findViewById(R.id.tv_end_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 40) / 2,
                        (DeviceUtil.getScreenWidth(mContext) - 160) / 3));
        params.setMargins(20, 0, 20, 0);
        holder.iv_image.setLayoutParams(params);
        holder.tv_end_time.getLayoutParams().width=(DeviceUtil
                .getScreenWidth(mContext) - 40) / 2;
        holder.tv_end_time.setBackgroundColor(Color.argb(123, 0, 0, 0));
        Jiangjia item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getUrl(), holder.iv_image, ImageLoaderConfig.normal);
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_end_time;
    }
}



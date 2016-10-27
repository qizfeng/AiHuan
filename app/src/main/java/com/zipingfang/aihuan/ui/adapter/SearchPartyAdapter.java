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
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/22.
 */

public class SearchPartyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Attention> mData = new ArrayList<Attention>();
    private LayoutInflater mInflater;
    private String mid;

    public SearchPartyAdapter(Context mContext, List<Attention> mData, String mid) {
        this.mContext = mContext;
        this.mData = mData;
        this.mid = mid;
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

//    long time;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
//        if (convertView == null) {
        holder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.zpf_item_attention_special, null);
        holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        holder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
        holder.tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
        holder.tv_min = (TextView) convertView.findViewById(R.id.tv_min);
        holder.tv_sec = (TextView) convertView.findViewById(R.id.tv_sec);
        convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        final Attention item = mData.get(position);
        holder.iv_delete.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_name.setText(item.getTitle());
        holder.tv_hot.setText(item.getCnt());
        holder.tv_name.setText(item.getTitle());
        final long  time = Long.parseLong(item.getLast_time());
       if(time>0){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    long curTime = Long.parseLong(item.getLast_time())-1;
                    item.setLast_time(curTime+"");

                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(Long.parseLong(item.getLast_time()));
                    String[] split = formatLongToTimeStr.split(":");
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            holder.tv_hour.setText("距离结束还有" + split[0] + ":");
                        }
                        if (i == 1) {
                            holder.tv_min.setText(split[1] + ":");
                        }
                        if (i == 2) {
                            holder.tv_sec.setText(split[2] + "");
                        }

                    }
                    if (time > 0) {
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        } else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    long curTime = Math.abs(Long.parseLong(item.getLast_time()))-1;
                    item.setLast_time(curTime+"");

                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(Math.abs(Long.parseLong(item.getLast_time())));
                    String[] split = formatLongToTimeStr.split(":");
//                    Log.e("qizfeng", "split:" + split[0] + "," + split[1] + "," + split[2]);
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            holder.tv_hour.setText("距离开始还有" + split[0] + ":");
                        }
                        if (i == 1) {
                            holder.tv_min.setText(split[1] + ":");
                        }
                        if (i == 2) {
                            holder.tv_sec.setText(split[2] + "");
                        }

                    }
                    if (Math.abs(time) > 0) {
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }

//        holder.tv_price.setText(item.getP());
        return convertView;
    }



    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_time;
        private TextView tv_hot;
        private TextView tv_price;
        private TextView tv_name;
        private ImageView iv_delete;
        private TextView tv_hour, tv_min, tv_sec;
    }

    Handler handler = new Handler() {
    };

}

package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.DelAttentionServerDao;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class ActiveAdapter extends BaseAdapter {
    private Context mContext;
    private List<Active> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public ActiveAdapter(Context mContext, List<Active> mData) {
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
//        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_active, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            holder.tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
            holder.tv_min = (TextView) convertView.findViewById(R.id.tv_min);
            holder.tv_sec = (TextView) convertView.findViewById(R.id.tv_sec);
            holder.tv_active_type=(TextView)convertView.findViewById(R.id.tv_active_type);
            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        final Active item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_name.setText(item.getTitle());
        holder.tv_hot.setText(item.getCnt());
        if("party".equals(item.getParty_type()))
              holder.tv_active_type.setText("拍");
        else if("seckill".equals(item.getParty_type()))
            holder.tv_active_type.setText("秒");
        else if("sale".equals(item.getParty_type()))
            holder.tv_active_type.setText("抢");
//        time = Long.parseLong(item.getLast_time());
        time = 15530;
        if ("5".equals(item.getStatus())) {
            holder.tv_time.setText("专场已结束");
        } else if ("4".equals(item.getStatus())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(time);
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
        } else if ("3".equals(item.getStatus())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    String formatLongToTimeStr = DateUtils.formatLongToTimeStr(time);
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
                    if (time > 0) {
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
        private TextView tv_active_type;
    }

    Handler handler = new Handler() {
    };

}

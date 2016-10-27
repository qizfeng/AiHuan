package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class JoinPartyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Party> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public JoinPartyAdapter(Context mContext, List<Party> mData) {
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
            convertView = mInflater.inflate(R.layout.zpf_item_join_party, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_price_type = (TextView) convertView.findViewById(R.id.tv_price_type);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
            holder.tv_min = (TextView) convertView.findViewById(R.id.tv_min);
            holder.tv_sec = (TextView) convertView.findViewById(R.id.tv_sec);
            holder.ll_time = (LinearLayout) convertView.findViewById(R.id.ll_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Party item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_title.setText(item.getAuction_name());
        holder.tv_price.setText("￥" + item.getMax_price());
        holder.tv_price_type.setText(item.getPrice_desc());
        if ("off".equals(item.getStart_status())) {
            holder.tv_status.setText("已结束");
            holder.ll_time.setVisibility(View.GONE);
        } else if ("on".equals(item.getStart_status())) {
            time = Long.parseLong(item.getRange_time());
            holder.tv_status.setText("距离结束还有");
            holder.ll_time.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    String formatLongToTimeStr = formatLongToTimeStr(time);
                    String[] split = formatLongToTimeStr.split("：");
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            holder.tv_hour.setText(split[0]);
                        }
                        if (i == 1) {
                            holder.tv_min.setText(split[1] + "分钟");
                        }
                        if (i == 2) {
                            holder.tv_sec.setText(split[2] + "秒");
                        }

                    }
                    if (time > 0) {
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_price_type;
        private TextView tv_status;
        private TextView tv_hour, tv_min, tv_sec;
        private LinearLayout ll_time;
    }


    Handler handler = new Handler();

    public String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour + "：" + minute + "：" + second;
        return strtime;

    }

}

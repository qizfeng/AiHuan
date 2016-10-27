package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Message;
import com.zipingfang.aihuan.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/9/29.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Message> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public MessageAdapter(Context mContext, List<Message> mData) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_message, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Message item = mData.get(position);
        holder.tv_content.setText(item.getContent());
        holder.tv_title.setText(item.getTitle());
        holder.tv_time.setText(DateUtils.getCurrDate(Long.parseLong(item.getCreate_time())));
        if (item.getMsg_type().equals("定时消息")) {
            holder.iv_image.setImageResource(R.mipmap.img_timernesw);
        } else if (item.getMsg_type().equals("系统消息")) {
            holder.iv_image.setImageResource(R.mipmap.img_systemnews);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_time;
    }
}

package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.ValueCard;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ValueCardBuyServerDao;
import com.zipingfang.aihuan.dao.ValueCardMyServerDao;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/21.
 */

public class ValueCardAdapter extends BaseAdapter {
    private Context mContext;
    private List<ValueCard> mData = new ArrayList<ValueCard>();
    private LayoutInflater mInflater;
    private boolean isBuy = false;
    private String mid;
    public ValueCardAdapter(Context mContext, List<ValueCard> mData,boolean isBuy,String mid) {
        this.mContext = mContext;
        this.mData = mData;
        this.isBuy = isBuy;
        this.mid = mid;
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
            convertView = mInflater.inflate(R.layout.zpf_item_value_card, null);
            holder.rl_bg = (RelativeLayout) convertView.findViewById(R.id.rl_value_card_bg);
            holder.rl_buy = (RelativeLayout) convertView.findViewById(R.id.rl_buy);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_card_content);
            holder.tv_moeny = (TextView) convertView.findViewById(R.id.tv_card_money);
            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_card_no);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(isBuy)
            holder.rl_buy.setVisibility(View.VISIBLE);
        else
            holder.rl_buy.setVisibility(View.GONE);
        ValueCard item = mData.get(position);
        holder.tv_no.setText("NO."+item.getCard_id());
        holder.tv_moeny.setText("¥"+item.getCard_money());
        holder.tv_content.setText(item.getCard_description());
        if(position%2==0){
            holder.rl_bg.setBackground(mContext.getResources().getDrawable(R.drawable.zpf_shape_bg_round_blue));
        }else {
            holder.rl_bg.setBackground(mContext.getResources().getDrawable(R.drawable.zpf_shape_bg_round_pink));
        }
//        onBuy(item,holder.rl_buy);
        return convertView;
    }

    class ViewHolder {
        private RelativeLayout rl_bg;
        private TextView tv_content;
        private TextView tv_moeny;
        private TextView tv_no;
        private RelativeLayout rl_buy;

    }


}
package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.ui.index.GoodsDetailPartyActivity;
import com.zipingfang.aihuan.ui.trade.GoodsListActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.List;

/**
 * Created by 峰 on 2016/9/27.
 */
public class RecyclerAuctionAdapter extends RecyclerView.Adapter<RecyclerAuctionAdapter.ViewHolder> {

    private List<Party> items;
    private int itemLayout;
    private Context mContext;

    public RecyclerAuctionAdapter(List<Party> items, int itemLayout, Context context) {
        this.items = items;
        this.itemLayout = itemLayout;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }
    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                new LinearLayout.LayoutParams((DeviceUtil
//                        .getScreenWidth(mContext) - 40) / 2,
//                        (DeviceUtil.getScreenWidth(mContext) - 160) / 3));
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 40) /2,
                        (DeviceUtil.getScreenWidth(mContext) - 160) / 3)));
//        holder.ll_content.getLayoutParams().height=(DeviceUtil.getScreenWidth(mContext) - 160)/ 3;
        params1.setMargins(10, 0,10, 0);
        holder.iv_image.setLayoutParams(params1);
        holder.tv_end_time.getLayoutParams().width=(DeviceUtil
                .getScreenWidth(mContext) - 40) / 2;
        holder.tv_end_time.setBackgroundColor(Color.argb(123, 0, 0, 0));
        final Party item = items.get(position);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("party_type", item.getParty_type());
                intent.putExtra("party_id", item.getPid());
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView iv_image;
        public TextView tv_end_time;
        public LinearLayout ll_content;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_end_time = (TextView) itemView.findViewById(R.id.tv_end_time);
            ll_content =(LinearLayout)itemView.findViewById(R.id.ll_content);
        }
    }
}


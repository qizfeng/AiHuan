package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.ui.index.GoodsDetailSecKillActivity;
import com.zipingfang.aihuan.ui.trade.GoodsListActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.List;

/**
 * Created by 峰 on 2016/9/27.
 */
public class RecyclerMiaoshaAdapter extends RecyclerView.Adapter<RecyclerMiaoshaAdapter.ViewHolder> {

    private List<Goods> items;
    private int itemLayout;
    private Context mContext;

    public RecyclerMiaoshaAdapter(List<Goods> items, int itemLayout, Context context) {
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
//                        .getScreenWidth(mContext) - 160) / 3,
//                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4));
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 160) / 3,
                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4)));
        params1.setMargins(20, 20, 20, 0);
        holder.iv_image.setLayoutParams(params1);
        final Goods item = items.get(position);
        holder.tv_now_price.setText("￥" + item.getSeckill_price());
        holder.tv_origin_price.setText("￥" + item.getStarting_price());
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_image, ImageLoaderConfig.normal);
        holder.itemView.setTag(item);
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("party_type", "seckill");
                intent.putExtra("party_id", item.getPid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;
        public ImageView iv_image;
        public TextView tv_origin_price;
        public TextView tv_now_price;
        public LinearLayout ll_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_now_price);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_content);
        }
    }
}
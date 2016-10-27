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
import com.zipingfang.aihuan.bean.Trade;
import com.zipingfang.aihuan.ui.trade.ActiveListActivity;
import com.zipingfang.aihuan.ui.trade.GoodsListActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

import java.util.List;

/**
 * Created by 峰 on 2016/9/27.
 */
public class RecyclerTradeAdapter extends RecyclerView.Adapter<RecyclerTradeAdapter.ViewHolder> {

    private List<Trade> items;
    private int itemLayout;
    private Context mContext;

    public RecyclerTradeAdapter(List<Trade> items, int itemLayout, Context context) {
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
//                        .getScreenWidth(mContext) - 120) / 3,
//                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4));
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 120) / 3,
                        (DeviceUtil.getScreenWidth(mContext) - 160) / 4)));
        params1.setMargins(20, 0, 20, 0);
        holder.iv_image.setLayoutParams(params1);
        final Trade item = items.get(position);
        ImageLoader.getInstance().displayImage(item.getImg(), holder.iv_image, ImageLoaderConfig.normal);
        holder.tv_goods_name.setText(item.getCname());
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActiveListActivity.class);
                intent.putExtra("party_type", item.getParty_type());
                intent.putExtra("category_id",item.getCategory_id());
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
        public ImageView iv_image;
        public TextView tv_goods_name;
        public LinearLayout ll_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_content);
        }
    }
}

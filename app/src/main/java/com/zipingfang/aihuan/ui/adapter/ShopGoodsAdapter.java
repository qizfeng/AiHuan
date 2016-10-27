package com.zipingfang.aihuan.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.ShopGoods;
import com.zipingfang.aihuan.bean.ShopOrder;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ShopManagerGoodsUpOrDownServerDao;
import com.zipingfang.aihuan.utils.DateUtils;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/27.
 */

public class ShopGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopGoods> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public ShopGoodsAdapter(Context mContext, List<ShopGoods> mData) {
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
            convertView = mInflater.inflate(R.layout.zpf_item_shop_goods, null);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
            holder.tv_start_price = (TextView) convertView.findViewById(R.id.tv_start_price);
            holder.tv_scope = (TextView) convertView.findViewById(R.id.tv_scope);
            holder.tv_deposit = (TextView) convertView.findViewById(R.id.tv_deposit);
            holder.tv_reserve = (TextView) convertView.findViewById(R.id.tv_reserve);
            holder.btn_up_down = (Button) convertView.findViewById(R.id.btn_up_down);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopGoods item = mData.get(position);
        ImageLoader.getInstance().displayImage(item.getCover(), holder.iv_img, ImageLoaderConfig.normal);
        holder.tv_title.setText(item.getGoods_name());
        holder.tv_status.setText(item.getStatus_text());
        if (!StringUtils.isEmpty(item.getStock_code()))
            holder.tv_no.setText(item.getStock_code());
        holder.tv_category.setText(item.getCategory_text());
        holder.tv_start_price.setText("¥" + item.getStarting_price());
        holder.tv_scope.setText("¥" + item.getStock_num());
        holder.tv_deposit.setText("¥" + item.getDeposit());
        holder.tv_reserve.setText("¥" + item.getReserve_price());
        if ("正常".equals(item.getStatus_text())) {
            holder.btn_up_down.setText("下架");
            upOrdown(holder.btn_up_down, true, item);
        } else {
            holder.btn_up_down.setText("上架");
            upOrdown(holder.btn_up_down, false, item);
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_status;
        private TextView tv_no;
        private TextView tv_category;
        private TextView tv_start_price;
        private TextView tv_scope;
        private TextView tv_deposit;
        private TextView tv_reserve;
        private Button btn_up_down;
    }

    private void upOrdown(View view, final boolean isUp, final  ShopGoods item) {
        final ShopManagerGoodsUpOrDownServerDao serverDao = new ShopManagerGoodsUpOrDownServerDao(mContext);
        serverDao.agid = item.getAgid();
        if (isUp)
            serverDao.action = "1";
        else
            serverDao.action = "2";
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverDao.loadData(new BaseDao.IDaoCallback() {
                    @Override
                    public void exec(boolean success, Object obj) {
                        if (serverDao.isSucc) {
                            ToastUtils.show(mContext, "操作成功");
                            if(isUp){
                                item.setStatus_text("正常");
                            }else {
                                item.setStatus_text("已下架");
                            }
                            notifyDataSetChanged();
                        } else {
                            ToastUtils.show(mContext, serverDao.desc);
                        }
                    }
                });
            }
        });

    }
}




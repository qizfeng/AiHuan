package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.DelAttentionServerDao;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 峰 on 2016/10/8.
 */
public class AttentionGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Attention> mData = new ArrayList<Attention>();
    private String mid;
    private LayoutInflater mInflater;

    public AttentionGoodsAdapter(Context mContext, List<Attention> mData,String mid) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_attention_goods, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            holder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Attention item = mData.get(position);
        onDeleteListener(holder.iv_delete, position,item);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)
                (new LinearLayout.LayoutParams((DeviceUtil
                        .getScreenWidth(mContext) - 40) / 2,
                        (DeviceUtil.getScreenWidth(mContext) - 40) /2)));
        params.setMargins(20, 20, 20, 0);
        holder.iv_image.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(item.getParty_cover(), holder.iv_image, ImageLoaderConfig.normal);
//        holder.tv_name.setText(item.getParty_title());
        holder.tv_hot.setText("热度" + item.getCnt());
        holder.tv_price.setText(item.getStarting_price());
        holder.tv_name.setText(item.getGoods_name());
//        holder.ll_content.getLayoutParams().width = (DeviceUtil
//                .getScreenWidth(mContext) - 20) / 2;
//        holder.ll_content.getLayoutParams().height =  (DeviceUtil.getScreenWidth(mContext) - 160) / 3;
        return convertView;
    }

    private void onDeleteListener(ImageView imageView, final int position, final Attention item) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog.Builder builder = new DeleteDialog.Builder(mContext);
                builder.setMessage("确认要删除吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final DelAttentionServerDao serverDao = new DelAttentionServerDao(mContext);
                        serverDao.mid = mid;
                        serverDao.goods_id = item.getAgid();
                        serverDao.type=2;
                        serverDao.loadData(new BaseDao.IDaoCallback() {
                            @Override
                            public void exec(boolean success, Object obj) {
                                if (serverDao.isSucc) {
                                    ToastUtils.show(mContext, "删除成功");
                                    mData.remove(position);
                                    notifyDataSetChanged();
                                } else
                                    ToastUtils.show(mContext, serverDao.desc);
                            }
                        });
                    }
                });

                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_hot;
        private TextView tv_price;
        private TextView tv_name;
        private ImageView iv_delete;
        private LinearLayout ll_content;
    }

}

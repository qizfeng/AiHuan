package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Address;
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.dao.AddressInfoServerDao;
import com.zipingfang.aihuan.dao.AddressServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.DelAddressServerDao;
import com.zipingfang.aihuan.dao.DelAttentionServerDao;
import com.zipingfang.aihuan.ui.user.AddressListActivity;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.ui.user.ModifyAddressActivity;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zipingfang.aihuan.R.id.lv_container;

/**
 * Created by 峰 on 2016/10/9.
 */
public class AddressListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Address> mData = new ArrayList<Address>();
    private String mid;
    private LayoutInflater mInflater;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

    public AddressListAdapter(Context mContext, List<Address> mData, String mid) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.zpf_item_address, null);
            holder.tv_buyer_name = (TextView) convertView.findViewById(R.id.tv_buyer_name);
            holder.tv_edit = (TextView) convertView.findViewById(R.id.tv_edit);
            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            holder.tv_buyer_phone = (TextView) convertView.findViewById(R.id.tv_buyer_phone);
            holder.tv_buyer_address = (TextView) convertView.findViewById(R.id.tv_buyer_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RadioButton rb_default = (RadioButton) convertView.findViewById(R.id.rb_is_default);
        holder.rb_default = rb_default;
        final Address item = mData.get(position);
        if (item.getIs_default().equals("1"))
            holder.rb_default.setChecked(true);
        else
            holder.rb_default.setChecked(false);
        holder.tv_buyer_address.setText("收货地址:" + item.getAddr_area() + item.getAddr_detail());
        holder.tv_buyer_name.setText("收货人:" + item.getReceiver_name());
        holder.tv_buyer_phone.setText(item.getReceiver_phone());

        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        holder.rb_default.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //重置，确保最多只有一项被选中
                for (int i = 0; i < states.size(); i++) {
                    mData.get(i).setIs_default("0");
                }
                mData.get(position).setIs_default("1");
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), rb_default.isChecked());
                AddressServerDao serverDao = new AddressServerDao(mContext);
                serverDao.mid = mid;
                serverDao.address_id = item.getId();
                serverDao.setDefaultAddress();
                notifyDataSetChanged();
            }
        });
        boolean res = false;

        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
//            item.setIs_default("0");
            if (item.getIs_default().equals("1"))
                res = true;
            else
                res = false;
//            res = false;
            states.put(String.valueOf(position), false);
        } else {
//            item.setIs_default("1");
            res = true;
        }

        if (res)
            holder.rb_default.setText("默认地址");
        else
            holder.rb_default.setText("设为默认");
        Log.e("qizfeng", "isDefault;" + res);
        holder.rb_default.setChecked(res);
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ModifyAddressActivity.class);
                intent.putExtra("address_id", item.getId());
                mContext.startActivity(intent);
            }
        });
        onDeleteListener(holder.tv_delete, position, item);
        return convertView;
    }


    public void onDeleteListener(TextView textView, final int position, final Address item) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog.Builder builder = new DeleteDialog.Builder(mContext);
                builder.setMessage("确认要删除吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final DelAddressServerDao serverDao = new DelAddressServerDao(mContext);
                        serverDao.mid = mid;
                        serverDao.address_id = item.getId();
                        serverDao.loadData(new BaseDao.IDaoCallback() {
                            @Override
                            public void exec(boolean success, Object obj) {
                                if (serverDao.isSucc) {
                                    ToastUtils.show(mContext, "删除成功");
                                    mData.remove(position);
                                    loadData();

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

    public void loadData() {
        final AddressServerDao serverDao = new AddressServerDao(mContext);
        serverDao.mid = mid;
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                mData = new ArrayList<>();
                mData.addAll(serverDao.mData);
                notifyDataSetChanged();
            }
        });
    }

    class ViewHolder {
        private TextView tv_buyer_name;
        private TextView tv_edit;
        private TextView tv_delete;
        private TextView tv_buyer_phone;
        private TextView tv_buyer_address;
        private RadioButton rb_default;
    }
}

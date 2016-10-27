package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Category;
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.dao.AddressServerDao;
import com.zipingfang.aihuan.utils.DeviceUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 峰 on 2016/10/11.
 */

public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Category> mData;
    private LayoutInflater mInflater;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

    public CategoryAdapter(Context mContext, List<Category> mData) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.zpf_item_category, null);
        final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.getLayoutParams().width = DeviceUtil
                .getScreenWidth(mContext) / 5;
        Category item = mData.get(position);
        radioButton.setText(item.getCname());

        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
//        radioButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //重置，确保最多只有一项被选中
//                for (String key : states.keySet()) {
//                    states.put(key, false);
//                }
//                states.put(String.valueOf(position), radioButton.isChecked());
//                notifyDataSetChanged();
//            }
//        });
        boolean res = false;
//        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
//            states.put(String.valueOf(position), false);
//            res = false;
//        } else {
//            res = true;
//        }
        Log.e("qizfeng","check:"+position+","+Constants.category_index);
        if (position == Constants.category_index) {
            radioButton.setChecked(true);
        } else
            radioButton.setChecked(res);
        return convertView;
    }
}

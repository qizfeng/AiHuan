package com.zipingfang.aihuan.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Category;
import com.zipingfang.aihuan.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by qizfeng on 16/10/19.
 */

public class GridViewRadioAdapter extends BaseAdapter {
    private Context mContext;           // 定义Context
    //    private Vector<Integer> mImageIds = new Vector<Integer>();  // 定义一个向量作为图片源
    private Vector<Boolean> mImage_bs = new Vector<Boolean>();  // 定义一个向量作为选中与否容器
    private List<Category> mData = new ArrayList<>();
    private int lastPosition = 0;      //记录上一次选中的图片位置，-1表示未选中任何图片
    private boolean multiChoose;        //表示当前适配器是否允许多选
    private String selectIndex="0";
    public GridViewRadioAdapter(Context c, List<Category> mData, int selectIndex) {
        mContext = c;
        this.mData = mData;
        // 装入资源
        for (int i = 0; i < mData.size(); i++) {
            if(selectIndex==i)
                mImage_bs.add(true);
            else
                mImage_bs.add(false);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);        // 给ImageView设置资源

            textView.setLayoutParams(new GridView.LayoutParams(220,100));   // 设置布局图片
            textView.setGravity(Gravity.CENTER);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);     // 设置显示比例类型
        } else {
            textView = (TextView) convertView;
        }
//        imageView.setImageDrawable(makeBmp(mImageIds.elementAt(position),
//                mImage_bs.elementAt(position)));

        if (mImage_bs.elementAt(position)) {
            textView.setBackground(mContext.getResources().getDrawable(R.mipmap.img_bg_content_s));
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            textView.setBackground(mContext.getResources().getDrawable(R.mipmap.img_bg_content_n));
            textView.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        Category item = mData.get(position);
        textView.setText(item.getCname());
        return textView;
    }


    // 修改选中的状态
    public void changeState(int position) {

        // 单选时
        if (lastPosition != -1)
            mImage_bs.setElementAt(false, lastPosition);    //取消上一次的选中状态
        mImage_bs.setElementAt(!mImage_bs.elementAt(position), position);   //直接取反即可
        lastPosition = position;        //记录本次选中的位置

        notifyDataSetChanged();     //通知适配器进行更新
    }
}

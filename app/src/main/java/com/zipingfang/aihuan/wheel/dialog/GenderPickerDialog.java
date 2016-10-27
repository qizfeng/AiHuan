package com.zipingfang.aihuan.wheel.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.wheel.OnWheelChangedListener;
import com.zipingfang.aihuan.wheel.OnWheelScrollListener;
import com.zipingfang.aihuan.wheel.WheelView;
import com.zipingfang.aihuan.wheel.adapter.AbstractWheelTextAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 峰 on 2016/9/28.
 */
public class GenderPickerDialog extends BaseDialog implements
        View.OnClickListener {

    public static final int DIALOG_MODE_CENTER = 0;
    public static final int DIALOG_MODE_BOTTOM = 1;

    private WheelView wvGender;

    private View vDialog;
    private View vDialogChild;
    private ViewGroup vDialogPicker;
    private TextView tvTitle;
    private TextView btnSure;
    private TextView btnCancel;

    private Context context;
    private JSONObject mJsonObj;
    private String[] mProvinceDatas;

    private ArrayList<String> arrProvinces = new ArrayList<String>();
    private GenderTextAdapter provinceAdapter;

    private String strTitle = "取消";
    private String strGender = "男";
    private OnGenderCListener OnGenderCListener;

    private int maxsize = 20;
    private int minsize = 15;

    public GenderPickerDialog(Context context) {
        super(context, R.layout.dialog_picker_center);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        vDialogPicker = (ViewGroup) findViewById(R.id.ly_dialog_picker);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        // 此处相当于布局文件中的Android:layout_gravity属性
        lp.gravity = Gravity.CENTER_VERTICAL;

        wvGender = new WheelView(context);
        wvGender.setLayoutParams(lp);
        vDialogPicker.addView(wvGender);

        vDialog = findViewById(R.id.ly_dialog);
        vDialogChild = findViewById(R.id.ly_dialog_child);
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        btnSure = (TextView) findViewById(R.id.btn_dialog_sure);
        btnCancel = (TextView) findViewById(R.id.btn_dialog_cancel);

        tvTitle.setText(strTitle);
        vDialog.setOnClickListener(this);
        vDialogChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
//        if (null != btnCancel) {
            tvTitle.setOnClickListener(this);
//        }

        initGender();
        provinceAdapter = new GenderTextAdapter(context, arrProvinces,
                getProvinceItem(strGender), maxsize, minsize);
        wvGender.setVisibleItems(5);
        wvGender.setViewAdapter(provinceAdapter);
        wvGender.setCurrentItem(getProvinceItem(strGender));


        wvGender.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel
                        .getCurrentItem());
                strGender = currentText;
                setTextviewSize(currentText, provinceAdapter);

            }
        });

        wvGender.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel
                        .getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnGenderCListener {
        public void onClick(String province);
    }

    public void setOnGenderCListener(OnGenderCListener OnGenderCListener) {
        this.OnGenderCListener = OnGenderCListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnSure) {
            if (OnGenderCListener != null) {
                OnGenderCListener.onClick(strGender);
            }
        } else if (v.getId() == R.id.tv_dialog_title) {
            dismiss();
        } else if (v == vDialogChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();
    }



    /**
     * 初始数据
     */
    public void initGender() {
        arrProvinces.add("男");
        arrProvinces.add("女");
    }


    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText,
                                GenderTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }

    /**
     * 设置dialog弹出框模式
     *
     * @param dialogMode DIALOG_MODE_CENTER 从屏幕中间弹出
     *                   DIALOG_MODE_BOTTOM 从屏幕底部弹出
     */
    public void setDialogMode(int dialogMode) {
        if (dialogMode == DIALOG_MODE_BOTTOM) {
            resetContent(R.layout.dialog_picker_bottom);
            setAnimation(R.style.AnimationBottomDialog);
            setGravity(Gravity.BOTTOM);
        }
    }

    public void setTitle(String title) {
        this.strTitle = title;
    }

    @Override
    protected int dialogWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 初始化地点
     *
     * @param province
     * @param city
     */
    public void setAddress(String province, String city) {
        if (province != null && province.length() > 0) {
            this.strGender = province;
        }

    }

    /**
     * 返回省会索引，没有就返回默认“四川”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = arrProvinces.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(arrProvinces.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            strGender = "四川";
            return 22;
        }
        return provinceIndex;
    }

    

    private class GenderTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected GenderTextAdapter(Context context, ArrayList<String> list,
        int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem,
                    maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

}
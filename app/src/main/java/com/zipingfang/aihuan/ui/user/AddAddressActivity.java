package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.AddressServerDao;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.ValidateUtil;
import com.zipingfang.aihuan.wheel.OnWheelChangedListener;
import com.zipingfang.aihuan.wheel.OnWheelScrollListener;
import com.zipingfang.aihuan.wheel.WheelView;
import com.zipingfang.aihuan.wheel.adapter.ArrayWheelAdapter;

/**
 * Created by 峰 on 2016/10/10.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener, OnWheelScrollListener {
    private EditText et_name;
    private EditText et_phone;
    private LinearLayout ll_area;
    private EditText et_addr;
    private RelativeLayout rl_area_wheel;
    private TextView tv_dialog_cancel, tv_dialog_sure;
    private WheelView wv_province, wv_city, wv_district;
    private LinearLayout ll_layout;
    private TextView tv_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_add_address);
        initView();
        initActionBar();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_addr = (EditText) findViewById(R.id.et_address);

        ll_area = (LinearLayout) findViewById(R.id.ll_area);
        rl_area_wheel = (RelativeLayout) findViewById(R.id.rl_area_wheel);

        tv_dialog_cancel = (TextView) findViewById(R.id.tv_dialog_cancel);
        tv_dialog_sure = (TextView) findViewById(R.id.btn_dialog_sure);
        wv_province = (WheelView) findViewById(R.id.wv_province);
        wv_city = (WheelView) findViewById(R.id.wv_city);
        wv_district = (WheelView) findViewById(R.id.wv_district);
        tv_area = (TextView) findViewById(R.id.tv_area);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        ll_layout.setOnClickListener(this);
        ll_area.setOnClickListener(this);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_sure.setOnClickListener(this);
        setUpListener();
        setUpData();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("添加新地址");
        tv_right.setText("保存");
        tv_right.setTextColor(getResources().getColor(R.color.white));
        btn_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title_right:
                postData();
                break;
            case R.id.ll_area:
                rl_area_wheel.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_layout:
                rl_area_wheel.setVisibility(View.GONE);
                break;
            case R.id.tv_dialog_cancel:
                rl_area_wheel.setVisibility(View.GONE);
                break;
            case R.id.btn_dialog_sure:
                tv_area.setText(mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentDistrictName);
                rl_area_wheel.setVisibility(View.GONE);
                break;
        }
    }

    private void postData() {
        String addr_area = tv_area.getText().toString();
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String addr_detail = et_addr.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.show(this, "请输入收件人");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号");
            return;
        }

        if (!ValidateUtil.isPhone(phone)) {
            ToastUtils.show(this, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(addr_area)) {
            ToastUtils.show(this, "请输入收货地区");
            return;
        }
        if (StringUtils.isEmpty(addr_detail)) {
            ToastUtils.show(this, "请输入详细地址");
            return;
        }
        AddressServerDao serverDao = new AddressServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = 1;
        serverDao.addr_area = addr_area;
        serverDao.addr_detail = addr_detail;
        serverDao.receiver_name = name;
        serverDao.receiver_phone = phone;
        serverDao.editAddress();
        finish();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wv_province) {
            updateCities();
        } else if (wheel == wv_city) {
            updateAreas();
        } else if (wheel == wv_district) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void setUpListener() {
        // 添加change事件
        wv_province.addChangingListener(this);
        // 添加change事件
        wv_city.addChangingListener(this);
        // 添加change事件
        wv_district.addChangingListener(this);
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {

    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        if (wheel == wv_province) {
            setUpData();
        } else if (wheel == wv_city) {
            updateCities();
        } else if (wheel == wv_district) {
            updateAreas();
        }
    }

    /**
     * 返回省会索引，没有就返回默认“北京”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = mProvinceDatas.length;
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(mProvinceDatas[i])) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            mCurrentProviceName = "北京";
            return 2;
        }
        return provinceIndex;
    }

    private void setUpData() {
        initProvinceDatas();
        ArrayWheelAdapter<String> mAdapter = new ArrayWheelAdapter<String>(this, mProvinceDatas, getProvinceItem(mCurrentProviceName), 24, 14);
        wv_province.setViewAdapter(mAdapter);
        mAdapter.notifyDataChangedEvent();
        // 设置可见条目数量
        wv_province.setVisibleItems(7);
        wv_city.setVisibleItems(7);
        wv_district.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    /**
     * 得到城市索引，没有返回默认“海淀区”
     *
     * @param district
     * @return
     */
    public int getDistrictItem(String district, String districts[]) {
        int size = districts.length;
        int districtIndex = 0;
        boolean noDistrict = true;
        for (int i = 0; i < size; i++) {
            if (district.equals(districts[i])) {
                noDistrict = false;
                return districtIndex;
            } else {
                districtIndex++;
            }
        }
        if (noDistrict) {
            mCurrentDistrictName = "昌平区";
            return 0;
        }
        return districtIndex;
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = wv_city.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        ArrayWheelAdapter<String> mAdapter = new ArrayWheelAdapter<String>(this, areas, getDistrictItem(mCurrentDistrictName, areas), 24, 14);
        wv_district.setViewAdapter(mAdapter);
        wv_district.setCurrentItem(getDistrictItem(mCurrentDistrictName, areas));
        mAdapter.notifyDataChangedEvent();
    }

    /**
     * 得到城市索引，没有返回默认“海淀区”
     *
     * @param city
     * @return
     */
    public int getCityItem(String city, String cities[]) {
        int size = cities.length;
        int cityIndex = 0;
        boolean nocity = true;
        for (int i = 0; i < size; i++) {
            if (city.equals(cities[i])) {
                nocity = false;
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        if (nocity) {
            mCurrentCityName = "北京市";
            return 0;
        }
        return cityIndex;
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = wv_province.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        Log.e("qizfeng", ">>>" + getCityItem(mCurrentCityName, cities));
        ArrayWheelAdapter<String> mAdapter = new ArrayWheelAdapter<String>(this, cities, getCityItem(mCurrentCityName, cities), 24, 14);
        wv_city.setViewAdapter(mAdapter);
        wv_city.setCurrentItem(getCityItem(mCurrentCityName, cities));
        mAdapter.notifyDataChangedEvent();
        updateAreas();
    }
}

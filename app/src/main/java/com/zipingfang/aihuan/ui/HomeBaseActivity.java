package com.zipingfang.aihuan.ui;

import android.os.Bundle;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;

/**
 * Created by 峰 on 2016/9/19.
 */
public class HomeBaseActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_main);
    }
}

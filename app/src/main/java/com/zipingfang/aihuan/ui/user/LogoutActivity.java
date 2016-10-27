package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;

/**
 * Created by 峰 on 2016/9/21.
 */
public class LogoutActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout pop_layout;
    private Button btn_ok;
    private Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_layout_exit_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pop_layout = (RelativeLayout) findViewById(R.id.pop_layout);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok =(Button)findViewById(R.id.btn_ok);
        pop_layout.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                clearUserCache(this);
                Intent intent = new Intent(LogoutActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                finishActivity();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.pop_layout:
                finish();
                break;
        }
    }
}

package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;

/**
 * Created by 峰 on 2016/9/21.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_setting);
        View il_topbar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView)il_topbar.findViewById(R.id.tv_title);
        tv_title.setText("设置");
        ImageButton btn_back = (ImageButton)il_topbar.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_exit =(Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                Intent intent = new Intent(SettingActivity.this,LogoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
               finish();
                break;
        }
    }
}

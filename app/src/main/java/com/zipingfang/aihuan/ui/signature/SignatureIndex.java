package com.zipingfang.aihuan.ui.signature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.utils.StringUtils;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class SignatureIndex extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_person, ll_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_signature_index);
        ll_person = (LinearLayout) findViewById(R.id.ll_person);
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        ll_company.setOnClickListener(this);
        ll_person.setOnClickListener(this);
        initActionBar();
    }


    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("商户签约");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SignatureIndex.this, SignData1Activity.class);
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_person:
                if ("0".equals(getUser(SignatureIndex.this).getMerchant_status())) {
                    intent.setClass(SignatureIndex.this, SignData1Activity.class);
                } else if (StringUtils.isEmpty(getUser(SignatureIndex.this).getMerchant_status()) ||
                        "-1".equals(getUser(SignatureIndex.this).getMerchant_status())) {
                    intent.setClass(SignatureIndex.this, SignData1Activity.class);
                } else {
                    intent.setClass(SignatureIndex.this, SignStatusPersonActivity.class);
                }


                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.ll_company:
                if ("0".equals(getUser(SignatureIndex.this).getMerchant_status())) {
                    intent.setClass(SignatureIndex.this, SignData1Activity.class);
                } else if (StringUtils.isEmpty(getUser(SignatureIndex.this).getMerchant_status()) ||
                        "-1".equals(getUser(SignatureIndex.this).getMerchant_status())) {
                    intent.setClass(SignatureIndex.this, SignData1Activity.class);
                } else {
                    intent.setClass(SignatureIndex.this, SignStatusCompanyActivity.class);
                }
                intent.putExtra("type", "2");
                startActivity(intent);
                break;

        }
    }
}

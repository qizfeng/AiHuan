package com.zipingfang.aihuan.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.ForgetPassServerDao;
import com.zipingfang.aihuan.dao.VerifyCodeServerDao;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.ValidateUtil;
import com.zipingfang.aihuan.view.TimeButton;

/**
 * Created by 峰 on 2016/9/28.
 */
public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone;
    private EditText et_verify;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_submit;
    private TextView tv_protocol;
    private TimeButton btn_verify;
    InputMethodManager manager;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_forget_password);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
    }


    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_verify = (EditText) findViewById(R.id.et_verify);
        et_password = (EditText) findViewById(R.id.et_password);
        et_repassword = (EditText) findViewById(R.id.et_repassword);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_verify = (TimeButton) findViewById(R.id.btn_verify);
        tv_protocol = (TextView) findViewById(R.id.tv_protocol);
        initActionBar();
        btn_submit.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
    }

    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("忘记密码");
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit:
                doSubmit();
                break;
            case R.id.btn_verify:
                getVerify();
                break;
        }
    }

    /**
     * 注册
     */
    private void doSubmit() {
        String phone = et_phone.getText().toString();
        String verify = et_verify.getText().toString();
        String password = et_password.getText().toString();
        String repassword = et_repassword.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(this, "手机号不能为空");
            return;
        }
        if (!ValidateUtil.isPhone(phone)) {
            ToastUtils.show(this, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(verify)) {
            ToastUtils.show(this, "验证码不能为空");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.show(this, "密码不能为空");
            return;
        }
        if (!ValidateUtil.isPwd(password)) {
            ToastUtils.show(this, "请输入6-16位密码");
            return;
        }
        if (StringUtils.isEmpty(repassword)) {
            ToastUtils.show(this, "确认密码不能为空");
            return;
        }
        if (!password.equals(repassword)) {
            ToastUtils.show(this, "两次密码输入不一致");
            return;
        }
        final ForgetPassServerDao forgetPassDao = new ForgetPassServerDao(this);
        forgetPassDao.phone = phone;
        forgetPassDao.verify = verify;
        forgetPassDao.password = password;
        forgetPassDao.repeatpass = repassword;
        showLoading(this, "");
        forgetPassDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                ToastUtils.show(ForgetPassActivity.this, forgetPassDao.desc);
                if (!StringUtils.isEmpty(forgetPassDao.res)) {
                    if (forgetPassDao.isSucc) {
                        Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        hideLoading();
    }

    private void getVerify() {
        final String phone = et_phone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号");
            return;
        }
        if (!ValidateUtil.isPhone(phone)) {
            ToastUtils.show(this, "请输入正确的手机号");
            return;
        }
        final VerifyCodeServerDao verifyDao = new VerifyCodeServerDao(this);
        verifyDao.phone = phone;
        verifyDao.remark="3";
        verifyDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    if (verifyDao.isSucc)
                        btn_verify.setTextBefore("获取验证码").setTextAfter("s").setlength(60).setPhone(phone);
                }
                if (StringUtils.isEmpty(verifyDao.res))
                    ToastUtils.show(ForgetPassActivity.this, verifyDao.desc);
            }
        });

    }
}

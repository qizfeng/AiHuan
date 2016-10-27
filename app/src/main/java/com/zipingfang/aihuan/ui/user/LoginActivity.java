package com.zipingfang.aihuan.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.LoginServerDao;
import com.zipingfang.aihuan.ui.MainActivity;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.ValidateUtil;

/**
 * Created by 峰 on 2016/9/9.
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone;//手机号
    private EditText et_password;//密码
    private Button btn_login;//登录按钮
    private ImageView iv_wechat;//微信登录
    private ImageView iv_qq;//qq登录
    private ImageView iv_weibo;//微博登录
    private TextView tv_forget;//忘记密码
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
        setContentView(R.layout.zpf_activity_login);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_wechat = (ImageView) findViewById(R.id.iv_wechat);
        iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        btn_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        initActionBar();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText(getResources().getString(R.string.login));
        tv_right.setText("注册");
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
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.tv_title_right:
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget:
                intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(LoginActivity.this, "手机号不能为空");
            return;
        }
        if (!ValidateUtil.isPhone(phone)) {
            ToastUtils.show(LoginActivity.this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show(LoginActivity.this, "密码不能为空");
            return;
        }
        showLoading(LoginActivity.this, "");
        final LoginServerDao loginDao = new LoginServerDao(LoginActivity.this);
        loginDao.phone = phone;
        loginDao.password = password;
        loginDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                hideLoading();
                ToastUtils.show(LoginActivity.this, loginDao.desc);
                if (loginDao.isSucc) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}

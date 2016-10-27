package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.UserInfoServerDao;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

/**
 * Created by 峰 on 2016/9/26.
 */
public class EditNickNameActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private TextView tv_cancel;
    private TextView tv_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_layout_edit_nickname_dialog);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_content:
                finish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_ok:
                postData();
                break;
        }
    }

    private void postData(){
        String nickName = et_name.getText().toString();
        if(StringUtils.isEmpty(nickName)){
            ToastUtils.show(this,"用户名不能为空");
            return;
        }
        UserInfoServerDao userInfoDao = new UserInfoServerDao(this);
        userInfoDao.mid = getUserId(this);
        userInfoDao.editUserInfo("username",nickName);
        finish();
    }
}

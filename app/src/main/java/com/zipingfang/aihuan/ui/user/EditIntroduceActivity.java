package com.zipingfang.aihuan.ui.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.UserInfoServerDao;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

/**
 * Created by 峰 on 2016/9/27.
 */
public class EditIntroduceActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_content;
    private TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_layout_edit_introduce);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_content.addTextChangedListener(new EditChangedListener());
        initActionBar();
    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 200;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tv_count.setText(s.length() + "/" + 200);

        }

        @Override
        public void afterTextChanged(Editable s) {
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            editStart = et_content.getSelectionStart();
            editEnd = et_content.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                ToastUtils.show(EditIntroduceActivity.this, "个人简介字数不能超过200");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                et_content.setText(s);
                et_content.setSelection(tempSelection);
            }

        }
    }

    ;

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("个人简介");
        tv_right.setText("保存");
        tv_right.setTextColor(getResources().getColor(R.color.white));
        btn_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            finish();
        } else if (v.getId() == R.id.tv_title_right) {
            postData();
        }
    }

    /**
     * 提交数据
     */
    private void postData() {
        String content = et_content.getText().toString();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(this, "个人简介不能为空");
            return;
        }
        UserInfoServerDao userInfoServerDao = new UserInfoServerDao(this);
        userInfoServerDao.mid=getUserId(this);
        userInfoServerDao.editUserInfo("introduce", content);
        finish();
    }
}

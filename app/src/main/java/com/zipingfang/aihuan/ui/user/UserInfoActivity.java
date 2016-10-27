package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.UserInfoServerDao;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.wheel.dialog.DatePickerDialog;
import com.zipingfang.aihuan.wheel.dialog.GenderPickerDialog;

/**
 * Created by 峰 on 2016/9/26.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_name;
    private LinearLayout ll_introduce;
    private LinearLayout ll_gender;
    private LinearLayout ll_birth;
    private RelativeLayout rl_header;
    public static ImageView iv_header;
    private TextView tv_introduce;
    private TextView tv_name;
    private TextView tv_gender;
    private TextView tv_birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_user_data);
        initView();
    }

    private void initView() {
        ll_birth = (LinearLayout) findViewById(R.id.ll_birth);
        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        ll_introduce = (LinearLayout) findViewById(R.id.ll_introduce);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        rl_header = (RelativeLayout) findViewById(R.id.rl_header);
        tv_birth = (TextView) findViewById(R.id.tv_birth);
        tv_introduce = (TextView) findViewById(R.id.tv_introduce);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        iv_header = (ImageView) findViewById(R.id.iv_header);
        initActionBar();
        bindListener();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("我的资料");
        btn_back.setOnClickListener(this);
    }

    private void bindListener() {
        rl_header.setOnClickListener(this);
        ll_birth.setOnClickListener(this);
        ll_introduce.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_name.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_header:
                TakePhotoActivity.startActivityForSinglePhoto(this, 1, true);
                break;
            case R.id.ll_name:
                intent.setClass(UserInfoActivity.this, EditNickNameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_introduce:
                intent.setClass(UserInfoActivity.this, EditIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_gender:
//                intent.setClass(UserInfoActivity.this, EditGenderActivity.class);
//                startActivity(intent);
                showGenderPickerDialog();
                break;
            case R.id.ll_birth:
                showDatePickerDialog();
                break;
        }
    }


    /**
     * 性别
     */
    private void showGenderPickerDialog() {
        GenderPickerDialog genderPickerDialog = new GenderPickerDialog(this);
        genderPickerDialog.setDialogMode(DatePickerDialog.DIALOG_MODE_BOTTOM);
        genderPickerDialog.show();
        genderPickerDialog.setOnGenderCListener(new GenderPickerDialog.OnGenderCListener() {
            @Override
            public void onClick(String gender) {
                UserInfoServerDao userInfoServerDao = new UserInfoServerDao(UserInfoActivity.this);
                userInfoServerDao.mid = getUserId(UserInfoActivity.this);
                if ("男".equals(gender))
                    userInfoServerDao.editUserInfo("gender", "1");
                else if ("女".equals(gender))
                    userInfoServerDao.editUserInfo("gender", "0");
                tv_gender.setText(gender);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setDialogMode(DatePickerDialog.DIALOG_MODE_BOTTOM);
        datePickerDialog.show();
        datePickerDialog.setDatePickListener(new DatePickerDialog.OnDatePickListener() {
            @Override
            public void onClick(String year, String month, String day) {
                tv_birth.setText(year + "-" + month);
                UserInfoServerDao userInfoServerDao = new UserInfoServerDao(UserInfoActivity.this);
                userInfoServerDao.mid = getUserId(UserInfoActivity.this);
                userInfoServerDao.editUserInfo("birthday", year + "-" + month);
            }
        });
    }

    private void loadData() {
        final UserInfoServerDao userInfoDao = new UserInfoServerDao(this);
        userInfoDao.mid = getUserId(this);
        userInfoDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    tv_name.setText(userInfoDao.mUserInfo.getUsername());
                    if ("1".equals(userInfoDao.mUserInfo.getGender()))
                        tv_gender.setText("男");
                    else if ("0".equals(userInfoDao.mUserInfo.getGender()))
                        tv_gender.setText("女");
                    ImageLoader.getInstance().displayImage(userInfoDao.mUserInfo.getAvatar(), iv_header, ImageLoaderConfig.normal);
                    tv_introduce.setText(userInfoDao.mUserInfo.getIntroduce());
                    tv_birth.setText(userInfoDao.mUserInfo.getBirthday());
                } else {
                    ToastUtils.show(UserInfoActivity.this, userInfoDao.desc);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        // 打开相机或相册获得的图片路径
        String mTempPhotoUrl = TakePhotoActivity.getDataPath(arg2);
        // 判断是否是文件路径
        if (!StringUtils.isEmpty(mTempPhotoUrl)) {
            Log.e("qizfeng", "takephoto:" + mTempPhotoUrl);
//            ImageLoader.getInstance().displayImage("file://"+mTempPhotoUrl,iv_header);
            uploadPhoto(mTempPhotoUrl);
        } else {

        }

    }

    /**
     * 上传头像
     *
     * @param photoUrl
     */
    private void uploadPhoto(String photoUrl) {
        UserInfoServerDao userInfoServerDao = new UserInfoServerDao(this);
        userInfoServerDao.mid = getUserId(this);
        userInfoServerDao.editAvater(photoUrl);
//        ImageLoader.getInstance().displayImage(userInfoServerDao.avatar,iv_header);
    }
}

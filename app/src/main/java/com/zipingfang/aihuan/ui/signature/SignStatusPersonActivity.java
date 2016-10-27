package com.zipingfang.aihuan.ui.signature;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Sign;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.SignStatusServerDao;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.ToastUtils;

/**
 * Created by zipingfang on 16/10/26.
 */

public class SignStatusPersonActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_sign_status;
    private TextView tv_com_name_person;
    private TextView tv_real_name;
    private ImageView iv_card_front;
    private ImageView iv_card_back;
    private TextView tv_bank_name;
    private TextView tv_bank_no;
    private TextView tv_bank_name_person;
    private TextView tv_mobile;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_card_no;
    private Sign mData = new Sign();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_sign_status_person);
        initView();
        initActionBar();
        loadData();
    }
    private  void initView(){
        tv_sign_status =(TextView)findViewById(R.id.tv_sign_status);
        tv_com_name_person =(TextView)findViewById(R.id.tv_com_name);
        tv_real_name =(TextView)findViewById(R.id.tv_real_name);
        iv_card_front =(ImageView) findViewById(R.id.iv_card_front);
        iv_card_back =(ImageView) findViewById(R.id.iv_card_back);
        tv_bank_name =(TextView)findViewById(R.id.tv_bank_name);
        tv_bank_no =(TextView)findViewById(R.id.tv_bank_no);
        tv_bank_name_person =(TextView)findViewById(R.id.tv_bank_person_name);
        tv_mobile =(TextView)findViewById(R.id.tv_mobile);
        tv_phone =(TextView)findViewById(R.id.tv_phone);
        tv_address =(TextView)findViewById(R.id.tv_address);
        tv_card_no=(TextView)findViewById(R.id.tv_card_no);
    }
    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("个人申请签约");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    private void loadData(){
        final SignStatusServerDao serverDao  = new SignStatusServerDao(this);
        serverDao.mid  = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if(serverDao.isSucc){
                    mData = serverDao.signInfo;
                    bindData(mData);
                }else {
                    ToastUtils.show(SignStatusPersonActivity.this,serverDao.desc);
                }
            }
        });
    }
    private void bindData(Sign sign){
        Drawable drawable = getResources().getDrawable(R.mipmap.sign_img_ing);
        String status = sign.getApply_status();
        if("-1".equals(status)||"2".equals(status)){
            drawable = getResources().getDrawable(R.mipmap.sign_img_ing);
        }else if("0".equals(status)){
            drawable = getResources().getDrawable(R.mipmap.signin_img_no);
        }else if("1".equals(status)){
            drawable = getResources().getDrawable(R.mipmap.sign_img_ok);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tv_sign_status.setCompoundDrawables(drawable, null, null, null);//
        tv_sign_status.setText(sign.getStatus_text());
        tv_com_name_person.setText(sign.getMerchantuser_name());
        tv_real_name.setText(sign.getRealname());
        tv_card_no.setText(sign.getIdcard_no());
        ImageLoader.getInstance().displayImage(sign.getIdcard_front_img(),iv_card_front, ImageLoaderConfig.normal);
        ImageLoader.getInstance().displayImage(sign.getIdcard_back_img(),iv_card_back, ImageLoaderConfig.normal);
        tv_bank_name.setText(sign.getBank_name());
        tv_bank_name_person.setText(sign.getBank_username());
        tv_mobile.setText(sign.getPhone_number());
        tv_phone.setText(sign.getTelephone_number());
        tv_address.setText(sign.getAddress());
        tv_bank_no.setText(sign.getBank_card_no());

    }
}

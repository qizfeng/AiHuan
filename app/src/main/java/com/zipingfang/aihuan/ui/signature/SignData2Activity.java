package com.zipingfang.aihuan.ui.signature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.SignInfoUploadServerDao;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.ValidateUtil;

/**
 * Created by zipingfang on 16/10/25.
 */

public class SignData2Activity extends BaseActivity implements View.OnClickListener {
    private EditText et_bank_no;

    private EditText et_bank;
    private EditText et_bank_person_name;
    private EditText et_mobile;
    private EditText et_phone;
    private EditText et_address;


    private ImageView iv_license;
    private ImageView iv_bank_account;
    private EditText et_com_phone;
    private EditText et_company_person_name;
    private EditText et_com_name;
    private EditText et_bank_name;
    private EditText et_com_address;

    private Button btn_submit;

    private CheckBox cb_agreeement;
    private TextView tv_agreement;

    private TextView tv_title;
    private String type;
    private String frontUrl, backUrl;
    private String comName;
    private String realName;
    private String idcard_no;
    private String licenseUrl;
    private String bankAccountUrl;
    private void initView() {
        try{
            et_bank = (EditText) findViewById(R.id.et_bank);
            et_bank_no = (EditText) findViewById(R.id.et_bank_no);
            et_bank_person_name = (EditText) findViewById(R.id.et_bank_person_name);
            et_mobile = (EditText) findViewById(R.id.et_mobile);
            et_phone = (EditText) findViewById(R.id.et_phone);
            et_address = (EditText) findViewById(R.id.et_address);
            cb_agreeement = (CheckBox) findViewById(R.id.cb_agreement);
            tv_agreement = (TextView) findViewById(R.id.tv_agreement);
            btn_submit = (Button) findViewById(R.id.btn_submit_apply);

            iv_bank_account=(ImageView)findViewById(R.id.iv_bank_account);
            iv_license=(ImageView)findViewById(R.id.iv_license);
            et_com_phone = (EditText)findViewById(R.id.et_com_phone);
            et_company_person_name =(EditText)findViewById(R.id.et_company_person_name);
            et_com_name =(EditText)findViewById(R.id.et_com_name);
            et_bank_name=(EditText)findViewById(R.id.et_bank_name);
            et_com_address=(EditText)findViewById(R.id.et_com_address);
            btn_submit.setOnClickListener(this);

        }catch (Exception e){

        }



    }

    //    intent.putExtra("type", type);
//    intent.putExtra("front", card_front_img);
//    intent.putExtra("back", card_back_img);
//    intent.putExtra("comName", comName);
//    intent.putExtra("realName", realName);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        frontUrl = getIntent().getStringExtra("front");
        backUrl = getIntent().getStringExtra("back");
        comName = getIntent().getStringExtra("comName");
        realName = getIntent().getStringExtra("realName");
        idcard_no= getIntent().getStringExtra("idcard_no");
        if ("1".equals(type))
            setContentView(R.layout.zpf_activity_sign_data2_person);
        else if ("2".equals(type))
            setContentView(R.layout.zpf_activity_sign_data2_company);
        initView();
        initActionBar();
    }


    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        if ("1".equals(type))
            tv_title.setText("个人申请签约");
        else if ("2".equals(type))
            tv_title.setText("企业申请签约");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit_apply:
                postData();
                break;
        }
    }

    private void postData(){
        final SignInfoUploadServerDao serverDao = new SignInfoUploadServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.type = type;
        if("1".equals(type)){
            String bank_name = et_bank.getText().toString().trim();
            String bank_no = et_bank_no.getText().toString().trim();
            String bank_person_name = et_bank_person_name.getText().toString().trim();
            String mobile = et_mobile.getText().toString().trim();
            String phone = et_phone.getText().toString().trim();
            String adderss = et_address.getText().toString().trim();
            if(StringUtils.isEmpty(bank_name)){
                ToastUtils.show(SignData2Activity.this,"请输入开户行");
                return;
            }
            if(StringUtils.isEmpty(bank_no)){
                ToastUtils.show(SignData2Activity.this,"请输入银行卡号");
                return;
            }
            if(StringUtils.isEmpty(bank_person_name)){
                ToastUtils.show(SignData2Activity.this,"请输入开户人姓名");
                return;
            }
            if(StringUtils.isEmpty(mobile)){
                ToastUtils.show(SignData2Activity.this,"请输入移动电话");
                return;
            }
            if(!ValidateUtil.isPhone(mobile)){
                ToastUtils.show(SignData2Activity.this,"请输入正确格式的手机号");
                return;
            }
            if(!ValidateUtil.isPhoneOrNumber(phone)){
                ToastUtils.show(SignData2Activity.this,"请输入正确格式的座机号");
                return;
            }
            if(StringUtils.isEmpty(adderss)){
                ToastUtils.show(SignData2Activity.this,"请输入地址");
                return;
            }
            serverDao.merchantuser_name = comName;
            serverDao.realname = realName;
            serverDao.idcard_no= idcard_no;
            serverDao.bank_name=bank_name;
            serverDao.bank_card_no = bank_no;
            serverDao.bank_username = bank_person_name;
            serverDao.phone_number = mobile;
            serverDao.telephone_number= phone;
            serverDao.address = adderss;
            serverDao.idcard_front_img= frontUrl;
            serverDao.idcard_back_img=backUrl;

        }else if("2".equals(type)){
            String company_name = et_com_name.getText().toString();
            String company_person_name = et_company_person_name.getText().toString();
            String company_bank_name = et_bank_name.getText().toString();
            String company_bank_no = et_bank_no.getText().toString();
            String com_mobile = et_com_phone.getText().toString();
            String com_address = et_com_address.getText().toString();
            if(StringUtils.isEmpty(company_name)){
                ToastUtils.show(SignData2Activity.this,"请输入企业名称");
                return;
            }
            if(StringUtils.isEmpty(company_person_name)){
                ToastUtils.show(SignData2Activity.this,"请输入企业法人");
                return;
            }
            if(StringUtils.isEmpty(company_bank_name)){
                ToastUtils.show(SignData2Activity.this,"请输入开户行名称");
                return;
            }
            if(StringUtils.isEmpty(company_bank_no)){
                ToastUtils.show(SignData2Activity.this,"请输入银行账号");
                return;
            }
            if(StringUtils.isEmpty(licenseUrl)){
                ToastUtils.show(SignData2Activity.this,"请上传营业执照正面照");
                return;
            }
            if(StringUtils.isEmpty(bankAccountUrl)){
                ToastUtils.show(SignData2Activity.this,"请上传对公账户照片");
                return;
            }
            if(!ValidateUtil.isPhone(com_mobile)){
                ToastUtils.show(SignData2Activity.this,"请输入正确格式的手机号");
                return;
            }
            if(StringUtils.isEmpty(com_address)){
                ToastUtils.show(SignData2Activity.this,"请输入地址");
                return;
            }
            serverDao.merchantuser_name= comName;
            serverDao.auth_name= realName;
            serverDao.auth_idcard_no=idcard_no;
            serverDao.com_name= company_name;
            serverDao.corparation = company_person_name;
            serverDao.bank_name = company_bank_name;
            serverDao.bank_account_id= company_bank_no;
            serverDao.com_phone= com_mobile;
            serverDao.com_address = com_address;
            serverDao.auth_idcard_front_img= frontUrl;
            serverDao.auth_idcard_back_img=backUrl;
            serverDao.com_license_img= licenseUrl;
            serverDao.bank_license_img = bankAccountUrl;
        }
        if(!cb_agreeement.isChecked()){
            ToastUtils.show(SignData2Activity.this,"请阅读并勾选签约协议");
            return;
        }
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if(serverDao.isSucc){
                    ToastUtils.show(SignData2Activity.this,"资料已提交,请等待审核");
                    Intent intent = new Intent();
                    if("1".equals(type)) {
                        intent.setClass(SignData2Activity.this,SignStatusPersonActivity.class);
                    }else if("2".equals(type)){
                        intent.setClass(SignData2Activity.this,SignStatusCompanyActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

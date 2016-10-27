package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/25.
 */

public class SignInfoUploadServerDao extends BaseDao {

    /**type=1#商户分类（1指注册个人商户，2指注册企业商户）
    *mid=100#会员id
    *merchantuser_name=XX旗舰店#商户名称
    *realname=xxx#商户真实姓名
    *idcard_no=10232122111111..#身份证号
    *bank_name=建设银行#会员开户行名称
    *bank_card_no=建设银行#会员开户行卡号
    *bank_username=建设银行#会员开户行户名
    *phone_number=123456#联系电话
    *telephone_number=123456#联系座机
    *address=地址#联系地址
    *idcard_front_img=#会员身份证正面照图片路径
    *idcard_back_img=会员身份证正面照图片路径
    *bank_card_img=会员银行卡正面照图片路径

    *auth_name=xxx#企业授权人真实姓名
    *auth_idcard_no=121021...#企业授权人身份证号码
    *com_name=aaa#企业名称
    *corparation=马云#企业法人
    *bank_name=建设银行#开户行名称
    *bank_account_id=235..#开户行卡号
    *com_phone=123456#企业公司电话
    *com_address=22231#企业办公地址
    *auth_idcard_front_img=#授权人身份证正面照图片路径
    *auth_idcard_back_img=授权人身份证正面照图片路径
    *com_license_img=企业营业执照图片路径
    *bank_license_img=银行开户许可证图片路径*/
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String type;
    public String mid;
    public String merchantuser_name;
    public String realname;
    public String idcard_no;
    public String bank_name;
    public String bank_card_no;
    public String bank_username;
    public String phone_number;
    public String telephone_number;
    public String address;
    public String idcard_front_img;
    public String idcard_back_img;
    public String bank_card_img;
    public String auth_name;
    public String auth_idcard_no;
    public String com_name;
    public String corparation;
    public String bank_account_id;
    public String com_phone;
    public String com_address;
    public String auth_idcard_front_img;
    public String auth_idcard_back_img;
    public String com_license_img;
    public String bank_license_img;
    public SignInfoUploadServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_DO_SIGN;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid",mid);
        maps.put("type",type);
        maps.put("merchantuser_name",merchantuser_name);
        maps.put("bank_name",bank_name);
        if("1".equals(type)){
            maps.put("realname",realname);
            maps.put("idcard_no",idcard_no);
            maps.put("bank_card_no",bank_card_no);
            maps.put("bank_username",bank_username);
            maps.put("phone_number",phone_number);
            maps.put("telephone_number",telephone_number);
            maps.put("address",address);
            maps.put("idcard_front_img",idcard_front_img);
            maps.put("idcard_back_img",idcard_back_img);
            maps.put("bank_card_img",bank_card_img);
        }else if("2".equals(type)){
            maps.put("auth_name",auth_name);
            maps.put("auth_idcard_no",auth_idcard_no);
            maps.put("com_name",com_name);
            maps.put("corparation",corparation);
            maps.put("bank_account_id",bank_account_id);
            maps.put("com_phone",com_phone);
            maps.put("com_address",com_address);
            maps.put("auth_idcard_front_img",auth_idcard_front_img);
            maps.put("auth_idcard_back_img",auth_idcard_back_img);
            maps.put("com_license_img",com_license_img);
            maps.put("bank_license_img",bank_license_img);
        }
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                isSucc = true;
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }

    }

}



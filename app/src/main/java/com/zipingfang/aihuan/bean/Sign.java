package com.zipingfang.aihuan.bean;

/**
 * Created by zipingfang on 16/10/25.
 */

public class Sign {
    /**type=1#商户分类（1指注册个人商户，2指注册企业商户）*mid=100#会员id
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
    *bank_card_img=会员银行卡正面照图片路径*/
    private String type;
    private String mid;
    private String merchantuser_name;
    private String realname;
    private String idcard_no;
    private String bank_name;
    private String bank_card_no;
    private String bank_username;
    private String phone_number;
    private String telephone_number;
    private String address;
    private String idcard_front_img;
    private String idcard_back_img;
    private String bank_card_img;

    /**type=2#商户分类（以下注册企业商户参数）
    *mid=100#会员id
    *merchantuser_name=XX旗舰店#商户名称
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
    private String auth_name;
    private String auth_idcard_no;
    private String com_name;
    private String corparation;
    private String bank_account_id;
    private String com_phone;
    private String com_address;
    private String auth_idcard_front_img;
    private String auth_idcard_back_img;
    private String com_license_img;
    private String back_license_img;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMerchantuser_name() {
        return merchantuser_name;
    }

    public void setMerchantuser_name(String merchantuser_name) {
        this.merchantuser_name = merchantuser_name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard_no() {
        return idcard_no;
    }

    public void setIdcard_no(String idcard_no) {
        this.idcard_no = idcard_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account_id() {
        return bank_account_id;
    }

    public void setBank_account_id(String bank_account_id) {
        this.bank_account_id = bank_account_id;
    }

    public String getCom_phone() {
        return com_phone;
    }

    public void setCom_phone(String com_phone) {
        this.com_phone = com_phone;
    }

    public String getCom_address() {
        return com_address;
    }

    public void setCom_address(String com_address) {
        this.com_address = com_address;
    }

    public String getAuth_idcard_front_img() {
        return auth_idcard_front_img;
    }

    public void setAuth_idcard_front_img(String auth_idcard_front_img) {
        this.auth_idcard_front_img = auth_idcard_front_img;
    }

    public String getAuth_idcard_back_img() {
        return auth_idcard_back_img;
    }

    public void setAuth_idcard_back_img(String auth_idcard_back_img) {
        this.auth_idcard_back_img = auth_idcard_back_img;
    }

    public String getCom_license_img() {
        return com_license_img;
    }

    public void setCom_license_img(String com_license_img) {
        this.com_license_img = com_license_img;
    }

    public String getBack_license_img() {
        return back_license_img;
    }

    public void setBack_license_img(String back_license_img) {
        this.back_license_img = back_license_img;
    }

    public String getBank_card_no() {
        return bank_card_no;
    }

    public void setBank_card_no(String bank_card_no) {
        this.bank_card_no = bank_card_no;
    }

    public String getBank_username() {
        return bank_username;
    }

    public void setBank_username(String bank_username) {
        this.bank_username = bank_username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard_front_img() {
        return idcard_front_img;
    }

    public void setIdcard_front_img(String idcard_front_img) {
        this.idcard_front_img = idcard_front_img;
    }

    public String getIdcard_back_img() {
        return idcard_back_img;
    }

    public void setIdcard_back_img(String idcard_back_img) {
        this.idcard_back_img = idcard_back_img;
    }

    public String getBank_card_img() {
        return bank_card_img;
    }

    public void setBank_card_img(String bank_card_img) {
        this.bank_card_img = bank_card_img;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }

    public String getAuth_idcard_no() {
        return auth_idcard_no;
    }

    public void setAuth_idcard_no(String auth_idcard_no) {
        this.auth_idcard_no = auth_idcard_no;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getCorparation() {
        return corparation;
    }

    public void setCorparation(String corparation) {
        this.corparation = corparation;
    }

    private String apply_status;
    private String status_text;

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}

package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/9/18.
 */
public class User {
    //    "mid": "187",
//            "username": "",
//            "email": "",
//            "phone": "15210213731",
//            "requester": "",
//            "gender": "1",
//            "birthday": "0000-00-00",
//            "avatar": "",
//            "reg_time": "1474162349",
//            "is_merchant": "0",
//            "is_realperson": "-1",
//            "merchant_id": "0",
//            "status": "1",
//            "openid": null,
//            "uptime": null,
//            "ttype": "0",
//            "token": null
    private String mid;
    private String username;
    private String email;
    private String requester;
    private String gender;
    private String birthday;
    private String avatar;
    private String reg_time;
    private String is_merchant;//0普通用户 1个人商户 2企业商户
    private String is_realperson;
    private String merchant_id;
    private String status;
    private String openid;
    private String uptime;
    private String ttype;
    private String token;
    private String merchant_status;//审核状态
    private String introduce;
    private String balance;
    private String point;
    private String level;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(String is_merchant) {
        this.is_merchant = is_merchant;
    }

    public String getIs_realperson() {
        return is_realperson;
    }

    public void setIs_realperson(String is_realperson) {
        this.is_realperson = is_realperson;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getTtype() {
        return ttype;
    }

    public String getMerchant_status() {
        return merchant_status;
    }

    public void setMerchant_status(String merchant_status) {
        this.merchant_status = merchant_status;
    }
    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

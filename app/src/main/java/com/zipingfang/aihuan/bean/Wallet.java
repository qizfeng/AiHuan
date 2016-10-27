package com.zipingfang.aihuan.bean;

/**
 * Created by haozhiyu on 16/10/20.
 */

public class Wallet {
    private String balance;
    private String paycard_num;
    private String coupon_num;
    private String point;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPaycard_num() {
        return paycard_num;
    }

    public void setPaycard_num(String paycard_num) {
        this.paycard_num = paycard_num;
    }

    public String getCoupon_num() {
        return coupon_num;
    }

    public void setCoupon_num(String coupon_num) {
        this.coupon_num = coupon_num;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}

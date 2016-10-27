package com.zipingfang.aihuan.bean;

/**
 * Created by zipingfang on 16/10/22.
 */

public class Coupon {
    /* {
         "id": "10065",
             "member_id": "190",
             "coupon_id": "10000",
             "money": "7",
             "coupon_description": "全场通用1000元优惠券",
             "start_time": "1476171805",
             "expired_time": "1478763805",
             "catid_1": "569",
             "type": "1",
             "scope": "1",
             "status": "未使用",
             "add_time": "1476943254",
             "valid": "2016.10.11-2016.11.10"
     }*/
    private String id;
    private String member_id;
    private String coupon_id;
    private String money;
    private String coupon_description;
    private String start_time;
    private String expired_time;
    private String catid_1;
    private String type;
    private String scope;
    private String status;
    private String add_time;
    private String valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCoupon_description() {
        return coupon_description;
    }

    public void setCoupon_description(String coupon_description) {
        this.coupon_description = coupon_description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public String getCatid_1() {
        return catid_1;
    }

    public void setCatid_1(String catid_1) {
        this.catid_1 = catid_1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}

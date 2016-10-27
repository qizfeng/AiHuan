package com.zipingfang.aihuan.bean;

/**
 * Created by zipingfang on 16/10/20.
 */

public class IncomeDetail {
    private String id;
    private String mid;
    private String increase_money;
    private String decrease_money;
    private String order_id;
    private String add_time;
    private String status;
    private String comment;
    private String ex_member_mid;
    private String current_balance;
    private String type;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIncrease_money() {
        return increase_money;
    }

    public void setIncrease_money(String increase_money) {
        this.increase_money = increase_money;
    }

    public String getDecrease_money() {
        return decrease_money;
    }

    public void setDecrease_money(String decrease_money) {
        this.decrease_money = decrease_money;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEx_member_mid() {
        return ex_member_mid;
    }

    public void setEx_member_mid(String ex_member_mid) {
        this.ex_member_mid = ex_member_mid;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

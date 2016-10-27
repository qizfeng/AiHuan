package com.zipingfang.aihuan.bean;

import com.zipingfang.aihuan.asynthttp.RequestParams;

/**
 * Created by zipingfang on 16/10/21.
 */

public class PointDetail {

    /*        "detail": [
    {
        "id": "4",
            "mid": "70",
            "increase_score": "10000",//本次获得
            "decrease_score": "0",//（使用）积分
            "action_context": "购买储值卡",
            "order_id": "0",
            "add_time": "2016-10-21 10:14:35",
            "status": "1",//1正常，2异常
            "current_point": "30000",//当前剩余积分
            "comment": "消费奖励",
            "type": "1",//类型：1消费奖励，2使用积分
            "ex_member_mid": "0"
    }
    ]*/
    private String id;
    private String mid;
    private String increase_score;
    private String decrease_score;
    private String action_context;
    private String order_id;
    private String add_time;
    private String status;
    private String current_point;
    private String comment;
    private String type;
    private String ex_member_mid;

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

    public String getIncrease_score() {
        return increase_score;
    }

    public void setIncrease_score(String increase_score) {
        this.increase_score = increase_score;
    }

    public String getDecrease_score() {
        return decrease_score;
    }

    public void setDecrease_score(String decrease_score) {
        this.decrease_score = decrease_score;
    }

    public String getAction_context() {
        return action_context;
    }

    public void setAction_context(String action_context) {
        this.action_context = action_context;
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

    public String getCurrent_point() {
        return current_point;
    }

    public void setCurrent_point(String current_point) {
        this.current_point = current_point;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEx_member_mid() {
        return ex_member_mid;
    }

    public void setEx_member_mid(String ex_member_mid) {
        this.ex_member_mid = ex_member_mid;
    }
}

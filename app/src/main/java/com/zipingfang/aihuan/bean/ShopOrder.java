package com.zipingfang.aihuan.bean;

/**
 * Created by zipingfang on 16/10/26.
 */

public class ShopOrder {
           /*  "id":"1000041",
            "goods_id":"175",
            "goods_name":"me测试测试测试测试",
            "goods_img":"http://182.92.152.135:85/upload/IMG_4394.JPG",
            "transaction_price":"1100.00",
            "pay_success_time":"0",
            "status":"201",//状态101待付款，102待发货，201待收货，401已完成
            "create_time":"1469258401",
            "status_text":"待收货",//状态描述
            "content":"确认完成",//操作
            "send_time":"1469260424"//发货时间*/
    private String id;
    private String goods_id;
    private String goods_name;
    private String goods_img;
    private String transaction_price;
    private String pay_success_time;
    private String status;
    private String create_time;
    private String status_text;
    private String content;
    private String send_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getTransaction_price() {
        return transaction_price;
    }

    public void setTransaction_price(String transaction_price) {
        this.transaction_price = transaction_price;
    }

    public String getPay_success_time() {
        return pay_success_time;
    }

    public void setPay_success_time(String pay_success_time) {
        this.pay_success_time = pay_success_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}

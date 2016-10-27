package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/9/29.
 */
public class Deposit extends Object{
//    order_id: "1423502",
    private String order_id;//保证金订单表ID
//    type: "1",
    private String type;//第三方支付平台 京东：1，兴业银行：2
//    party_id: "150",
    private String party_id;//拍卖专场ID
//    goods_id: "129",
    private String goods_id;// 拍品ID
//    member_id: "161",
    private String member_id;//缴纳保证金会员ID
//    cash_deposit: "2000.00",
    private String cash_deposit;//交纳保证金
//    add_time: "2016-07-15 22:15:49",
    private String add_time;//下单时间
//    add_ip: "218.240.36.242",
    private String add_ip;//下单ip
//    payment_status: "1",
    private String payment_status;//支付状态: 0 未支付, 1 已支付, 2 支付失败
//    payment_time: "1468591663",
    private String payment_time;//支付成功时间
//    payment_remark: "支付成功",
    private String  payment_remark;//支付备注
//    reback_status: "0",
    private String reback_status;//
//    reback_remark: "",
    private String reback_remark;//
//    reback_time: "0",
    private String reback_time;
//    deduct_remark: "未在付款期限内付款",
    private String deduct_remark;
//    deduct_time: "0",
    private String deduct_time;
//    pg_id: "474",
    private String  pg_id;
//    starting_price: "13000.00",
    private String starting_price;
//    cover: "./upload/0K2A3651.JPG",
    private String conver;
//    auction_name: "12321312313213",
    private String auction_name;
//    auction_result: "0",
    private String  auction_result;
//    auction_result_time: "0",
    private String auction_result_time;
//    party_start_time: "1468591560",
    private String party_start_time;
//    party_end_time: "1468593360",
    private String party_end_time;
//    insureMoney: "130000.00",
    private String insureMoney;
//    procceed_time: "2016-07-15 22:15:49",
    private String procceed_time;
//    status_txt: "返还中"
    private String status_txt;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCash_deposit() {
        return cash_deposit;
    }

    public void setCash_deposit(String cash_deposit) {
        this.cash_deposit = cash_deposit;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAdd_ip() {
        return add_ip;
    }

    public void setAdd_ip(String add_ip) {
        this.add_ip = add_ip;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getPayment_remark() {
        return payment_remark;
    }

    public void setPayment_remark(String payment_remark) {
        this.payment_remark = payment_remark;
    }

    public String getReback_status() {
        return reback_status;
    }

    public void setReback_status(String reback_status) {
        this.reback_status = reback_status;
    }

    public String getReback_remark() {
        return reback_remark;
    }

    public void setReback_remark(String reback_remark) {
        this.reback_remark = reback_remark;
    }

    public String getReback_time() {
        return reback_time;
    }

    public void setReback_time(String reback_time) {
        this.reback_time = reback_time;
    }

    public String getDeduct_remark() {
        return deduct_remark;
    }

    public void setDeduct_remark(String deduct_remark) {
        this.deduct_remark = deduct_remark;
    }

    public String getDeduct_time() {
        return deduct_time;
    }

    public void setDeduct_time(String deduct_time) {
        this.deduct_time = deduct_time;
    }

    public String getPg_id() {
        return pg_id;
    }

    public void setPg_id(String pg_id) {
        this.pg_id = pg_id;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getConver() {
        return conver;
    }

    public void setConver(String conver) {
        this.conver = conver;
    }

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }

    public String getAuction_result() {
        return auction_result;
    }

    public void setAuction_result(String auction_result) {
        this.auction_result = auction_result;
    }

    public String getAuction_result_time() {
        return auction_result_time;
    }

    public void setAuction_result_time(String auction_result_time) {
        this.auction_result_time = auction_result_time;
    }

    public String getParty_start_time() {
        return party_start_time;
    }

    public void setParty_start_time(String party_start_time) {
        this.party_start_time = party_start_time;
    }

    public String getParty_end_time() {
        return party_end_time;
    }

    public void setParty_end_time(String party_end_time) {
        this.party_end_time = party_end_time;
    }

    public String getInsureMoney() {
        return insureMoney;
    }

    public void setInsureMoney(String insureMoney) {
        this.insureMoney = insureMoney;
    }

    public String getProcceed_time() {
        return procceed_time;
    }

    public void setProcceed_time(String procceed_time) {
        this.procceed_time = procceed_time;
    }

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }
}

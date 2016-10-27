package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/10/8.
 */
public class Order {
    /* "id":"19",
    "goods_id":"158",
    "goods_name":"123456789",
    "goods_img":"http://182.92.152.135:8085/upload/0K2A3608.JPG",
    "pay_success_time":"0",
    "status":"201",// '101.待付款;102.已付款;201.商户已发货;202.商户无需发货;301.平台确认收货;302.平台发货;303.平台无需发货;401.卖家已收货;'501.未按时付款平台取消订单 502.买家手动取消订单（预留）
    "create_time":"1468673522",
    "status_txt":"待收货",
    "send_time":"1474735581"//待付款状态显示订单时间(create_time)，待发货状态显示付款时间（pay_success_time），待收货状态显示发货时间(send_time)*/
    private String id;
    private String send_time;

    /* "id":"1000010",
     "party_id":"170",
     "goods_id":"164",
     "goods_name":"雷达手表全新_有保留价",
     "goods_img":"http://182.92.152.135:85/upload/0K2A3670.JPG",
     "buyer_mid":"168",
     "buyer_name":"李永文",
     "buyer_phone":"18611224496",
     "buyer_address":"本地减肥的分离式的分离式的发送到",
     "deposit":"2000.00",//保证金
     "transaction_price":"80000.00",//成交价
     "seller_mid":"101",
     "seller_name":"wang001",
     "seller_phone":"80917614192",
     "status":"201",//订单状态: '101.待付款;102.已付款;201.商户已发货;202.商户无需发货;301.平台确认收货;302.平台发货;303.平台无需发货;401.卖家已收货;'501.未按时付款平台取消订单 502.买家手动取消订单（预留）
     "m_logistics_comment":null,
     "p_logistics_comment":null,
     "create_time":"1468758044",
     "pay_end_time":"1468844444",
     "send_end_time":"0",
     "send_end_time_extended":"0",
     "send_end_time_extended_reason":"",
     "pay_success_time":"0",//成功付款时间
     "s2p_s_time":null,
     "s2p_r_time":null,
     "p2b_s_time":null,
     "p2b_r_time":null,
     "remove_time":"0",
     "deposit_orderid":"0",
     "last_money":78000,//尾款
     "status_txt":"待收货"*/
    private String party_id;
    private String goods_name;
    private String goods_id;
    private String goods_img;
    private String buyer_mid;
    private String buyer_name;
    private String buyer_phone;
    private String buyer_address;
    private String deposit;
    private String transaction_price;
    private String seller_mid;
    private String seller_name;
    private String seller_phone;
    private String status;
    private String m_logistics_comment;
    private String p_logistics_comment;
    private String create_time;
    private String pay_end_time;
    private String send_end_time;
    private String send_end_time_extended;
    private String send_end_time_extended_reason;
    private String pay_success_time;
    private String s2p_s_time;
    private String s2p_r_time;
    private String p2b_s_time;
    private String p2b_r_time;
    private String deposit_orderid;
    private String last_money;
    private String status_txt;
    private String time;
    private String time_text;
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

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getTransaction_price() {
        return transaction_price;
    }

    public void setTransaction_price(String transaction_price) {
        this.transaction_price = transaction_price;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getBuyer_mid() {
        return buyer_mid;
    }

    public void setBuyer_mid(String buyer_mid) {
        this.buyer_mid = buyer_mid;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_phone() {
        return buyer_phone;
    }

    public void setBuyer_phone(String buyer_phone) {
        this.buyer_phone = buyer_phone;
    }

    public String getBuyer_address() {
        return buyer_address;
    }

    public void setBuyer_address(String buyer_address) {
        this.buyer_address = buyer_address;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getSeller_mid() {
        return seller_mid;
    }

    public void setSeller_mid(String seller_mid) {
        this.seller_mid = seller_mid;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_phone() {
        return seller_phone;
    }

    public void setSeller_phone(String seller_phone) {
        this.seller_phone = seller_phone;
    }

    public String getM_logistics_comment() {
        return m_logistics_comment;
    }

    public void setM_logistics_comment(String m_logistics_comment) {
        this.m_logistics_comment = m_logistics_comment;
    }

    public String getP_logistics_comment() {
        return p_logistics_comment;
    }

    public void setP_logistics_comment(String p_logistics_comment) {
        this.p_logistics_comment = p_logistics_comment;
    }

    public String getPay_end_time() {
        return pay_end_time;
    }

    public void setPay_end_time(String pay_end_time) {
        this.pay_end_time = pay_end_time;
    }

    public String getSend_end_time() {
        return send_end_time;
    }

    public void setSend_end_time(String send_end_time) {
        this.send_end_time = send_end_time;
    }

    public String getSend_end_time_extended() {
        return send_end_time_extended;
    }

    public void setSend_end_time_extended(String send_end_time_extended) {
        this.send_end_time_extended = send_end_time_extended;
    }

    public String getSend_end_time_extended_reason() {
        return send_end_time_extended_reason;
    }

    public void setSend_end_time_extended_reason(String send_end_time_extended_reason) {
        this.send_end_time_extended_reason = send_end_time_extended_reason;
    }

    public String getS2p_s_time() {
        return s2p_s_time;
    }

    public void setS2p_s_time(String s2p_s_time) {
        this.s2p_s_time = s2p_s_time;
    }

    public String getS2p_r_time() {
        return s2p_r_time;
    }

    public void setS2p_r_time(String s2p_r_time) {
        this.s2p_r_time = s2p_r_time;
    }

    public String getP2b_s_time() {
        return p2b_s_time;
    }

    public void setP2b_s_time(String p2b_s_time) {
        this.p2b_s_time = p2b_s_time;
    }

    public String getP2b_r_time() {
        return p2b_r_time;
    }

    public void setP2b_r_time(String p2b_r_time) {
        this.p2b_r_time = p2b_r_time;
    }

    public String getDeposit_orderid() {
        return deposit_orderid;
    }

    public void setDeposit_orderid(String deposit_orderid) {
        this.deposit_orderid = deposit_orderid;
    }

    public String getLast_money() {
        return last_money;
    }

    public void setLast_money(String last_money) {
        this.last_money = last_money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }
}

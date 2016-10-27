package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/10/8.
 */
public class Party {
         /*    "party_id":"148",
            "goods_id":"139",
            "member_id":"161",
            "payment_time":"1468487253",
            "payment_status":"1",//支付状态：0未支付，1已支付，2支付失败
            "party_start_time":"1468563360",
            "party_end_time":"1474369237",
            "cover":"http://182.92.152.135:85/upload/0K2A3651.JPG",
            "auction_id":"139",
            "auction_name":"测试拍品11",
            "start_status":"off",//状态：已结束，on未结束
            "max_price":2000000//最高出价
            //未结束状态多range_time，距离结束时间*/
    private String party_id;
    private String goods_id;
    private String member_id;
    private String payment_time;
    private String payment_status;
    private String party_start_time;
    private String party_end_time;
    private String cover;
    private String auction_id;
    private String auction_name;
    private String start_status;
    private String max_price;
    private String range_time;
    private String price_desc;

           /* "pid": "2",
            "party_type": "2",
            "title": "冬季特卖会",
            "cover": "http://aihuan.ccifc.cnhttp://localhost/branch_160408/Uploads/16-05-13/5735781fbd767.jpg",
            "description": "阿斯顿",
            "party_start_time": "1463208360",
            "party_end_time": "1476611303",
            "status": "4",
            "cnt": "100",
            "last_time": 700917//距离拍卖结束的时间*/

    private String pid;
    private String party_type;
    private String title;
    private String description;
    private String status;

    private String cnt;
    private String last_time;
    private String attention;
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

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getStart_status() {
        return start_status;
    }

    public void setStart_status(String start_status) {
        this.start_status = start_status;
    }

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getRange_time() {
        return range_time;
    }

    public void setRange_time(String range_time) {
        this.range_time = range_time;
    }

    public String getPrice_desc() {
        return price_desc;
    }

    public void setPrice_desc(String price_desc) {
        this.price_desc = price_desc;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}

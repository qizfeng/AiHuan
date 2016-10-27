package com.zipingfang.aihuan.bean;

import com.zipingfang.aihuan.utils.StringUtils;

/**
 * Created by 峰 on 2016/9/29.
 */
public class Goods {
    private String goods_id;
    private String cover;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    /*     "goods_name": "123231321321",
             "cover": "http://aihuan.ccifc.cn",
             "starting_price": "13000.00",
             "cnt": "24",
             "pid": "105",
             "auction_id": "131"*/
    private String goods_name;
    private String cnt;
    private String starting_price;
    private String pid;
    private String auction_id;
    private String seckill_price;
           /* "goods_id": "257",
            "goods_name": "包包",
            "cat_id1": "2",
            "status": "1",
            "cover": "http://aihuan.ccifc.cnkkkkkkkkkkk",
            "add_time": "1475219015",
            "starting_price": "11.00",
            "cnt": "0",//热度
            "sale_num": "0"//已售*/
    private String cat_id1;
    private String status;
    private String add_time;
    private String sale_num;
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getSeckill_price() {
        return seckill_price;
    }

    public void setSeckill_price(String seckill_price) {
        this.seckill_price = seckill_price;
    }

    public String getCat_id1() {
        return cat_id1;
    }

    public void setCat_id1(String cat_id1) {
        this.cat_id1 = cat_id1;
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

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    private String party_id;
    private String auction_name;

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }
    private String bid;
    private String apply_count;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getApply_count() {
        return apply_count;
    }

    public void setApply_count(String apply_count) {
        this.apply_count = apply_count;
    }
    private String party_type;

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }
}

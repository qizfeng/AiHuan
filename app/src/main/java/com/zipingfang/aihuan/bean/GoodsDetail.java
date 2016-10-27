package com.zipingfang.aihuan.bean;

import com.zipingfang.aihuan.utils.StringUtils;

/**
 * Created by 峰 on 2016/10/12.
 */

public class GoodsDetail {

    private String auction_id;
    private String auction_name;
    private String party_id;
    private String starting_price;
    private String reserve_price;
    private String deposit;
    private String bid_increments;
    private String agid;
    private String goods_name;
    private String catid_1;
    private String catid_2;
    private String catid_3;
    private String catid_4;
    private String catid_5;
    private String rules_item;
    private String img_1;
    private String img_2;
    private String img_3;
    private String img_4;
    private String img_5;
    private String cover;
    private String descrption;
    private String stock_num;
    private String stock_code;
    private String status;
    private String apply_status;
    private String add_time;
    private String recorder;
    private String auction_type;
    private String special_service;
    private String delay_minutes;
    private String listing;
    private String aftersale_service;
    private String usaged_item;
    private String brand_name;
    private String merchant_id;
    private String cnt;
    private String apply_count;
    private String status_describe;
    private String last_time;
    private String meet_moeny;
    private String reduce_money;
    private String seckill_price;
    // "meet_money": "6000",//满减活动满足金额
    //"reduce_money": "200",//满减活动减免金额
    //"seckill_price":30000//秒杀活动秒杀价
    //拍卖中和已结束有 当前价（max_price）  出价次数（price_count）
    private String max_price;
    private String price_count;
    //pay_deposit  是否支付保证金（1已支付，0未支付）
    //pay_deposit_money  //支付保证金的金额
    private String pay_deposit;
    private String pay_deposit_moeny;

    private String child_img;
    private String attention;
    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getReserve_price() {
        return reserve_price;
    }

    public void setReserve_price(String reserve_price) {
        this.reserve_price = reserve_price;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBid_increments() {
        return bid_increments;
    }

    public void setBid_increments(String bid_increments) {
        this.bid_increments = bid_increments;
    }

    public String getAgid() {
        return agid;
    }

    public void setAgid(String agid) {
        this.agid = agid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCatid_1() {
        return catid_1;
    }

    public void setCatid_1(String catid_1) {
        this.catid_1 = catid_1;
    }

    public String getCatid_2() {
        return catid_2;
    }

    public void setCatid_2(String catid_2) {
        this.catid_2 = catid_2;
    }

    public String getCatid_3() {
        return catid_3;
    }

    public void setCatid_3(String catid_3) {
        this.catid_3 = catid_3;
    }

    public String getCatid_4() {
        return catid_4;
    }

    public void setCatid_4(String catid_4) {
        this.catid_4 = catid_4;
    }

    public String getCatid_5() {
        return catid_5;
    }

    public void setCatid_5(String catid_5) {
        this.catid_5 = catid_5;
    }

    public String getRules_item() {
        return rules_item;
    }

    public void setRules_item(String rules_item) {
        this.rules_item = rules_item;
    }

    public String getImg_1() {
        return img_1;
    }

    public void setImg_1(String img_1) {
        this.img_1 = img_1;
    }

    public String getImg_2() {
        return img_2;
    }

    public void setImg_2(String img_2) {
        this.img_2 = img_2;
    }

    public String getImg_3() {
        return img_3;
    }

    public void setImg_3(String img_3) {
        this.img_3 = img_3;
    }

    public String getImg_4() {
        return img_4;
    }

    public void setImg_4(String img_4) {
        this.img_4 = img_4;
    }

    public String getImg_5() {
        return img_5;
    }

    public void setImg_5(String img_5) {
        this.img_5 = img_5;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getStock_num() {
        return stock_num;
    }

    public void setStock_num(String stock_num) {
        this.stock_num = stock_num;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getAuction_type() {
        return auction_type;
    }

    public void setAuction_type(String auction_type) {
        this.auction_type = auction_type;
    }

    public String getSpecial_service() {
        return special_service;
    }

    public void setSpecial_service(String special_service) {
        this.special_service = special_service;
    }

    public String getDelay_minutes() {
        return delay_minutes;
    }

    public void setDelay_minutes(String delay_minutes) {
        this.delay_minutes = delay_minutes;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }

    public String getAftersale_service() {
        return aftersale_service;
    }

    public void setAftersale_service(String aftersale_service) {
        this.aftersale_service = aftersale_service;
    }

    public String getUsaged_item() {
        return usaged_item;
    }

    public void setUsaged_item(String usaged_item) {
        this.usaged_item = usaged_item;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getSeckill_price() {
        return seckill_price;
    }

    public void setSeckill_price(String seckill_price) {
        this.seckill_price = seckill_price;
    }

    public String getApply_count() {
        return apply_count;
    }

    public void setApply_count(String apply_count) {
        this.apply_count = apply_count;
    }

    public String getStatus_describe() {
        return status_describe;
    }

    public void setStatus_describe(String status_describe) {
        this.status_describe = status_describe;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getMeet_moeny() {
        return meet_moeny;
    }

    public void setMeet_moeny(String meet_moeny) {
        this.meet_moeny = meet_moeny;
    }

    public String getReduce_money() {
        return reduce_money;
    }

    public void setReduce_money(String reduce_money) {
        this.reduce_money = reduce_money;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        if (!StringUtils.isEmpty(max_price))
            this.max_price = max_price;
        else
            this.max_price = "0";
    }

    public String getPrice_count() {
        return price_count;
    }

    public void setPrice_count(String price_count) {
        this.price_count = price_count;
    }

    public String getPay_deposit() {
        return pay_deposit;
    }

    public void setPay_deposit(String pay_deposit) {
        this.pay_deposit = pay_deposit;
    }

    public String getPay_deposit_moeny() {
        return pay_deposit_moeny;
    }

    public void setPay_deposit_moeny(String pay_deposit_moeny) {
        this.pay_deposit_moeny = pay_deposit_moeny;
    }


    private String inventory;
    private String sale_number;

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSale_number() {
        return sale_number;
    }

    public void setSale_number(String sale_number) {
        this.sale_number = sale_number;
    }

    public String getChild_img() {
        return child_img;
    }

    public void setChild_img(String child_img) {
        this.child_img = child_img;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}

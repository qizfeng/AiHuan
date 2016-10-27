package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/10/13.
 */

public class PriceRecord {
    /*   "id": "12",
     "party_id": "35",
     "goods_id": "74",
     "price": "3435435345345.00",
     "username": "ll",
     "attender": "123",
     "add_time": "2016-06-16 14:43:24"*/
    private String id;
    private String party_id;
    private String goods_id;
    private String price;
    private String username;
    private String attender;
    private String add_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttender() {
        return attender;
    }

    public void setAttender(String attender) {
        this.attender = attender;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}

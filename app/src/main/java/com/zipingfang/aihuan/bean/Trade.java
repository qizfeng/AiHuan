package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/9/21.
 */
public class Trade {
    private String cname;
    private String category_id;
    private String sort;
    private String party_type;
    private String img;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
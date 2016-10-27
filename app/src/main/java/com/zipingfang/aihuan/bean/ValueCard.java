package com.zipingfang.aihuan.bean;

import android.annotation.SuppressLint;

/**
 * Created by zipingfang on 16/10/21.
 */

public class ValueCard {
    private String id;
    private String card_id;
    private String card_password;
    private String card_money;
    private String card_description;
    private String card_expiration;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_password() {
        return card_password;
    }

    public void setCard_password(String card_password) {
        this.card_password = card_password;
    }

    public String getCard_money() {
        return card_money;
    }

    public void setCard_money(String card_money) {
        this.card_money = card_money;
    }

    public String getCard_description() {
        return card_description;
    }

    public void setCard_description(String card_description) {
        this.card_description = card_description;
    }

    public String getCard_expiration() {
        return card_expiration;
    }

    public void setCard_expiration(String card_expiration) {
        this.card_expiration = card_expiration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

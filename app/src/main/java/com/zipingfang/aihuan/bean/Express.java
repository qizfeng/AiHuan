package com.zipingfang.aihuan.bean;

/**
 * Created by zipingfang on 16/10/26.
 */

public class Express {
            /*"id": "1",
            "name": "中通快递",
            "description": "324234",
            "address": "234",
            "url": "http://adadadsda.com",
            "contact_first": "2342",
            "tel_first": "4324",
            "contact_second": "234323",
            "tel_second": "234234",
            "status": "0",//状态：1开启，2关闭，3禁用
            "add_time": "2016-03-28 19:10:06",
            "recorder": "1",
            "logo": "./Uploads/16-03-28/56f91100009ca.jpg"*/
    private String id;
    private String name;
    private String description;
    private String address;
    private String url;
    private String contact_first;
    private String tel_second;
    private String status;
    private String add_time;
    private String recorder;
    private String logo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContact_first() {
        return contact_first;
    }

    public void setContact_first(String contact_first) {
        this.contact_first = contact_first;
    }

    public String getTel_second() {
        return tel_second;
    }

    public void setTel_second(String tel_second) {
        this.tel_second = tel_second;
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

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}

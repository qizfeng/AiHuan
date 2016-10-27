package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/10/9.
 */
public class Address {
          /*   "id":"99",
            "member_id":"161",
            "receiver_name":"1242",
            "receiver_phone":"13522574406",
            "is_default":"1",//是否是默认收货地址（1默认，0取消）
            "addr_area":"北京",
            "addr_detail":"北京测试",
            "receiver_addr":"",
            "create_date":"1468566488"*/
    private String id;
    private String  member_id;
    private String receiver_name;
    private String receiver_phone;
    private String is_default;
    private String  addr_area;
    private String  addr_detail;
    private String receiver_addr;
    private String create_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getAddr_area() {
        return addr_area;
    }

    public void setAddr_area(String addr_area) {
        this.addr_area = addr_area;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getReceiver_addr() {
        return receiver_addr;
    }

    public void setReceiver_addr(String receiver_addr) {
        this.receiver_addr = receiver_addr;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}

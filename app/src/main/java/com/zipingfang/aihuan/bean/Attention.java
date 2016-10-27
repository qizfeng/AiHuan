package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/9/22.
 */
public class Attention {
        /*    "id": "33",
            "member_id": "121",
            "party_id": "53",
            "party_title": "564647567657567",
            "party_cover": "http://182.92.152.135:82/Uploads/16-06-21/5768df030d6dc.jpg",
            "party_overtime": "1466577300",
            "party_starttime": "1466493600",
            "status": "1",//关注状态：1已关注2取消
            "add_time": "1466499183",
            "is_start": "已结束",	//专场进行状态
            "last_time": "1466577300",//结束时间（未结束时是结束剩余时间）
            "cnt": "142",//热度
            "proceed_count": "0"//出价次数
            starting_price*/

         /*   "agid": "110",
            "goods_name": "23234323432423",
            "starting_price": "13000.00",
            "cover": "http://182.92.152.135:85/upload/0K2A3562.JPG",
            "status": "1",
            "cnt": "3",
            "apply_num": "0",
            "proceed_count": "0"*/
    private String agid;
    private String  goods_name;
    private String apply_num;
    private String proceed_count;
    private String id;
    private String member_id;
    private String party_id;
    private String party_title;
    private String party_cover;
    private String party_starttime;
    private String party_overtime;
    private String status;
    private String add_time;
    private String is_start;
    private String last_time;
    private String cnt;
    private String proceed_pcuont;
    private String starting_price;
    private String party_type;
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

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getParty_title() {
        return party_title;
    }

    public void setParty_title(String party_title) {
        this.party_title = party_title;
    }

    public String getParty_cover() {
        return party_cover;
    }

    public void setParty_cover(String party_cover) {
        this.party_cover = party_cover;
    }

    public String getParty_starttime() {
        return party_starttime;
    }

    public void setParty_starttime(String party_starttime) {
        this.party_starttime = party_starttime;
    }

    public String getParty_overtime() {
        return party_overtime;
    }

    public void setParty_overtime(String party_overtime) {
        this.party_overtime = party_overtime;
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

    public String getIs_start() {
        return is_start;
    }

    public void setIs_start(String is_start) {
        this.is_start = is_start;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getProceed_pcuont() {
        return proceed_pcuont;
    }

    public void setProceed_pcuont(String proceed_pcuont) {
        this.proceed_pcuont = proceed_pcuont;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
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

    public String getApply_num() {
        return apply_num;
    }

    public void setApply_num(String apply_num) {
        this.apply_num = apply_num;
    }

    public String getProceed_count() {
        return proceed_count;
    }

    public void setProceed_count(String proceed_count) {
        this.proceed_count = proceed_count;
    }

    /*"agid": "21",
            "goods_name": "【98新】欧米茄-大三针-日历-自动机械-钢-未使用-男款腕表890",
            "cover": "http://img11.360buyimg.com/n1/jfs/t1951/226/1764258986/450836/7cdc13eb/56d7f575Ncec16bc9.jpg",
            "starting_price": "12312.00",
            "cnt": "502",//热度
            "apply_count": "0",//报名人数
            "proceed_count": "0",//出价次数
            "price_text": "起拍价"*/
    private String cover;
    private String apply_count;
    private String price_text;

   /* "pid": "10",
            "title": "去年买了个表",
            "cover": "",
            "party_start_time": "1463657340",
            "party_end_time": "1477793721",
            "cnt": "0",//热敷
            "proceed_count": "0",//出价次数
            "party_type": "party",//所属专场类型
            "last_time": 1703191//专场结束时间*/
    private String pid;
    private String title;
    private String party_start_time;
    private String party_end_time;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getApply_count() {
        return apply_count;
    }

    public void setApply_count(String apply_count) {
        this.apply_count = apply_count;
    }

    public String getPrice_text() {
        return price_text;
    }

    public void setPrice_text(String price_text) {
        this.price_text = price_text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

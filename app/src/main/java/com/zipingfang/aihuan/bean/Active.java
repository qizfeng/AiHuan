package com.zipingfang.aihuan.bean;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class Active {
    private String pid;
    private String title;
    private String cover;
    private String party_start_time;
    private String party_end_time;
    private String status;
    private String cnt;
    private String proceed_count;
    private String party_type;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getProceed_count() {
        return proceed_count;
    }

    public void setProceed_count(String proceed_count) {
        this.proceed_count = proceed_count;
    }

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }
}

package com.zipingfang.aihuan.bean;

/**
 * Created by 峰 on 2016/10/9.
 */
public class MessageIndex {
    /* "sysdata": {
         "sys_msgcount": "33",//系统消息总数
                 "sys_lastmsg_title": "拍品审核结束",//最新一条系统消息标题
                 "sys_lastmsg_time": "1471318877",//最新一条系统消息发送时间
                 "msg_type": "系统消息"
     },
             "timerdata": {
         "timer_msgcount": "0",//定时消息总数
                 "timer_lastmsg_title": "暂时没有收到定时消息",//最新一条定时消息标题
                 "timer_lastmsg_time": null//最新一条定时消息发送时间
         msg_type": "定时消息"
     }*/
    private String sys_msgcount;
    private String sys_lastmsg_title;
    private String sys_lastmsg_time;
    private String msg_type;

    private String timer_msgcount;
    private String timer_lastmsg_title;
    private String timer_lastmsg_time;

    public String getSys_msgcount() {
        return sys_msgcount;
    }

    public void setSys_msgcount(String sys_msgcount) {
        this.sys_msgcount = sys_msgcount;
    }

    public String getSys_lastmsg_title() {
        return sys_lastmsg_title;
    }

    public void setSys_lastmsg_title(String sys_lastmsg_title) {
        this.sys_lastmsg_title = sys_lastmsg_title;
    }

    public String getSys_lastmsg_time() {
        return sys_lastmsg_time;
    }

    public void setSys_lastmsg_time(String sys_lastmsg_time) {
        this.sys_lastmsg_time = sys_lastmsg_time;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getTimer_msgcount() {
        return timer_msgcount;
    }

    public void setTimer_msgcount(String timer_msgcount) {
        this.timer_msgcount = timer_msgcount;
    }

    public String getTimer_lastmsg_title() {
        return timer_lastmsg_title;
    }

    public void setTimer_lastmsg_title(String timer_lastmsg_title) {
        this.timer_lastmsg_title = timer_lastmsg_title;
    }

    public String getTimer_lastmsg_time() {
        return timer_lastmsg_time;
    }

    public void setTimer_lastmsg_time(String timer_lastmsg_time) {
        this.timer_lastmsg_time = timer_lastmsg_time;
    }
}

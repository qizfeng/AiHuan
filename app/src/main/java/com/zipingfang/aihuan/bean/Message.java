package com.zipingfang.aihuan.bean;

import com.zipingfang.aihuan.asynthttp.RequestParams;

/**
 * Created by 峰 on 2016/9/29.
 */
public class Message {
            /* "id": "22",
            "title": "拍品审核结束",
            "content": "的【。",
            "status": "0",//阅读状态
            "to_mid": "101",
            "create_time": "1466681182",
            "msg_type": "系统消息"*/
    private String id;
    private String title;
    private String content;
    private String status;
    private String to_mid;
    private String create_time;
    private String msg_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo_mid() {
        return to_mid;
    }

    public void setTo_mid(String to_mid) {
        this.to_mid = to_mid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }
}

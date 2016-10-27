package com.zipingfang.aihuan.bean;

import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.utils.StringUtils;

/**
 * Created by 峰 on 2016/9/13.
 */
public class BannerInfo {
    private String id;
    private String url;
    private String content;
    private String type;//内容类型：1、专场；2、活动；3、单品；4、图片
    private String cover;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* "id":"6",
     "img":"",
     "type_id":"0",//轮播图所属类型的id
     "url":"",
     "description":"",
     "action_start":"2016-06-12 10:24:00",
     "action_end":"2016-06-13 10:25:00",
     "recorder":"1",
     "addtime":"2016-06-12 10:21:57",
     "status":"1",//发布状态：1已发布，2关闭，3未发布
     "sort":"1",//排序
     "distinguish":"2",//1首页，2启动页
     "os_state":"4",//开启平台
     "bg_color":"",
     "type":"1",
     "title":"fwrwefewrwr",
     "ad_position":"1"//广告位位置：1首页顶部，2首页中部，3交易大厅顶部，4备用*/
    private String img;
    private String type_id;
    private String description;
    private String action_start;
    private String action_end;
    private String recorder;
    private String addtime;
    private String status;
    private String sort;
    private String distinguish;
    private String os_state;
    private String bg_color;
    private String title;
    private String ad_position;
    private String party_id;
    private String auction_id;
    private String party_type;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction_start() {
        return action_start;
    }

    public void setAction_start(String action_start) {
        this.action_start = action_start;
    }

    public String getAction_end() {
        return action_end;
    }

    public void setAction_end(String action_end) {
        this.action_end = action_end;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDistinguish() {
        return distinguish;
    }

    public void setDistinguish(String distinguish) {
        this.distinguish = distinguish;
    }

    public String getOs_state() {
        return os_state;
    }

    public void setOs_state(String os_state) {
        this.os_state = os_state;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAd_position() {
        return ad_position;
    }

    public void setAd_position(String ad_position) {
        this.ad_position = ad_position;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }

    public String getParty_id() {
        return party_id;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public String getParty_type() {
        return party_type;
    }
}

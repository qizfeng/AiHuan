package com.zipingfang.aihuan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/21.
 */

public class Point {
   /* "point": "30000",//当前积分余额
            "level": "1",//会员等级1普通，2银牌，3金牌，4钻石
            "detail": [
    {
        "id": "4",
            "mid": "70",
            "increase_score": "10000",//本次获得
            "decrease_score": "0",//（使用）积分
            "action_context": "购买储值卡",
            "order_id": "0",
            "add_time": "2016-10-21 10:14:35",
            "status": "1",//1正常，2异常
            "current_point": "30000",//当前剩余积分
            "comment": "消费奖励",
            "type": "1",//类型：1消费奖励，2使用积分
            "ex_member_mid": "0"
    }
    ]*/
    private String point;
    private String level;
    private List<PointDetail> detail = new ArrayList<>();

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<PointDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<PointDetail> detail) {
        this.detail = detail;
    }
}

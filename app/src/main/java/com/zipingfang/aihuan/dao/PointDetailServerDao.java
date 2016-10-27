package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.bean.Point;
import com.zipingfang.aihuan.bean.PointDetail;
import com.zipingfang.aihuan.bean.PriceRecord;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.ui.adapter.MessageAdapter;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.view.MultipleRadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/21.
 */

public class PointDetailServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public List<PointDetail> points = new ArrayList<>();
    public Point point =new Point();
    public PointDetailServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_POINT_DETAIL;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid",mid);
        postData(maps, url);
    }

    protected void analyseData(String data, String status) throws Exception {
        Log.e("qizfeng", "verifyDao:>>>" + data + "," + status);
        res = data;
        if (!StringUtils.isEmpty(res)) {
            isSucc = true;
            point =(Point) GsonFactory.getInstanceByJson(Point.class,res);
            points = point.getDetail();
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}
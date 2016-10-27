package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/27.
 */

public class ShopManagerGoodsUpOrDownServerDao extends BaseDao{
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String agid;
    public String action;//1下架 2上架
    public ShopManagerGoodsUpOrDownServerDao(Context context) {
        super(context);
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_UP_DOWN;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid",mid);
        maps.put("agid",agid);
        maps.put("action",action);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                isSucc = true;
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }

    }

}

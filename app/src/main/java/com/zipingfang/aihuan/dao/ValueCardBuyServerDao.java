package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.ValueCard;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/21.
 */

public class ValueCardBuyServerDao extends BaseDao{
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String card_id;
    public ValueCardBuyServerDao(Context context) {
        super(context);
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_VALUE_CARD_BUY;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid",mid);
        maps.put("card_id",card_id);
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

package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.ShopOrder;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/27.
 */

public class ShopOrderServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String pageIndex = "1";
    public String pageSize="15";
    public String type;
    public List<ShopOrder> shopOrders = new ArrayList<>();
    public ShopOrderServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_SHOPMANAGER_ORDER;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid","96");
        maps.put("type",type);
        maps.put("page",pageIndex);
        maps.put("row",pageSize);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                isSucc = true;
                shopOrders = GsonFactory.jsonToArrayList(res,ShopOrder.class);
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }

    }

}



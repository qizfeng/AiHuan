package com.zipingfang.aihuan.dao;

import android.content.Context;

import com.zipingfang.aihuan.bean.Order;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/9.
 */
public class OrderDetailServerDao extends BaseDao {
    public String desc;
    public String res;
    public boolean isSucc;
    private Context mContext;
    public Order order;
    public String order_id;

    public OrderDetailServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ORDER_DETAIL;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("oid", order_id);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        if (!StringUtils.isEmpty(res)) {
            order = (Order) GsonFactory.getInstanceByJson(Order.class, data);
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}
package com.zipingfang.aihuan.dao;

import android.content.Context;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.bean.Order;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/8.
 */
public class OrderListServerDao extends BaseDao {
    public String mid;
    public String type;//101待付款，102待发货，201待收货，401已完成
    public String desc;
    public String res;
    public boolean isSucc;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    public List<Order> mData = new ArrayList<>();

    public OrderListServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ORDER_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", "169");
        maps.put("type", type);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        if (!StringUtils.isEmpty(res)) {
            mData.addAll(GsonFactory.jsonToArrayList(data, Order.class));
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}

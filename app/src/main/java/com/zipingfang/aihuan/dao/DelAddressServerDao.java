package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/10.
 */
public class DelAddressServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String address_id;
    public DelAddressServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ADDRESS_DELETE;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        maps.put("address_id",address_id);
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


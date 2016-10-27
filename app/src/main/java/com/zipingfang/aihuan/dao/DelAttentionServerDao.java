package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/9.
 */
public class DelAttentionServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public int type;
    public String party_type;
    public String party_id;
    public String goods_id;

    public DelAttentionServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_DEL_ATTENTION;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        if (type == 1) {
            maps.put("party_type", party_type);
            maps.put("party_id", party_id);
        } else {
            maps.put("goods_id", goods_id);
        }
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

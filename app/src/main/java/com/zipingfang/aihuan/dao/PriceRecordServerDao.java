package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.PriceRecord;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/13.
 */

public class PriceRecordServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String goods_id;
    public List<PriceRecord> recordList = new ArrayList<>();

    public PriceRecordServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_PRICE_RECORD;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("goods_id", goods_id);
        postData(maps, url);
    }

    protected void analyseData(String data, String status) throws Exception {
        Log.e("qizfeng", "verifyDao:>>>" + data + "," + status);
        res = data;
        if (!StringUtils.isEmpty(res)) {
            recordList = GsonFactory.jsonToArrayList(res, PriceRecord.class);
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}

package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/22.
 */

public class SearchServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String keyword;
    public List<Attention> goodsList = new ArrayList<>();
    public List<Attention> partyList= new ArrayList<>();
    public String type;
    public SearchServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_SERACH;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("keyword",keyword);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                JSONObject object = new JSONObject(res);
                if ("party".equals(type)) {
//                    JSONObject object = new JSONObject(data);
//                    JSONArray array = object.optJSONArray(type);
//                    Log.e("qizfeng","attention:"+array.toString());
                    partyList.addAll(GsonFactory.jsonToArrayList(object.opt("party_array").toString(), Attention.class));
                } else if ("goods".equals(type)) {
                    goodsList.addAll(GsonFactory.jsonToArrayList(object.opt("goods_array").toString(), Attention.class));
                }
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


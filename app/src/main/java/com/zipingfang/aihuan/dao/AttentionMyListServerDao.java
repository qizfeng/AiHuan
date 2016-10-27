package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/22.
 */
public class AttentionMyListServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public int pageSize = 15;
    public int pageIndex;
    public String mid;
    public String type;

    public List<Attention> goodsList = new ArrayList<>();
    public List<Attention> partyList= new ArrayList<>();
    public AttentionMyListServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ATTENTION_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("type", type);
        maps.put("mid", mid);
        maps.put("row",pageSize+"");
        maps.put("page", pageIndex + "");
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                if ("party".equals(type)) {
//                    JSONObject object = new JSONObject(data);
//                    JSONArray array = object.optJSONArray(type);
//                    Log.e("qizfeng","attention:"+array.toString());
                    partyList.addAll(GsonFactory.jsonToArrayList(data, Attention.class));
                } else if ("goods".equals(type)) {
                    goodsList.addAll(GsonFactory.jsonToArrayList(data, Attention.class));
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

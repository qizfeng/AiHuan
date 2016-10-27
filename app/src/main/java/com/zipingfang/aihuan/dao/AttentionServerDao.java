package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haozhiyu on 16/10/19.
 */

public class AttentionServerDao extends BaseDao{
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String agid;
    public String type;
    public String pid;
    public String ptitle;
    public String cover;
    public String starttime;
    public String overtime;
    public AttentionServerDao(Context context) {
        super(context);
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ATTENTION;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("type", type);
        maps.put("mid", mid);
        maps.put("agid",agid);
        maps.put("pid",pid);
        maps.put("ptitle",ptitle);
        maps.put("cover",cover);
        maps.put("starttime",starttime);
        maps.put("overtime",overtime);
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

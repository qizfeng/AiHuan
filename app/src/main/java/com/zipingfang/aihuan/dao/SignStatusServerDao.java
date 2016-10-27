package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Sign;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/26.
 */

public class SignStatusServerDao extends BaseDao{
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String type;
    public String mid;
    public Sign  signInfo = new Sign();
    public SignStatusServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_SIGN_STATUS;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid",mid);

        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                isSucc = true;
                signInfo = (Sign) GsonFactory.getInstanceByJson(Sign.class,res);
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }

    }

}




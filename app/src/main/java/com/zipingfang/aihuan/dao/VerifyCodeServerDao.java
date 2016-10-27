package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/12.
 * 验证码
 */
public class VerifyCodeServerDao extends BaseDao {
    public String phone;
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String remark;//1注册 2登录 3找回密码
    public VerifyCodeServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("phone", phone);
        maps.put("remark",remark);
        postData(maps, Constants.URL_VERIFY);
    }

    protected void analyseData(String data, String status) throws Exception {
        Log.e("qizfeng", "verifyDao:>>>" + data + "," + status);
        res = data;
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = new JSONObject(data);
            desc = object.optString("desc");
            Log.e("qifeng", "data>>>" + data);
            isSucc=true;
        } else {
            desc = analyseStatus(status);
            isSucc=false;
        }
    }
}

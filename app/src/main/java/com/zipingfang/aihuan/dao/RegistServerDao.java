package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/12.
 * 注册
 */
public class RegistServerDao extends BaseDao {
    private Context mContext;
    public String phone;//手机号
    public String userName;//用户名
    public String verify;//验证码
    public String password;//密码
    public String repeatpass;//重复密码
    public String requester;//邀请人
    public String desc;
    public String res;
    public boolean isSucc;
    public RegistServerDao(Context context) {
        super(context);
        mContext = context;
    }
    @Override
    public void exec() throws Exception {
        String url = Constants.URL_REGISTER;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("phone", phone);
        if (!TextUtils.isEmpty(userName)) {
            maps.put("username", userName);
        }
        maps.put("verify", verify);
        if (!StringUtils.isEmpty(requester)) {
            maps.put("requester", requester);
        }
        maps.put("password", password);
        maps.put("repeatpass", repeatpass);
        postData(maps, url);
    }

    //    @Override
    protected void analyseData(String data, String status) throws Exception {
//        super.analyseData(data,status);
        res = data;
        Log.e("qizfeng", "registDao:>>>" + data + "," + status);
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = new JSONObject(data);
            desc = object.optString("desc");
            Log.e("qifeng", "data>>>" + data);
            isSucc=true;
        } else {
            desc =   analyseStatus(status);
            isSucc=false;
        }
    }
}

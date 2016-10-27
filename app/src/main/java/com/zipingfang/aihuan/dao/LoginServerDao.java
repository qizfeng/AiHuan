package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.XmlUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/8.
 * 登录
 */
public class LoginServerDao extends BaseDao {
    private Context mContext;
    public static LoginServerDao loginDao;
    public String phone;//手机号
    public String userName;//用户名
    public String verify;//验证码
    public String password;//密码
    public String repeatpass;//重复密码
    public String requester;//邀请人
    public String desc;
    public String res;
    public boolean isSucc;

    public LoginServerDao(Context context) {
        super(context);
        mContext = context;
    }

    public static LoginServerDao getInstance(Context context) {
        if (loginDao == null)
            loginDao = new LoginServerDao(context);
        return loginDao;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_LOGIN;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("phone", phone);
        maps.put("password", password);
        postData(maps, url);
    }

    //    @Override
    protected void analyseData(String data, String status) throws Exception {
//        super.analyseData(data,status);
        Log.e("qizfeng", "loginDao:>>>" + data + "," + status);
        res = data;
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = new JSONObject(data);
            String user = object.optJSONObject("info").toString();
            desc = object.optString("desc");
            XmlUtils.saveToXml(mContext, "user", user);
            Log.e("qifeng", "data>>>" + data);
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}

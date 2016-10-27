package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.asynthttp.AsyncHttpResponseHandler;
import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.bean.User;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.ui.user.UserInfoActivity;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.XmlUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/26.
 */
public class UserInfoServerDao extends BaseDao {
    public String mid;
    public String desc;
    public String res;
    public boolean isSucc;
    public User mUserInfo = new User();
    private AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    public String avatar;
    public UserInfoServerDao(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public void exec() throws Exception {
        String url = Constants.URL_MEMBER_INFO;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        Log.e("qizfeng", "userInfo:" + data);
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = new JSONObject(data);
//            String user = object.optJSONObject("info").toString();
//            desc = object.optString("desc");
            XmlUtils.saveToXml(mContext, "user", data);
            mUserInfo = BaseActivity.getUser(context);
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }

    /**
     * 修改昵称
     *
     * @param key
     * @param value
     */
    public void editUserInfo(String key,String value) {
        RequestParams params = new RequestParams();
        params.put("mid", mid);
        params.put("field", key);
        params.put("value", value);
        Log.e("qizfeng", Constants.URL_EDIT_MEMBER_INFO + params.toString());
        client.post(Constants.URL_EDIT_MEMBER_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    desc = analyseStatus(new JSONObject(res).optJSONObject("status") + "");
                    Log.e("qizfeng", "res:" + desc);
                    if (StringUtils.isEmpty(desc))
                        ToastUtils.show(mContext, "修改成功");
                    else
                        ToastUtils.show(mContext, desc);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void editAvater(String fileUrl) {
        RequestParams params = new RequestParams();
        params.put("mid", mid);
        File file = new File(fileUrl);
        if (file.isFile()) {
            try {
                params.put("avatar", file);
                Log.e("qizfeng","upload:"+Constants.URL_EDIT_MEMBER_AVATAR+params.toString());
                client.post(Constants.URL_EDIT_MEMBER_AVATAR,
                        params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String json = new String(responseBody);
                                Log.e("qizfeng", "userInfoDao:" + json);
                                try {
                                    JSONObject object = new JSONObject(json);
                                    desc = analyseStatus(object.optJSONObject("status") + "");
                                    if (StringUtils.isEmpty(desc)) {
                                        avatar = object.optJSONObject("data").optString("avatar");
                                        Log.e("qizfeng", "userInfoDao:" + avatar);
                                        ImageLoader.getInstance().displayImage(avatar, UserInfoActivity.iv_header);

                                    } else
                                        ToastUtils.show(mContext, desc);
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.e("qizfeng", "userInfoDao:" + new String(responseBody));
                            }
                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // 不是文件
                ToastUtils.show(mContext, "请选择一个正确的文件");
            }

        } else {
            // 不是文件
            ToastUtils.show(mContext, "请选择一个正确的文件");
        }
    }
}

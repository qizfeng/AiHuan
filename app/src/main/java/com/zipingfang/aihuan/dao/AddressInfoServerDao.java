package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.asynthttp.AsyncHttpResponseHandler;
import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.bean.Address;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/10.
 */
public class AddressInfoServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String address_id;
    public Address address = new Address();

    public AddressInfoServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ADDRESS_DETAIL;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        maps.put("address_id", address_id);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                address = (Address) GsonFactory.getInstanceByJson(Address.class, data);
                isSucc = true;
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }
    }

    public AsyncHttpClient httpClient = new AsyncHttpClient();

    /**
     * 删除收货地址
     */
    public void deleteAddress() {
        String url = Constants.URL_ADDRESS_DELETE;
        RequestParams params = new RequestParams();
        params.put("mid", mid);
        params.put("address_id", address_id);
        httpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject object = new JSONObject(res);
                    JSONObject status = object.optJSONObject("status");
                    int succ = status.optInt("succeed");
                    if (succ == 1) {
                        ToastUtils.show(mContext, "删除成功");
                    } else {
                        ToastUtils.show(mContext, "删除失败");
                    }

                } catch (Exception e) {
                    Log.e("qizfeng", "error:" + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    String res = new String(responseBody);
                    Log.e("qizfeng", "setDefault:" + res);
                } catch (Exception e) {

                }
            }
        });
    }
}

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
 * Created by 峰 on 2016/10/9.
 */
public class AddressServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public String address_id;
    public List<Address> mData = new ArrayList<>();
    public AsyncHttpClient httpClient = new AsyncHttpClient();

    public AddressServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ADDRESS_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                mData = GsonFactory.jsonToArrayList(res, Address.class);
                isSucc = true;
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }
    }


    public void setDefaultAddress() {
        String url = Constants.URL_SET_DEFAULT_ADDR;
        RequestParams params = new RequestParams();
        params.put("mid", mid);
        params.put("address_id", address_id);
        Log.e("qizfeng", "isDefault:" + url + params.toString());
        httpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
//                    JSONObject object = new JSONObject(res);
                    Log.e("qizfeng", "setDefault:" + res);

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

    /* *mid=101#会员id
                *type=1#操作类型（1新增，2修改）
        *receiver_name=测试#收货人
                *receiver_phone=18627976612#收货人电话
                *addr_area=中国#收货地区
                *addr_detail=海淀区中关村软件园#收货详细地址*/
    public int type;
    public String receiver_name;
    public String receiver_phone;
    public String addr_area;
    public String addr_detail;

    public void editAddress() {
        String url = Constants.URL_ADD_ADDR;
        RequestParams params = new RequestParams();
        params.put("mid", mid);
        params.put("type", type);
        params.put("address_id",address_id);
        params.put("receiver_name", receiver_name);
        params.put("receiver_phone", receiver_phone);
        params.put("addr_area", addr_area);
        params.put("addr_detail", addr_detail);
        Log.e("qizfeng", "editAddress:" + url+params.toString());
        httpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    Log.e("qizfeng", "editAddress:" + res);
                    JSONObject object = new JSONObject(res);
                    JSONObject status = object.optJSONObject("status");
                    int succ = status.optInt("succeed");
                    if (succ == 1) {
                        ToastUtils.show(mContext, "操作成功");
                    } else {
                        ToastUtils.show(mContext, "操作失败");
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

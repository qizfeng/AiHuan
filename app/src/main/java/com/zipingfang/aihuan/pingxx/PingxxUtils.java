package com.zipingfang.aihuan.pingxx;


import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zipingfang on 16/10/20.
 */

public class PingxxUtils {
    /**
     * 银联支付渠道
     */
    public static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_WECHAT = "wx";
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_QPAY = "qpay";
    /**
     * 支付支付渠道
     */
    public static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    public static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    public static final String CHANNEL_JDPAY_WAP = "jdpay_wap";

    public static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Log.e("qizfeng","url:"+url+json);
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }


}

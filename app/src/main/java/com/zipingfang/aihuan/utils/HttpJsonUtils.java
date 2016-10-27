package com.zipingfang.aihuan.utils;

import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpJsonUtils {

    public static final int CONST_CONNECT_TIME = 60;

    /**
     * 返回string
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getResultStr(String url) throws Exception {
        return getResultStr(url, CONST_CONNECT_TIME);
    }

    public static String getResultStr(String url, int second) throws Exception {
        info(url);
        String body = getReceiveData(getResponse(url, second));// 取得内容-json对象
        debug("getReceiveData:" + body + "!!!!!!!!!");
        return body;
    }

    /**
     * 取得内容-json对象 json eg: JSONArray array = new JSONArray(body); for(int i=0;
     * i<array.length(); i++){ JSONObject obj = array.getJSONObject(i);
     * page5_user.setText(obj.getString("id"));
     * page5_title.setText(obj.getString("name"));
     * page5_contect.setText(obj.getString("memo")); }
     *
     * @param response
     * @return
     * @throws Exception
     */
    private static String getReceiveData(HttpResponse response)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    entity.getContent()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
        }
        return sb.toString();
    }

    /**
     * 接收时连接对象的实现
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HttpResponse getResponse(String url, int second)
            throws Exception {
        HttpResponse response = null;
        debug("  Timeout=" + second);

        HttpClient client = new DefaultHttpClient();
        HttpParams httpParams = client.getParams();

        HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 2 * second * 1000);

        // httpParams.setIntParameter("http.socket.timeout",second*1000); //毫秒

        HttpGet get = new HttpGet(url);
        // get.getParams().setIntParameter("http.socket.timeout",second*1000);
        // //毫秒
        response = client.execute(get);
        info("  Connect server sucess!");

        return response;
    }

    /**
     * post数据到服务器
     * http://182.92.159.221/mobileapi.php?action=register&viIden=linuxTest
     * &comId=10004
     * &actionTime=1410656685&appTitle=%E5%93%88%E5%93%88%E5%93%88&appName
     * =linux.zipingfang.com
     *
     * @throws Exception
     */
    public static String post(String url, Map<String, String> params) {
        try {
            Log.e("qizfeng", "__________请求URL:" + url);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            // 目标地址
            HttpPost httppost = new HttpPost(url);
            HttpParams httpParams = httppost.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    CONST_CONNECT_TIME * 1000);
            HttpConnectionParams.setSoTimeout(httpParams,
                    2 * CONST_CONNECT_TIME * 1000);

            // post 参数 传递
            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
            if (params != null) {
                String key, value = "";
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getKey() != null) {
                        key = entry.getKey().toString();
                        Object tmp = entry.getValue();
                        if (tmp != null) {
                            value = tmp.toString();
                            debug(key + "=" + value);
                            nvps.add(new BasicNameValuePair(key, value)); // 参数
                        }
                    }
                }
            }

            httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); // 设置参数给Post

            // 执行
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            debug("" + response.getStatusLine());
            if (entity != null) {
                debug("Response content length: " + entity.getContentLength());

                // 显示结果
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        entity.getContent(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (entity != null) {
                    entity.consumeContent();
                }
                return sb.toString();
            } else
                return "";
        } catch (Exception e) {
            Log.e("qizfeng","post:"+e.toString());
        }
        return "";
    }

    /**
     * post数据到服务器
     * http://182.92.159.221/mobileapi.php?action=register&viIden=linuxTest
     * &comId=10004
     * &actionTime=1410656685&appTitle=%E5%93%88%E5%93%88%E5%93%88&appName
     * =linux.zipingfang.com
     *
     * @throws Exception
     */
    public static String post(String requestURL, String params)
            throws Exception {
        DataOutputStream dos = null;
        InputStream input = null;
        try {
            debug(requestURL);
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONST_CONNECT_TIME * 1000);
            conn.setConnectTimeout(CONST_CONNECT_TIME * 1000);
            conn.setDoInput(true); // 允许输入
            conn.setDoOutput(true); // 允许输出
            conn.setUseCaches(false); // 不允许使用缓
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            HttpURLConnection.setFollowRedirects(true);

            /**
             * 当文件不为空时执行
             */
            dos = new DataOutputStream(conn.getOutputStream());

            int len = params.length();
            dos.write(params.getBytes(), 0, len);// 发送请求参数

            // flush输出流的缓冲
            dos.flush();

            /**
             * 获取响应200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            if (res == 200) {
                input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                debug(sb1.toString());
                return sb1.toString();
            } else {
                throw new Exception("result code=" + res);
            }
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 显示调试信息
     *
     * @param msg
     */
    public static boolean isDebug = Constants.debug;

    private static void debug(String msg) {
        if (isDebug)
            Lg.debug("    " + msg);
    }

    private static void info(String msg) {
        if (isDebug)
            Lg.info(msg);
    }

    @SuppressWarnings("unused")
    private static void error(String msg) {
        if (isDebug)
            Lg.error(msg);
    }
}

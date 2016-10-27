package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.ApkUtils;
import com.zipingfang.aihuan.utils.HttpJsonUtils;
import com.zipingfang.aihuan.utils.Lg;
import com.zipingfang.aihuan.utils.NetUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class BaseDao {

    /**
     * post使用的url
     */
    public static final String base_url = Constants.BASE_URL;

    protected String mTableName, mFields;
    public boolean mIsBusy;

    protected Context context;

    public BaseDao(Context context) {
        this.context = context;
    }

    public void loadData(final IDaoCallback callback) {
        debug("___loadData from remote>>>>>" + this.getClass().getSimpleName());
        if (mIsBusy) {// 查询完成会先删除全部,再插入新的,如果两次冲突,容易导致数据问题
            error("正在查询中,提交太过频繁!");
            if (callback != null)
                callback.exec(false, "");
            return;
        }
        if (!NetUtils.getInstance(context).isNetworkConnected()) {// 无网络连接
            error("无网络连接!");
            if (callback != null)
                callback.exec(false, "无网络连接!");
            ToastUtils.show(context, "无网络连接!");
            return;
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (callback != null) {
                    if (msg.what > 0)
                        callback.exec(false, msg.obj);
                    else
                        callback.exec(true, "");
                }
            }
        };
        // thread
        new Thread() {
            @SuppressWarnings("unused")
            @Override
            public void run() {
                try {
                    mIsBusy = true;
                    exec();
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    error(e);
                    String msg = "" + e;
                    if (e == null)
                        msg = "未知异常";
                    else if (msg.indexOf("timed out") >= 0)
                        msg = "连接服务器超时!";
                    else if (msg.indexOf("Timeout") >= 0)
                        msg = "连接服务器超时!";
                    else if (msg.indexOf("refused") >= 0)
                        msg = "连接服务器异常!";
                    else if (msg.indexOf("No route") >= 0)
                        msg = "网络异常!";
                    else if (msg.indexOf("network") >= 0)
                        msg = "网络异常!";
                    else if (msg.indexOf("网络") >= 0)
                        msg = "网络异常!";
                    handler.sendMessage(handler.obtainMessage(1, msg));
                } finally {
                    mIsBusy = false;
                }
            }// end run
        }.start();// end thread

    }

    public interface IDaoCallback {
        public void exec(boolean success, Object obj);
    }

    public abstract void exec() throws Exception;

    /**
     * 提交数据到服务器 eg: Map<String, String> maps=new HashMap<String,String>();
     * maps.put("action", "putTMT"); maps.put("viIden", Const.vilden);//*访客标识
     * maps.put("comId", Const.comId); //*APP企业ID maps.put("guestId",
     * getUserNo());//openfire ID maps.put("actionTime",
     * ""+System.currentTimeMillis()/1000);//秒为单位 ... postData(maps);// 提交数据到服务器
     *
     * @param maps
     * @throws Exception
     */
    protected void postData(Map<String, String> maps) throws Exception {
        postData(maps, null);
    }

    protected void postData(Map<String, String> maps, String url)
            throws Exception {
        if (!NetUtils.getInstance(context).isNetworkConnected()) {// 无网络连接
            error("无网络连接!");
            ToastUtils.show(context, "无网络连接!");
            return;
        }
        if (url == null || url.length() == 0)
            url = base_url;
        Log.e("qizfeng", ">>>" + url + maps.toString());
        String res = HttpJsonUtils.post(url, maps);
        Log.e("qizfeng", ">>>返回结果:" + res);
        if (isNotEmpty(res)) {
            res = res.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            JSONObject json;
            try {
                json = new JSONObject(res);
            } catch (Exception e) {
                Log.e("qizfeng", "解析JSONObject失败:" + res);
                throw e;
            }
            String data = "";
            String status = "";
            String type = "";
            String code = "";
//			if (json.has("code")) {
//				data = "" + json.opt("data");
//				status = "" + json.opt("code");
//				code = "" + json.opt("code");
//				// String type = ""+json.opt("type");
//				type = "" + json.opt("data_type");
//			} else if (json.has("data")) {
            data = "" + json.opt("data");
            status = "" + json.opt("status");
//				if (json.has("type"))
//					type = "" + json.opt("type");
            analyseData(data, status);
//			}
//			else {
//				data = res;
//				analyseData(data);
//				analyseStatus(data);
//			}
            // 返回数据分析
//			if (isNotEmpty(data)) {
////				if (type == null || type.length() == 0) {
////					try {
////						analyseData(data);
////					} catch (Exception e) {
////						Lg.error("解析错误>>>>>" + data);
////						throw e;
////					}
////				} else {
////					try {
////						analyseTypeData(type, data);
////					} catch (Exception e) {
////						Lg.error("解析错误>>>>>" + data);
////						throw e;
////					}
////				}
//				analyseData(data);
//			}
//			else{
//			// 错误分析
////			if (!isEmpty(status)) {
//				try {
//					analyseStatus(status);
//				} catch (Exception e) {
//					Log.e("qizfeng","解析错误>>>>>" + status);
//					throw e;
//				}
//
//			}

            // code
            if (!isEmpty(code)) {
                try {
                    analyseCodeData(code, type, data);
                } catch (Exception e) {
                    Lg.error("解析错误>>>>>" + status);
                    throw e;
                }

            }
        } else {
            error("");
        }
    }

    /**
     * 返回数据分析 { "data": "提交完成", "status": { "succeed": 1 } }
     * <p>
     * { "status": { "succeed": 0, "error_code": 29, "error_desc":
     * "暂时无法连接数据库，请稍后尝试" } }
     * <p>
     * eg: JSONArray array=new JSONArray(data); JSONObject json=new
     * JSONObject(data);
     *
     * @param data
     * @throws JSONException
     */
    protected void analyseData(String data, String status) throws Exception {
        error("____analyseData/请重写数据分析-data:" + data);
        analyseStatus(status);
    }

    protected void analyseTypeData(String type, String data) throws Exception {
        // error("____analyseTypeData/请重写数据分析-data:"+data);
//		analyseData(data);
    }

    protected void analyseCodeData(String code, String type, String data)
            throws Exception {
//		analyseData(data);
    }

    /**
     * 错误分析
     *
     * @param status
     * @throws Exception
     */
    protected String analyseStatus(String status) throws Exception {
        if (status != null && !status.startsWith("{") && !status.endsWith("}")) {
            Log.e("qizfeng", status);
            if ("0".equals(status))
                throw new Exception(status);
            return status;
        }
        Log.e("qizfeng", "error:" + status);
        JSONObject json_status = new JSONObject(status);
//		JSONObject json_status = json.optJSONObject("status");
        String succeed = "" + json_status.opt("succeed");
        String error_code = "" + json_status.opt("error_code");
        String error_desc = "" + json_status.opt("error_desc");
        Log.e("qizfeng", "return succeed:" + succeed + "," + error_code + "," + error_desc);
        // if ("0".equals(succeed)) throw new Exception(error_desc);
//        if ("0".equals(succeed)) {
//        ToastUtils.show(context, error_desc);

//            Log.e("qifeng","status000>>>"+error_desc);
////			throw new Exception(error_desc);
//        } else {
//            // do nothing
//        }
        return error_desc;
    }


    /**
     * 取得json里面某个字段的值
     *
     * @param json
     * @param key
     * @return
     */
    protected String getJsonValue(JSONObject json, String key) {
        String res = json.optString(key);
        if (isEmpty(res)) {
            // 防止大小写不一样
            if (json.has(key.toUpperCase())) {
                res = json.optString(key.toUpperCase());
            } else {
                if (json.toString().toUpperCase().indexOf(key.toUpperCase()) > -1) {
                    error(json.toString() + " >>>getJsonValue/设计错误,没找到:" + key);
                }
            }
            if ("null".equals(res))
                res = "";
            return res;
        } else {
            if ("null".equals(res))
                res = "";
            return res;
        }
    }

    /**
     * RegisterDeviceToServerDao set it!
     */
    public String getAppName() {
        return ApkUtils.getPackageName(context);
    }

    public String getAppTitile() {
        return getAppName();
    }

    /**
     * 字符编码,防止url中的特殊字符传输导致错误
     *
     * @param value
     * @return
     */
    protected String encode(String value) {
        if (isEmpty(value))
            return "";
        else
            try {
                return URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                error(e);
                return "";
            }
    }

    protected int strToInt(String str) {
        if (isEmpty(str))
            return 0;
        else {
            try {
                return Integer.valueOf(str);
            } catch (Exception e) {
                Lg.error(e);
                return 0;
            }
        }
    }

    protected boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    protected boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0
                || "null".equals(value);
    }

    protected String getMsg(int id) {
        return context.getResources().getString(id);
    }

    protected void debug(String msg) {
        Lg.debug(this.getClass().getSimpleName() + ">>>" + msg);
    }

    protected void info(String msg) {
        Lg.info(this.getClass().getSimpleName() + ">>>" + msg);
    }

    protected void error(String msg) {
        Lg.error(this.getClass().getSimpleName() + "____" + msg);
    }

    public void error(Exception e) {
        Lg.error(this.getClass().getSimpleName() + "____" + e);
        Lg.error(e);
    }

}

package com.zipingfang.aihuan;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.google.gson.Gson;
import com.zipingfang.aihuan.bean.CityModel;
import com.zipingfang.aihuan.bean.DistrictModel;
import com.zipingfang.aihuan.bean.ProvinceModel;
import com.zipingfang.aihuan.bean.User;
import com.zipingfang.aihuan.utils.DialogUtils;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.RoundProgressDialog;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.XmlUtils;
import com.zipingfang.aihuan.wheel.XmlParserHandler;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 峰 on 2016/9/8.
 */
public class BaseActivity extends FragmentActivity {
    private Context mContext;
    private DialogUtils mDialog;
    private static List<Activity> mActivities;// activity集合
    public String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog = new DialogUtils(mContext);
        addActivity(this);
    }


    /**
     * 获取保存的用户id
     *
     * @param context
     * @return
     */
    public String getUserId(Context context) {
        String userId = "";
        try {
            userId = getUser(context).getMid();
        } catch (Exception e) {

        }
        return userId;
    }

    /**
     * 获取保存的用户名
     *
     * @param context
     * @return
     */
    public String getUserName(Context context) {
        String userName = "";
        try {
            userName = getUser(context).getUsername();
        } catch (Exception e) {

        }
        return userName;
    }

    /**
     * 保存缓存数据
     *
     * @param context
     * @param key     键
     * @param value   值
     */
    public void saveCache(Context context, String key, String value) {
        XmlUtils.saveToXml(context, key, value);
    }


    // 进度框
    private static RoundProgressDialog mRoundProgressDialog;

    /**
     * 显示进度对话框
     *
     * @param msg
     */
    public static void showLoading(Context context, String msg) {
        if (mRoundProgressDialog == null) {
            mRoundProgressDialog = RoundProgressDialog.getInstance(context, msg,
                    true, null);
        } else {
            mRoundProgressDialog.setMessage(msg);
        }

        if (!mRoundProgressDialog.isShowing()) {
            mRoundProgressDialog.show();
        }
    }

    /**
     * 取消进度对话框
     */
    public void hideLoading() {
        if (mRoundProgressDialog != null && mRoundProgressDialog.isShowing()) {
            mRoundProgressDialog.cancel();
            mRoundProgressDialog = null;
        }
//        Looper.loop();
    }

    /**
     * 清楚用户缓存
     */
    public static void clearUserCache(Context context) {
        XmlUtils.saveToXml(context, "user", "");
    }

    /**
     * 获取本地缓存的用户
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static User getUser(Context context) {
        User user = new User();
        try {
            String json = XmlUtils.getFromXml(context, "user", "");
//
//            JSONObject object = new JSONObject(json);
//            String mid = object.opt("mid") + "";
//            String username = object.opt("username") + "";
//            String email = object.opt("email") + "";
//            String requester = object.opt("requester") + "";
//            String gender = object.opt("gender") + "";
//            String birthday = object.opt("birthday") + "";
//            String avatar = object.optString("avatar");
//            String reg_time = object.opt("reg_time") + "";
//            String is_merchant = object.opt("is_merchant") + "";
//            String is_realperson = object.opt("is_realperson") + "";
//            String merchant_id = object.opt("merchant_id") + "";
//            String status = object.opt("status") + "";
//            String openid = object.opt("openid") + "";
//            String uptime = object.opt("uptime") + "";
//            String ttype = object.opt("ttype") + "";
//            String token = object.opt("token") + "";
//            String merchant_status = object.opt("merchant_status") + "";
//            String introduce = object.opt("introduce") + "";
//            String balance = object.opt("balance") + "";
//            String point = object.opt("point") + "";
//            user.setMid(mid);
//            user.setUsername(username);
//            user.setEmail(email);
//            user.setRequester(requester);
//            if ("-1".equals(gender))
//                user.setGender("未知");
//            else if ("0".equals(gender))
//                user.setGender("女");
//            else if ("1".equals(gender))
//                user.setGender("男");
//            user.setBirthday(birthday);
//            user.setAvatar(avatar);
//            user.setReg_time(reg_time);
//            user.setIs_merchant(is_merchant);
//            user.setMerchant_id(merchant_id);
//            user.setIs_realperson(is_realperson);
//            user.setStatus(status);
//            user.setOpenid(openid);
//            user.setUptime(uptime);
//            user.setTtype(ttype);
//            user.setToken(token);
//            user.setMerchant_status(merchant_status);
//            if (!StringUtils.isEmpty(introduce))
//                user.setIntroduce(introduce);
//            user.setBalance(balance);
//            user.setPoint(point);
            user =(User) GsonFactory.getInstanceByJson(User.class,json);
        } catch (Exception e) {

        }
        return user;
    }


    public static boolean isLogin(Context context) {
        boolean isLogin = !StringUtils.isEmpty(XmlUtils.getFromXml(context, "user", ""));
        return isLogin;
    }


    public static boolean isMerchant(Context context){
        boolean isMerchant = !"0".equals(getUser(context).getIs_merchant());
        return isMerchant;
    }

    /**
     * 获取activity集合数量
     *
     * @return
     */
    public static int getActivityCount() {
        if (mActivities != null) {
            return mActivities.size();
        }
        return 0;
    }

    /**
     * 保存activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new ArrayList<Activity>();
        }
        mActivities.add(activity);
    }

    /**
     * 根据className移除activity
     *
     * @param className
     */
    public static void removeActivity(String className) {
        if (mActivities != null) {
            for (int i = 0, len = mActivities.size(); i < len; i++) {
                if (mActivities.get(i).getClass().getName()
                        .equalsIgnoreCase(className)) {
                    mActivities.remove(mActivities.get(i));
                    break;
                }
            }
        }
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    /**
     * 关闭activity
     */
    public static void finishActivity() {
        if (mActivities == null) {
            return;
        }
        for (int i = mActivities.size() - 1; i >= 0; i--) {
            mActivities.get(i).finish();
            mActivities.remove(i);
        }
        mActivities = null;
    }

    public static void checkPermission() {

    }

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


}

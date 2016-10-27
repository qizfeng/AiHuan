package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/10.
 */
public class IndexBannerServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;

    public List<BannerInfo> headerBanner = new ArrayList<>();
    public List<BannerInfo> headerLine = new ArrayList<>();
    public List<BannerInfo> middleBanner = new ArrayList<>();

    public IndexBannerServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_INDEX_AD_BANNER;
        Map<String, String> maps = new HashMap<String, String>();
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                Log.e("qizfeng", "banner:" + res);
                JSONObject object = new JSONObject(data);
                if (!StringUtils.isEmpty(object.opt("headbanner") + "")) {
                    headerBanner = GsonFactory.jsonToArrayList(object.opt("headbanner") + "", BannerInfo.class);
                }
                if (!StringUtils.isEmpty(object.opt("headline") + "")) {
                    headerLine = GsonFactory.jsonToArrayList(object.opt("headline") + "", BannerInfo.class);
                }
                if (!StringUtils.isEmpty(object.opt("middlebanner") + "")) {
                    middleBanner = GsonFactory.jsonToArrayList(object.opt("middlebanner") + "", BannerInfo.class);
                }
                isSucc = true;
            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }

    }
}

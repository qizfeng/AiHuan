package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.asynthttp.AsyncHttpResponseHandler;
import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/9/26.
 */
public class IndexServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public List<Goods> secKillList = new ArrayList<>();
    public List<Goods> suggestList = new ArrayList<>();
    public List<Party> aucList = new ArrayList<>();

    public List<Party> upPartList = new ArrayList<>();
    public List<Party> downPartList = new ArrayList<>();

    public int type = 1;//1 用户 2 商家

    public IndexServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_INDEX_DATA;
        Map<String, String> maps = new HashMap<String, String>();
        if (type == 1) {
            maps.put("sn", "10");
            maps.put("pn", "10");
            maps.put("rn", "20");
        } else if (type == 2) {
            url = Constants.URL_INDEX_MEMBER_DATA;
            maps.put("pn", "10");
        }
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                JSONObject object = new JSONObject(res);
                if (type == 1) {
                    if (!StringUtils.isEmpty(object.opt("seckill") + "")) {
                        secKillList = GsonFactory.jsonToArrayList(object.opt("seckill") + "", Goods.class);
                    }
                    if (!StringUtils.isEmpty(object.opt("recommend") + "")) {
                        suggestList = GsonFactory.jsonToArrayList(object.opt("recommend") + "", Goods.class);
                    }
                    if (!StringUtils.isEmpty(object.opt("party") + "")) {
                        aucList = GsonFactory.jsonToArrayList(object.opt("party") + "", Party.class);
                    }
                }else if(type==2){
                    if (!StringUtils.isEmpty(object.opt("up_party") + "")) {
                        upPartList = GsonFactory.jsonToArrayList(object.opt("up_party") + "", Party.class);
                    }
                    if (!StringUtils.isEmpty(object.opt("down_party") + "")) {
                        downPartList = GsonFactory.jsonToArrayList(object.opt("down_party") + "", Party.class);
                    }
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

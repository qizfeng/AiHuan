package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Active;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozhiyu on 16/10/19.
 */

public class ActivePartyServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public String party_type;//party,seckill,sale
    public String party_id;
    public String mid;
    public boolean isSucc;
    public Party partyInfo = new Party();
    public List<Goods> goodsList = new ArrayList<>();

    public ActivePartyServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ACTIVE_PARTY_INFO;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("party_type", party_type);
        maps.put("party_id",party_id);
        maps.put("mid",mid);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                isSucc = true;
                JSONObject object = new JSONObject(res);
                if(object.has("party_info")){
                    partyInfo = (Party) GsonFactory.getInstanceByJson(Party.class,object.opt("party_info")+"");
                }

                if(object.has("goods_lists")){
                    goodsList = GsonFactory.jsonToArrayList(object.opt("goods_lists").toString(),Goods.class);
                }

            } else {
                desc = analyseStatus(status);
                isSucc = false;
            }
        } catch (Exception e) {
            Log.e("qizfeng", "error:" + e.toString());
        }
    }


}



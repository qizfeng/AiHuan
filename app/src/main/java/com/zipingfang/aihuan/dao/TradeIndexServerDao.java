package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Auction;
import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.bean.Trade;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class TradeIndexServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public List<BannerInfo> bannerInfos = new ArrayList<>();
    public List<Trade> partyList = new ArrayList<>();
    public List<Trade> secKillList = new ArrayList<>();
    public List<Trade> saleList = new ArrayList<>();
    public List<Goods> suggestList = new ArrayList<>();

    public TradeIndexServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_TRADE_INDEX;
        Map<String, String> maps = new HashMap<String, String>();
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            Log.e("trade", "trade:"+ res);
            if (!StringUtils.isEmpty(res)) {
                JSONObject object = new JSONObject(res);
                if (!StringUtils.isEmpty(object.opt("headimage") + "")) {
                    bannerInfos = GsonFactory.jsonToArrayList(object.opt("headimage") + "", BannerInfo.class);
                }
                if (!StringUtils.isEmpty(object.opt("party_category") + "")) {
                    partyList = GsonFactory.jsonToArrayList(object.opt("party_category") + "", Trade.class);
                }
                if (!StringUtils.isEmpty(object.opt("seckill_category") + "")) {
                    secKillList = GsonFactory.jsonToArrayList(object.opt("seckill_category") + "", Trade.class);
                }
                if (!StringUtils.isEmpty(object.opt("sale_category") + "")) {
                    saleList = GsonFactory.jsonToArrayList(object.opt("sale_category") + "", Trade.class);
                }
                if (!StringUtils.isEmpty(object.opt("hostgoods") + "")) {
                    suggestList = GsonFactory.jsonToArrayList(object.opt("hostgoods") + "", Goods.class);
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

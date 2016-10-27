package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.bean.Party;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/8.
 */
public class JoinPartyServerDao extends BaseDao {
    public String mid;
    public String desc;
    public String res;
    public boolean isSucc;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    public List<Party> mData = new ArrayList<>();

    public JoinPartyServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_JOIN_PARTY_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        Log.e("qizfeng", "joinParty:" + data);
               /*  "party_id":"148",
                "goods_id":"139",
                "member_id":"161",
                "payment_time":"1468487253",
                "payment_status":"1",//支付状态：0未支付，1已支付，2支付失败
                "party_start_time":"1468563360",
                "party_end_time":"1474369237",
                "cover":"http://182.92.152.135:85/upload/0K2A3651.JPG",
                "auction_id":"139",
                "auction_name":"测试拍品11",
                "start_status":"off",//状态：已结束，on未结束
                "max_price":2000000,//最高出价
                //未结束状态有range_time，值是距离结束时间
                "price_desc":"成交价"*/
        if (!StringUtils.isEmpty(res)) {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.optJSONObject(i);
                Party party = new Party();
                party.setParty_id(obj.opt("party_id")+"");
                party.setGoods_id(obj.opt("goods_id")+"");
                party.setMember_id(obj.opt("member_id")+"");
                party.setParty_end_time(obj.opt("party_end_time")+"");
                party.setPayment_time(obj.opt("payment_time")+"");
                party.setPayment_status(obj.opt("payment_status")+"");
                party.setParty_start_time(obj.opt("party_start_time")+"");
                party.setCover(obj.opt("cover")+"");
                party.setAuction_id(obj.opt("auction_id")+"");
                party.setAuction_name(obj.opt("auction_name")+"");
                party.setStart_status(obj.opt("start_status")+"");
                party.setMax_price(obj.opt("max_price")+"");
                if(obj.has("range_time"))
                    party.setRange_time(obj.opt("range_time")+"");
                party.setPrice_desc(obj.opt("price_desc")+"");
                mData.add(party);
            }
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}

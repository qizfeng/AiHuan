package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Address;
import com.zipingfang.aihuan.bean.Wallet;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qizfeng on 16/10/20.
 */

public class WalletIndexServerDao extends BaseDao{
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public Wallet wallet = new Wallet();
    public WalletIndexServerDao(Context context) {
        super(context);
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_WALLET_INDEX;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", mid);
        postData(maps, url);

    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                wallet = (Wallet) GsonFactory.getInstanceByJson(Wallet.class,res);
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

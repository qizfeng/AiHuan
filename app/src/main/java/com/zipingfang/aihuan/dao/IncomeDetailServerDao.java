package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.bean.IncomeDetail;
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
 * Created by zipingfang on 16/10/20.
 */

public class IncomeDetailServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public List<IncomeDetail> incomeDetailList = new ArrayList<>();
    public String mid;
    public int type = 1;//0全部 1 收入 2 支出

    public IncomeDetailServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_INCOME_DETAIL;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid","70");
        maps.put("type",type+"");
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                incomeDetailList = GsonFactory.jsonToArrayList(res,IncomeDetail.class);
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


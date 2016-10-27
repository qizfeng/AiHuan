package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Attention;
import com.zipingfang.aihuan.bean.Goods;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/12.
 */

public class AllGoodsServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public int pageSize = 10;
    public int pageIndex;
    public String category_id;
    public List<Goods> goodsList = new ArrayList<>();

    public AllGoodsServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_GOODS_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("category", category_id);
        maps.put("page", pageIndex + "");
        maps.put("row", pageSize + "");
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                goodsList = GsonFactory.jsonToArrayList(res, Goods.class);
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


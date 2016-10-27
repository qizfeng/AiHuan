package com.zipingfang.aihuan.dao;

import android.content.Context;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.bean.Express;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zipingfang on 16/10/26.
 */

public class ExpressesServerDao extends BaseDao {
    public String desc;
    public String res;
    public boolean isSucc;
    private Context mContext;
    public List<Express> expresses = new ArrayList<>();

    public ExpressesServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_EXPRESSES;
        Map<String, String> maps = new HashMap<String, String>();
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        if (!StringUtils.isEmpty(res)) {
            expresses.addAll(GsonFactory.jsonToArrayList(data, Express.class));
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}


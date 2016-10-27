package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.asynthttp.AsyncHttpResponseHandler;
import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.bean.Active;
import com.zipingfang.aihuan.bean.Address;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozhiyu on 16/10/18.
 */

public class ActiveListServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public String party_type;//party,seckill,sale
    public String status;//专场状态 -1:全部,1:未发布,2:报名中,3:等待开拍(审核完毕),4:正在进行拍卖的专场,5:拍卖结束的专场
    public String category;//分类id,全部传-1,其余根据接口返回的分类id传
    public String pageIndex;
    public String pageSize = "15";
    public List<Active> activeList = new ArrayList<>();
    public boolean isSucc;

    public ActiveListServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ACTIVE_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("party_type", party_type);
        if (StringUtils.isEmpty(category))
            maps.put("category", "-1");
        else
            maps.put("category", category);
        if (!StringUtils.isEmpty(status))
            maps.put("status", status);
        else
            maps.put("status", "-1");
        maps.put("page",pageIndex);
        maps.put("row",pageSize);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                activeList = GsonFactory.jsonToArrayList(res,Active.class);
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


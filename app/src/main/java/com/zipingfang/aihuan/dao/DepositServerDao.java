package com.zipingfang.aihuan.dao;

import android.content.Context;

import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.bean.Deposit;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/8.
 */
public class DepositServerDao extends BaseDao {
    public String mid;
    public String type;//1已退款，2已支付，3已扣除，4全部
    public String desc;
    public String res;
    public boolean isSucc;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    public List<Deposit> mData = new ArrayList<>();

    public DepositServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_DEPOSIT_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", "161");
        maps.put("type",type);
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) throws Exception {
        res = data;
        if (!StringUtils.isEmpty(res)) {
            mData.addAll(GsonFactory.jsonToArrayList(data, Deposit.class));
            isSucc = true;
        } else {
            desc = analyseStatus(status);
            isSucc = false;
        }
    }
}

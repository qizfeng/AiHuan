package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.Message;
import com.zipingfang.aihuan.bean.MessageIndex;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 峰 on 2016/10/9.
 */
public class MessageListServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String mid;
    public List<Message> mData = new ArrayList<>();

    public MessageListServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_MESSAGE_LIST;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("mid", "101");
        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                mData = GsonFactory.jsonToArrayList(res, Message.class);
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

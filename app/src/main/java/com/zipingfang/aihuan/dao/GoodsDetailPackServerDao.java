package com.zipingfang.aihuan.dao;

import android.content.Context;
import android.util.Log;

import com.zipingfang.aihuan.bean.BannerInfo;
import com.zipingfang.aihuan.bean.GoodsDetail;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.GsonFactory;
import com.zipingfang.aihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozhiyu on 16/10/19.
 */

public class GoodsDetailPackServerDao extends BaseDao {
    private Context mContext;
    public String desc;
    public String res;
    public boolean isSucc;
    public String goods_id;
    public String party_id;
    public String party_type;
    public GoodsDetail goodsDetail = new GoodsDetail();
    public List<BannerInfo> bannerInfoList = new ArrayList<>();

    public GoodsDetailPackServerDao(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void exec() throws Exception {
        String url = Constants.URL_ACTIVE_PACK_DETAIL;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("party_type",party_type);
        maps.put("party_id",party_id);
        maps.put("goods_id",goods_id);

        postData(maps, url);
    }

    @Override
    protected void analyseData(String data, String status) {
        try {
            res = data;
            if (!StringUtils.isEmpty(res)) {
                goodsDetail = (GoodsDetail) GsonFactory.getInstanceByJson(GoodsDetail.class, res);
                if (!StringUtils.isEmpty(goodsDetail.getImg_1())) {
                    BannerInfo bannerInfo = new BannerInfo();
                    bannerInfo.setImg(goodsDetail.getImg_1());
                    bannerInfoList.add(bannerInfo);
                }
                if (!StringUtils.isEmpty(goodsDetail.getImg_2())) {
                    BannerInfo bannerInfo = new BannerInfo();
                    bannerInfo.setImg(goodsDetail.getImg_2());
                    bannerInfoList.add(bannerInfo);
                }
                if (!StringUtils.isEmpty(goodsDetail.getImg_3())) {
                    BannerInfo bannerInfo = new BannerInfo();
                    bannerInfo.setImg(goodsDetail.getImg_3());
                    bannerInfoList.add(bannerInfo);
                }
                if (!StringUtils.isEmpty(goodsDetail.getImg_4())) {
                    BannerInfo bannerInfo = new BannerInfo();
                    bannerInfo.setImg(goodsDetail.getImg_4());
                    bannerInfoList.add(bannerInfo);
                }
                if (!StringUtils.isEmpty(goodsDetail.getImg_5())) {
                    BannerInfo bannerInfo = new BannerInfo();
                    bannerInfo.setImg(goodsDetail.getImg_5());
                    bannerInfoList.add(bannerInfo);
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

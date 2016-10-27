package com.zipingfang.aihuan.constants;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.StreamCorruptedException;
import java.io.StringReader;

/**
 * API
 * 常量
 *
 * @author 峰
 */
public class Constants {
    public static final String BASE_URL = "http://aihuan.ccifc.cn/";

    /**
     * 注册
     */
    public static final String URL_REGISTER = BASE_URL + "index.php?s=/api/Register/index";

    /**
     * 忘记密码
     */
    public static final String URL_FORGET_PASS = BASE_URL + "index.php?s=/api/Login/seekPass";
    /**
     * 登录
     */
    public static final String URL_LOGIN = BASE_URL + "index.php?s=/api/Login/index";

    /**
     * 获取验证码
     */
    public static final String URL_VERIFY = BASE_URL + "index.php?s=/api/Register/getCode";

    /**
     * 注册协议
     */
    public static final String URL_REG_PROTOCOL = BASE_URL + "index.php?s=/api/agreement/regagreement";


    /**
     * 关注列表
     */
    public static final String URL_ATTENTION_LIST = BASE_URL + "index.php?s=/api/Attention/getList";

    /**
     * 会员信息
     */
    public static final String URL_MEMBER_INFO = BASE_URL + "index.php?s=/api/Member/memberinfo";

    /**
     * 修改用户信息
     */
    public static final String URL_EDIT_MEMBER_INFO = BASE_URL + "index.php?s=/api/member/updateInfo";

    /**
     * 修改用户头像
     */
    public static final String URL_EDIT_MEMBER_AVATAR = BASE_URL + "index.php?s=/api/member/updateAvatar";


    /**
     * 启动页广告
     */
    public static final String URL_GUIDE_IMG = BASE_URL + "index.php?s=/api/home/startImage";

    /**
     * 首页顶部banner
     */
    public static final String URL_INDEX_AD_BANNER = BASE_URL + "index.php?s=/api/Home/getAdBanner";

    /**
     * 首页用户列表数据
     */
    public static final String URL_INDEX_DATA = BASE_URL + "index.php?s=/api/Home/getUserAuctionData";

    /**
     * 首页商家列表数据
     */
    public static final String URL_INDEX_MEMBER_DATA = BASE_URL + "index.php?s=/api/Home/getMerchantAuctionData";

    /**
     * 参拍列表
     */
    public static final String URL_JOIN_PARTY_LIST = BASE_URL + "index.php?s=/api/Participation/joinParty";

    /**
     * 保证金列表
     */
    public static final String URL_DEPOSIT_LIST = BASE_URL + "index.php?s=/api/Participation/deposit";

    /**
     * 订单列表
     */
    public static final String URL_ORDER_LIST = BASE_URL + "index.php?s=/api/Order/orderList";


    /**
     * 订单详情
     */
    public static final String URL_ORDER_DETAIL = BASE_URL + "index.php?s=/api/Order/orderDetail";

    /**
     * 取消关注
     */
    public static final String URL_DEL_ATTENTION = BASE_URL + "index.php?s=/api/Attention/delAttention";

    /*
    *消息中心
    */
    public static final String URL_MESSAGE_INDEX = BASE_URL + "index.php?s=/api/Settings/messageIndex";

    /**
     * 消息列表
     */
    public static final String URL_MESSAGE_LIST = BASE_URL + "index.php?s=/api/Settings/messageList";

    /**
     * 收货地址列表
     */
    public static final String URL_ADDRESS_LIST = BASE_URL + "index.php?s=/api/Order/receiveAddress";

    /**
     * 设置默认地址
     */
    public static final String URL_SET_DEFAULT_ADDR = BASE_URL + "index.php?s=/api/Order/setDefaultAddress";


    /**
     * 添加/修改收货地址
     */
    public static final String URL_ADD_ADDR = BASE_URL + "index.php?s=/api/Order/addReceiveAddress";


    /**
     * 收货地址详情
     */
    public static final String URL_ADDRESS_DETAIL = BASE_URL + "index.php?s=/api/Order/editReceiveAddress";


    /**
     * 删除收货地址
     */
    public static final String URL_ADDRESS_DELETE = BASE_URL + "index.php?s=/api/Order/delAddress";
    /**
     * 上传设备alias
     */
    public static final String URL_SET_ALIAS = BASE_URL + "index.php?s=/api/Jpush/getClient";

    /**
     * 商品分类
     */
    public static final String URL_CATEGORY_LIST = BASE_URL + "index.php?s=/api/Home/category";


    /**
     * 商品列表
     */
    public static final String URL_GOODS_LIST = BASE_URL + "index.php?s=/api/Home/generalGoodsList";


    /**
     * 专场商品详情
     */
    public static final String URL_GOODS_DETIAL = BASE_URL + "index.php?s=/api/Home/partyGoodsInfo";

    /**
     * 出价记录
     */
    public static final String URL_PRICE_RECORD = BASE_URL + "index.php?s=/api/Home/proceedRecord";

    /**
     * 普通购买商品详情
     */
    public static final String URL_GOODS_DETAIL_NORMAL = BASE_URL + "index.php?s=/api/Home/generalGoodsInfo";

    /**
     * 交易大厅首页
     */
    public static final String URL_TRADE_INDEX = BASE_URL + "index.php?s=/api/Hall/tradingHall";

    /**
     * 活动专场列表
     */
    public static final String URL_ACTIVE_LIST = BASE_URL + "index.php?s=/api/Hall/activeList";

    /**
     * 专场商品信息
     */
    public static final String URL_ACTIVE_PARTY_INFO = BASE_URL + "index.php?s=/api/Home/activeParty";

    /**
     * 打包拍卖详情
     */
    public static final String URL_ACTIVE_PACK_DETAIL = BASE_URL + "index.php?s=/api/Home/partyGoodsInfo";

    /**
     * 添加关注
     */
    public static final String URL_ATTENTION = BASE_URL + "index.php?s=/api/Home/attention";

    /**
     * 我的钱包首页
     */
    public static final String URL_WALLET_INDEX = BASE_URL + "index.php?s=/api/Wallet/walletIndex";


    /**
     * 调起ping++
     */
    public static final String URL_PAY_PINGXX = BASE_URL + "index.php?s=/Api/Pay/pay";


    /**
     * 收支明细
     */
    public static final String URL_INCOME_DETAIL = BASE_URL + "index.php?s=/api/Wallet/account";

    /**
     * 儲值卡列表
     */
    public static final String URL_VALUE_CARD_LIST = BASE_URL+"index.php?s=/api/Wallet/payCardList";


    /**
     * 购买储值卡
     */
    public static final String URL_VALUE_CARD_BUY = BASE_URL+"index.php?s=/api/Wallet/buyPayCard";

    /**
     * 我的储值卡列表
     */
    public static final String URL_VALUE_CARD_MY=BASE_URL+"index.php?s=/api/Wallet/myPayCard";

    /**
     * 积分详细
     */
    public static final String URL_POINT_DETAIL=BASE_URL+"index.php?s=/api/Wallet/point";

    /**
     * 优惠券列表
     */
    public static final String URL_COUPON_LIST = BASE_URL+"index.php?s=/api/Wallet/coupon";

    /**
     * 搜索
     */
    public static final String URL_SERACH = BASE_URL+"index.php?s=/api/Hall/search";

    /**
     * 上传文件接口
     */
    public static final String URL_UPLOAD_FILE = BASE_URL+"index.php?s=/api/Merchant/uploadFiles";

    /**
     * 提交签约信息
     */
    public static final String URL_DO_SIGN = BASE_URL+"index.php?s=/api/Merchant/addMerchant";

    /**
     * 审核状态信息
     */
    public static final String URL_SIGN_STATUS= BASE_URL+"index.php?s=/api/Merchant/merchantInfo";

    /**
     * 店铺数量
     */
    public static final String URL_SHOP_COUNT = BASE_URL+"index.php?s=/api/Store/storeIndex";

    /**
     * 关于我们
     */
    public static final String URL_ABOUT =BASE_URL+"index.php?s=/api/Settings/aboutUs/aboutUs";

    /**
     * 快递公司列表
     */
    public static final String URL_EXPRESSES = BASE_URL+"index.php?s=/api/Store/sendCompany";

    /**
     * 店铺管理订单列表
     */
    public static final String URL_SHOPMANAGER_ORDER=BASE_URL+"index.php?s=/api/Store/storeOrderList/";

    /**
     * 店铺管理商品列表
     */
    public static final String URL_SHOPMANAGER_GOODS = BASE_URL+"index.php?s=/api/Store/storeGoodsList/";

    /**
     * 店铺商品上下架
     */
    public static final String URL_UP_DOWN=BASE_URL+"index.php?s=/api/Store/changeStatus";

    /**
     * 工程名
     */
    public static String projectName = "aihuan";

    public static String WEBVIEW_URL = "webViewUrl";

    /**
     * true：调试模式，打印详细的log
     */
    public static boolean debug = false;
    public static boolean info = false;
    public static boolean error = false;


    /**
     * 取得SD卡的路径
     *
     * @return
     */
    public static String SD_PATH = null;

    public static String getSDCardPath(Context context) {
        if (SD_PATH != null) return SD_PATH;

        if (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();

            SD_PATH = path.toString(); //只是SDCARD才给赋值
            return SD_PATH;
        } else {
            SD_PATH = context.getFilesDir().toString();//data/data/com..../files
            return SD_PATH;
        }
    }//end getSDCardPath

    public static String categoryId = "";

    public static int category_index = 0;
    /**
     * --=====================================================================
     * 建立工程目录   getSDCardPath()+File.separator+projectName;
     * --=====================================================================
     */
    private static String projectPath = null; //下面会赋值

    public static String getProjectPath(Context context) {
        if (SD_PATH != null && projectPath != null) return projectPath;
        else {
            projectPath = getSDCardPath(context) + File.separator + projectName;
            File path = new File(projectPath); //建立工程目录
            if (!path.exists()) {
                path.mkdirs();
            }
            return projectPath;
        }
    }

    /**
     * 照片/视频
     */
    public static final int PHOTO_MODEL_TAKE = 101;
    public static final int VIDEO_MODEL_TAKE = 110;
    /**
     * 本地相册
     */
    public static final int PHOTO_MODEL_ALBUM = 102;
    public static final int PHOTO_MODEL_CROP = 103;
    public static final String PHOTO_TEMP_SUFFIX = ".jpg";
    /**
     * 发布最大图片张数
     */
    public static final int IMG_COUNT_MAX = 5;
    /**
     * 图片压缩最大宽度
     */
    public static final int IMG_ZOOM_WIDTH_MAX = 640;
    /**
     * 图片压缩最大高度
     */
    public static final int IMG_ZOOM_HEIGHT_MAX = 960;
    /**
     * 图片压缩质量
     */
    public static final int IMG_ZOOM_QUALITY = 80;
}

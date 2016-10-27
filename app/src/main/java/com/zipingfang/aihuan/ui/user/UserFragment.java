package com.zipingfang.aihuan.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.WebViewActivity;
import com.zipingfang.aihuan.bean.User;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.UserInfoServerDao;
import com.zipingfang.aihuan.ui.signature.SignStatusCompanyActivity;
import com.zipingfang.aihuan.ui.signature.SignStatusPersonActivity;
import com.zipingfang.aihuan.ui.signature.SignatureIndex;
import com.zipingfang.aihuan.ui.user.shopmanager.ShopManagerIndexActivity;
import com.zipingfang.aihuan.ui.user.wallet.WalletIndexActivity;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.ElasticScrollView;

/**
 * Created by 峰 on 2016/9/18.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ElasticScrollView scrollView;
    private TextView tv_wallet;//钱包
    //    private TextView tv_concern;//关注
//    private TextView tv_join;//参拍
//    private TextView tv_deposit;//保证金
//    private TextView tv_order;//订单
    private LinearLayout ll_address_manager;//地址管理
    private LinearLayout ll_sign;//商户签约
    private LinearLayout ll_shop_manager;//店铺管理
    private LinearLayout ll_setting;//设置
    private LinearLayout ll_about;//关于

    private ImageView iv_header;

    private TextView tv_attention;//关注数
    private TextView tv_join;//参拍数
    private TextView tv_deposit;//保证金数
    private TextView tv_order;//订单数
    private TextView tv_shop_status;//审核状态
    private TextView tv_msg;//消息数
    private TextView tv_name;
    private BaseActivity base;
    private LinearLayout ll_share;
    private TextView tv_data;
    private LinearLayout ll_wallet;
    private TextView tv_user_category;
    private TextView tv_user_level;
    private LinearLayout ll_user_is_login;
    private ImageView iv_msg;
    private User mData = new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zpf_fragment_user, null);
        initView();
        return view;
    }

    private void initView() {
        base = (BaseActivity) getActivity();
        scrollView = (ElasticScrollView) view.findViewById(R.id.scrollView);
        ll_wallet = (LinearLayout) view.findViewById(R.id.ll_wallet);
        iv_header = (ImageView) view.findViewById(R.id.iv_header);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);
//        ll_concern = (LinearLayout) view.findViewById(R.id.ll_concern);
//        ll_join = (LinearLayout) view.findViewById(R.id.ll_join);
//        ll_deposit = (LinearLayout) view.findViewById(R.id.ll_deposit);
//        ll_order = (LinearLayout) view.findViewById(R.id.ll_order);
        ll_address_manager = (LinearLayout) view.findViewById(R.id.ll_address_manager);
        ll_sign = (LinearLayout) view.findViewById(R.id.ll_sign);
        ll_shop_manager = (LinearLayout) view.findViewById(R.id.ll_shop_manager);
        ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
        ll_about = (LinearLayout) view.findViewById(R.id.ll_about);

        tv_attention = (TextView) view.findViewById(R.id.tv_attention);
        tv_join = (TextView) view.findViewById(R.id.tv_join);
        tv_deposit = (TextView) view.findViewById(R.id.tv_deposit);
        tv_order = (TextView) view.findViewById(R.id.tv_order);
        tv_shop_status = (TextView) view.findViewById(R.id.tv_status);
//        tv_msg = (TextView) view.findViewById(R.id.tv_msg_count);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
        tv_data = (TextView) view.findViewById(R.id.tv_data);

        ll_user_is_login = (LinearLayout) view.findViewById(R.id.ll_user_is_login);
        tv_user_category = (TextView) view.findViewById(R.id.tv_user_category);
        tv_user_level = (TextView) view.findViewById(R.id.tv_user_level);
        iv_msg = (ImageView) view.findViewById(R.id.iv_msg);
        bindListener();
//        loadData();
    }


    private void bindListener() {
        tv_data.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        tv_attention.setOnClickListener(this);
        tv_join.setOnClickListener(this);
        tv_deposit.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        ll_address_manager.setOnClickListener(this);
        ll_sign.setOnClickListener(this);
        ll_shop_manager.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        tv_data.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        ll_sign.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                loadData();
            } catch (Exception e) {

            }
        }
    }

    private void loadData() {
        if (base.isLogin(getActivity())) {
            final UserInfoServerDao userInfoDao = new UserInfoServerDao(getActivity());
            userInfoDao.mid = base.getUserId(getActivity());
            userInfoDao.loadData(new BaseDao.IDaoCallback() {
                @Override
                public void exec(boolean success, Object obj) {
                    if (success) {
                        mData = userInfoDao.mUserInfo;
                        tv_name.setText(base.getUserName(getActivity()));
                        ImageLoader.getInstance().displayImage(mData.getAvatar(), iv_header, ImageLoaderConfig.normal);
                        tv_data.setText("个人资料");
                        ll_user_is_login.setVisibility(View.VISIBLE);
                        String is_merchant = mData.getIs_merchant();
                        if ("0".equals(is_merchant))
                            tv_user_category.setText("普通用户");
                        else if ("1".equals(is_merchant))
                            tv_user_category.setText("个人商户");
                        else if ("2".equals(is_merchant))
                            tv_user_category.setText("企业商户");
                        if (StringUtils.isEmpty(mData.getMerchant_status()))
                            tv_shop_status.setText("未审核");
                        else if ("-1".equals(mData.getMerchant_status())) {
                            tv_shop_status.setText("未审核");
                        } else if ("0".equals(mData.getMerchant_status())) {
                            tv_shop_status.setText("审核失败");
                        } else if ("1".equals(mData.getMerchant_status())) {
                            tv_shop_status.setText("已审核");
                        } else if ("2".equals(mData.getMerchant_status())) {
                            tv_shop_status.setText("审核中");
                        }
                    } else {
                    }
                }
            });
        } else {
            tv_data.setText("去登录");
            ll_user_is_login.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_data:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_wallet:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), WalletIndexActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_attention:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), MyAttentionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_join:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), JoinPartyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_deposit:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), DepositActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_order:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), OrderListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_address_manager:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), AddressListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_shop_manager:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if ("0".equals(mData.getIs_merchant())) {
                        ToastUtils.show(getActivity(), "当前不是商家身份,无法进行此操作");
                    } else {
                        intent.setClass(getActivity(), ShopManagerIndexActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_sign:
                if ("0".equals(mData.getIs_merchant())) {
                    intent.setClass(getActivity(), SignatureIndex.class);
                } else if ("1".equals(mData.getIs_merchant())) {
                    if (StringUtils.isEmpty(mData.getMerchant_status()) || "-1".equals(mData.getMerchant_status())) {
                        intent.setClass(getActivity(), SignatureIndex.class);
                    } else {
                        intent.setClass(getActivity(), SignStatusPersonActivity.class);
                    }
                } else if ("2".equals(mData.getIs_merchant())) {
                    if (StringUtils.isEmpty(mData.getMerchant_status()) || "-1".equals(mData.getMerchant_status())) {
                        intent.setClass(getActivity(), SignatureIndex.class);
                    } else {
                        intent.setClass(getActivity(), SignStatusCompanyActivity.class);
                    }
                }
                startActivity(intent);
                break;
            case R.id.ll_setting:
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_about:
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.WEBVIEW_URL,Constants.URL_ABOUT);
                startActivity(intent);
                break;
            case R.id.iv_msg:
                if (!base.isLogin(getActivity())) {
                    ToastUtils.show(getActivity(), "请先登录");
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), MessagesActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }
}

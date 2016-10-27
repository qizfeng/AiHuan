package com.zipingfang.aihuan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.UploadAliasServerDao;
import com.zipingfang.aihuan.receiver.JPushUtil;
import com.zipingfang.aihuan.ui.index.IndexFragment;
import com.zipingfang.aihuan.ui.signature.SignatureIndex;
import com.zipingfang.aihuan.ui.trade.TradeFragment;
import com.zipingfang.aihuan.ui.user.DeleteDialog;
import com.zipingfang.aihuan.ui.user.LoginActivity;
import com.zipingfang.aihuan.ui.user.UserFragment;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private IndexFragment indexFragment = new IndexFragment();
    private TradeFragment tradeFragment = new TradeFragment();
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private UserFragment userFragment = new UserFragment();
    private LinearLayout ll_user;
    private LinearLayout ll_trade;
    private LinearLayout ll_index;
    private ImageView iv_index, iv_trade, iv_user;
    private TextView tv_index, tv_trade, tv_user;
    private LinearLayout ll_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_main);
        initView();
        initFragment();
        FragmentAdapter adapter = new FragmentAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        Log.e("qizfeng", "push：" + JPushUtil.getRegId(this));
        JPushUtil.setAliasAndTags(true, this, JPushUtil.getRegId(this), null, null);
        UploadAliasServerDao serverDao = new UploadAliasServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.alias = JPushUtil.getRegId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {

            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        ll_user.setOnClickListener(this);
        ll_trade = (LinearLayout) findViewById(R.id.ll_house);
        ll_trade.setOnClickListener(this);
        ll_index = (LinearLayout) findViewById(R.id.ll_index);
        ll_index.setOnClickListener(this);
        iv_index = (ImageView) findViewById(R.id.iv_index);
        iv_trade = (ImageView) findViewById(R.id.iv_house);
        iv_user = (ImageView) findViewById(R.id.iv_user);

        tv_index = (TextView) findViewById(R.id.tv_index);
        tv_trade = (TextView) findViewById(R.id.tv_house);
        tv_user = (TextView) findViewById(R.id.tv_user);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ll_bottom.setAlpha(.5f);
    }

    private void initFragment() {
        fragments.add(new IndexFragment());
        fragments.add(new TradeFragment());
        fragments.add(new UserFragment());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_user:
                viewPager.setCurrentItem(2);
                iv_user.setImageResource(R.mipmap.tabbar_me_s);
                iv_trade.setImageResource(R.mipmap.tabbar_trade_n);
                iv_index.setImageResource(R.mipmap.tabbar_home_n);

                tv_user.setTextColor(getResources().getColor(R.color.tabbar_select));
                tv_trade.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                tv_index.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                break;
            case R.id.ll_house:
                if (isLogin(MainActivity.this)) {

                    viewPager.setCurrentItem(1);

                    iv_user.setImageResource(R.mipmap.tabbar_me_n);
                    iv_trade.setImageResource(R.mipmap.tabbar_trade_s);
                    iv_index.setImageResource(R.mipmap.tabbar_home_n);

                    tv_user.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                    tv_trade.setTextColor(getResources().getColor(R.color.tabbar_select));
                    tv_index.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                    if (isMerchant(MainActivity.this)) {

                        viewPager.setCurrentItem(1);

                        iv_user.setImageResource(R.mipmap.tabbar_me_n);
                        iv_trade.setImageResource(R.mipmap.tabbar_trade_s);
                        iv_index.setImageResource(R.mipmap.tabbar_home_n);

                        tv_user.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                        tv_trade.setTextColor(getResources().getColor(R.color.tabbar_select));
                        tv_index.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                    } else {


                        DeleteDialog.Builder builder = new DeleteDialog.Builder(MainActivity.this);
                        builder.setMessage("您还没有进行签约认证,现在要去认证吗?");
                        builder.setTitle("提示");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, SignatureIndex.class);
                                startActivity(intent);
                            }
                        });

                        builder.setNegativeButton("取消",
                                new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.create().show();
                    }
                }else {
                    DeleteDialog.Builder builder = new DeleteDialog.Builder(MainActivity.this);
                    builder.setMessage("您还没有登录,现在去登录吗?");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                    });

                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();
                }
                break;
            case R.id.ll_index:
                viewPager.setCurrentItem(0);
                iv_user.setImageResource(R.mipmap.tabbar_me_n);
                iv_trade.setImageResource(R.mipmap.tabbar_trade_n);
                iv_index.setImageResource(R.mipmap.tabbar_home_s);

                tv_user.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                tv_trade.setTextColor(getResources().getColor(R.color.tabbar_unselect));
                tv_index.setTextColor(getResources().getColor(R.color.tabbar_select));
                break;
        }
    }

    // Fragment适配器
    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    //再按一次退出程序时间间隔
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                finishActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushUtil.onPause(this);
    }
}

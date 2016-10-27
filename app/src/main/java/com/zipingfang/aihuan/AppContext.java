package com.zipingfang.aihuan;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zipingfang.aihuan.receiver.JPushUtil;

import java.util.Map;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class AppContext extends Application {
    public static int mNetWorkState;
    public static Map<String, Long> map;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        //激光推送初始化
        JPushUtil.init(this);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        JPushInterface.setPushNotificationBuilder(1, builder);

    }
}

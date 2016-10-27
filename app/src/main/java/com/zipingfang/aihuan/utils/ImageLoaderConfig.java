package com.zipingfang.aihuan.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zipingfang.aihuan.R;

public class ImageLoaderConfig {

    static {
        options = new DisplayImageOptions.Builder()
                // .showStubImage(R.drawable.umeng_socialize_default_avatar) //
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_error)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10))// 设置圆角
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static DisplayImageOptions options;

    static {
        normal = new DisplayImageOptions.Builder()
                // .showStubImage(R.drawable.umeng_socialize_default_avatar) //
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_error)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static DisplayImageOptions normal;
}

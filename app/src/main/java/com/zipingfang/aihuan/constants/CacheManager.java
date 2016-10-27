package com.zipingfang.aihuan.constants;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.zipingfang.aihuan.R;

import java.io.File;

/**
 * 路径及缓存管理 Author:heiyue Email:heiyue623@126.com 2015-3-25上午11:00:46
 */
public class CacheManager {
	public static final String DIR_CACHE = Environment
			.getExternalStorageDirectory() + "/";

	/**
	 * 获取图片缓存路径 如果不存在，则创建路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getImgDir(Context context) {
		String packageName = context.getPackageName();
		String path = DIR_CACHE + packageName + "/.img/";
		// String path = DIR_CACHE + "/.img/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	/**
	 * 清除缓存图片
	 */
	public static void chearImgCache(Context context) {
		try {
			String imgDir = getImgDir(context);
			File dir = new File(imgDir);
			if (dir != null && dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					file.delete();
				}
			}
		} catch (Exception e) {
			Log.e("chearImgCache", e.toString());
		}

	}

	/**
	 * 获取二维码保存的路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getQrcodeDir(Context context) {
		String path = DIR_CACHE
				+ context.getResources().getString(R.string.appNme) + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	/**
	 * 获取默认的图片显示参数
	 * 
	 * @return
	 */
	public static DisplayImageOptions getDefaultDisplay() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
				.cacheInMemory(true)
				// .showImageOnLoading(R.drawable.we_img_photo)
				.showImageOnFail(R.mipmap.icon_error)
				.showImageForEmptyUri(R.mipmap.icon_error)
				.bitmapConfig(Config.RGB_565) // default
				.imageScaleType(ImageScaleType.EXACTLY)
				// .delayBeforeLoading(200)
				// .displayer(new FadeInBitmapDisplayer(1000)) // default
				// 可以设置动画，比如圆角或者渐变
				// .resetViewBeforeLoading(true)
				.build();
		return options;
	}

	/**
	 * 获取默认的图片显示参数
	 * 
	 * @return
	 */
	public static DisplayImageOptions getQrcodeDisplay() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true)
				// .showImageOnFail(R.drawable.me_img_code)
				// .showImageForEmptyUri(R.drawable.me_img_code)
				.bitmapConfig(Config.RGB_565) // default
				.imageScaleType(ImageScaleType.EXACTLY)
				// .delayBeforeLoading(200)
				// .displayer(new FadeInBitmapDisplayer(1000)) // default
				// 可以设置动画，比如圆角或者渐变
				// .resetViewBeforeLoading(true)
				.build();
		return options;
	}

	/**
	 * 加载本地图片
	 * 
	 * @return
	 */
	public static DisplayImageOptions getLocalDisplayerOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(false).cacheInMemory(false)
				.bitmapConfig(Config.RGB_565) // default
				.imageScaleType(ImageScaleType.EXACTLY)
				// .delayBeforeLoading(200)
				.displayer(new FadeInBitmapDisplayer(1000)) // default
															// 可以设置动画，比如圆角或者渐变
				// .resetViewBeforeLoading(true)
				.build();
		return options;
	}

	/**
	 * 加载本地图片
	 * 
	 * @return
	 */
	public static DisplayImageOptions getLocalDisplayerOptions4Upload() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(false).cacheInMemory(true)
				.bitmapConfig(Config.RGB_565) // default
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// .delayBeforeLoading(200)
				// .displayer(new FadeInBitmapDisplayer(1000)) // default
				// 可以设置动画，比如圆角或者渐变
				// .resetViewBeforeLoading(true)
				.build();
		return options;
	}

	/**
	 * 初始化imageLoader
	 */
	public static void initImageLoader(Context context) {
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(context));
	}
}

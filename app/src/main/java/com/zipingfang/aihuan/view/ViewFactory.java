package com.zipingfang.aihuan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView, ImageLoaderConfig.normal);
		return imageView;
	}
}

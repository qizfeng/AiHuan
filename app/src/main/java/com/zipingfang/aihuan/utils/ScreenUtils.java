package com.zipingfang.aihuan.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕分辨率信息
 * new ScreenInfo(this).getWeight();
 * 
 * eg2:
 * act_image_photo form=new act_image_photo(this, view);
 * form.show(((int)(120*ds)), ((int)(120*ds)));
 */
public class ScreenUtils {

	public DisplayMetrics dm=null;
	
	private ScreenUtils(Activity context) {
		if (context!=null){
			initMet(context);
		}
	}
 
	public void initMet(Activity context) {
		try{
			dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);  
		}catch(Exception e){
			dm=null;
			Lg.error("ScreenUtils/请把代码onAppStart放到启动Activity中运行!");//nullpointexception
		}
	}

	/**
	 * 单例
	 */
    private static ScreenUtils instance; 
    public static ScreenUtils getInstance(Activity context) {
        if (instance == null) {
            synchronized (ScreenUtils.class) {
                if (instance == null) {
                    instance = new ScreenUtils(context);
                }
            }
        }else{
        	if (instance.dm==null) instance.initMet(context);
        }
        return instance;
    }

    public static int dpToPx(Activity context, int dp) {
        if (context == null) {
            return -1;
        }
        float ds=ScreenUtils.getInstance(context).getDensity();
        //return (int)(dp * context.getResources().getDisplayMetrics().density + 0.5f);
        return (int)(dp * ds + 0.5f);
    }

	/**
	 * 得到宽度
	 * @return
	 */
	public int getWidth(){
		if (dm!=null) return dm.widthPixels;
		else return 480;
	}

	/**
	 * 得到高度
	 * @return
	 */
	public int getHeight(){
		if (dm!=null) return dm.heightPixels;
		else return 800;
	}

	/**
	 * 屏幕密度DPI（120 / 160 / 240）
	 * @return
	 */
	public int getDensityDpi(){
		if (dm!=null) return dm.densityDpi;  
		else return 160;
	}

	/**
	 * 屏幕密度（0.75 / 1.0 / 1.5）
	 * @return
	 */
	public float getDensity(){
		if (dm!=null) return dm.density;  
		else return 1.0f;
	}
	
	/**
	 * 华为三星手机是 480/1.5=320
	 * 公司平板是 690/1=600
	 * if (limitDipWidth >= 479) {
			setContentView(R.layout.loading);//loading_large
		} else {
			setContentView(R.layout.loading);
		}
	 * @return
	 */
	public int getLimitDipWidth() { 
		if (dm!=null) return (int)(dm.widthPixels / dm.density);
		else return 320;
	}
	
	 
}

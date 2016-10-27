package com.zipingfang.aihuan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ApkUtils {

	  
	/**
	 * 本程序版本信息
	 * 1. 取得信息:android:versionName="2012.10.22"
	 * 
	 * eg:
	 * showMsg(SysConfig.getInstance(this).getAppVersionName());//2012.10.22
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {    
	     String versionName = "";    
	     if (context==null) return "";
	     try {     
	         PackageManager pm = context.getPackageManager();  
	         if (pm==null) return "";
	         PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
	         if (pi==null) return "";
	         versionName = pi.versionName;
	         if (versionName == null || versionName.length() <= 0) {    
	             return "";    
	         }    
	     } catch (Exception e) {    
	         e.printStackTrace();  
	         return "";
	     }    
	     return versionName;    
	}
	//android:versionCode="1"
	public static int getAppVersionCode(Context context) {    
		int versionCode = 1;    
	     try {     
	         PackageManager pm = context.getPackageManager();    
	         PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
	         versionCode = pi.versionCode; 
	     } catch (Exception e) {    
	         e.printStackTrace();  
	         return 1;
	     }    
	     return versionCode;    
	}  
	
	public static String getPackageName(Context context) {    
	     String apkName = "";    
	     if (context==null) return "";
	     try {     
	         PackageManager pm = context.getPackageManager();  
	         if (pm==null) return "";
	         PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
	         if (pi==null) return "";
	         apkName = pi.packageName;
	         if (apkName == null || apkName.length() <= 0) {    
	             return "";    
	         }    
	     } catch (Exception e) {    
	         e.printStackTrace();  
	         return "";
	     }    
	     return apkName;    
	}
	  
  
}

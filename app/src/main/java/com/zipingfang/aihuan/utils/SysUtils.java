package com.zipingfang.aihuan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />  
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 * 
 * eg:
 * public static String getImei(Context context) {
		if (!StringUtils.isEmpty(_imei)){
			return _imei;
		}else{
			//1. db
			String res = getFromDb(context);
			if (StringUtils.isEmpty(res)){
				//2. imei
				res=SysUtils.getImei(context);
				if (StringUtils.isEmpty(res)){   
					//3. mac address
					res=SysUtils.getMacAddr(context);
					if (StringUtils.isEmpty(res)){   
						//4. guid
						res=SysUtils.getGuid(context); 
					} 
				} 
			}
			
			
			if (!StringUtils.isEmpty(res)){
				_imei = res;
				saveToDb(context, res);
			}
			
			Lg.debug("imei="+res);
			return res;
		} 
	}
 */ 
public class SysUtils {

//	<uses-permission android:name="android.permission.READ_PHONE_STATE" />  
	public static String getImei(Context context) {
		try{
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			 
	        String tmDevice = tm.getDeviceId();//获取本机IMEI号码（仅手机存在）
	        if (tmDevice!=null && tmDevice.length()>0) return tmDevice;  
	        else{
	        	String tmSerial =  tm.getSimSerialNumber();//获取设备序列号【测试设备都装载有SIM卡】
	        	return tmSerial; 
	        }
	        /*error:
	        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	     
	        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	        String uniqueId = deviceUuid.toString();
	        
	        return uniqueId;    */
		}catch(Exception e){
			Lg.error(e.toString());
			return null;
		} 
	}

//	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	public static String getMacAddr(Context context) {
		String mac = WifiUtils.getLocalMacAddressFromWifiInfo(context);
		if (mac!=null) return mac.replaceAll(":", "");
		else return null; 
	}

	public static String getGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getDevInfo(Context context) {
		String phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", ID: " + android.os.Build.ID;
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", USER: " + android.os.Build.USER; 
        System.out.println(phoneInfo);
        return phoneInfo;
	}


	/**
	 * 例子汇总 ----------okokok
	 * http://blog.csdn.net/joebaby_/article/details/7962838
	 * @param tel
	 */
	public static void call(Activity context, String tel) {
		try { 
			//跳转
			Uri uri = Uri.parse("tel:"+tel);
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			context.startActivity(intent);
		} catch (Exception e) {
			//直接拨打电话 ok
			Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + tel));
			context.startActivity(phoneIntent);//启动
		} 
	} 
	
}

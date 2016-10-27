package com.zipingfang.aihuan.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class NetUtils {

	
	private Context context;
	private ConnectivityManager connManager;
	private NetUtils(){
	}

	/**
	 * SysConfig.getInstance(this)
	 */
	private static NetUtils instance = null;
	public static NetUtils getInstance(Context context) {
		if (instance==null){
			instance = new NetUtils(); 	
		}
		instance.context = context;
		instance.connManager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
		return instance;
	} 
	  
	/**
	 * 简单的判断网络是否连接
	 * @return
	 */
    public boolean isNetworkConnected()
    {
		NetworkInfo networkInfo = null; 
		try {
			networkInfo = connManager.getActiveNetworkInfo(); 
		} catch (Exception e) { 
			Lg.error(e);
			return false;
		} 
		if (networkInfo==null) return false; 
		else return networkInfo.isConnected();
    }

	/**
	 * android.permission.ACCESS_NETWORK_STATE
	 * @return wifi是否连接可用
	 */
	public boolean isWifiConnected() { 
		NetworkInfo networkInfo = null; 
		try {
			networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		} catch (Exception e) {
			e.printStackTrace();  
			return false;
		} 
		if (networkInfo==null) return false;
  
		State wifi = networkInfo.getState(); 
		return (wifi == State.CONNECTED || wifi == State.CONNECTING); 
	}

	/**
	 * 当wifi不能访问网络时，mobile才会起作用
	 * @return GPRS是否连接可用
	 */
	public boolean isMobileConnected() {
		NetworkInfo networkInfo = null; 
		try {
			networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		} catch (Exception e) {
			e.printStackTrace();  
			return false;
		} 
		if (networkInfo==null) return false;
  
		State mMobile = networkInfo.getState(); 
		return (mMobile == State.CONNECTED || mMobile == State.CONNECTING);  
	}

	/**
	 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0607/1621.html
	 * info.getSubtype()取值列表如下：
 
         * NETWORK_TYPE_CDMA 网络类型为CDMA
         * NETWORK_TYPE_EDGE 网络类型为EDGE
         * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
         * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
         * NETWORK_TYPE_GPRS 网络类型为GPRS
         * NETWORK_TYPE_HSDPA 网络类型为HSDPA
         * NETWORK_TYPE_HSPA 网络类型为HSPA
         * NETWORK_TYPE_HSUPA 网络类型为HSUPA
         * NETWORK_TYPE_UMTS 网络类型为UMTS
			 
		联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信
		的3G为EVDO
	 * @return true:3g, false:2g
	 */
	public boolean isFastMobileNetwork() {
		NetworkInfo networkInfo = null; 
		try {
			networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		} catch (Exception e) {
			e.printStackTrace();  
			return false;
		} 
		if (networkInfo==null) return false;

		int type=networkInfo.getSubtype();
		switch (type) {
	        case TelephonyManager.NETWORK_TYPE_1xRTT:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_CDMA:
	            return false; // ~ 14-64 kbps
	        case TelephonyManager.NETWORK_TYPE_EDGE:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_0:
	            return true; // ~ 400-1000 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_A:
	            return true; // ~ 600-1400 kbps
	        case TelephonyManager.NETWORK_TYPE_GPRS:
	            return false; // ~ 100 kbps
	        case TelephonyManager.NETWORK_TYPE_HSDPA:
	            return true; // ~ 2-14 Mbps
	        case TelephonyManager.NETWORK_TYPE_HSPA:
	            return true; // ~ 700-1700 kbps
	        case TelephonyManager.NETWORK_TYPE_HSUPA:
	            return true; // ~ 1-23 Mbps
	        case TelephonyManager.NETWORK_TYPE_UMTS:
	            return true; // ~ 400-7000 kbps
	        case TelephonyManager.NETWORK_TYPE_EHRPD:
	            return true; // ~ 1-2 Mbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_B:
	            return true; // ~ 5 Mbps
	        case TelephonyManager.NETWORK_TYPE_HSPAP:
	            return true; // ~ 10-20 Mbps
	        case TelephonyManager.NETWORK_TYPE_IDEN:
	            return false; // ~25 kbps
	        case TelephonyManager.NETWORK_TYPE_LTE:
	            return true; // ~ 10+ Mbps
	        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
	            return false;
	        default:
	            return false;
			} 
	}
	
    /**
     * 判断手机是否是飞行模式
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     * 
     * @return 是否处于飞行模式
     */
    public boolean isAirplaneModeOn() {  
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), 
                Settings.System.AIRPLANE_MODE_ON, 0) ; 
        return (isAirplaneMode == 1)?true:false; 
    }  
 
    
	/**
	 * GPRS网络开关 反射ConnectivityManager中hide的方法setMobileDataEnabled 可以开启和关闭GPRS网络
	 * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
	 * @param isEnable
	 * @throws Exception
	 */
	public void setGprs(boolean isEnable) throws Exception {
		Class<?> cmClass = connManager.getClass();
		Class<?>[] argClasses = new Class[1];
		argClasses[0] = boolean.class;

		// 反射ConnectivityManager中hide的方法setMobileDataEnabled，可以开启和关闭GPRS网络
		Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);
		method.invoke(connManager, isEnable);
	} 
	
	/**
	 * WIFI网络开关
	 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /> 
	 * @param enabled
	 * @return 设置是否success
	 */
	public boolean setWiFi(boolean enabled) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wm.setWifiEnabled(enabled);

	}

    /**
     * 设置手机飞行模式
     * // 广播飞行模式信号的改变，让相应的程序可以处理。  
        // 不发送广播时，在非飞行模式下，Android 2.2.1上测试关闭了Wifi,不关闭正常的通话网络(如GMS/GPRS等)。  
        // 不发送广播时，在飞行模式下，Android 2.2.1上测试无法关闭飞行模式。  
        // 2.3及以后，需设置此状态，否则会一直处于与运营商断连的情况  
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     * @param context
     * @param enabling true:设置为飞行模式 false:取消飞行模式
     */ 
    public void setAirplaneModeOn(Context context,boolean enabling) { 
        Settings.System.putInt(context.getContentResolver(), 
                             Settings.System.AIRPLANE_MODE_ON,enabling ? 1 : 0); 
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED); 
        intent.putExtra("state", enabling); 
        context.sendBroadcast(intent); 
    } 
    
	
	/**
	 * 网络状态判断, 1=wifi,2=gps
	 * add:
	 * 		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * eg1:
	 * 		config.CheckNetworkState(true);
	 * eg2:
	 * 		if (config.CheckNetworkState(false)==0)
        		showMsg("网络没开!");
	 */
	public int checkNetworkState(boolean showSetting) { 
		if (context==null) return 0;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager==null) return 0;

		NetworkInfo networkInfo = null;
		NetworkInfo networkInfo2 = null;
		try {
			networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			networkInfo2 = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);//显示3G网络连接状态
		} catch (Exception e) {
			e.printStackTrace();  
			return 0;
		} 
		if (networkInfo==null) return 0;

		int res = 0;
		// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面 
		State wifi = networkInfo.getState(); 
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) res=1; 
		else{//else=同时打开wifi/gprs时,默认还是wifi更新的
			State mobile = networkInfo2.getState();
			if (mobile == State.CONNECTED || mobile == State.CONNECTING) res=2; //GPS和数据开关都必须打开才行
		}
		
		if (showSetting && res==0) showTips(1);
		Lg.debug("network status="+res);
		return res;
	}
    
	/**
	 * 如果没有网络连接，则进入网络设置界面
	 */
    private void showTips(final int type)
    { 
    	if (type==1) context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)); // 如果没有网络连接，则进入网络设置界面
    	else context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //进入手机中的wifi网络设置界面
    }  
    
  
}

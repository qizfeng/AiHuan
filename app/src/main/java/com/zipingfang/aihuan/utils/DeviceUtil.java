package com.zipingfang.aihuan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore.MediaColumns;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 设备和app信息工具
 * 
 * @ClassName: DeviceUtil
 * @Description:
 * @author qizfeng
 * @date 2014-7-2 上午12:33:40
 * 
 */
public class DeviceUtil {
	/**
	 * 获取当前应用的的包信息
	 * 
	 * @param context
	 * @return
	 */
	public static PackageInfo getMPackageInfo(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			return packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get IMSI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();

		return imsi;
	}

	/**
	 * get IMEI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();

		if (imei == null || imei.length() <= 0) {

			try {
				Class<?> c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", String.class);

				imei = (String) get.invoke(c, "ro.serialno");
			} catch (SecurityException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			} catch (IllegalArgumentException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			} catch (ClassNotFoundException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			} catch (NoSuchMethodException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			} catch (IllegalAccessException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			} catch (InvocationTargetException e) {
				Log.e("DeviceUtil", e.getLocalizedMessage());
			}
		}
		return imei;
	}

	// 获取包名
	public static String getPackageName(Context ctx) {
		return ctx.getPackageName();
	}

	public static String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	public static String getSimCardNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimSerialNumber();
	}

	public static String getSimProvider(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI = tm.getSubscriberId();
		return IMSI;
	}

	/**
	 * get sdk version
	 * 
	 * @return
	 */
	public static int getSdkversion() {
		Log.e("getSdkversion", "current version:" + Build.VERSION.SDK_INT);
		return Build.VERSION.SDK_INT;
	}

	/**
	 * get package version
	 * 
	 * @return
	 */
	public static int getPackageVersion(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * get package version
	 * 
	 * @return
	 */
	public static String getPackageVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1.0";
		}
	}

	/**
	 * get package version
	 * 
	 * @return
	 */
	public static String getAppName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		return (String) packageManager.getApplicationLabel(applicationInfo);
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		int versionCode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versionCode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * get Screen Width
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		return getDisplay(context).getWidth();
	}

	/**
	 * get Screen Heght
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		return getDisplay(context).getHeight();
	}

	/**
	 * get Screen Density
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.densityDpi;
	}

	/**
	 * get Display
	 *
	 * @param context
	 * @return
	 */
	private static Display getDisplay(Context context) {
		Display display = ((WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display;
	}

	/**
	 * 打开键盘
	 *
	 * @param context
	 */
	public static void openKeyboard(Context context) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 传�?token关闭键盘
	 *
	 * @param context
	 * @param token
	 */
	public static void closeKeyboard(Context context, IBinder token) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(token, 0);
	}

	public static boolean isAliveIme(Context context) {
		InputMethodManager m = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (m.isActive()) {
			return true;
		}
		return false;
	}

	/** 获取本机ip */
	public static InetAddress getLocalIpAddress(Context context)
			throws UnknownHostException {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(android.content.Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return InetAddress.getByName(String.format("%d.%d.%d.%d",
				(ipAddress & 0xff), (ipAddress >> 8 & 0xff),
				(ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
	}

	public static String logMemory() {
		long maxMemory = Runtime.getRuntime().maxMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		return "total:" + maxMemory + ",current:" + totalMemory;
	}

	public static int dip2px(Context context, float dipValue) {
		return (int) (dipValue * (getScreenDensity(context) / 160f));
	}

	public static int px2dip(Context context, float pxValue) {
		return (int) ((pxValue * 160) / getScreenDensity(context));
	}

	public static void fillScree(Activity activity) {
		// 无title
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public static String getRealPathFromURI(Activity context, Uri contentUri) {
		String[] proj = { MediaColumns.DATA };
		Cursor cursor = context
				.managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}


	/**
	 * 获取屏幕的尺寸
	 *
	 * @param context
	 * @return int[0]=screenWidth int[1]=screenHeight
	 */
	public static int[] getScreenSize(Activity context){
		// 获取屏幕密度（方法3）
		DisplayMetrics  dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		float density  = dm.density;      // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		return new int[]{dm.widthPixels,dm.heightPixels};
	}

	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int dip2px(Context context, int dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

//	public static int px2dip(Context context, float pxValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (pxValue / scale + 0.5f);
//	}
	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}
}

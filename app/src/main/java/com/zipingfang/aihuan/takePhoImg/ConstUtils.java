package com.zipingfang.aihuan.takePhoImg;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class ConstUtils {
	
	public static String mProjectName=".youkeSDK";

	/**
	 * 取得图片本地路径
	 * @param context
	 * @return
	 */
	public static String getFileLocalPath(Context context) {
		String filePath = getSDCardPath(context)+ File.separator+mProjectName+ File.separator;
		if (!new File(filePath).exists()){
			new File(filePath).mkdirs();
		}
		return filePath;
	}

    /**
     * 取得SD卡的路径
     * @return
     */ 
	public static String getSDCardPath(Context context) {
		if (android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED)){
			File path = Environment.getExternalStorageDirectory();
			return path.toString(); //只是SDCARD才给赋值
		}else{
			System.out.println("_______________________________________");
			System.out.println("no use sdcard,use data dir!!!!!!!!!");
			System.out.println("_______________________________________");
//			File path = Environment.getDataDirectory();//data/.c_nais目录无法写文件 
//			SD_PATH=path.toString(); 
			return context.getFilesDir().toString();//data/data/com..../files
		} 
	}//end getSDCardPath

}

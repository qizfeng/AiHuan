package com.zipingfang.aihuan.utils;

import android.util.Log;

import com.zipingfang.aihuan.constants.Constants;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Lg {
 
	public static void debug(String msg) {
		if (msg==null) return;
		if (!Constants.debug) return;
		
		Log.d(Constants.projectName, msg); 
	}
   
	public static void info(String msg) {
		if (msg==null) return;
		if (!Constants.info) return;
		
		Log.i(Constants.projectName, msg); 
	}
  
	public static void error(String msg) {
		if (msg==null) return;
		if (!Constants.error) return;
		
		Log.e(Constants.projectName, msg); 
	}

	public static void error(Exception e){
		if (e==null) return;
		if (!Constants.error) return;
		
		StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw)); 
        String msg=sw.toString();
        
		error(msg);
	} 

}

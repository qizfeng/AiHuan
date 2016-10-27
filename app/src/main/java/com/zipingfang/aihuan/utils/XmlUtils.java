package com.zipingfang.aihuan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class XmlUtils {

    private static final String CONST_SHARE = "xml_tools";

    public static void saveToXml(Context context, String key, String value) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null) sharedPreferences.edit().putString(key, value).commit();
        } catch (Exception e) {
            Lg.error(e);
        }
    }

    public static String getFromXml(Context context, String key, String defValue) {
        try {
            if (context == null) return defValue;

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null)
                return sharedPreferences.getString(key, defValue);
            else
                return defValue;
        } catch (Exception e) {
            Lg.error(e);
            return defValue;
        }
    }
}

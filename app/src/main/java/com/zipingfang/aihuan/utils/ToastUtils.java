package com.zipingfang.aihuan.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * ToastUtils
 *
 * @author 峰
 */
public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, Object... args) {
        show(context,
                String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration,
                            Object... args) {
        show(context,
                String.format(context.getResources().getString(resId), args),
                duration);
    }

    public static void show(Context context, String format, int duration,
                            Object... args) {
        show(context, String.format(format, args), duration);
    }

    /**
     * ToastUtils.show(getApplicationContext(), "test");
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void show(Context context, CharSequence text, int duration) {
        try {
//            Looper.prepare();
            if (toast == null) {
                toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                twoTime = System.currentTimeMillis();
                if (text.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = text.toString();
                    toast.setText(text);
                    toast.show();
                }
            }
            oneTime = twoTime;
//            Looper.loop();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("qizfeng","exception:"+e.toString());
        }
        // Toast.makeText(context, text, duration).show();
    }

    /**
     * 可在线程里面运行 eg: new Thread(){ public void run() {
     * ToastUtils.showInThread(getApplicationContext(), "test"); }; }.start();
     *
     * @param context
     * @param text
     */
    public static void showInThread(final Context context,
                                    final CharSequence text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                show(context, text, Toast.LENGTH_SHORT);
            }
        });
    }
}

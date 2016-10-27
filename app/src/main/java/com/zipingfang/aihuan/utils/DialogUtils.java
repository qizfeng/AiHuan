package com.zipingfang.aihuan.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * DialogUtils>>>系统弹出框
 * RoundProgressDialog>>>自定义圆角ProgressDialog
 */
public class DialogUtils {

    Context context;

    public DialogUtils(Context context) {
        this.context = context;
    }

    /**
     * 显示等待对话框 or: ProgressDialog.show(this, null, "正在加载…");
     *
     * @param msg
     */
    private ProgressDialog mLoadingDailog = null;

    public void showDailogLoading() {
        try {
            showDailogLoading("请稍后...");
        } catch (Exception e) {//mLoadingDailog.show();也会出现异常,导致app挂掉
            Lg.error(e);
        }
    }

    public void showDailogLoading(String msg) {
        if (mLoadingDailog == null)
            mLoadingDailog = new ProgressDialog(context);
        mLoadingDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoadingDailog.setIndeterminate(false);
        mLoadingDailog.setCancelable(true);
        mLoadingDailog.setMessage(msg);//

        try {
            mLoadingDailog.show();
        } catch (Exception e) {
            Lg.error(e);
            mLoadingDailog = null;
        }
    }

    public void hideDailogLoading() {
        if (mLoadingDailog != null) {
            if (mLoadingDailog.isShowing()) {
                try {
                    mLoadingDailog.dismiss();
                } catch (Exception e) {
                }
            }
            mLoadingDailog = null;
        }
    }


    /**
     * --==============================================================
     * 系统弹出框相关
     * --==============================================================
     */
    public static String DAILOG_OK_TITLE = "確定";
    public static String DAILOG_CANCEL_TITLE = "取消";

    /**
     * Tools.showDaily(this, "提示", "你的信息是123456");
     */
    public static void showDaily(Context context, String title, String msg) {
        Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(msg);
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //none
            }
        };
        myAlertDialog.setNeutralButton(DAILOG_OK_TITLE, OkClick);
        final Dialog dialog = myAlertDialog.show();
    }

    /**
     * Tools.showDaily(this, "退出系统", "你确定要退出系统吗？", new Tools.ICallback() {
     *
     * @Override public void exec(boolean ok) {
     * if (ok) doLogout();
     * }
     * });
     */
    public static void showDaily(Context context, String title, String msg, final ICallback callback) {
        Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(msg);

        //set ok event
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) callback.exec(true);
            }
        };
        myAlertDialog.setNeutralButton(DAILOG_OK_TITLE, OkClick);

        //set cancel event
        DialogInterface.OnClickListener CancelClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) callback.exec(false);
            }
        };
        myAlertDialog.setNegativeButton(DAILOG_CANCEL_TITLE, CancelClick);
        final Dialog dialog = myAlertDialog.show();
    }

    public interface ICallback {
        void exec(boolean ok);
    }

    /**
     * SingleChoice
     * final String[] list = new String[] { "50", "100", "150"};
     * Tools.showDaily(this, "select", list, 1, new Tools.ICallbackSel() {
     *
     * @Override public void exec(boolean ok, Integer index) {
     * if (ok) showMsg(list[index]);
     * }
     * });
     */
    static int mDailySelIndex;

    public static void showDaily(Context context, final String title, String[] list
            , Integer selIndex, final ICallbackSel callback) {
        Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(title);
        myAlertDialog.setCancelable(true);

        if (selIndex >= list.length) selIndex = 0;
        mDailySelIndex = selIndex;

        //set list
        myAlertDialog.setSingleChoiceItems(list, selIndex,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title != null && title.length() > 0) {
                            mDailySelIndex = which;
                        } else {
                            if (callback != null) {
                                callback.exec(true, which);
                                dialog.dismiss();
                            }
                        }

                    }
                });

        if (title != null && title.length() > 0) {
            //set ok event
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) callback.exec(true, mDailySelIndex);
                }
            };
            myAlertDialog.setNeutralButton(DAILOG_OK_TITLE, OkClick);

            //set cancel event
            DialogInterface.OnClickListener CancelClick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) callback.exec(false, mDailySelIndex);
                }
            };
            myAlertDialog.setNegativeButton(DAILOG_CANCEL_TITLE, CancelClick);
        }
        myAlertDialog.show();
    }

    public interface ICallbackSel {
        void exec(boolean ok, Integer index);
    }

    /**
     * checkbox
     *
     * @param context
     * @param title
     * @param list
     * @param list_chk
     * @param callback
     */
    public static void showDaily(Context context, final String title, String[] list, final boolean[] list_chk
            , final ICallbackChk callback) {
        Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(title);
        myAlertDialog.setCancelable(true);

        //set list
        myAlertDialog.setMultiChoiceItems(list, list_chk, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which, boolean isChecked) {
                if (title != null && title.length() > 0) {
                    list_chk[which] = isChecked;
                } else {
                    list_chk[which] = isChecked;
                    if (callback != null) callback.exec(false, list_chk);
                    dialog.dismiss();
                }

            }
        });

        if (title != null && title.length() > 0) {
            //set ok event
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) callback.exec(true, list_chk);
                }
            };
            myAlertDialog.setNeutralButton(DAILOG_OK_TITLE, OkClick);

            //set cancel event
            DialogInterface.OnClickListener CancelClick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) callback.exec(false, list_chk);
                }
            };
            myAlertDialog.setNegativeButton(DAILOG_CANCEL_TITLE, CancelClick);
        }
        myAlertDialog.show();
    }

    public interface ICallbackChk {
        void exec(boolean ok, boolean[] list_chk);
    }

    /**
     * 带有一个edit的对话框
     *
     * @param context
     * @param title
     * @param lbTitle
     * @param callback
     */
    public static void showDailyEdit(final Context context, String title, String lbTitle, final ICallbackEdt callback) {
        Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(title);
        myAlertDialog.setCancelable(true);

        ScrollView sv = new ScrollView(context);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(26, 10, 26, 10);
        sv.addView(ll);

        final TextView tname = new TextView(context);
        tname.setText(lbTitle);
        tname.setPadding(6, 0, 0, 10);

        final EditText sname = new EditText(context);
        sname.setText("");

        ll.addView(tname);
        ll.addView(sname);

        // Set an EditText view to get user input
        myAlertDialog.setView(sv);

        //set ok event
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    try {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(sname.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        imm.hideSoftInputFromInputMethod(sname.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                    }
                    callback.exec(true, sname.getText().toString());
                }
            }
        };
        myAlertDialog.setNeutralButton(DAILOG_OK_TITLE, OkClick);

        //set cancel event
        DialogInterface.OnClickListener CancelClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) callback.exec(false, "");
            }
        };
        myAlertDialog.setNegativeButton(DAILOG_CANCEL_TITLE, CancelClick);
        myAlertDialog.show();
    }

    public interface ICallbackEdt {
        void exec(boolean ok, String res);
    }

}

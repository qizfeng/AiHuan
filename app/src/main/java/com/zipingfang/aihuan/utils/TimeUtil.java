package com.zipingfang.aihuan.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 */
public abstract class TimeUtil {

    private int CONST_SECOND = 1 * 60; //1*60 秒
    private int CONST_START_AFTER = 10;
    private Timer timer;

    public TimeUtil(int while_second) {
        CONST_SECOND = while_second;
    }

    public void start() {
        // 开启定时器，每隔1*60秒刷新一次
        if (timer == null) {
            debug("_______________ start timer ____________________");
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), CONST_START_AFTER * 1000, CONST_SECOND * 1000);
        }
    }

    public void stop() {
        timer.cancel();
        timer = null;
        debug("_______________stop service timer ______________");
    }

    /**
     * 开启定时器，每隔0.5秒刷新一次
     *
     * @author Administrator
     */
    class RefreshTask extends TimerTask {
        @Override
        public void run() {
            try {
                exec();
            } catch (Exception e) {
                Lg.error(e);
            }
        }
    }

    public abstract void exec();

    /**
     * 显示调试信息
     *
     * @param msg
     */
    protected void debug(String msg) {
        Lg.debug(msg);
    }

    protected void info(String msg) {
        Lg.info(msg);
    }

    protected void error(String msg) {
        Lg.info(msg);
    }
}

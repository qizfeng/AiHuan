package com.zipingfang.aihuan.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * 日期操作
 *
 * @author 峰
 */
public class DateUtils {

    public static void main(String[] args) {

        System.out.println(getDiff(toDate("2011-02-02 12:02:04"), toDate("2011-02-01 12:02:04")));
        System.out.println(getFirstDayOfMonth());

        System.out.println(formatDateTime(convertLongToDate(1425695151)));
        System.out.println(formatDateTime(convertLongToDate(DateUtils.toDate("2015-06-04").getTime() / 1000)));
    }

    /**
     * 时间戳取时分秒
     * @param l
     * @return
     */
    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour + ":" + minute + ":" + second;
        return strtime;
    }

    /**
     * 将一个字符串时间转化为一个Date类型
     * eg: toDate("2011-02-10 00:00:00")//"yyyy-MM-dd HH:mm:ss"
     * eg: toDate("2011-02-10", "yyyy-MM-dd")
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date toDate(String strDate) {
        return toDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(strDate);
        } catch (Exception e) {
            try {
                return DateUtils.parseDate(strDate);
            } catch (ParseException e1) {
                Lg.error("format date error:" + strDate);
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * format sqldate:"yyyy-MM-dd HH:mm:ss"
     *
     * @return string
     */
    public static String formatDateTime(Date date) {
        return formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * format sqldate:"yyyy-MM-dd"
     *
     * @return string
     */
    public static String formatDate(Date date) {
        return formatDateTime(date, "yyyy-MM-dd");
    }

    /**
     * format sqldate:"HH:mm:ss"
     *
     * @return string
     */
    public static String formatTime(Date date) {
        return formatDateTime(date, "HH:mm:ss");
    }

    /**
     * format date with [format]
     *
     * @param format
     * @return string
     */
    public static String formatDateTime(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                Lg.error("date:" + date);
                ex.printStackTrace();
            }
        }
        return result;

    }

    /**
     * 返回当天的日期
     * <p>
     * strDate:显示的格式yyyy-MM-dd HH:mm:ss
     *
     * @return //or: Calendar cal = Calendar.getInstance();
     * <p>
     * 字母 日期或时间组件 实例 G 公元前/后 AD y 公元年份 2005 M 月份 七月 w 一年的第几周 27 W 一个月的第几周 2 D
     * 一个年的第几天 2 d 一个月的第几天 10 d Day of week in month 2 E 星期几 星期二 a am/pm PM H
     * 小时(0-23) 0 k 小时(1-24) 24 K 小时(0-11) 0 h 小时(1-12) 12 m 分钟 30 s 秒钟 55 S 毫秒
     * 978 z 时区 Pacific Standard Time; PST; GMT-08:00 Z RFC 822时区 -0800
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    public static Date date() {
        return new Date();
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 返回当天的日期
     *
     * @param strDate
     * @return
     */
    public static String getDate(String strDate) {
        Date dt = new Date();
        return formatDateTime(dt, strDate);
    }

    /**
     * formatDateTime(date, format);
     *
     * @param dt
     * @param format
     * @return
     */
    public static String dateToStr(Date dt, String format) {
        return formatDateTime(dt, format);
    }

    /**
     * 把一个格式的日期转化为另外一个格式的日期
     * formatToOtherF("2011-02-02 12:00:00", "yy/MM/dd") = 11/02/02
     *
     * @param source  原始日期 字符串,格式必须是"yyyy-MM-dd HH:mm:ss"
     * @param pattern 需要得到的格式
     * @return
     */
    public static String formatToOtherF(String source, String pattern) {
        SimpleDateFormat baseFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        Date date = new Date();
        try {

            date = baseFormat.parse(source);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return formatter.format(date);
    }

    /**
     * get now second value
     *
     * @return
     */
    public static int getCurSecond() {
        return Integer.parseInt(getDate("hh")) * 60 * 60
                + Integer.parseInt(getDate("mm")) * 60
                + Integer.parseInt(getDate("ss"));
    }

    /**
     * 瓤程序等待(秒钟)
     *
     * @param Second:1..n
     */
    public static void waitSecond(int Second) {
        try {
            Thread.sleep(Second);
        } catch (InterruptedException ex) {
        }
    }

    public static void waitMinute(int Second) {
        try {
            Thread.sleep(1000 * Second);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * 取得指定月份的第一天
     * eg: getFirstDayOfMonth("2011-02-20 00:00:00") = 2011-02-01
     *
     * @param strdate String
     * @return String
     */
    public static String getFirstDayOfMonth(String strdate) {
        java.util.Date date = toDate(strdate);
        return formatDateTime(date, "yyyy-MM") + "-01";
    }

    public static String getFirstDayOfMonth() {
        java.util.Date date = now();
        return formatDateTime(date, "yyyy-MM") + "-01";
    }


    /**
     * return the moth end of day
     * eg: getMaxDayOfMonth(2011, 3)=31, getMaxDayOfMonth(2011, 2)=28
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDayOfMonth(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);
        return time.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回年份
     * eg: getYear(toDate("2011-02-01 12:00:00")) = 2011
     *
     * @return 返回年份
     */
    public static int getYear(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.YEAR);
    }

    /**
     * 返回月份
     * eg: getMonth(toDate("2011-02-01 12:00:00")) = 2
     *
     * @return 返回月份
     */
    public static int getMonth(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     * eg: getDay(toDate("2011-02-01 12:00:00")) = 1
     *
     * @return 返回日份
     */
    public static int getDay(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     * eg: getHour(toDate("2011-02-01 12:00:00")) = 12
     *
     * @return 返回小时
     */
    public static int getHour(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     * eg: getMinute(toDate("2011-02-01 12:02:00")) = 2
     *
     * @return 返回分钟
     */
    public static int getMinute(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     * eg: getSecond(toDate("2011-02-01 12:02:04")) = 4
     * getSecond(toDate(today()))
     *
     * @return 返回秒钟
     */
    public static int getSecond(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.SECOND);
    }

    /**
     * 返回毫秒
     * eg: getMillis(toDate("2011-02-01 12:02:04")) = 1300526464000
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date) {
        if (date == null) return 0;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }
//  static public String mm_dd_yyyy = "MM-dd-yyyy HH:mm:ss";

    /**
     * 获得当前年份
     *
     * @return 当前年份，格式如：2003
     */
    public static int getCurrentYear() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
        return Integer.parseInt(sdf.format(new java.util.Date()));
    }

    /**
     * 获得当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M");
        return Integer.parseInt(sdf.format(new java.util.Date()));
    }

    /**
     * 获得当前天
     *
     * @return 当前天
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static String getMonthDay(java.util.Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M月d日");
        return sdf.format(date);
    }

    public static String getHourMinute(java.util.Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("H:mm");
        return sdf.format(date);
    }

    /**
     * 判断字符串是否符合日期格式
     *
     * @return
     */
    public static boolean isDate(String strDate) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * 日期相加
     * eg: addDate(toDate("2011-02-01 12:02:04"), 1) = Wed Feb 02 12:02:04 CST 2011
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static java.util.Date addDate(java.util.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     * eg: getDiff(toDate("2011-02-02 12:02:04"), toDate("2011-02-01 12:02:04")) = 1
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后的日期
     */
    public static int getDiff(java.util.Date date, java.util.Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * d1 is less d2?true:false
     *
     * @param d1 参考日期
     * @return d1在d2时间前返回true
     * @throws ParseException
     */
    public static boolean isLess(String d1, String d2)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(d1).before(sdf.parse(d2));
    }

    /**
     * 得到时间差
     * eg: getDiff("2011-02-01 12:33:34", "2011-02-01 12:33:33")=1000
     *
     * @return 时间差
     * @throws ParseException
     */
    public static long getDiff(String maxDate, String minDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (maxDate.length() == 10) maxDate = maxDate + " 00:00:00";
        if (minDate.length() == 10) minDate = minDate + " 00:00:00";

        long res = 0;
        try {
            Date remoteDate = formatter.parse(maxDate);
            Date localDate = formatter.parse(minDate);
            res = remoteDate.getTime() - localDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据时间差计算服务器端的时间并返回, like getDiff()
     * eg: nowAddTime(1000)=2011-03-19 16:40:42
     *
     * @param timeDifference 时间差
     * @return 准确服务器时间
     * @throws ParseException
     */
    public static String nowAddTime(long timeDifference) {
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dt.setTime(dt.getTime() + timeDifference);

        return formatter.format(dt);

    }

    /**
     * 计算两个时间
     *
     * @param str 原时间，strsub，需减少的时间
     * @return 计算后的时间
     */
    public String subTime(String str, String strSub) {
        String rsTime = "";
        int hour = 0;
        int sec = 0;
        int secsub = 0;
        str = str.trim();
        strSub = strSub.trim();
        if (str.length() == 5) {
            hour = Integer.parseInt(str.substring(0, 2));

            sec = Integer.parseInt(str.substring(3, 5));

        } else if (str.length() == 4) {
            hour = Integer.parseInt(str.substring(0, 1));

            sec = Integer.parseInt(str.substring(2, 4));

        }
        if (strSub.trim().length() == 5) {
            secsub = Integer.parseInt(strSub.substring(0, 2));

        } else if (strSub.trim().length() == 4) {
            secsub = Integer.parseInt(strSub.substring(0, 1));

        }

        //int a = sec + secsub;
        if (sec < secsub) {
            //System.out.println("sub <");
            String jstr = Integer.toString(sec + 60 - secsub);
            String hstr = Integer.toString(hour - 1);
            //System.out.println("jstr="+jstr);
            //System.out.println("hstr="+hstr);
            if (jstr.length() == 1) {
                jstr = "0" + jstr;
            }
            if (hstr.length() == 1) {
                hstr = "0" + hstr;
            }
            rsTime = hstr + ":" + jstr;

        } else if (sec == secsub) {
            //System.out.println("sub =");
            String strh = Integer.toString(hour);
            //System.out.println("strh="+strh);
            if (strh.length() == 1) {
                strh = "0" + strh;
            }
            rsTime = strh + ":00";

        } else if (sec > secsub) {
            //System.out.println("sub >");
            String jstr = Integer.toString(sec - secsub);
            //System.out.println("jstr="+jstr);
            String hstr = Integer.toString(hour);
            //System.out.println("hstr="+hstr);
            if (jstr.length() == 1) {
                jstr = "0" + jstr;
            }
            if (hstr.length() == 1) {
                hstr = "0" + hstr;
            }
            rsTime = hstr + ":" + jstr;

        }
        return rsTime;
    }

    /**
     * 计算两个时间相差的分钟数
     *
     * @param time1 string，time2，string
     * @return string
     */
    public String diffTime(String time1, String time2) {
        String rsTime = "";
        int hour = 0;
        int hour2 = 0;
        int sec = 0;
        int sec2 = 0;
        String str1 = time1.trim();
        String str2 = time2.trim();
        if (str1.length() == 5) {
            hour = Integer.parseInt(str1.substring(0, 2));

            sec = Integer.parseInt(str1.substring(3, 5));

        } else if (str1.length() == 4) {
            hour = Integer.parseInt(str1.substring(0, 1));

            sec = Integer.parseInt(str1.substring(2, 4));

        }
        if (str2.length() == 5) {
            hour2 = Integer.parseInt(str2.substring(0, 2));

            sec2 = Integer.parseInt(str2.substring(3, 5));

        } else if (str2.length() == 4) {
            hour2 = Integer.parseInt(str2.substring(0, 1));

            sec2 = Integer.parseInt(str2.substring(2, 4));

        }

        //int a = sec + secsub;
        if (sec < sec2) {
            //System.out.println("sub <");
            String jstr = Integer.toString(sec + 60 - sec2);
            if (jstr.length() == 1) {
                jstr = "0" + jstr;
            }
            if ((hour - 1) != hour2) {

                String hstr = Integer.toString(hour - 1 - hour2);

                if (hstr.length() == 1) {
                    hstr = "0" + hstr;
                }
                rsTime = hstr + ":" + jstr + ":00";
            } else {
                rsTime = jstr + ":00";
            }
        } else if (sec == sec2) {
            //System.out.println("sub =");
            if (hour != hour2) {

                String strh = Integer.toString(hour - hour2);
                //System.out.println("strh="+strh);
                if (strh.length() == 1) {
                    strh = "0" + strh;
                }
                rsTime = strh + ":00" + ":00";
            } else {
                rsTime = "00:00";
            }
        } else if (sec > sec2) {
            //System.out.println("sub >");
            String jstr = Integer.toString(sec - sec2);
            //System.out.println("jstr="+jstr);
            if (jstr.length() == 1) {
                jstr = "0" + jstr;
            }
            if (hour != hour2) {
                String hstr = Integer.toString(hour - hour2);
                //System.out.println("hstr="+hstr);
                if (hstr.length() == 1) {
                    hstr = "0" + hstr;
                }
                rsTime = hstr + ":" + jstr + ":00";
            } else {
                rsTime = jstr + ":00";
            }
        }
        return rsTime;
    }

    /**
     * 计算两个时间
     *
     * @param str 原时间，stradd，需增加的时间
     * @return 计算后的时间
     */
    public String addTime(String str, String stradd) {
        String rsTime = "";
        int hour = 0;
        int sec = 0;
        int secadd = 0;
        int houradd = 0;
        str = str.trim();
        stradd = stradd.trim();
        if (str.length() == 5) {
            hour = Integer.parseInt(str.substring(0, 2));

            sec = Integer.parseInt(str.substring(3, 5));

        } else if (str.length() == 4) {
            hour = Integer.parseInt(str.substring(0, 1));

            sec = Integer.parseInt(str.substring(2, 4));

        }
        if (stradd.trim().length() == 5) {

            secadd = Integer.parseInt(stradd.substring(0, 2));

        } else if (stradd.trim().length() == 4) {
            secadd = Integer.parseInt(stradd.substring(0, 1));

        } else if (stradd.trim().length() == 7) {
            houradd = Integer.parseInt(stradd.substring(0, 1));
            secadd = Integer.parseInt(stradd.substring(2, 4));
        }
        int a = sec + secadd;
        if (a < 60) {
            String stra = Integer.toString(a);
            String strh = Integer.toString(hour + houradd);
            if (stra.length() == 1) {
                stra = "0" + stra;
            }
            if (strh.length() == 1) {
                strh = "0" + strh;
            } else if (Integer.parseInt(strh) > 24) {
                int h = Integer.parseInt(strh) / 24;
                strh = Integer.toString(h);
                if (h < 10) {
                    strh = "0" + Integer.toString(h);
                }
            }
            rsTime = strh + ":" + stra;

        } else if (a == 60) {
            String strh = Integer.toString(hour + houradd + 1);
            if (strh.length() == 1) {
                strh = "0" + strh;
            } else if (Integer.parseInt(strh) > 24) {
                int h = Integer.parseInt(strh) / 24;
                strh = Integer.toString(h);
                if (h < 10) {
                    strh = "0" + Integer.toString(h);
                }
            }
            rsTime = strh + ":00";

        } else if (a > 60) {
            int i = a / 60;
            int j = a % 60;
            String strj = Integer.toString(j);

            if (strj.length() == 1) {
                strj = "0" + strj;
            }
            String strh = Integer.toString(hour + houradd + i);
            if (strh.length() == 1) {
                strh = "0" + strh;
            } else if (Integer.parseInt(strh) > 24) {
                int h = Integer.parseInt(strh) / 24;
                strh = Integer.toString(h);
                if (h < 10) {
                    strh = "0" + Integer.toString(h);
                }
            }
            rsTime = strh + ":" + strj;

            if (j == 0) {
                rsTime = strh + ":00";

            }

        }
        return rsTime;
    }


    /**
     * 取得系统当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        Calendar calValue = Calendar.getInstance();
        return new Date(calValue.getTime().getTime());
    }

    /**
     * 取得当前日期years年后的日期
     *
     * @param years int
     * @return Date
     */
    public static Date getAfterDateByYears(int years) {
        Calendar calValue = Calendar.getInstance();
        calValue.add(Calendar.YEAR, years);
        return new Date(calValue.getTime().getTime());
    }

    /**
     * 取得当前日期days天后的日期
     *
     * @param days int
     * @return Date
     */
    public static Date getAfterDateByDays(int days) {
        Calendar calValue = Calendar.getInstance();
        calValue.add(Calendar.DATE, days);
        return new Date(calValue.getTime().getTime());
    }


    /**
     * 取得 year 年的第weeksOfYear周的第一天和最后一天的数组。
     *
     * @param year int
     * @return Date[]
     */
    public static Date[] getBetweenDate(int year, int weeksOfYear) {
        Calendar c = Calendar.getInstance();

        if (year <= 0) {
            year = c.get(Calendar.YEAR);
        }
        if (weeksOfYear <= 0) {
            weeksOfYear = c.get(Calendar.WEEK_OF_YEAR) - 1;
        }
        c.set(Calendar.YEAR, year);
        Date[] betweens = new Date[2];

        c.set(Calendar.DAY_OF_WEEK, 1);
        c.set(Calendar.WEEK_OF_YEAR, weeksOfYear);
        betweens[0] = toDate(new Date(c.getTime().getTime()).toString());

        c.set(Calendar.DAY_OF_WEEK, 7);
        c.set(Calendar.WEEK_OF_YEAR, weeksOfYear);
        betweens[1] = toEndDate(new Date(c.getTime().getTime()).toString());

        return betweens;
    }

    /**
     * 取得enddate 之间 startdate的秒数
     *
     * @param startdate Date
     * @param enddate   Date
     * @return int
     */
    public static int getSeconds(Date startdate, Date enddate) {
        long time = enddate.getTime() - startdate.getTime();
        int totalS = new Long(time / 1000).intValue();
        return totalS;
    }

    /**
     * 取得当前年份
     *
     * @return int
     */
    public static int getCurrentYear2() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }


    /**
     * 如果 s ＝null ，则返回null；
     * 否则按s.toString()字符串内容构造日期：
     * 格式为 “yyyy-mm-dd HH:mm:ss.fffffffff”，“yyyy-mm-dd HH:mm:ss”
     * 或“yyyy-mm-dd”有效。
     *
     * @param s String
     * @return Date
     */
    public static Date toDate(Object o) {
        return toDate(toString(o));
    }

    /**
     * 返回d
     *
     * @param d Date
     * @return Date
     */
    public static Date toDate(Date d) {
        return d;
    }

    /**
     * 如果s ＝ null 返回 null
     * 否则按s.toString()字符串内容构造日期,并设置日期的时分秒为“23：59：59”：
     * 格式为 “yyyy-mm-dd HH:mm:ss.fffffffff”，“yyyy-mm-dd HH:mm:ss”
     * 或“yyyy-mm-dd”有效。
     *
     * @param s Object
     * @return Date
     */
    public static Date toEndDate(Object o) {
        return getDayEnd(toDate(toString(o)));
    }

    /**
     * 返回Object类型的字符串形式;
     * 如果为null 则返回“”；否则返回object.toString();
     *
     * @param object Object
     */
    public static String toString(Object object) {
        return (null == object) ? "" : object.toString();
    }

    /**
     * 如果s ＝ null 返回 null
     * 否则设置日期的时分秒为“23：59：59”：
     *
     * @param s Object
     * @return Date
     */
    public static Date getDayEnd(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return new Date(c.getTime().getTime());
    }

    /**
     * 如果s ＝ null 返回 null
     * 否则设置日期的时分秒为“0：0：0”：
     *
     * @param d Date
     * @return Date
     */
    public static Date getDayStart(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return new Date(c.getTime().getTime());

    }

    /**
     * 计算日期间隔的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     * @pre beginDate != null
     */
    public static int daysBetweenDates(Date beginDate, Date endDate) {
        int days = 0;
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(beginDate);
        caln.setTime(endDate);
        int oday = calo.get(6);
        int nyear = caln.get(1);
        for (int oyear = calo.get(1); nyear > oyear; ) {
            calo.set(2, 11);
            calo.set(5, 31);
            days += calo.get(6);
            oyear++;
            calo.set(1, oyear);
        }

        int nday = caln.get(6);
        days = (days + nday) - oday;
        return days;
    }

    /**
     * 字符转化成时间
     *
     * @param strDate      时间字符串
     * @param oracleFormat 格式
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Date stringToDate(String strDate, String oracleFormat) {
        if (strDate == null)
            return null;
        @SuppressWarnings("rawtypes")
        Hashtable h = new Hashtable();
        String javaFormat = "";
        String s = oracleFormat.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");
        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");
        for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
        }

        for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
        }

        for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
        }

        for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
        }

        if (s.indexOf("\u5E74") != -1)
            h.put(new Integer(s.indexOf("\u5E74")), "\u5E74");
        if (s.indexOf("\u6708") != -1)
            h.put(new Integer(s.indexOf("\u6708")), "\u6708");
        if (s.indexOf("\u65E5") != -1)
            h.put(new Integer(s.indexOf("\u65E5")), "\u65E5");
        if (s.indexOf("\u65F6") != -1)
            h.put(new Integer(s.indexOf("\u65F6")), "\u65F6");
        if (s.indexOf("\u5206") != -1)
            h.put(new Integer(s.indexOf("\u5206")), "\u5206");
        if (s.indexOf("\u79D2") != -1)
            h.put(new Integer(s.indexOf("\u79D2")), "\u79D2");
        int i = 0;
        while (h.size() != 0) {
            @SuppressWarnings("rawtypes")
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));
            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat);
        Date myDate = new Date();
        try {
            myDate = df.parse(strDate);
        } catch (Exception e) {
            return null;
        }
        return myDate;
    }

    /**
     * 日期转化成字符串
     *
     * @param d      日期
     * @param format 格式
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String dateToString(Date d, String format) {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = "";
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");
        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");
        for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
        }

        for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
        }

        for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
        }

        for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
        }

        if (s.indexOf("\u5E74") != -1)
            h.put(new Integer(s.indexOf("\u5E74")), "\u5E74");
        if (s.indexOf("\u6708") != -1)
            h.put(new Integer(s.indexOf("\u6708")), "\u6708");
        if (s.indexOf("\u65E5") != -1)
            h.put(new Integer(s.indexOf("\u65E5")), "\u65E5");
        if (s.indexOf("\u65F6") != -1)
            h.put(new Integer(s.indexOf("\u65F6")), "\u65F6");
        if (s.indexOf("\u5206") != -1)
            h.put(new Integer(s.indexOf("\u5206")), "\u5206");
        if (s.indexOf("\u79D2") != -1)
            h.put(new Integer(s.indexOf("\u79D2")), "\u79D2");
        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));
            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat,
                new DateFormatSymbols());
        return df.format(d);
    }


    /**
     * 格式化日期
     *
     * @param d      日期
     * @param format 格式
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String getDate(Date d, String format) {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = "";
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");
        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");
        for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
        }

        for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
        }

        for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
        }

        for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
        }

        if (s.indexOf("\u5E74") != -1)
            h.put(new Integer(s.indexOf("\u5E74")), "\u5E74");
        if (s.indexOf("\u6708") != -1)
            h.put(new Integer(s.indexOf("\u6708")), "\u6708");
        if (s.indexOf("\u65E5") != -1)
            h.put(new Integer(s.indexOf("\u65E5")), "\u65E5");
        if (s.indexOf("\u65F6") != -1)
            h.put(new Integer(s.indexOf("\u65F6")), "\u65F6");
        if (s.indexOf("\u5206") != -1)
            h.put(new Integer(s.indexOf("\u5206")), "\u5206");
        if (s.indexOf("\u79D2") != -1)
            h.put(new Integer(s.indexOf("\u79D2")), "\u79D2");
        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));
            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat,
                new DateFormatSymbols());
        return df.format(d);
    }


    /**
     * 获得oracle格式的日期字符串
     *
     * @param d 日期
     * @return
     */
    public static String getOracleFormatDateStr(Date d) {
        return getDate(d, "YYYY-MM-dd HH24:MI:SS");
    }


    public static Date parseDateTime(String str)
            throws ParseException {
        if (str.length() == 10) str = str + " 00:00:00";
        return parse(str, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseDate(String str)
            throws ParseException {
        if (str.length() > 10) str = str.substring(0, 10);
        return parse(str, "yyyy-MM-dd");
    }

    public static Date parse(String str, String format)
            throws ParseException {
        return (new SimpleDateFormat(format)).parse(str);
    }

    public static int getDays(String startDate, String endDate)
            throws Exception {
        Date sd;
        Date ed;
        sd = parseDate(startDate);
        ed = parseDate(endDate);
        return (int) ((ed.getTime() - sd.getTime()) / 0x5265c00L);
    }

    public static String subDay(String date, int num)
            throws Exception {
        Date rd;
        Date d = parseDate(date);
        rd = new Date(d.getTime() + num * 24L * 60L * 60L * 1000L);
        return formatDate(rd);
    }

    public static int getMonths(String startMonth, String endMonth)
            throws Exception {
        int sy;
        int sm;
        int ey;
        int em;
        sy = Integer.parseInt(startMonth.substring(0, 4));
        sm = Integer.parseInt(startMonth.substring(5, 7));
        ey = Integer.parseInt(endMonth.substring(0, 4));
        em = Integer.parseInt(endMonth.substring(5, 7));
        return ((ey - sy) * 12 + em) - sm;
    }

    public static String subMonth(String month, int num)
            throws Exception {
        int rm;
        int ry;
        int y = Integer.parseInt(month.substring(0, 4));
        int m = Integer.parseInt(month.substring(5, 7));
        rm = m + num;
        ry = y;
        while (rm < 1 || rm > 12)
            if (rm > 12) {
                rm -= 12;
                ry++;
            } else if (rm < 1) {
                rm += 12;
                ry--;
            }
        return (new StringBuilder(String.valueOf(ry))).append("-").append(rm >= 10 ? "" : "0").append(rm).toString();
    }

    /**
     * 昨天
     *
     * @return
     */
    public static Date yesterday() {
        return addDay(-1);
    }

    /**
     * 明天
     *
     * @return
     */
    public static Date tomorrow() {
        return addDay(1);
    }

    /**
     * 按日加
     *
     * @param value
     * @return
     */
    public static Date addDay(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按日加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按月加
     *
     * @param value
     * @return
     */
    public static Date addMonth(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按月加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addMonth(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按年加
     *
     * @param value
     * @return
     */
    public static Date addYear(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按年加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addYear(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按小时加
     *
     * @param value
     * @return
     */
    public static Date addHour(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按小时加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addHour(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按分钟加
     *
     * @param value
     * @return
     */
    public static Date addMinute(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 增加时间,单位为秒
     * 下面有点误差
     * return new Date(date.getTime() + (long)num * 24L * 60L);
     *
     * @param date
     * @param num
     * @return
     */
    public static Date addMinute(Date date, int num) {
        //Date ret = new Date(date.getTime() + (long)num * 24L * 60L);
        //return ret;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, num);
        return calendar.getTime();
    }

    /**
     * 时间相减: 大-小的, 大在前
     * eg: getDiff(toDate("2011-02-02 12:02:04"), toDate("2011-02-01 12:02:04")) = 1
     *
     * @param date      日期
     * @param smallDate 日期
     * @return 返回相减后的日期
     */
    public static long getDiffDay(java.util.Date date, java.util.Date smallDate) {
        if (date == null || smallDate == null) return -1;
        try {
            return ((date.getTime() - smallDate.getTime()) / (24 * 3600 * 1000));
        } catch (Exception e) {
            Lg.error(e);
            return -1;
        }
    }

    public static long getDiffHour(java.util.Date date, java.util.Date smallDate) {
        if (date == null || smallDate == null) return -1;
        try {
            return ((date.getTime() - smallDate.getTime()) / (3600 * 1000));
        } catch (Exception e) {
            Lg.error(e);
            return -1;
        }
    }

    public static long getDiffMinute(java.util.Date date, java.util.Date smallDate) {
        if (date == null || smallDate == null) return -1;
        try {
            return ((date.getTime() - smallDate.getTime()) / (60 * 1000));
        } catch (Exception e) {
            Lg.error(e);
            return -1;
        }
    }

    public static long getDiffSecond(java.util.Date date, java.util.Date smallDate) {
        if (date == null || smallDate == null) return -1;
        try {
            return ((date.getTime() - smallDate.getTime()) / (1000));
        } catch (Exception e) {
            Lg.error(e);
            return -1;
        }
    }

    /**
     * System.out.println(DateUtil.formatDateTime(convertLongToDate(1393645489)));
     * System.out.println(DateUtil.now().getTime()/1000);
     *
     * @param time
     * @return
     */
    public static Date convertLongToDate(long time) {
        try {
            return new java.util.Date(time * 1000 + 129000);
        } catch (Exception e) {
            return new Date();
        }
    }


    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param timeLength Millisecond
     * @return
     */
    public static String toTime(int timeLength) {
        timeLength /= 1000;
        int minute = timeLength / 60;
        int hour = 0;
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        int second = timeLength % 60;
        // return String.format("%02d:%02d:%02d", hour, minute, second);
        return String.format("%02d:%02d", minute, second);
    }

    /**
     * @param timeLength second
     * @return
     */
    public static String toTimeBySecond(int timeLength) {
        // timeLength /= 1000;
        int minute = timeLength / 60;
        int hour = 0;
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        int second = timeLength % 60;
        // return String.format("%02d:%02d:%02d", hour, minute, second);
        return String.format("%02d:%02d", minute, second);
    }

    /**
     * 获取当前时分秒
     *
     * @param time
     * @return
     */
    public static String getCurrTime(long time) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        System.out.println(format.format(date));
        result = format.format(date);
        return result;
    }


    public static String getCurrDate(long time) {
        String timeStr = time + "";
        if (timeStr.length() == 10) {
            time = time * 1000;
        }
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date(time);
        System.out.println(format.format(date));
        result = format.format(date);
        return result;
    }


    public static String getTimestampStr() {
        return Long.toString(System.currentTimeMillis());
    }
}



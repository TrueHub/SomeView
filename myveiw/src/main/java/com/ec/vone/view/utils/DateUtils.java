package com.ec.vone.view.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by user on 2017/4/6.
 */

public class DateUtils {

    private static SimpleDateFormat sf = null;

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date d = new Date(time);
        return sf.format(d);
    }


    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 传入一个long型的time，比较与现在时间的差异
     * @param diff 相差毫秒数
     * @return r
     */
    public static String getSubTimeFromLong(long diff) {
        diff -= System.currentTimeMillis();
        final String HOURS = "h";
        final String MINUTES = "min";
        final String SECONDS = "sec";

        final long MS_IN_A_DAY = 1000 * 60 * 60 * 24;
        final long MS_IN_AN_HOUR = 1000 * 60 * 60;
        final long MS_IN_A_MINUTE = 1000 * 60;
        final long MS_IN_A_SECOND = 1000;
        long numDays = diff / MS_IN_A_DAY;
        diff = diff % MS_IN_A_DAY;
        long numHours = diff / MS_IN_AN_HOUR;
        diff = diff % MS_IN_AN_HOUR;
        long numMinutes = diff / MS_IN_A_MINUTE;
        diff = diff % MS_IN_A_MINUTE;
        long numSeconds = diff / MS_IN_A_SECOND;
        diff = diff % MS_IN_A_SECOND;
        long numMilliseconds = diff;

        StringBuffer buf = new StringBuffer();
        if (numHours > 0) {
            buf.append(numHours + " " + HOURS + ", ");
        }

        if (numMinutes > 0) {
            buf.append(numMinutes + " " + MINUTES);
        }

        buf.append(numSeconds + " " + SECONDS);

        String result = buf.toString();

        if (numSeconds < 1) {
            result = "< 1 second";
        }
        return result;
    }

    public static void main(String[] args){
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTimeZone());
        System.out.println(c.getTimeInMillis());
        System.out.println(c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.getTime());
        c.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        System.out.println(c.getTimeZone());
        System.out.println(c.getTimeInMillis());
        System.out.println(c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.getTime());
    }
}
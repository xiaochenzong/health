package com.iflytek.speech.util;
/*
 *  @项目名：  JiuDian
 *  @包名：    com.szsunson.hotel.util
 *  @文件名:   DatesUtils
 *  @创建者:   ${小陈}
 *  @创建时间:  2017/7/11 11:41
 *  @描述：    计算日期的工具类
 */

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DatesUtils {

    /**
     * 将Date转化成String
     */
    public static String dateToStr(Date date) {
        return dateToStr("yyyy-MM-dd", date);
    }

    /**
     * 将Date转化成String
     */
    public static String dateToStr(String template, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 返回当月的第一天
     */
    public static Date getFirstDay() {
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return gcLast.getTime();
    }

    /**
     * 根据时间字符串得到日期
     */

    public static Date getDateByDateStr(String dateStr) {
        return getDateByDateStr("yyyyMMdd", dateStr);
    }

  /*  public static Date getDateByDateStr(String dateStr)
    {
        return getDateByDateStr("yyyy年MM月dd日", dateStr);
    }*/

    /**
     * 根据指定格式的时间字符串得到日期
     */
    public static Date getDateByDateStr(String template, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateStr, pos);
    }

    /**
     * 获取指定日期的时间戳
     *
     * @param template
     * @param dateStr
     * @return
     */
    public static long getTimestamp(String template, String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(template, Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        Date date = df.parse(dateStr, pos);
        Calendar cal = Calendar.getInstance();
        Log.d("time", "getTimestamp: " + cal);
        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        return timestamp;
    }

    /*
     * 获取到当前时间
     *
     */
    public static String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /*
     * 获取到当前时间(时分秒)
     * */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getNowTime(String template) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template);
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据时间字符串得到Calendar
     */
    public static Calendar getCalendarByDateStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = sdf.parse(dateStr, pos);
        calendar.setTime(strtodate);
        return calendar;
    }

    /**
     * 得到选择的日期的近几天
     */
    public static Date getLatelyDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        return daysBetween(smdate, bdate, "yyyy-MM-dd");
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate, String template) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        Log.d("aaaa", "daysBetween: smdate ====" + smdate + "bdate ====" + bdate + "sdf===" + sdf);
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        //long between_days = Math.abs((time2 - time1)) / (1000 * 3600 * 24);
        //long between_days = (time2 - time1) / (1000 * 3600 * 24);
        long between_days = (time2 - time1);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getRefreshTime(Context context) {

        return "最近更新:" + android.text.format.DateUtils.formatDateTime(context,
                System.currentTimeMillis(), android.text.format.DateUtils.FORMAT_24HOUR | android.text.format.DateUtils.FORMAT_SHOW_TIME
                        | android.text.format.DateUtils.FORMAT_SHOW_DATE
                        | android.text.format.DateUtils.FORMAT_ABBREV_ALL);
    }

    /*
     * 转化成yyyy年MM月dd日
     * */
    public static String Time(String time) {
        int a = time.indexOf("-");
        StringBuffer s = new StringBuffer(time.split(" ")[0]);
        s.replace(a, a + 1, "年");
        int a1 = s.indexOf("-");
        s.replace(a1, a1 + 1, "月");
        String ss = new String(s);
        ss = ss + "日";
        return ss;
    }

    /**
     * 日期格式转换
     *
     * @param dateStrTemplate yyyyMMdd
     * @param dateStr         20190721
     * @param template        yyyy/MM/dd
     * @return
     */
    public static String formatConversion(String dateStrTemplate, String dateStr, String template) {
        final Date dateByDateStr = getDateByDateStr(dateStrTemplate, dateStr);
        return dateToStr(template, dateByDateStr);
    }

    /**
     * 毫秒值转时分
     *
     * @param l
     * @return
     */
    public static String millisToStringShort(long l) {
        StringBuffer sb = new StringBuffer();
        long millis = 1;
        long seconds = 1000 * millis;
        long minutes = 60 * seconds;
        long hours = 60 * minutes;
        long days = 24 * hours;
        if (l / days >= 1)
            sb.append((int) (l / days) + "天");
        if (l % days / hours >= 1)
            sb.append((int) (l % days / hours) + "小时");
        if (l % days % hours / minutes >= 1)
            sb.append((int) (l % days % hours / minutes) + "分");
        /*if(l%days%hours%minutes/seconds>=1)
            sb.append((int)(l%days%hours%minutes/seconds)+"秒");
        if(l%days%hours%minutes%seconds/millis>=1)
            sb.append((int)(l%days%hours%minutes%seconds/millis)+"毫秒");*/
        return sb.toString();
    }

    /**
     * 将int类型数字转换成时分秒毫秒的格式数据
     *
     * @param time long类型的数据
     * @return HH:mm:ss.SSS
     * @author zero 2019/04/11
     */
    public static String msecToTime(Long time) {
        String timeStr = null;
        Long day = 0L;
        Long hour = 0L;
        Long minute = 0L;
        Long second = 0L;
        Long millisecond = 0L;
        if (time <= 0)
            return "00:00:00.00";
        else {
            second = time / 1000;
            minute = second / 60;
            hour = minute / 60;
            millisecond = time % 1000;
            if (second < 60) {
                timeStr = "00:00:00:" + unitFormat(second);
            } else if (minute < 60) {
                second = second % 60;
                timeStr = "00:00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else if (hour < 24) {// 数字>=3600 000的时候
                hour = minute / 60;
                minute = minute % 60;
                second = second - hour * 3600 - minute * 60;
                timeStr = "00:" + unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                day = hour / 24;
                hour = minute / 60;
                minute = minute % 60;
                second = second - hour * 3600 - minute * 60;
                timeStr = unitFormat(day) + ":" + unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }

        }
        return timeStr;
    }

    public static String unitFormat(Long i) {// 时分秒的格式转换
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String unitFormat2(Long i) {// 毫秒的格式转换
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "00" + Long.toString(i);
        else if (i >= 10 && i < 100) {
            retStr = "0" + Long.toString(i);
        } else
            retStr = "" + i;
        return retStr;
    }

}

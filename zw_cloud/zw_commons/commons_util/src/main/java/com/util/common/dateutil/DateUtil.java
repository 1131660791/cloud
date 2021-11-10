package com.util.common.dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期类工具类
 *
 * @author hzw
 * @Copyright
 * @Date:2015年6月15日
 */
public class DateUtil {

    /**
     * 获取当前的时间：字符串类型
     *
     * @return
     * @author hzw
     * @Date 2015年6月15日
     */
    public static String getCurrentTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * <p>
     * 获取当前的时间：long类型
     * </p>
     *
     * @return
     * @author hzw
     * @Date 2015年3月5日
     */
    public static long getCurrentTimeLong() {
        Date date = new Date();
        long currentTime = date.getTime();
        return currentTime;
    }

    /**
     * 获取当前的日期：long类型
     *
     * @return
     * @author hzw
     * @Date 2015年6月15日
     */
    public static Long getCurrentDateLong() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cDate = sdf.format(new Date());
        Date dateCurrent = null;
        try {
            dateCurrent = new SimpleDateFormat("yyyy-MM-dd").parse(cDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateCurrent.getTime();
    }

    /**
     * 获取当前日期：字符串类型
     *
     * @return
     * @author zlf
     * @Date 2015年6月15日
     */
    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 把yyyy-MM-dd解析为long类型时间
     *
     * @param dateStr
     * @return
     * @author zlf
     * @Date 2015年6月15日
     */
    public static long parseDateMyYYMMDDToLong(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception ex) {

        }
        return date.getTime();
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss解析为long类型时间
     *
     * @param dateStr
     * @return
     * @author zlf
     * @Date 2015年6月15日
     */
    public static long parseDateMyYYMMDDHHMMSSToLong(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception ex) {

        }
        return date.getTime();
    }

    /**
     * 把yyyyMMddHHmmss解析为long类型时间
     *
     * @param dateStr
     * @return
     * @author hzw
     * @Date 2015年6月15日
     */
    public static long parseYYYYMMDDHHMMSSToLong(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 把yyyyMMddHHmmss格式化为yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     * @author hzw
     * @Date 2015年6月15日
     */
    public static String formatDateToNormal(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 日期转换成字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param hzw
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取本周第一天
     *
     * @return
     */
    public static String getWeekStartDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        String date1 = df.format(date);
        return date1 + " 00:00:00";
    }

    /**
     * 获取当前时间所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfWeek() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        Date Time = c.getTime();
        String date1 = df.format(Time);
        return date1 + " 23:59:59";
    }

    /**
     * 获取本月第一天
     *
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    /**
     * 获取当天 晚上 11.59
     *
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();
    }

    /**
     * 获取现在日期 加上天数的日期
     *
     * @param createDate 开始天数
     * @param day        天数
     * @throws ParseException
     */
    public static Date getUltimately(Date createDate, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(createDate);
        ca.add(Calendar.DATE, day); // num为增加的天数，可以改变的
        createDate = ca.getTime();
        String enddate = format.format(createDate);
        return createDate;
    }
}

package com.emagsoftware.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * @Title: 公共方法
 * @Description:
 * @Copyright: Copyright (c) 2011-8-22
 * @Company: 幻方朗睿
 * @Author: jiangy
 * @Version: 1.0
 */

public final class BaseUtils {

    /**
     * 构造函数
     */
    private BaseUtils() {
    }

    /**
     * 获取系统换行符
     *
     * @return 换行符
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 判断是数字
     *
     * @param param 参数
     * @return boolean
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNum(String param) {
        if (null != param && param.matches("\\d+")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是数字（有小数位）
     *
     * @param param 参数
     * @return boolean
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNumFloat(String param) {
        if (null != param && (param.matches("[0-9]+.[0-9]+") || param.matches("[0-9]+"))) {
            return true;
        }
        return false;
    }

    /**
     * 判断数字是1~10
     *
     * @param param 参数
     * @return boolean
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public static boolean isOneToTen(String param) {
        if (null != param && param.matches("[1-9]") || param.matches("10")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是Email
     *
     * @param email Email
     * @return boolean
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            String regu = "(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|com|gov|mil|org|edu|int|name|asia)$";
            if (email.matches(regu)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前时间
     *
     * @param style 格式
     * @return String 当前时间
     * @throws 无
     * @see [类、类#方法、类#成员]
     */
    public static String getCurrentDate(String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取日志Key
     *
     * @return 日志Key
     */
    public synchronized static long getLogId() {
        long time = System.currentTimeMillis();
        Random rdm = new Random();
        return time + rdm.nextInt(100);
    }

    /**
     * 把NULL值转化为空("")
     *
     * @param strValue String 传入一个字符串
     * @return String strValue=null,return"";str=str
     */

    public static String getString(String strValue) {
        try {
            if (strValue == null) {
                return "";
            }
            return strValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static boolean isEmpty(Object obj) {
        return null == obj || obj.equals("");
    }

    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * Date类型转String
     */
    public static String DateToString(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 在传入日期上加hours个小时
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, Integer hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }


    public static void main(String[] args) {

        System.out.println(addHours(new Date(), 3));

    }
}

package com.xlfd.commonutilsx;

import android.content.Context;
import android.text.format.DateFormat;

public class TimeUtils {
    /**
     * 判断当前是否是24小时制
     *
     * @param context
     * @return
     */
    public static boolean is24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }


    /**
     * 判断当前年份是否是闰年
     *
     * @param year
     * @return 闰年 true；   平年 false
     */
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) return true;
        if ((year % 4 == 0) && (year % 100 == 0)) return true;
        return false;
    }

    /**
     * 获取一个月共有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayOfMonth(int year, int month) {
        if (month == 2) {
            if (isLeapYear(year)) return 29;
            else return 28;
        }
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) {
            return 31;
        } else {
            return 30;
        }
    }


}

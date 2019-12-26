package com.ace.study.spike.utls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // 按照传入的格式生成一个simpledateformate对象
    static long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
    static long nh = 1000 * 60 * 60;// 一小时的毫秒数
    static long nm = 1000 * 60;// 一分钟的毫秒数
    static long ns = 1000;// 一秒钟的毫秒数
    static long diff;
    static long day = 0;
    static long hour = 0;
    static long min = 0;
    static long sec = 0;


    public static Long dateDiffMin(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return diffMinutes;
    }

    public static Long dateDiffSec(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds  = diff / 1000 % 60;
        return diffSeconds ;
    }


    /**
     * @Author Ace
     * @Description
     * 获取相差几分钟
     * @Date 2019/12/26 10:09
     * @Param [startTime, endTime, format, str]
     * @return java.lang.Long
    **/
    public static Long strDiffMin(String startTime, String endTime,
                                String format, String str) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return min;
    }

    /**
     * @Author Ace
     * @Description
     * 获取相差几秒
     * @Date 2019/12/26 10:10
     * @Param [startTime, endTime, format, str]
     * @return java.lang.Long
    **/
    public static Long strDiffSec(String startTime, String endTime,
                                   String format, String str) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sec;
    }



}

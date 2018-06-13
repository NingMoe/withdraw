package com.phicomm.smarthome.ssp.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiangrong.ke on 2017/6/22.
 */
public class CommonUtils {

    private static final Logger LOGGER = LogManager.getLogger(CommonUtils.class);

    /*
     * 将时间转换为时间戳(注意返回值是秒）
     */
    public static long dateToStamp(String s) {
        return dateToStamp(s, "yyyy-MM-dd");
    }

    /*
     * 将时间转换为时间戳 (注意返回值是秒）
     */
    public static long dateToStamp(String s, String pattern) {
        long res = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(s);
            res = date.getTime() / 1000;

        } catch (ParseException e) {
            res = 0;
        }
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static int getNowTimeToStamp() {
        int res = 0;
        Date date = new Date();
        res = (int) (date.getTime() / 1000);
        return res;
    }

    // 日期合法性校验，
    public static void isValidData(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        format.parse(strDate);
    }

    /**
     *
     * @param timeStamp
     *            单位毫秒
     * @param pattern
     * @return
     */
    public static String stampToDateTimeStr(long timeStamp, String pattern) {
        LOGGER.info("stampToDateTimeStr timeStamp=" + timeStamp + ",pattern=" + pattern);
        Timestamp ts = new Timestamp(timeStamp * 1000);
        String tsStr = "";
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            tsStr = df.format(ts);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return tsStr;
    }

    public static String stampToDateTimeStr(long timeStamp) {
        return stampToDateTimeStr(timeStamp, "yyyy-MM-dd HH:mm:ss");
    }
}

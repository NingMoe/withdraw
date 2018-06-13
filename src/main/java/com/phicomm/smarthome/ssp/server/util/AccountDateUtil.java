package com.phicomm.smarthome.ssp.server.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.phicomm.smarthome.ssp.server.model.WeekModel;
import com.phicomm.smarthome.util.OptDateUtil;

/**
 * 日期时间操作类
 * 
 * @author fujiang.mao
 *
 */
public class AccountDateUtil {

    public static final Logger LOGGER = LogManager.getLogger(AccountDateUtil.class);

    private static transient int gregorianCutoverYear = 1582;

    /** 闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /** 非闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /** 代表数组里的年、月、日 */
    private static final int Y = 0;
    private static final int M = 1;
    private static final int D = 2;

    // 获取当前日期所在周的上下界
    public static final int CURRENT_WEEK = 0;
    // 获取当前日期上一周的上下界
    public static final int LAST_WEEK = 1;
    // 获取当前日期所在月的上下界
    public static final int CURRENT_MONTH = 2;
    // 获取当前日期上一月的上下界
    public static final int LAST_MONTH = 3;
    // 获取当前日期所在月的上下界
    public static final int CURRENT_YEAR = 4;
    // 获取当前日期上一月的上下界
    public static final int LAST_YEAR = 5;

    // 一周的秒数 60*60*24*7
    private static long SevenDay = 604800;

    /**
     * 将代表日期的字符串分割为代表年月日的整形数组
     * 
     * @param date
     * @return
     */
    public static int[] splitYMD(String date) {
        date = date.replace("-", "");
        int[] ymd = { 0, 0, 0 };
        ymd[Y] = Integer.parseInt(date.substring(0, 4));
        ymd[M] = Integer.parseInt(date.substring(4, 6));
        ymd[D] = Integer.parseInt(date.substring(6, 8));
        return ymd;
    }

    /**
     * 检查传入的参数代表的年份是否为闰年
     * 
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ? ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
                : (year % 4 == 0);
    }

    /**
     * 日期加1天
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static int[] addOneDay(int year, int month, int day) {
        if (isLeapYear(year)) {
            day++;
            if (day > DAYS_P_MONTH_LY[month - 1]) {
                month++;
                if (month > 12) {
                    year++;
                    month = 1;
                }
                day = 1;
            }
        } else {
            day++;
            if (day > DAYS_P_MONTH_CY[month - 1]) {
                month++;
                if (month > 12) {
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }
        int[] ymd = { year, month, day };
        return ymd;
    }

    /**
     * 将不足两位的月份或日期补足为两位
     * 
     * @param decimal
     * @return
     */
    public static String formatMonthDay(int decimal) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(decimal);
    }

    /**
     * 将不足四位的年份补足为四位
     * 
     * @param decimal
     * @return
     */
    public static String formatYear(int decimal) {
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(decimal);
    }

    /**
     * 计算两个日期之间相隔的天数
     * 
     * @param startDatea
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long countDay(String startDatea, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start;
        Date end;
        long day = 0;
        try {
            start = format.parse(startDatea);
            end = format.parse(endDate);
            day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil countDay error " + e.getMessage());
        }
        return day;
    }

    /**
     * 获取日期范围内所有日期
     * 
     * @param startDate
     * @param endDate
     * @return xxxx-xx-xx
     */
    public static List<String> getEveryday(String startDate, String endDate) {
        long days = countDay(startDate, endDate);
        int[] ymd = splitYMD(startDate);
        List<String> everyDays = new ArrayList<String>();
        everyDays.add(startDate);
        for (int i = 0; i < days; i++) {
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
            everyDays.add(formatYear(ymd[Y]) + "-" + formatMonthDay(ymd[M]) + "-" + formatMonthDay(ymd[D]));
        }
        return everyDays;
    }

    /**
     * 获取日期间隔天数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDifferentDays(String startDate, String endDate) {
        int days = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start;
        Date end;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
            days = (int) ((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil getDifferentDays error " + e.getMessage());
        }
        return days;
    }

    /**
     * 获取日期间隔周数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDifferentWeeks(String startDate, String endDate) {
        int weeks = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long from = sdf.parse(startDate).getTime();
            long to = sdf.parse(endDate).getTime();
            weeks = (int) ((to - from) / (1000 * 3600 * 24 * 7));
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil getDifferentWeeks error " + e.getMessage());
        }
        return weeks;
    }

    /**
     * 获取两个日期间隔月数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDifferentMonth(String startDate, String endDate) {
        int month = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(startDate));
            c2.setTime(sdf.parse(endDate));
            int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            year = Math.abs(year);
            month = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
            month = Math.abs(month + year * 12);
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil getDifferentMonth error " + e.getMessage());
        }
        return month;
    }

    /**
     * 将日期xxxx/xx/xx转为xxxx-xx-xx格式
     * 
     * @param date
     * @return
     */
    public static String reverseDateFormat(String date) {
        String result = "";
        try {
            String[] array = date.split("/");
            for (String str : array) {
                // 一位数字自动补0
                if (str.length() < 2) {
                    str = "0" + str;
                }
                result += str + "-";
            }
            result = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            LOGGER.error("AccountDateUtil reverseDateFormat error " + e.getMessage());
        }
        return result;
    }

    /**
     * 日期自动补零
     * 
     * @param date
     * @return
     */
    public static String formatDateZero(String date) {
        String result = "";
        try {
            String[] array = date.split("-");
            for (String str : array) {
                // 一位数字自动补0
                if (str.length() < 2) {
                    str = "0" + str;
                }
                result += str + "-";
            }
            result = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            LOGGER.error("AccountDateUtil formatDateZero error " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据日期计算所在周的上下界
     * 
     * @param time
     */
    public static Map<String, Object> convertWeekByDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Map<String, Object> map = new HashMap<String, Object>();
        Date time;
        try {
            time = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            LOGGER.debug("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
            cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            String imptimeBegin = sdf.format(cal.getTime());
            cal.add(Calendar.DATE, 6);
            String imptimeEnd = sdf.format(cal.getTime());

            map.put("first", imptimeBegin);

            map.put("last", imptimeEnd);
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil convertWeekByDate error " + e.getMessage());
        }

        return map;
    }

    /**
     * 遍历出日期范围内的所有周一和周日日期
     * 
     * @param start
     * @param end
     * @return
     */
    public static List<WeekModel> getWeekSplit(String start, String end) {
        ArrayList<WeekModel> weekList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);

            Calendar firstMondayday = Calendar.getInstance();
            firstMondayday.set(startCal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
            while (firstMondayday.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                firstMondayday.add(Calendar.DAY_OF_YEAR, 1);
            }
            while (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                startCal.add(Calendar.DAY_OF_YEAR, 1);
            }
            while (endCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                endCal.add(Calendar.DAY_OF_YEAR, -1);
            }
            LOGGER.debug(sdf.format(startCal.getTime()) + "\t" + sdf.format(endCal.getTime()));
            int startYear = startCal.get(Calendar.YEAR);
            while (startCal.compareTo(endCal) < 0) {
                int endYear = startCal.get(Calendar.YEAR);
                if (startYear < endYear) {
                    firstMondayday.set(endYear, Calendar.JANUARY, 1, 0, 0, 0);
                    while (firstMondayday.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                        firstMondayday.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    startYear = endYear;
                }
                WeekModel everyWeek = new WeekModel();
                long weekNum = (startCal.getTimeInMillis() / 1000 - firstMondayday.getTimeInMillis() / 1000) / SevenDay
                        + 1;
                String weekNumStr = String.valueOf(weekNum);
                if (weekNum < 10) {
                    weekNumStr = "0" + weekNum;
                }
                everyWeek.setYear(startCal.get(Calendar.YEAR));
                everyWeek.setWeekBegin(sdf.format(startCal.getTime()));
                startCal.add(Calendar.DATE, 6);
                everyWeek.setWeekEnd(sdf.format(startCal.getTime()));
                everyWeek.setWeekNum(weekNumStr);
                startCal.add(Calendar.DATE, 1);
                weekList.add(everyWeek);
            }
            Iterator<WeekModel> iter = weekList.iterator();
            LOGGER.debug("开始计算日期范围内周开始和周结束");
            while (iter.hasNext()) {
                WeekModel everyweek = iter.next();
                LOGGER.debug(everyweek.getYear() + "年第" + everyweek.getWeekNum() + "周\t" + "开始时间："
                        + everyweek.getWeekBegin() + "\t结束时间" + everyweek.getWeekEnd());
            }
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil getWeekSplit error " + e.getMessage());
        }
        return weekList;
    }

    /**
     * 将xxxx-xx-xx格式化为xx-xx（月-日）
     * 
     * @param date
     * @return
     */
    public static String formatDateOnlyMD(String date) {
        String result = "";
        if (date.length() == 10) {
            result = date.substring(date.length() - 5, date.length());
        } else {
            LOGGER.error("AccountDateUtil formatDateOnlyMD error date unlegal");
        }
        return result;
    }

    /**
     * 获取日期范围内所有月份
     * 
     * @param startDate
     * @param endDate
     * @return xxxx-xx（年-月）
     */
    public static List<String> getMonthBetween(String startDate, String endDate) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(startDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(endDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (Exception e) {
            LOGGER.error("AccountDateUtil getMonthBetween error date unlegal");
        }
        return result;
    }

    /**
     * 获取日期所在月份天数
     * 
     * @param dateStr
     * @return
     */
    public static int getDaysOfMonth(String dateStr) {
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            LOGGER.error("AccountDateUtil getDaysOfMonth error " + e.getMessage());
        }
        return days;
    }

    /**
     * 获取日期N天前的日期
     * 
     * @param dateStr
     * @param beforeDays
     * @return
     */
    public static String getDayBeforeDate(String dateStr, int beforeDays) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -beforeDays);
        result = sdf.format(calendar.getTime());
        return result;
    }

    /**
     * 获取日期N天后的日期
     * 
     * @param dateStr
     * @param beforeDays
     * @return
     */
    public static String getDayAfterDate(String dateStr, int afterDays) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, afterDays);
        result = sdf.format(calendar.getTime());
        return result;
    }

    /**
     * 比较两个日期大小
     * 
     * @param firstDate
     *            xxxx-xx-xx
     * @param secondDate
     *            xxxx-xx-xx
     * @return
     */
    public static boolean compareTwoDate(String firstDate, String secondDate) {
        return (int) (OptDateUtil.getLTimeByStr(firstDate + " 00:00:00")
                - OptDateUtil.getLTimeByStr(secondDate + " 00:00:00")) >= 0 ? true : false;
    }

    /**
     * 获取当前日期
     * 
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 20180101转2018-01-01
     * 
     * @param date
     * @return
     */
    public static String getDateByDtStr(String str) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(str);
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
            result = sdfs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getNowTime());
        System.out.println(getNowDate());
    }

}
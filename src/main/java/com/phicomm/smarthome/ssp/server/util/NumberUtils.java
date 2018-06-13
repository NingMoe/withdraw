package com.phicomm.smarthome.ssp.server.util;

import java.math.BigDecimal;

import com.phicomm.smarthome.util.StringUtil;

public class NumberUtils {
    /**
     * 对double数据进行取精度.
     * 
     * @param value
     *            double数据.
     * @param scale
     *            精度位数(保留的小数位数).
     * @param roundingMode
     *            精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    public static double round(String value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * double 相加
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2);
    }

    public static BigDecimal sum(String s1, String s2) {
        BigDecimal bd1 = new BigDecimal(s1);
        BigDecimal bd2 = new BigDecimal(s2);
        return bd1.add(bd2);
    }

    public static BigDecimal sum(String s1, double d2) {
        BigDecimal bd1 = new BigDecimal(s1);
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2);
    }

    public static BigDecimal sum(BigDecimal bd1, String s2) {
        BigDecimal bd2 = new BigDecimal(s2);
        return bd1.add(bd2);
    }

    /**
     * double 相减
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法 当然在此之前，你要判断分母是否为0， 为0你可以根据实际需求做相应的处理
     * 
     * @param d1
     * @param d2
     * @param scale
     *            四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {

        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 分转元
     * 
     * @param amount
     * @return
     */
    public static String changeF2Y(String amount) {
        if (StringUtil.isNullOrEmpty(amount)) {
            return "0";
        }
        String currencyFenRegex = "\\-?[0-9]+";
        if (!amount.matches(currencyFenRegex)) {
            // throw new Exception("金额格式有误");
            return "0";
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 分转元
     * 自动补零  x.xx元
     * @param amount
     * @return
     */
    public static String changeF2YZero(String amount) {
        if (StringUtil.isNullOrEmpty(amount)) {
            return "0";
        }
        String currencyFenRegex = "\\-?[0-9]+";
        if (!amount.matches(currencyFenRegex)) {
            // throw new Exception("金额格式有误");
            return "0";
        }
        BigDecimal divide = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100));
        return String.format("%.2f", divide);
    }
    
    public static String bigdecimalSubByStr(String num1, String num2) {
        if(StringUtil.isNullOrEmpty(num1)||StringUtil.isNullOrEmpty(num2)) {
            return "0";
        }
        BigDecimal bigDecimalOne = new BigDecimal(num1);
        BigDecimal bigDecimalTwo = new BigDecimal(num2);
        return bigDecimalOne.subtract(bigDecimalTwo).toString();
        
    }

}

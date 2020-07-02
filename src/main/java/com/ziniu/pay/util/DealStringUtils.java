package com.ziniu.pay.util;

import java.io.UnsupportedEncodingException;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/3 19:15
 * @Description : 字符串处理
 */
public class DealStringUtils {

    public static String getResPonse(String str) {
        String[] split = str.split("#");
        if (split != null && split.length > 0) {
            return split[0];
        }else{
            return null;
        }
    }
    public static String getResData(String str) {
        String[] split = str.split("#");
        if (split != null && split.length > 1) {
            String resCode = split[0];
            return str.substring(resCode.length(),str.lastIndexOf("#@@@@"));
        }else{
            return null;
        }
    }

    public static String toGbk(String plain){
        byte[] bytes = new byte[plain.length()];
        byte[] bytes2 = new byte[plain.length()];
        try {
            System.err.println("before:"+plain);
            bytes = plain.getBytes("utf-8");
            bytes2 = new String(bytes, "utf-8").getBytes("gb2312");
            plain=new String(bytes2, "gb2312");
            System.err.println("last:"+plain);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return plain;
    }

    public static String toUtf8(String plain){
        byte[] bytes = new byte[plain.length()];
        byte[] bytes2 = new byte[plain.length()];
        try {
            System.err.println("before:"+plain);
            bytes = plain.getBytes("gbk");
            bytes2 = new String(bytes, "gbk").getBytes("utf-8");
            plain=new String(bytes2, "utf-8");
            System.err.println("LAST:"+plain);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return plain;
    }
//    public static void main(String[] args) {
//        String str= "000000#111111111111#20100505#2#1|Rmb|123454|0|100.00|2222222222|Zhang|Huaxiaxing|20000.00|Sdfsd|Qwer|1|Sdf|Sdfs|#2|Rmb|123455|0|100.00|2222222222|李|北京华夏|20000.00|Sdfsd|Qwer|1|Sdf|Sdfs|#@@@@"
//                ;
//        String resData = getResPonse(str);
//        System.err.println(resData);
//
//        String resData1 = getResData(str);
//        System.err.println(resData1);
//
//    }

}

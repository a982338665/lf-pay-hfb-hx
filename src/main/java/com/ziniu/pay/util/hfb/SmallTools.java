package com.ziniu.pay.util.hfb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/27.
 */
public class SmallTools {
    /**
     * MD5加密
     * @param str 需要加密的值
     * @return 加密完成的值(小写)
     */
    public static String MD5en(String str){
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }


    /**
     * 获取当前时间（格式自传）
     * @param dateFormat 要返回的时间格式，例如yyyy/MM/dd HH:mm:ss
     * @return
     */
    public static String getDate(String dateFormat){
        Date date = new Date();
        SimpleDateFormat dateF = new SimpleDateFormat(dateFormat);//可以方便地修改日期格式
        String retu = dateF.format(date);
        return retu;
    }


    /**
     * @param str 传入例如aaa=123&bbb=234这种&符号连接的字符串
     * @return 返回key=aaa value=123这样的HashMap
     */
    public static HashMap<String,String> getHash(String str){
        HashMap<String,String> hash = new HashMap<String, String>();
        String[] list = str.split("&");
        for (int i=0; i<list.length; i++){
            String[] i_ss = list[i].split("=");
            if (i_ss.length==1){
                hash.put(i_ss[0],"");
            }else {
                hash.put(i_ss[0],i_ss[1]);
            }
        }
        return hash;
    }
}

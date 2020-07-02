package com.ziniu.pay.util.hfb;

/**
 * Created by Administrator on 2017/10/10.
 */


import com.ziniu.pay.util.hfb.Des.Des;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * 3DES加密
 *
 * @author SHANHY(365384722@QQ.COM)
 * @date 2015-8-18
 */
public class ThreeDES {

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    // DES,DESede,Blowfish

    /**
     * 加密方法
     *
     * @param key
     *            加密密钥，长度为24字节
     * @param str
     *            被加密的数据缓冲区（源）
     * @return
     * @author SHANHY
     * @date 2015-8-18
     */
    public static String encryptMode(String key, String str) {
        try {
            byte[] src = str.getBytes("gbk");
            // 生成密钥
            byte[] keybyte = key.getBytes("UTF-8");
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            System.out.println(c1.doFinal(src));
            return byte2hex(c1.doFinal(src));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key
     *            加密密钥，长度为24字节
     * @param str
     *            加密后的缓冲区
     * @return
     * @author SHANHY
     * @date 2015-8-18
     */
    public static byte[] decryptMode(String key, String str) {
        try {
            byte[] src = a(str);
            System.out.println("byte:" + src);
            // 生成密钥
            byte[] keybyte = key.getBytes("UTF-8");
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成十六进制字符串
     *
     * @param b
     * @return
     * @author SHANHY
     * @date 2015-8-18
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private static final byte[] b = new byte[128];
    /*
    *转换成byte数组
    */
    public static byte[] a(String var0) {
        var0 = var0;
        StringBuffer var1 = new StringBuffer();
        int var2 = var0.length();

        byte var4;
        for(int var3 = 0; var3 < var2; ++var3) {
            boolean var10000;
            label54: {
                if((var4 = (byte)var0.charAt(var3)) != 61) {
                    if(var4 < 0 || var4 >= 128) {
                        var10000 = false;
                        break label54;
                    }

                    if(b[var4] == -1) {
                        var10000 = false;
                        break label54;
                    }
                }

                var10000 = true;
            }

            if(var10000) {
                var1.append(var0.charAt(var3));
            }
        }

        byte[] var8;
        if((var0 = var1.toString()).charAt(var0.length() - 2) == 61) {
            var8 = new byte[(var0.length() / 4 - 1) * 3 + 1];
        } else if(var0.charAt(var0.length() - 1) == 61) {
            var8 = new byte[(var0.length() / 4 - 1) * 3 + 2];
        } else {
            var8 = new byte[var0.length() / 4 * 3];
        }

        int var6 = 0;

        byte var5;
        byte var9;
        byte var10;
        for(int var7 = 0; var6 < var0.length() - 4; var7 += 3) {
            var9 = b[var0.charAt(var6)];
            var10 = b[var0.charAt(var6 + 1)];
            var4 = b[var0.charAt(var6 + 2)];
            var5 = b[var0.charAt(var6 + 3)];
            var8[var7] = (byte)(var9 << 2 | var10 >> 4);
            var8[var7 + 1] = (byte)(var10 << 4 | var4 >> 2);
            var8[var7 + 2] = (byte)(var4 << 6 | var5);
            var6 += 4;
        }

        if(var0.charAt(var0.length() - 2) == 61) {
            var9 = b[var0.charAt(var0.length() - 4)];
            var10 = b[var0.charAt(var0.length() - 3)];
            var8[var8.length - 1] = (byte)(var9 << 2 | var10 >> 4);
        } else if(var0.charAt(var0.length() - 1) == 61) {
            var9 = b[var0.charAt(var0.length() - 4)];
            var10 = b[var0.charAt(var0.length() - 3)];
            var4 = b[var0.charAt(var0.length() - 2)];
            var8[var8.length - 2] = (byte)(var9 << 2 | var10 >> 4);
            var8[var8.length - 1] = (byte)(var10 << 4 | var4 >> 2);
        } else {
            var9 = b[var0.charAt(var0.length() - 4)];
            var10 = b[var0.charAt(var0.length() - 3)];
            var4 = b[var0.charAt(var0.length() - 2)];
            var5 = b[var0.charAt(var0.length() - 1)];
            var8[var8.length - 3] = (byte)(var9 << 2 | var10 >> 4);
            var8[var8.length - 2] = (byte)(var10 << 4 | var4 >> 2);
            var8[var8.length - 1] = (byte)(var4 << 6 | var5);
        }

        return var8;
    }




    /**
     * 测试
     *
     * @param args
     * @author SHANHY
     * @date 2015-8-18
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());

        // 24字节的密钥（我们可以取apk签名的指纹的前12个byte和后12个byte拼接在一起为我们的密钥）
        final String keyBytes = "588AA8D960FA4C4D8558BD72";
        String szSrc = "郭新胜";

        System.out.println("加密前的字符串:" + szSrc);

        String encoded = Des.Encrypt3Des(szSrc, keyBytes,"ToHex16");
        System.out.println("加密后的字符串:" + encoded);

        String srcBytes = Des.Decrypt3Des(encoded, keyBytes,"ToHex16");

        System.out.println("解密后的字符串:" + srcBytes);
    }

}

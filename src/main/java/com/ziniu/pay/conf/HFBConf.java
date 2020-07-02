package com.ziniu.pay.conf;

/**
 * 汇付宝配置信息:
 * 需要更换的配置：
 * 商户id，3des，MD5
 * @author : Mr huangye
 * @createTime : 2020/6/10 19:10
 */
public interface HFBConf {

    /**
     * 小额地址
     */
    String URL_SMALL = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";

    /**
     * 大额地址
     */
    String URL_BIG = "https://Pay.heepay.com/API/PayTransit/PayTransferWithLargeWork.aspx";

    /**
     * 当前接口版本号
     */
    String URL_VERSION = "3";

    /**
     * 商户ID
     */
    String AGENT_ID = "1664222";
    /**
     * 3des
     * String DesKey = "xxxxxxxxxxxxxxxxxxxxxxxx";
     */
    String DES_KEY = "xxxxxxxxxxxxxxxxxxxxxxxx";
    /**
     * md5
     * String key = "xxxxxxxxxxxxxxxxxxxxxxxx"
     */
    String MD5 = "xxxxxxxxxxxxxxxxxxxxxxxx";
    /**
     * 异步通知的的回调地址
     */
    String NOTIFY_URL = "http://www.hy.com";
//    String NOTIFY_URL = "http://a982338665.e1.luyouxia.net:27983/hfb-notice";

    /**
     * 查询uri
     */
    String QUERY_URL = "https://Pay.heepay.com/API/PayTransit/QueryTransfer.aspx" ;



}

package com.ziniu.pay.util.hfb;


import com.ziniu.pay.conf.HFBConf;
import com.ziniu.pay.entity.hfb.PayInfo;
import com.ziniu.pay.entity.hfb.PayInfoDetail;
import com.ziniu.pay.util.hfb.Des.Des;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/10.
 */
@Slf4j
public class PayTransit {
//    public static void main(String[] args) {
//        String s = pay();
//    }

    private static String pay() {
        //请求地址
//        String url = "http://211.103.157.45/payheepay/api/paytransit/PayTransferWithSmallAll.aspx";  //小额
//        String url = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";  //小额
        String url = HFBConf.URL_SMALL;  //小额
        //String url = "https://Pay.heepay.com/API/PayTransit/PayTransferWithLargeWork.aspx";   //大额
        //请求参数
        String version = "3";               //当前接口版本号
        String agent_id = "1664502";    //商户ID
        String batch_no = "2017101010101044";    //批量付款订单号
        String batch_amt = "0.02";       //付款总金额
        String batch_num = "2";   //该次付款总笔数
        //String detail_data = "A123456^2^0^6217000010100164865^张三^0.01^商户付款^北京^北京市^建设银行";           //批付到银行帐户格式
        String detail_data = "A123456^2^0^6217000010100164865^张三^0.01^商户付款^北京^北京市^建设银行|A123456^2^0^6217000010100164865^张三^0.01^商户付款^北京^北京市^建设银行";           //批付到银行帐户格式
        String notify_url = "http://www.hy.com";                //异步通知地址
        String ext_param1 = "123";                //商户自定义
        String DesKey = "B62F36A6DF8C4BC6A0F9B196";
        String key = "06F844CDFBFA4979BD7B5860";               //签名密钥
        String sign = "";                   //签名结果
        //组织签名串
        StringBuilder sign_sb = new StringBuilder();
        sign_sb.append("agent_id").append("=").append(agent_id).append("&")
                .append("batch_amt").append("=").append(batch_amt).append("&")
                .append("batch_no").append("=").append(batch_no).append("&")
                .append("batch_num").append("=").append(batch_num).append("&")
                .append("detail_data").append("=").append(detail_data).append("&")
                .append("ext_param1").append("=").append(ext_param1).append("&")
                .append("key").append("=").append(key).append("&")
                .append("notify_url").append("=").append(notify_url).append("&")
                .append("version").append("=").append(version);
        log.info("签名参数：" + sign_sb.toString().toLowerCase());
        sign = SmallTools.MD5en(sign_sb.toString().toLowerCase());
        log.info("签名结果：" + sign);
        //3DES加密detail_data
        detail_data = Des.Encrypt3Des(detail_data, DesKey, "ToHex16");
        //请求参数
        StringBuilder requestParams = new StringBuilder();
        requestParams.append("version").append("=").append(version).append("&")
                .append("agent_id").append("=").append(agent_id).append("&")
                .append("batch_no").append("=").append(batch_no).append("&")
                .append("batch_amt").append("=").append(batch_amt).append("&")
                .append("batch_num").append("=").append(batch_num).append("&")
                .append("detail_data").append("=").append(detail_data).append("&")
                .append("notify_url").append("=").append(notify_url).append("&")
                .append("ext_param1").append("=").append(ext_param1).append("&")
                .append("sign").append("=").append(sign);
        log.info("请求参数：" + requestParams.toString());
        String retu = HttpUtil.sendPost(url, requestParams.toString());
//        AnalysisDOM4J.parserDOM4ForStrXml(retu);
//        AnalysisDOM4J.parserDOM4ForStrXml("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><ret_code>E110</ret_code><ret_msg>商户订单号已经存在</ret_msg><sign>eefbd57eca9bcac95ad09088dc048d61</sign></root>\n");
//        AnalysisDOM4J.parserDOM4ForStrXml("<root><ret_code>E110</ret_code><ret_msg>商户订单号已经存在</ret_msg><sign>eefbd57eca9bcac95ad09088dc048d61</sign></root>\n");
        log.info(retu);
        System.err.println("+++++++++++++++++++++++++++++++");
        PayInfo payInfo = new PayInfo();
        PayInfoDetail payInfoDetail1 = new PayInfoDetail("A123456", "2", "0", "6217000010100164865", "张三", "0.01", "商户付款",
                "北京", "北京市", "建设银行");
        PayInfoDetail payInfoDetail2 = new PayInfoDetail("A123456", "2", "0", "6217000010100164865", "张三", "0.01", "商户付款",
                "北京", "北京市", "建设银行");
        ArrayList<PayInfoDetail> payInfoDetails = new ArrayList<>();
        payInfoDetails.add(payInfoDetail1);
        payInfoDetails.add(payInfoDetail2);
        payInfo.setDetail_data(payInfoDetails);
        payInfo.setBatch_amt(batch_amt);
        payInfo.setBatch_no(batch_no);
        payInfo.setBatch_num(batch_num);
        payInfo.setExt_param1(ext_param1);
        String s = payInfo.toBuildParamter();
        System.err.println(s);
        System.err.println(s.equals(requestParams.toString()));

        return retu;
    }
}

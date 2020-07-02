package com.ziniu.pay.entity.hfb;

import com.ziniu.pay.conf.HFBConf;
import com.ziniu.pay.util.hfb.Des.Des;
import com.ziniu.pay.util.hfb.SmallTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/11 19:42
 * @Description : 汇付宝支付实体接收类
 */
@Data
@Slf4j
public class PayInfo {

    private String batch_no;
    private String batch_amt;
    private String batch_num;
    private List<PayInfoDetail> detail_data;
    private String ext_param1;

    public String toSelect(){
        String version = HFBConf.URL_VERSION;
        String agent_id = HFBConf.AGENT_ID;
        String key = HFBConf.MD5;
//        String desKey = HFBConf.DES_KEY;
        String sign;
        StringBuilder sign_sb = new StringBuilder();
        sign_sb.append("agent_id").append("=").append(agent_id).append("&")
                .append("batch_no").append("=").append(batch_no).append("&")
                .append("key").append("=").append(key).append("&")
                .append("version").append("=").append(version);
        log.info("签名参数：" + sign_sb.toString().toLowerCase());
        sign = SmallTools.MD5en(sign_sb.toString().toLowerCase());
        log.info("签名结果：" + sign);
        //请求参数
        StringBuilder requestParams = new StringBuilder();
        requestParams.append("version") .append("=").append(version)      .append("&")
                .append("agent_id")     .append("=").append(agent_id)     .append("&")
                .append("batch_no")     .append("=").append(batch_no)     .append("&")
                .append("sign")          .append("=").append(sign);
        log.info("请求参数："+requestParams.toString());
        return requestParams.toString();
    }
    /**
     * 构建请求参数
     *
     * @return
     */
    public String toBuildParamter() {
        String notify_url = HFBConf.NOTIFY_URL;
        String version = HFBConf.URL_VERSION;
        String agent_id = HFBConf.AGENT_ID;
        String key = HFBConf.MD5;
        String desKey = HFBConf.DES_KEY;
        String sign;
        String detailData = getDetailData();
        System.err.println("detail:"+detailData);
        //组织签名串
        StringBuilder sign_sb = new StringBuilder();
        sign_sb.append("agent_id").append("=").append(agent_id).append("&")
                .append("batch_amt").append("=").append(batch_amt).append("&")
                .append("batch_no").append("=").append(batch_no).append("&")
                .append("batch_num").append("=").append(batch_num).append("&")
                .append("detail_data").append("=").append(detailData).append("&")
                .append("ext_param1").append("=").append(ext_param1).append("&")
                .append("key").append("=").append(key).append("&")
                .append("notify_url").append("=").append(notify_url).append("&")
                .append("version").append("=").append(version);
        log.info("签名参数：" + sign_sb.toString().toLowerCase());
        sign = SmallTools.MD5en(sign_sb.toString().toLowerCase());
        log.info("签名结果：" + sign);
        //3DES加密detail_data
        detailData = Des.Encrypt3Des(detailData, desKey, "ToHex16");
        //请求参数
        StringBuilder requestParams = new StringBuilder();
        requestParams.append("version") .append("=").append(version)      .append("&")
                .append("agent_id")     .append("=").append(agent_id)     .append("&")
                .append("batch_no")     .append("=").append(batch_no)     .append("&")
                .append("batch_amt")    .append("=").append(batch_amt)    .append("&")
                .append("batch_num")    .append("=").append(batch_num)	.append("&")
                .append("detail_data")  .append("=").append(detailData)	.append("&")
                .append("notify_url")   .append("=").append(notify_url)	.append("&")
                .append("ext_param1")   .append("=").append(ext_param1)	.append("&")
                .append("sign")          .append("=").append(sign);
        log.info("请求参数："+requestParams.toString());
        return requestParams.toString();
    }


    private String getDetailData() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < detail_data.size(); i++) {
            PayInfoDetail pay = detail_data.get(i);
            if(detail_data.size() -1 == i){
                stringBuffer.append(pay.toConcatString());

            }else{
                stringBuffer.append(pay.toConcatString()).append("|");

            }
        }
        return stringBuffer.toString();
    }

}

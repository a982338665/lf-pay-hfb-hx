package com.ziniu.pay.entity.hfb;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/11 19:46
 * @Description : 支付详情
 * GET请求实例：
 * https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx?
 * version=3&agent_id=1664502&batch_no=20170718080721&batch_amt=0.01&batch_num=1
 * &detail_data=
 * 848163D73BA0FABEEAA1F950B159B1E8AC95057794DEBD5592192974A329DAC2EEF200D91FE721B90DF548B0FF51F422A6E711D08B937D3B9C82E543CC122829E8FE8615AF2087D1BD8B6E07B366506710F99C0DC187E38D&
 * notify_url=http://www.xxx.com/xxx.aspx&ext_param1=%b2%e2%ca%d4&sign=e6e9a7d9c23e2b4b99bfaf3f7bbf4ebe
 */
@Data
@AllArgsConstructor
public class PayInfoDetail {

    private String child_batch_no;
    private String bank_no;
    private String type;
    private String account;
    private String name;
    private String money;
    private String reason;
    private String province;
    private String city;
    private String bank_name;

    public String toConcatString() {
        return new StringBuffer("")
                .append(child_batch_no)
                .append("^")
                .append(bank_no)
                .append("^")
                .append(type)
                .append("^")
                .append(account)
                .append("^")
                .append(name)
                .append("^")
                .append(money)
                .append("^")
                .append(reason)
                .append("^")
                .append(province)
                .append("^")
                .append(city)
                .append("^")
                .append(bank_name)
                .toString();
    }


   /* public static void main(String[] args) {
        String string = new PayInfoDetail("A123456","2","0","6217000010100164865","张三","0.01","商户付款",
                "北京","北京市","建设银行").toConcatString();
        System.err.println(string);
    }*/
}

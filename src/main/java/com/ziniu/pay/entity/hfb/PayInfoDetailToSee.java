package com.ziniu.pay.entity.hfb;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/11 19:46
 * @Description : 支付查询详情
 * GET请求实例：
 * "A1234596^6217000010100164865^张三^0.0100^F^交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配|
 * A12345610^6217000010100164865^张三^0.0100^F^交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配
 * 付款状态S表示付款成功，状态F代表失败
 */
@Data
@AllArgsConstructor
public class PayInfoDetailToSee {

    private String child_batch_no;
    private String account;
    private String name;
    private String money;
    private String code;
    private String reason;

    //A1234596^6217000010100164865^张三^0.0100^F^交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配|A12345610^6217000010100164865^张三^0.0100^F^交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配
    public static List<PayInfoDetailToSee> analyseDetail(String detail){
        List<PayInfoDetailToSee> objects = new ArrayList<>();
        String[] split = detail.split("\\|");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
//            System.err.println(s);
            String[] data = s.split("\\^");
            PayInfoDetailToSee payInfoDetailToSee = new PayInfoDetailToSee(data[0],data[1],data[2],data[3],data[4],data.length == 6?data[5]:"success");
            objects.add(payInfoDetailToSee);
        }
        return objects;
    }
//
//    public static void main(String[] args) {
//        String xxx= "A1234596^6217000010100164865^张三^0.0100^F^交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配";
//
//    }

}

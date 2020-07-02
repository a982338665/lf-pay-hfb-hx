package com.ziniu.pay.util;

import com.ziniu.pay.entity.BankInfo;
import com.ziniu.pay.entity.ReturnError;
import com.ziniu.pay.service.JDBCService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/4 8:42
 * @Description : 汇付宝银行信息导入小程序
 */

@Component
public class ImprotBankUtils {

    @Resource
    JDBCService jdbcService;

    /**
     * 使用junit测试，进行基础数据添加
     */
    public void importData(){
        ArrayList<String> list1 = TextUtil.readTxt("src/main/resources/data/hfb.txt");
        for (String data:list1
        ) {
            //去空格
            String trim = data.trim();
            String[] s = trim.split(" ");
            if(s.length>1){
                System.err.println(s[0]+"|"+s[1]);
            }else{
                System.err.println("======"+trim);
            }
            BankInfo bankInfo = new BankInfo();
            bankInfo.setIsUse("1");
            bankInfo.setOrgNo(s[0]);
            bankInfo.setOrgName(s[1]);
            jdbcService.insertBankInfo(bankInfo);
            //判断首字符为#的去掉
            /*if (!trim.startsWith("#") && trim.length() != 0) {
                System.err.println(trim.length() + "||" + trim + "}}");
                int i = trim.indexOf("=");
                String code = trim.substring(0, i);
                String desc = trim.substring(i + 1, trim.length());
                if(code.equals("BAN118")){
                    fff = true;
                }
                if(fff){
                    ReturnError returnError = new ReturnError();
                    returnError.setErrorCode(code);
                    returnError.setErrorDesc(desc);
                    returnError.setErrorOwner(0);
                    returnError.setErrorType(1);
                    jdbcService.insert(returnError);
                }
            }*/
        }
    }
}

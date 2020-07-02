package com.ziniu.pay.service;

import com.ziniu.pay.entity.AreaInfo;
import com.ziniu.pay.entity.BankInfo;
import com.ziniu.pay.entity.PayLogs;
import com.ziniu.pay.entity.ReturnError;

/**
 * @author : Mr huangye
 * @createTime : 2020/6/3 15:26
 */
public interface JDBCService {


    ReturnError selectByCode(String resPonse);

    void insert(ReturnError returnError);

    void insertLogs(PayLogs payLogs);

    void insertBankInfo(BankInfo bankInfo);

    void insertArearHFB(AreaInfo areaInfo);
}

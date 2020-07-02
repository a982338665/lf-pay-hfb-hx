package com.ziniu.pay.service.impl;

import com.ziniu.pay.dao.JdbcDao;
import com.ziniu.pay.entity.AreaInfo;
import com.ziniu.pay.entity.BankInfo;
import com.ziniu.pay.entity.PayLogs;
import com.ziniu.pay.entity.ReturnError;
import com.ziniu.pay.service.JDBCService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : Mr huangye
 * @createTime : 2020/6/3 15:26
 */
@Service("jdbcService")
public class JDBCServiceImpl implements JDBCService {

    @Resource
    JdbcDao jdbcDao;

    @Override
    public ReturnError selectByCode(String resPonse) {
        try {

            return jdbcDao.selectByCode(resPonse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(ReturnError returnError) {
        jdbcDao.insert(returnError);
    }

    @Override
    public void insertLogs(PayLogs payLogs) {
        jdbcDao.insertPayLogs(payLogs);
    }

    @Override
    public void insertBankInfo(BankInfo bankInfo) {
        jdbcDao.insertBankInfo(bankInfo);
    }

    @Override
    public void insertArearHFB(AreaInfo areaInfo) {

        jdbcDao.insertArearHFB(areaInfo);

    }
}

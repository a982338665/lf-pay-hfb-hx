package com.ziniu.pay.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 汇付宝 银行信息
 *
 * @author lishengbo
 * @Table (t_l_pay_logs)
 * @since 2020-06-03 19:13:11
 */
@Data
public class BankInfo implements Serializable {
    private static final long serialVersionUID = 964463263454986264L;
    private Integer id;
    private String orgNo;
    private String orgName;
    private String isUse;
}

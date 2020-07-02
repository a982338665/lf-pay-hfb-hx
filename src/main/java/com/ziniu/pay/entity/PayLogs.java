package com.ziniu.pay.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付日志记录(PayLogs)实体类
 *
 * @author lishengbo
 * @Table (t_l_pay_logs)
 * @since 2020-06-03 19:13:11
 */
@Data
public class PayLogs implements Serializable {
    private static final long serialVersionUID = 964463263454986264L;
    private Integer id;
    private String reqContent;
    private String resResult;
    private String resCode;
    private String reqType;
    private Date createTime;


}

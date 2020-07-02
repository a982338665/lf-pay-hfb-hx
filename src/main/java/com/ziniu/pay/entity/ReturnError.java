package com.ziniu.pay.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 华夏银行，错误码对应表(ReturnError)实体类
 *
 * @author lishengbo
 * @Table (t_d_return_error)
 * @since 2020-06-03 19:11:21
 */
@Data
public class ReturnError implements Serializable {
    private static final long serialVersionUID = 470739391227023678L;
    private Integer id;
    private String errorCode;
    private String errorDesc;
    private Integer errorOwner;
    private Integer errorType;


}

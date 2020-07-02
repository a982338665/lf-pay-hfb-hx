package com.ziniu.pay.vo;

import com.ziniu.pay.entity.ReturnError;
import lombok.Data;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/3 19:31
 * @Description :
 */
@Data
public class ResponseDataVo {

    private ReturnError returnError;

    private String otherData;
}

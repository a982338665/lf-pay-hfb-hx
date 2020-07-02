package com.ziniu.pay.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 汇付宝 地区信息
 *
 * @author lishengbo
 * @Table (t_d_area_info_hfb)
 * @since 2020-06-03 19:13:11
 */
@Data
public class AreaInfo implements Serializable {
    private static final long serialVersionUID = 964463263454986264L;
    private Integer id;
    private String parentName;
    private String childName;
    private String isUse;
}

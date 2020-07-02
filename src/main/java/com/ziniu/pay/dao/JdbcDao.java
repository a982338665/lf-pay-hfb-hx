package com.ziniu.pay.dao;

import com.ziniu.pay.entity.AreaInfo;
import com.ziniu.pay.entity.BankInfo;
import com.ziniu.pay.entity.PayLogs;
import com.ziniu.pay.entity.ReturnError;
import org.apache.ibatis.annotations.*;

/**
 * jdbc操作通用类
 */
@Mapper
public interface JdbcDao {

    @Select("select * from t_d_return_error where error_code = #{resPonse}")
    ReturnError selectByCode(@Param("resPonse")String resPonse);

    @Insert("replace into t_d_return_error(error_code,error_desc,error_owner,error_type) " +
            "values (#{v.errorCode},#{v.errorDesc},#{v.errorOwner},#{v.errorType})")
    @Options(useGeneratedKeys = true, keyProperty = "v.id")
    void insert(@Param("v")ReturnError returnError);

    @Insert("insert into t_l_pay_logs(req_content,res_result,res_code,req_type,create_time) " +
            "values (#{v.reqContent},#{v.resResult},#{v.resCode},#{v.reqType},#{v.createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "v.id")
    void insertPayLogs(@Param("v") PayLogs logs);

    @Insert("replace into t_d_bank_info_hfb(org_no,org_name,is_use) " +
            "values (#{v.orgNo},#{v.orgName},#{v.isUse})")
    @Options(useGeneratedKeys = true, keyProperty = "v.id")
    void insertBankInfo(@Param("v") BankInfo bankInfo);
    @Insert("replace into t_d_area_info_hfb(parent_name,child_name,is_use) " +
            "values (#{v.parentName},#{v.childName},#{v.isUse})")
    @Options(useGeneratedKeys = true, keyProperty = "v.id")
    void insertArearHFB(@Param("v") AreaInfo areaInfo);

    /* *//**
     * 校验项目名称是否存在:项目名称需要唯一，返回值不能为两个
     *
     * @param projectName
     * @return
     *//*
    @Select("select * from t_s_project where project_name = #{project_name}")
    NBProjectVo getProjectInfo(@Param("project_name") String projectName);

    *//**
     * 校验接口参数是否已添加
     * @return
     *//*
    @Select("select count(0) from t_s_param where interface_id=#{interface_id} and interface_code=#{interface_code}")
    int getInterfaceInfoByInterfaceCode(@Param("interface_id") String interfaceId, @Param("interface_code") String interfaceCode);
    *//**
     * 校验项目下接口是否存在
     *
     * @param vo
     * @return
     *//*
    @Select("select * from t_s_interface where project_code = #{projectCode} and interface_path= #{interfacePath}" +
            " and interface_type=#{interfaceType}")
    NBProjectInterfaceVo getProjectInterfaceInfo(NBProjectInterfaceVo vo);

    *//**
     * 项目表-新增
     *
     * @param vo
     *//*
    @Insert("insert into t_s_project(project_code,project_name,project_des,is_use,created_by,created_time)" +
            "values (#{nBProjectVo.projectCode},#{nBProjectVo.projectName},#{nBProjectVo.projectDes}," +
            "#{nBProjectVo.isUse},#{nBProjectVo.createdBy},#{nBProjectVo.createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "nBProjectVo.id")
    void insert(@Param("nBProjectVo") NBProjectVo vo);

    *//**
     * 项目用户关联表-新增
     *
     * @param vo
     *//*
    @Insert("insert into t_s_project_user(user_id,user_code,project_id,project_code,full_control,read_all," +
            "read_by_creator,write_by_creator,write_all,is_use,created_by,created_time) " +
            "values (#{nBProjectUserVo.userId},#{nBProjectUserVo.userCode},#{nBProjectUserVo.projectId},#{nBProjectUserVo.projectCode}," +
            "#{nBProjectUserVo.fullControl},#{nBProjectUserVo.readAll},#{nBProjectUserVo.readByCreator},#{nBProjectUserVo.writeByCreator}," +
            "#{nBProjectUserVo.writeAll},#{nBProjectUserVo.isUse},#{nBProjectUserVo.createdBy},#{nBProjectUserVo.createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "nBProjectUserVo.id")
    void insertProjectUser(@Param("nBProjectUserVo") NBProjectUserVo vo);


    *//**
     * 项目表-修改
     *
     * @param toBeUpdate
     *//*
    @Update({"<script>",
            "update t_s_project set ",
            "<when test='projectCode!=null'>",
            "project_code=#{projectCode},",
            "</when>",
            "<when test='projectName!=null'>",
            "project_name=#{projectName},",
            "</when>",
            "<when test='projectDes!=null'>",
            "project_des=#{projectDes},",
            "</when>",
            "updated_by=#{updatedBy},updated_time=#{updatedTime} where id = #{id}",
            "</script>"
    })
    void update(NBProjectVo toBeUpdate);

    *//**
     * 接口表-修改
     *
     * @param toBeUpdate
     *//*
    @Update({"update t_s_interface set interface_name=#{interfaceName},updated_by=#{updatedBy},updated_time=#{updatedTime}" +
            " where id = #{id} "})
    void updateInterface(NBProjectInterfaceVo toBeUpdate);

    *//**
     * 接口表-新增
     *
     * @param vo
     *//*
    @Insert("insert into t_s_interface(interface_code,project_id,project_code,interface_name," +
            "interface_path,interface_type,interface_state,is_use,created_by,created_time)" +
            " values (#{vo.interfaceCode},#{vo.projectId},#{vo.projectCode},#{vo.interfaceName},#{vo.interfacePath}," +
            "#{vo.interfaceType},#{vo.interfaceState},#{vo.isUse},#{vo.createdBy},#{vo.createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "vo.id")
    void insertProjectInterface(@Param("vo") NBProjectInterfaceVo vo);
    *//**
     * 参数表-新增
     *
     * @param vo
     *//*
    @Insert({"<script>" +
            "insert into t_s_param\n" +
            "(param_code,interface_id,interface_code,param_parent_code,param_name,param_des,\n" +
            "param_data_type,param_type,is_use,created_by,created_time) " +
            "values  " +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "(#{item.paramCode}, #{item.interfaceId}, " +
            "#{item.interfaceCode},  " +
            "#{item.paramParentCode}, #{item.paramName}, " +
            "#{item.paramDes}, #{item.paramDataType}, " +
            "#{item.paramType}, #{item.isUse}, " +
            "#{item.createdBy}, #{item.createdTime})" +
            "</foreach>" +
            "</script>"})
    @Options(useGeneratedKeys = false)//防止id取不到而报错
    void insertInterfaceParam(@Param("list") List<NBParamVo> vo);


    *//**
     * 参数表-统一更新id
     *//*
    @Update({"update t_s_param p INNER JOIN t_s_param s on p.param_parent_code=s.param_code set p.param_parent_id = s.id\n" +
            "where p.param_parent_code is not null and p.param_parent_id is null"})
    void updateParamId();


    *//**
     * 根据id集合查询表单接口信息
     * 1.暂用于接口生成
     * @param ids
     * @return
     *//*
    @Select({
            "<script>",
            "SELECT * FROM t_s_form_info t where t.id in",
            "<foreach collection='ids' item='item' open='(' separator=',' close=')'>",
                "#{item}",
            "</foreach>",
            "</script>"
    })
    List<Map<String,Object>> getFormInfo(@Param("ids") List<String> ids);*/
}

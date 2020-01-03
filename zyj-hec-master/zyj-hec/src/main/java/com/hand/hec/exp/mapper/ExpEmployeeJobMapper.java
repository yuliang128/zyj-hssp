package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpEmployeeJob;

public interface ExpEmployeeJobMapper extends Mapper<ExpEmployeeJob> {

    /**
     * 预算检查的员工职务符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param employeeId 当前占用行员工ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlEmpJobCodeFrom 控制规则员工职务代码从
     * @param controlEmpJobCodeTo 控制规则员工职务代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的员工职务
     */
    List<ExpEmployeeJob> checkExpEmpJob(@Param("controlType") String controlType, @Param("employeeId") Long employeeId,
            @Param("filtrateMethod") String filtrateMethod,
            @Param("controlEmpJobCodeFrom") String controlEmpJobCodeFrom,
            @Param("controlEmpJobCodeTo") String controlEmpJobCodeTo);

    List<ExpEmployeeJob> queryForCb(ExpEmployeeJob employeeJob);

    /**
     * 员工职务信息查询
     * @param dto
     * @return 员工职务信息
     * @author Zhongyu 2019-2-25 14:03
     */
    List<ExpEmployeeJob> queryEmpJobs(ExpEmployeeJob dto);

    /**
     * 获取员工职务
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-27 17:15
     * @return java.util.List<com.hand.hec.exp.dto.ExpEmployeeJob> 员工职务
     */
    List<ExpEmployeeJob> getEmployeeJobListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

}

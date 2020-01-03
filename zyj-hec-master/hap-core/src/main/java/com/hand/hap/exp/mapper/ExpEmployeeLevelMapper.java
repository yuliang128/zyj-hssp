package com.hand.hap.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.mybatis.common.Mapper;

public interface ExpEmployeeLevelMapper extends Mapper<ExpEmployeeLevel> {

    /**
     * 预算检查的员工级别符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param employeeId 当前占用行员工ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlEmpLevelCodeFrom 控制规则员工职务代码从
     * @param controlEmpLevelCodeTo   控制规则员工职务代码到
     * @return 符合的员工级别
     * @author mouse 2019-01-10 15:50
     */
    List<ExpEmployeeLevel> checkExpEmpLevel(@Param("controlType") String controlType,
            @Param("employeeId") Long employeeId, @Param("filtrateMethod") String filtrateMethod,
            @Param("controlEmpLevelCodeFrom") String controlEmpLevelCodeFrom,
            @Param("controlEmpLevelCodeTo") String controlEmpLevelCodeTo);

    /**
     * @param employeeLevel
     * @return 员工级别系列
     * @author Zhongyu 2019-2-15 15:04
     */
    List<ExpEmployeeLevel> queryExpEmpLevel(ExpEmployeeLevel employeeLevel);

    /**
     * 获取员工级别
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-28 14:17
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployeeLevel> 员工级别
     */
    List<ExpEmployeeLevel> getEmployeeLevelListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);
}
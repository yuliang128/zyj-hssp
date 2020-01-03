package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoEmpGroup;

/**
 * <p>
 * 员工组定义Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
public interface ExpMoEmpGroupMapper extends Mapper<ExpMoEmpGroup> {

    /**
     * 预算检查的员工组符合判断
     *
     * @param controlType             过滤类型，明细、汇总、全部
     * @param employeeId              当前占用行员工ID
     * @param filtrateMethod          控制规则取值方式
     * @param controlEmpGroupCodeFrom 控制规则员工组代码从
     * @param controlEmpGroupCodeTo   控制规则员工组代码到
     * @return 符合的员工组
     * @author mouse 2019-01-10 15:50
     */
    List<ExpMoEmpGroup> checkExpMoEmpGroup(@Param("controlType") String controlType,
            @Param("employeeId") Long employeeId, @Param("filtrateMethod") String filtrateMethod,
            @Param("controlEmpGroupCodeFrom") String controlEmpGroupCodeFrom,
            @Param("controlEmpGroupCodeTo") String controlEmpGroupCodeTo);

    /**
     * 获取员工组
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-27 14:39
     * @return java.util.List<com.hand.hec.exp.dto.ExpMoEmpGroup> 员工组
     */
    List<ExpMoEmpGroup> getEmpGroupListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

}

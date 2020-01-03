package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoUnitGroup;

/**
 * <p>
 * ExpMoUnitGroupMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:26
 */
public interface ExpMoUnitGroupMapper extends Mapper<ExpMoUnitGroup> {

    /**
     * 预算检查的部门组符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param unitId 当前占用行部门ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlUnitGroupCodeFrom 控制规则部门组代码从
     * @param controlUnitGroupCodeTo 控制规则部门组代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的部门组
     */
    List<ExpMoUnitGroup> checkExpOrgUnitGroup(@Param("controlType") String controlType, @Param("unitId") Long unitId,
            @Param("filtrateMethod") String filtrateMethod,
            @Param("controlUnitGroupCodeFrom") String controlUnitGroupCodeFrom,
            @Param("controlUnitGroupCodeTo") String controlUnitGroupCodeTo);

    /**
     * 获取部门组
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-28 19:19
     * @return java.util.List<com.hand.hec.exp.dto.ExpMoUnitGroup> 部门组
     */
    List<ExpMoUnitGroup> getUnitGroupListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);
}

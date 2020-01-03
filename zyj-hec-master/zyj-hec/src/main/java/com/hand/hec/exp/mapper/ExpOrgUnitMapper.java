package com.hand.hec.exp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpOrgUnit;

/**
 * <p>
 * ExpOrgUnitMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:17
 */
public interface ExpOrgUnitMapper extends Mapper<ExpOrgUnit> {

    /**
     * 预算检查的部门符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param unitId 当前占用行部门ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlUnitCodeFrom 控制规则部门代码从
     * @param controlUnitCodeTo 控制规则部门代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的部门
     */
    List<ExpOrgUnit> checkExpOrgUnit(@Param("controlType") String controlType, @Param("unitId") Long unitId,
            @Param("filtrateMethod") String filtrateMethod, @Param("controlUnitCodeFrom") String controlUnitCodeFrom,
            @Param("controlUnitCodeTo") String controlUnitCodeTo);

    /**
     * 部门信息查询
     *
     * @param expOrgUnit
     * @author guiyu 2019-01-21 15:08
     * @return 部门信息
     */
    List<ExpOrgUnit> queryUnitForLov(ExpOrgUnit expOrgUnit);


    /**
     * 查询可分配部门组的部门
     *
     * @param expOrgUnit
     * @author guiyu 2019-01-21 15:08
     * @return 可分配的部门
     */
    List<ExpOrgUnit> queryAssignUnit(ExpOrgUnit expOrgUnit);

    /**
     * 根据公司查询部门信息
     *
     * @param expOrgUnit
     * @author guiyu 2019-01-22 19:09
     * @return 该公司下的部门
     */
    List<ExpOrgUnit> queryByCompany(ExpOrgUnit expOrgUnit);

    /**
     *根据公司ID查询部门信息及公司启用时间（预算实体同步预算中心使用）
     *
     * @param companyId 公司ID
     * @author guiyuting 2019-02-27 18:23
     * @return 该公司下的信息
     */
    List<Map<String, Object>> queryWithCompany(@Param("companyId") Long companyId);

    /**
     * 获取部门
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-28 18:25
     * @return java.util.List<com.hand.hec.exp.dto.ExpOrgUnit> 部门
     */
    List<ExpOrgUnit> getOrgUnitListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);
}

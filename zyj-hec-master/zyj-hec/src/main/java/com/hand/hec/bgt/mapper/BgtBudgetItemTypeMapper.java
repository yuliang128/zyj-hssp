package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItemType;

/**
 * <p>
 * 预算项目类型Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:46
 */
public interface BgtBudgetItemTypeMapper extends Mapper<BgtBudgetItemType> {

    /**
     * 预算检查的预算项目类型符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param itemTypeId 当前占用行预算项目类型ID
     *      * @param filtrateMethod 控制规则取值方式
     *      * @param controlItemTypeCodeFrom 控制规则预算项目类型代码从
     *      * @param controlItemTypeCodeTo 控制规则预算项目类型代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算项目类型
     */
    List<BgtBudgetItemType> checkBgtItemType(@Param("controlType") String controlType, @Param("bgtOrgId") Long bgtOrgId,
            @Param("itemTypeId") Long itemTypeId, @Param("filtrateMethod") String filtrateMethod,
            @Param("controlItemTypeCodeFrom") String controlItemTypeCodeFrom,
            @Param("controlItemTypeCodeTo") String controlItemTypeCodeTo);

    /**
     * 根据预算组织ID 查询预算项目类型信息
     *
     * @param bgtBudgetItemType
     * @author guiyuting 2019-03-11 17:01
     * @return 符合条件的预算项目类型信息
     */
    List<BgtBudgetItemType> queryByBgtOrgId(BgtBudgetItemType bgtBudgetItemType);

    /**
     * 根据预算项目类型代码查询预算项目类型
     *
     * @param bgtOrgId 预算组织ID
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-27 9:50
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBudgetItemType> 预算项目类型
     */
    List<BgtBudgetItemType> getBudgetItemTypeListForQuery(@Param("bgtOrgId") Long bgtOrgId,
            @Param("parameterCode") String parameterCode, @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);
}

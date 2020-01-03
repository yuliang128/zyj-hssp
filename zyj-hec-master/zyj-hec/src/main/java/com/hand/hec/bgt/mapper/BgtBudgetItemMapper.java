package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItem;

/**
 * <p>
 * 预算项目Mapper
 * </p>
 *
 * @author mouse 2019/01/07 14:43
 */
public interface BgtBudgetItemMapper extends Mapper<BgtBudgetItem> {

    /**
     * 预算检查的预算项目符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param filtrateMethod 控制规则取值方式
     * @param itemId 当前占用行预算实体ID
     * @param controlItemCodeFrom 控制规则预算项目代码从
     * @param controlItemCodeTo 控制规则预算项目代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算项目
     */
    List<BgtBudgetItem> checkBgtItem(@Param("controlType") String controlType, @Param("bgtOrgId") Long bgtOrgId,
            @Param("filtrateMethod") String filtrateMethod, @Param("itemId") Long itemId,
            @Param("controlItemCodeFrom") String controlItemCodeFrom,
            @Param("controlItemCodeTo") String controlItemCodeTo);

    /**
     * 根据预算组织ID，查询汇总标志为Y，启用的预算项目信息
     *
     * @param bgtBudgetItem 预算项目信息
     * @author guiyuting 2019-03-11 15:50
     * @return 符合条件的预算项目信息
     */
    List<BgtBudgetItem> queryHierarchyByBgtOrgId(BgtBudgetItem bgtBudgetItem);

    /**
     * 根据预算组织ID查询不为汇总，未被分配的预算项目
     *
     * @param bgtBudgetItem
     * @author guiyuting 2019-03-13 16:27
     * @return 符合条件的预算项目
     */
    List<BgtBudgetItem> queryNoSummaryByBgtOrg(BgtBudgetItem bgtBudgetItem);

    /**
     * 预算日记账类型定义分配预算项目项目查询
     *
     * @param bgtBudgetItem
     * @author guiyuting 2019-03-20 17:21
     * @return 符合条件的预算项目
     */
    List<BgtBudgetItem> bgtJournalBatchAssign(BgtBudgetItem bgtBudgetItem);

    /**
     * 获取预算项目
     *
     * @param bgtOrgId 预算组织ID
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-26 18:10
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBudgetItem> 预算项目
     */
    List<BgtBudgetItem> getBudgetItemListForQuery(@Param("bgtOrgId") Long bgtOrgId,
            @Param("parameterCode") String parameterCode, @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

}

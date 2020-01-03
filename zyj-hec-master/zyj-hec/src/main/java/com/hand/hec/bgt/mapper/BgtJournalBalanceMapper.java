package com.hand.hec.bgt.mapper;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtJournalBalance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算余额Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:48
 */
public interface BgtJournalBalanceMapper extends Mapper<BgtJournalBalance> {

    /**
     * 检查公司是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @author YHL 2019-03-26 14:17
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkCompanyForQuery(@Param("parameterCode") String parameterCode);

    /**
     * 检查预算组织是否存在预算余额记录
     *
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-22 16:13
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtOrgForQuery(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查预算表是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 9:41
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtStructureForQuery(@Param("parameterCode") String parameterCode, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查预算场景是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 9:50
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtScenarioForQuery(@Param("parameterCode") String parameterCode, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查预算版本是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 9:57
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtVersionForQuery(@Param("parameterCode") String parameterCode, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查预算实体是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 11:19
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtEntityForQuery(@Param("parameterCode") String parameterCode, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查币种是否存在预算余额记录
     *
     * @param parameterCode 参数代码（值）
     * @author YHL 2019-03-25 14:14
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkCurrencyForQuery(@Param("parameterCode") String parameterCode);

    /**
     * 检查预算期间是否存在预算余额记录
     *
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 15:05
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtPeriodForQuery(@Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 检查预算中心是否存在预算余额记录
     *
     * @param centerId 预算中心ID
     * @param controlRuleRange 取值范围
     * @author YHL 2019-03-26 15:42
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtCenterForQuery(@Param("centerId") Long centerId,
            @Param("controlRuleRange") String controlRuleRange);

    /**
     * 检查预算项目是否存在预算余额记录
     *
     * @param budgetItemId 预算项目ID
     * @param controlRuleRange 取值范围
     * @author YHL 2019-03-26 18:20
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkBgtItemForQuery(@Param("budgetItemId") Long budgetItemId,
            @Param("controlRuleRange") String controlRuleRange);

    /**
     * 检查员工是否存在预算余额记录
     *
     * @param employeeId 员工ID
     * @author YHL 2019-03-27 14:06
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkEmployeeForQuery(@Param("employeeId") Long employeeId);

    /**
     * 检查员工组是否存在预算余额记录
     *
     * @param employeeGroupId 员工组ID
     * @param employeeId 员工ID
     * @author YHL 2019-03-27 15:39
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkEmpGroupForQuery(@Param("employeeGroupId") Long employeeGroupId, @Param("employeeId") Long employeeId);

    /**
     * 检查岗位是否存在预算余额记录
     *
     * @param positionId 岗位ID
     * @author YHL 2019-03-27 18:36
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkOrgPositionForQuery(@Param("positionId") Long positionId);

    /**
     * 检查岗位组是否存在预算余额记录
     *
     * @param positionGroupId 岗位组ID
     * @param positionId 岗位ID
     * @author YHL 2019-03-27 19:35
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkPositionGroupForQuery(@Param("positionGroupId") Long positionGroupId,
            @Param("positionId") Long positionId);

    /**
     * 检查部门是否存在预算余额记录
     *
     * @param unitId 部门ID
     * @author YHL 2019-03-28 18:31
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkOrgUnitForQuery(@Param("unitId") Long unitId);

    /**
     * 检查部门组是否存在预算余额记录
     *
     * @param unitGroupId 部门组ID
     * @param unitId 部门ID
     * @author YHL 2019-03-28 19:21
     * @return java.lang.Integer 预算余额记录条数
     */
    Integer checkUnitGroupForQuery(@Param("unitGroupId") Long unitGroupId, @Param("unitId") Long unitId);

    /**
     * 查询预算余额记录是否存在
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:39
     * @return 0表示不存在，1存在
     */
    int checkBalanceExists(BgtJournalBalance bgtJournalBalance);

    /**
     * 更新预算余额
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:49
     * @return
     */
    void updateBgtJournalBalance(BgtJournalBalance bgtJournalBalance);

    /**
     * 更新预算余额年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:49
     * @return 
     */
    void updateYearBalanceMonth(BgtJournalBalance bgtJournalBalance);

    /**
     * 更新预算余额年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:49
     * @return
     */
    void updateYearBalanceQuarter(BgtJournalBalance bgtJournalBalance);


    /**
     * 更新预算余额季度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:49
     * @return
     */
    void updateQuarterBalanceMonth(BgtJournalBalance bgtJournalBalance);

    /**
     * 根据条件查询预算余额信息
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:06
     * @return 
     */
    List<BgtJournalBalance> queryByOption(BgtJournalBalance bgtJournalBalance);

    /**
     * 按月控制，取季度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:28
     * @return 
     */
    Map<String,BigDecimal> queryTotalQuarterBalanceMonth(BgtJournalBalance bgtJournalBalance);

    /**
     * 按月控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:28
     * @return
     */
    Map<String,BigDecimal> queryTotalYearBalanceMonth(BgtJournalBalance bgtJournalBalance);

    /**
     * 按季度控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:28
     * @return
     */
    Map<String,BigDecimal> getYearBalanceQuarter(BgtJournalBalance bgtJournalBalance);

    /**
     * 按年控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:28
     * @return
     */
    Map<String,BigDecimal> getYearBalanceYear(BgtJournalBalance bgtJournalBalance);


}
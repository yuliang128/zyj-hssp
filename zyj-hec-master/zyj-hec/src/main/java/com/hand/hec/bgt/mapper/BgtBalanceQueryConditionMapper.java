package com.hand.hec.bgt.mapper;

import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import org.apache.ibatis.annotations.Param;

/**
 * description
 *
 * @author mouse 2019/04/18 15:21
 */
public interface BgtBalanceQueryConditionMapper {

    /**
     * 预算表编制期段为期间的预算余额数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterBalanceByPeriod(@Param("group") BgtBalanceQueryGroup group);


    /**
     * 预算表编制期段为期间的预算占用数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterReserveByPeriod(@Param("group") BgtBalanceQueryGroup group);

    /**
     * 预算表编制期段为季度的预算余额数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterBalanceByQuarter(@Param("group") BgtBalanceQueryGroup group);


    /**
     * 预算表编制期段为季度的预算占用数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterReserveByQuarter(@Param("group") BgtBalanceQueryGroup group);

    /**
     * 预算表编制期段为年度的预算余额数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterBalanceByYear(@Param("group") BgtBalanceQueryGroup group);


    /**
     * 预算表编制期段为年度的预算占用数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 15:22
     * @return void
     */
    void filterReserveByYear(@Param("group") BgtBalanceQueryGroup group);

    /**
     * 初始化预算项目类型数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceBudgetItemTypeTemp();

    /**
     * 初始化预算项目类型数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveBudgetItemTypeTemp();

    /**
     * 生成符合控制上下限的预算项目类型数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateBudgetItemType(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的预算项目类型条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countBudgetItemType();

    /**
     * 根据预算项目类型过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceBudgetItemType();

    /**
     * 根据预算项目类型过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveBudgetItemType();

    /**
     * 清除预算项目类型数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearBudgetItemTypeTemp();


    /**
     * 初始化预算项目数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBudgetItemTemp();

    /**
     * 初始化预算项目数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceBudgetItemTemp();

    /**
     * 初始化预算项目数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveBudgetItemTemp();

    /**
     * 生成符合控制上下限的预算项目数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateBudgetItem(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的预算项目条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countBudgetItem();

    /**
     * 根据预算项目过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceBudgetItem();

    /**
     * 根据预算项目过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveBudgetItem();

    /**
     * 清除预算项目数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearBudgetItemTemp();


    /**
     * 初始化部门数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceOrgUnitTemp();

    /**
     * 初始化部门数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveOrgUnitTemp();

    /**
     * 生成符合控制上下限部门数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateOrgUnit(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的部门条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countOrgUnit();

    /**
     * 根据部门过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceOrgUnit();

    /**
     * 根据部门过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveOrgUnit();

    /**
     * 清除部门数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearOrgUnitTemp();


    /**
     * 初始化部门组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceOrgUnitGroupTemp();

    /**
     * 初始化部门组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveOrgUnitGroupTemp();

    /**
     * 生成符合控制上下限的部门组数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateOrgUnitGroup(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的部门组条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countOrgUnitGroup();

    /**
     * 根据部门组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceOrgUnitGroup();

    /**
     * 根据部门组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveOrgUnitGroup();

    /**
     * 清除部门组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearOrgUnitGroupTemp();



    /**
     * 初始化岗位数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceOrgPositionTemp();

    /**
     * 初始化岗位数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveOrgPositionTemp();

    /**
     * 生成符合控制上下限岗位数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateOrgPosition(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的岗位条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countOrgPosition();

    /**
     * 根据岗位过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceOrgPosition();

    /**
     * 根据岗位过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveOrgPosition();

    /**
     * 清除岗位数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearOrgPositionTemp();



    /**
     * 初始化岗位组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceOrgPositionGroupTemp();

    /**
     * 初始化岗位组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveOrgPositionGroupTemp();

    /**
     * 生成符合控制上下限岗位组数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateOrgPositionGroup(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的岗位组条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countOrgPositionGroup();

    /**
     * 根据岗位组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceOrgPositionGroup();

    /**
     * 根据岗位组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveOrgPositionGroup();

    /**
     * 清除岗位组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearOrgPositionGroupTemp();



    /**
     * 初始化员工的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceEmployeeTemp();


    /**
     * 初始化员工的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveEmployeeTemp();

    /**
     * 生成符合控制上下限员工数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateEmployee(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的员工条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countEmployee();

    /**
     * 根据员工过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceEmployee();

    /**
     * 根据员工过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveEmployee();


    /**
     * 清除员工数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearEmployeeTemp();



    /**
     * 初始化员工组的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceEmployeeGroupTemp();

    /**
     * 初始化员工组的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveEmployeeGroupTemp();

    /**
     * 生成符合控制上下限员工组数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateEmployeeGroup(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的员工组条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countEmployeeGroup();

    /**
     * 根据员工组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceEmployeeGroup();

    /**
     * 根据员工组过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveEmployeeGroup();


    /**
     * 清除员工组数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearEmployeeGroupTemp();


    /**
     * 初始化员工级别的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceEmployeeLevelTemp();

    /**
     * 初始化员工级别的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveEmployeeLevelTemp();

    /**
     * 生成符合控制上下限员工级别数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateEmployeeLevel(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的员工级别条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countEmployeeLevel();

    /**
     * 根据员工级别过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceEmployeeLevel();

    /**
     * 根据员工级别过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveEmployeeLevel();


    /**
     * 清除员工级别数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearEmployeeLevelTemp();


    /**
     * 初始化员工职务的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceEmployeeJobTemp();

    /**
     * 初始化员工职务的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveEmployeeJobTemp();

    /**
     * 生成符合控制上下限员工职务数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateEmployeeJob(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的员工职务条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countEmployeeJob();

    /**
     * 根据员工职务过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterEmployeeJob();


    /**
     * 清除员工职务数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearEmployeeJobTemp();



    /**
     * 初始化责任中心的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initRespCenterTemp();

    /**
     * 初始化责任中心的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceRespCenterTemp();

    /**
     * 初始化责任中心的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveRespCenterTemp();

    /**
     * 生成符合控制上下限责任中心数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateRespCenter(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的责任中心条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countRespCenter();

    /**
     * 根据责任中心过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceRespCenter();

    /**
     * 根据责任中心过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveRespCenter();


    /**
     * 清除责任中心数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearRespCenterTemp();



    /**
     * 初始化预算中心的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBgtCenterTemp();

    /**
     * 初始化预算中心的临时数据
     *
     * @author guiyuting 2019-05-29 10:16
     * @return 
     */
    void initBalanceBgtCenterTemp();

    /**
     * 初始化预算中心的临时数据
     *
     * @author guiyuting 2019-05-29 10:16
     * @return 
     */
    void initReserveBgtCenterTemp();

    /**
     * 生成符合控制上下限预算中心数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateBgtCenter(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group);

    /**
     * 计算符合控制上下限的预算中心条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countBgtCenter();

    /**
     * 根据预算中心过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceBgtCenter();

    /**
     * 根据预算中心过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveBgtCenter();

    /**
     * 清除预算中心数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearBgtCenterTemp();



    /**
     * 初始化维度的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initDimensionTemp(@Param("dimSeq") Long dimSeq);

    /**
     * 初始化维度的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initBalanceDimensionTemp(@Param("dimSeq") Long dimSeq);

    /**
     * 初始化维度的临时数据
     *
     *
     * @author mouse 2019-04-18 16:47
     * @return void
     */
    void initReserveDimensionTemp(@Param("dimSeq") Long dimSeq);

    /**
     * 生成符合控制上下限维度数据
     *
     * @param condition
     * @param group
     * @author mouse 2019-04-18 16:49
     * @return void
     */
    void generateDimension(@Param("condition") BgtBalanceQueryCondition condition,
                    @Param("group") BgtBalanceQueryGroup group, @Param("dimSeq") Long dimSeq,
                    @Param("dimensionId") Long dimensionId);

    /**
     * 计算符合控制上下限的维度条数
     *
     *
     * @author mouse 2019-04-18 17:20
     * @return java.lang.Long
     */
    Long countDimension(@Param("dimSeq") Long dimSeq);

    /**
     * 根据维度过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterBalanceDimension(@Param("dimSeq") Long dimSeq);

    /**
     * 根据维度过滤balance和reserve
     *
     *
     * @author mouse 2019-04-18 19:07
     * @return void
     */
    void filterReserveDimension(@Param("dimSeq") Long dimSeq);

    /**
     * 清除维度数据的临时数据
     *
     *
     * @author mouse 2019-04-18 19:13
     * @return void
     */
    void clearDimensionTemp(@Param("dimSeq") Long dimSeq);

    /**
     * 扩展预算中心
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendBgtCenter(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteBgtCenter();

    /**
     * 扩展预算项目
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendBgtItem(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteBgtItem();

    /**
     * 扩展预算项目类型
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendBgtItemType(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteBgtItemType();

    /**
     * 扩展部门
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendOrgUnit(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    /**
     * 扩展部门组
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendOrgUnitGroup(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteOrgUnitGroup();

    /**
     * 扩展岗位
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendOrgPosition(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    /**
     * 扩展岗位组
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendOrgPositionGroup(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteOrgPositionGroup();

    /**
     * 扩展员工
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendEmployee(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    /**
     * 扩展员工组
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendEmployeeGroup(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteEmployeeGroup();

    /**
     * 扩展员工级别
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendEmployeeLevel(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteEmployeeLevel();

    /**
     * 扩展员工职务
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendEmployeeJob(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition);

    void extendDeleteEmployeeJob();


    /**
     * 扩展维度
     *
     * @param group
     * @param condition
     * @author mouse 2019-04-23 19:39
     * @return void
     */
    void extendDimension(@Param("group") BgtBalanceQueryGroup group,
                    @Param("condition") BgtBalanceQueryCondition condition, @Param("dimSeq") Long dimSeq);

    void extendDeleteDimension(@Param("dimSeq") Long dimSeq);

    void extendUpdateDimension();
}

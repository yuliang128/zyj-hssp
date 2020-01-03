package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtPeriod;
import com.hand.hec.bgt.dto.BgtStructure;
import com.hand.hec.bgt.exception.BgtBalanceQueryException;
import com.hand.hec.bgt.exception.BgtBalanceQueryNoResult;
import com.hand.hec.bgt.mapper.BgtBalanceQueryConditionMapper;
import com.hand.hec.bgt.service.IBgtBalanceQueryConditionService;
import com.hand.hec.bgt.service.IBgtBalanceQueryService;
import com.hand.hec.bgt.service.IBgtStructureService;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description
 *
 * @author mouse 2019/04/18 11:22
 */
@Service
public class BgtBalanceQueryConditionServiceImpl implements IBgtBalanceQueryConditionService {

    @Autowired
    IBgtStructureService structureService;

    @Autowired
    BgtBalanceQueryConditionMapper conditionMapper;

    @Autowired
    IFndDimensionService dimensionService;

    @Override
    public void filterPeriod(IRequest request, BgtBalanceQueryGroup group) {
        List<BgtPeriod> periodList = null;
        BgtStructure structure = new BgtStructure();
        structure.setStructureId(group.getStructureId());
        structure = structureService.selectByPrimaryKey(request, structure);

        if (BgtStructure.BUDGET_PERIOD_YEAR.equals(structure.getPeriodStrategy())) {
            // 预算表编制策略为年度
            conditionMapper.filterBalanceByYear(group);
            conditionMapper.filterReserveByYear(group);
        } else if (BgtStructure.BUDGET_PERIOD_QUARTER.equals(structure.getPeriodStrategy())) {
            // 预算表编制策略为季度
            conditionMapper.filterBalanceByQuarter(group);
            conditionMapper.filterReserveByQuarter(group);
        } else {
            // 预算表编制策略为月度
            conditionMapper.filterBalanceByPeriod(group);
            conditionMapper.filterReserveByPeriod(group);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void filterCondition(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer filterFieldSb) {
        if (BgtBalanceQueryCondition.BGT_BUDGET_CENTER.equals(condition.getParameterCode())) {
            filterBgtCenter(group, condition);
            appendField(filterFieldSb, "bgt_center_id");
        } else if (BgtBalanceQueryCondition.BGT_BUDGET_ITEM.equals(condition.getParameterCode())) {
            filterBudgetItem(group, condition);
            appendField(filterFieldSb, "budget_item_id");
        } else if (BgtBalanceQueryCondition.BGT_BUDGET_ITEM_TYPE.equals(condition.getParameterCode())) {
            filterBudgetItemType(group, condition);
            appendField(filterFieldSb, "budget_item_type_id");
        } else if (BgtBalanceQueryCondition.ORG_EMPLOYEE.equals(condition.getParameterCode())) {
            filterEmployee(group, condition);
            appendField(filterFieldSb, "employee_id");
        } else if (BgtBalanceQueryCondition.ORG_EMPLOYEE_GROUP.equals(condition.getParameterCode())) {
            filterEmployeeGroup(group, condition);
            appendField(filterFieldSb, "employee_group_id");
        } else if (BgtBalanceQueryCondition.ORG_EMPLOYEE_JOB.equals(condition.getParameterCode())) {
            filterEmployeeJob(group, condition);
            appendField(filterFieldSb, "employee_job_id");
        } else if (BgtBalanceQueryCondition.ORG_EMPLOYEE_LEVEL.equals(condition.getParameterCode())) {
            filterEmployeeLevel(group, condition);
            appendField(filterFieldSb, "employee_level_id");
        } else if (BgtBalanceQueryCondition.ORG_ORG_POSITION.equals(condition.getParameterCode())) {
            filterOrgPosition(group, condition);
            appendField(filterFieldSb, "position_id");
        } else if (BgtBalanceQueryCondition.ORG_ORG_POSITION_GROUP.equals(condition.getParameterCode())) {
            filterOrgPositionGroup(group, condition);
            appendField(filterFieldSb, "position_group_id");
        } else if (BgtBalanceQueryCondition.ORG_ORG_UNIT.equals(condition.getParameterCode())) {
            filterOrgUnit(group, condition);
            appendField(filterFieldSb, "unit_id");
        } else if (BgtBalanceQueryCondition.ORG_ORG_UNIT_GROUP.equals(condition.getParameterCode())) {
            filterOrgUnitGroup(group, condition);
            appendField(filterFieldSb, "unit_group_id");
        } else {
            String dimCode = condition.getParameterCode();
            FndDimension dimension = new FndDimension();
            dimension.setDimensionCode(dimCode);
            List<FndDimension> dimensionList = dimensionService.select(request, dimension, 0, 0);
            if (dimensionList == null || dimensionList.size() == 0) {
                throw new BgtBalanceQueryException("BGT", "传入的控制维度不存在", null);
            }

            dimension = dimensionList.get(0);
            filterDimension(group, condition, dimension.getDimensionSequence(), dimension.getDimensionId());

            appendField(filterFieldSb, "dimension" + dimension.getDimensionSequence() + "_id");
        }
    }

    /**
     * 根据预算项目类型过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterBudgetItemType(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化预算项目类型临时数据
        conditionMapper.initBalanceBudgetItemTypeTemp();
        conditionMapper.initReserveBudgetItemTypeTemp();

        // 根据过滤条件排除不满足的预算项目数据
        conditionMapper.generateBudgetItemType(condition, group);

        // 获取满足条件的预算项目数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long budgetItemTypeCount = conditionMapper.countBudgetItemType();
        if (budgetItemTypeCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "预算项目类型没有匹配项", null);
        }

        // 根据预算项目数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceBudgetItemType();
        conditionMapper.filterReserveBudgetItemType();

        // 删除初始化的预算项目类型临时数据
        conditionMapper.clearBudgetItemTypeTemp();
    }

    /**
     * 根据预算项目过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterBudgetItem(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化预算项目临时数据
        conditionMapper.initBudgetItemTemp();
        conditionMapper.initBalanceBudgetItemTemp();
        conditionMapper.initReserveBudgetItemTemp();

        // 根据过滤条件排除不满足的预算项目数据
        conditionMapper.generateBudgetItem(condition, group);

        // 获取满足条件的预算项目数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long budgetItemCount = conditionMapper.countBudgetItem();
        if (budgetItemCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "预算项目没有匹配项", null);
        }

        // 根据预算项目数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceBudgetItem();
        conditionMapper.filterReserveBudgetItem();

        // 删除初始化的预算项目临时数据
        conditionMapper.clearBudgetItemTemp();
    }

    /**
     * 根据部门过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterOrgUnit(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化部门临时数据
        conditionMapper.initBalanceOrgUnitTemp();
        conditionMapper.initReserveOrgUnitTemp();

        // 根据过滤条件排除不满足的部门数据
        conditionMapper.generateOrgUnit(condition, group);

        // 获取满足条件的部门数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long orgUnitCount = conditionMapper.countOrgUnit();
        if (orgUnitCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "部门没有匹配项", null);
        }

        // 根据部门数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceOrgUnit();
        conditionMapper.filterReserveOrgUnit();

        // 删除初始化的部门临时数据
        conditionMapper.clearOrgUnitTemp();
    }


    /**
     * 根据部门组过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterOrgUnitGroup(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化部门组临时数据
        conditionMapper.initBalanceOrgUnitGroupTemp();
        conditionMapper.initReserveOrgUnitGroupTemp();

        // 根据过滤条件排除不满足的部门组数据
        conditionMapper.generateOrgUnitGroup(condition, group);

        // 获取满足条件的部门组数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long orgUnitGroupCount = conditionMapper.countOrgUnitGroup();
        if (orgUnitGroupCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "部门组没有匹配项", null);
        }

        // 根据部门组数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceOrgUnitGroup();
        conditionMapper.filterReserveOrgUnitGroup();

        // 删除初始化的部门组临时数据
        conditionMapper.clearOrgUnitGroupTemp();
    }

    /**
     * 根据部门级别过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-19 10:35
     */
    private void filterOrgUnitLevel(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        //
        // 目前此逻辑不执行
        // ------------------------------------------------------------------------------
        return;
    }

    /**
     * 根据岗位过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterOrgPosition(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化部门临时数据
        conditionMapper.initBalanceOrgPositionTemp();
        conditionMapper.initReserveOrgPositionTemp();

        // 根据过滤条件排除不满足的部门数据
        conditionMapper.generateOrgPosition(condition, group);

        // 获取满足条件的部门数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long orgUnitPositionCount = conditionMapper.countOrgPosition();
        if (orgUnitPositionCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "预算岗位没有匹配项", null);
        }

        // 根据部门数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceOrgPosition();
        conditionMapper.filterReserveOrgPosition();

        // 删除初始化的部门临时数据
        conditionMapper.clearOrgPositionTemp();
    }

    /**
     * 根据岗位组过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterOrgPositionGroup(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化部门临时数据
        conditionMapper.initBalanceOrgPositionGroupTemp();
        conditionMapper.initReserveOrgPositionGroupTemp();

        // 根据过滤条件排除不满足的部门数据
        conditionMapper.generateOrgPositionGroup(condition, group);

        // 获取满足条件的部门数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long orgUnitPositionGroupCount = conditionMapper.countOrgPositionGroup();
        if (orgUnitPositionGroupCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "岗位组没有匹配项", null);
        }

        // 根据部门数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceOrgPositionGroup();
        conditionMapper.filterReserveOrgPositionGroup();

        // 删除初始化的部门临时数据
        conditionMapper.clearOrgPositionGroupTemp();
    }


    /**
     * 根据员工过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterEmployee(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化员工临时数据
        conditionMapper.initBalanceEmployeeTemp();
        conditionMapper.initReserveEmployeeTemp();

        // 根据过滤条件排除不满足的员工数据
        conditionMapper.generateEmployee(condition, group);

        // 获取满足条件的员工数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long employeeCount = conditionMapper.countEmployee();
        if (employeeCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "员工没有匹配项", null);
        }

        // 根据员工数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceEmployee();
        conditionMapper.filterReserveEmployee();

        // 删除初始化的员工临时数据
        conditionMapper.clearEmployeeTemp();
    }


    /**
     * 根据员工组过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterEmployeeGroup(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化员工组临时数据
        conditionMapper.initBalanceEmployeeGroupTemp();
        conditionMapper.initReserveEmployeeGroupTemp();

        // 根据过滤条件排除不满足的员工组数据
        conditionMapper.generateEmployeeGroup(condition, group);

        // 获取满足条件的员工组数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long employeeGroupCount = conditionMapper.countEmployeeGroup();
        if (employeeGroupCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "员工组没有匹配项", null);
        }

        // 根据员工组数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceEmployeeGroup();
        conditionMapper.filterReserveEmployeeGroup();

        // 删除初始化的员工组临时数据
        conditionMapper.clearEmployeeGroupTemp();
    }


    /**
     * 根据员工级别过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterEmployeeLevel(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化员工级别临时数据
        conditionMapper.initBalanceEmployeeLevelTemp();
        conditionMapper.initReserveEmployeeLevelTemp();

        // 根据过滤条件排除不满足的员工级别数据
        conditionMapper.generateEmployeeLevel(condition, group);

        // 获取满足条件的员工组级别数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long employeeLevelCount = conditionMapper.countEmployeeLevel();
        if (employeeLevelCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "员工级别没有匹配项", null);
        }

        // 根据员工级别数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceEmployeeLevel();
        conditionMapper.filterReserveEmployeeLevel();

        // 删除初始化的员工级别临时数据
        conditionMapper.clearEmployeeLevelTemp();
    }


    /**
     * 根据员工职务过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterEmployeeJob(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化员工职务临时数据
        conditionMapper.initBalanceEmployeeJobTemp();
        conditionMapper.initReserveEmployeeJobTemp();

        // 根据过滤条件排除不满足的员工职务数据
        conditionMapper.generateEmployeeJob(condition, group);

        // 获取满足条件的员工组级别数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long employeeJobCount = conditionMapper.countEmployeeJob();
        if (employeeJobCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "员工职务没有匹配项", null);
        }

        // 根据员工职务数据删除balance和reserve的初始化数据
        conditionMapper.filterEmployeeJob();

        // 删除初始化的员工职务临时数据
        conditionMapper.clearEmployeeJobTemp();
    }


    /**
     * 根据责任中心过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterRespCenter(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化责任中心临时数据
        conditionMapper.initBalanceRespCenterTemp();
        conditionMapper.initReserveRespCenterTemp();
        conditionMapper.initRespCenterTemp();

        // 根据过滤条件排除不满足的责任中心数据
        conditionMapper.generateRespCenter(condition, group);

        // 获取满足条件的员工组级别数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long respCenterCount = conditionMapper.countRespCenter();
        if (respCenterCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "责任中心没有匹配项", null);
        }

        // 根据责任中心数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceRespCenter();
        conditionMapper.filterReserveRespCenter();

        // 删除初始化的责任中心临时数据
        conditionMapper.clearRespCenterTemp();
    }


    /**
     * 根据预算中心过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterBgtCenter(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition) {
        // 初始化预算中心临时数据
        conditionMapper.initBalanceBgtCenterTemp();
        conditionMapper.initReserveBgtCenterTemp();
        conditionMapper.initBgtCenterTemp();

        // 根据过滤条件排除不满足的预算中心数据
        conditionMapper.generateBgtCenter(condition, group);

        // 获取满足条件的员工组级别数据，如果数量为0，抛出没有匹配项的异常，退出本次查询
        Long bgtCenterCount = conditionMapper.countBgtCenter();
        if (bgtCenterCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "预算中心没有匹配项", null);
        }

        // 根据预算中心数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceBgtCenter();
        conditionMapper.filterReserveBgtCenter();

        // 删除初始化的预算中心临时数据
        conditionMapper.clearBgtCenterTemp();
    }


    /**
     * 根据维度过滤balance和reserve数据
     *
     * @param group
     * @param condition
     * @return void
     * @author mouse 2019-04-18 19:22
     */
    private void filterDimension(BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition, Long dimSeq,
                    Long dimensionId) {
        // 初始化维度临时数据
        conditionMapper.initBalanceDimensionTemp(dimSeq);
        conditionMapper.initReserveDimensionTemp(dimSeq);
        conditionMapper.initDimensionTemp(dimSeq);

        // 根据过滤条件排除不满足的维度
        conditionMapper.generateDimension(condition, group, dimSeq, dimensionId);

        // 获取满足条件的维度数据，如果数量为0，排除没有匹配项的异常，退出本次查询
        Long dimensionCount = conditionMapper.countDimension(dimSeq);
        if (dimensionCount == 0L) {
            throw new BgtBalanceQueryNoResult("BGT", "预算中心没有匹配项", null);
        }

        // 根据预算中心数据删除balance和reserve的初始化数据
        conditionMapper.filterBalanceDimension(dimSeq);
        conditionMapper.filterReserveDimension(dimSeq);

        // 删除初始化的预算中心临时数据
        conditionMapper.clearDimensionTemp(dimSeq);
    }

    private void appendField(StringBuffer filterField, String field) {
        if (filterField.indexOf(field) == -1) {
            filterField.append("," + field);
        }
    }

    @Override
    public void extendCondition(IRequest request, BgtBalanceQueryGroup group,
                    Map<String, BgtBalanceQueryCondition> conditionMap, StringBuffer groupFieldSb) {
        Set<String> keySet = conditionMap.keySet();

        // 扩展预算中心
        if (keySet.contains(BgtBalanceQueryCondition.BGT_BUDGET_CENTER)) {
            extendBgtCenter(request, group, conditionMap.get(BgtBalanceQueryCondition.BGT_BUDGET_CENTER), groupFieldSb);
            appendField(groupFieldSb, "bgt_center_id");
        }

        // 扩展预算项目
        if (keySet.contains(BgtBalanceQueryCondition.BGT_BUDGET_ITEM)) {
            extendBgtItem(request, group, conditionMap.get(BgtBalanceQueryCondition.BGT_BUDGET_ITEM), groupFieldSb);
            appendField(groupFieldSb, "budget_item_id");
        }

        // 扩展预算项目类型
        if (keySet.contains(BgtBalanceQueryCondition.BGT_BUDGET_ITEM_TYPE)) {
            extendBgtItemType(request, group, conditionMap.get(BgtBalanceQueryCondition.BGT_BUDGET_ITEM_TYPE),
                            groupFieldSb);

            appendField(groupFieldSb, "budget_item_type_id");
        }

        // 扩展部门
        if (keySet.contains(BgtBalanceQueryCondition.ORG_ORG_UNIT)) {
            extendOrgUnit(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_ORG_UNIT), groupFieldSb);
            appendField(groupFieldSb, "unit_id");
        }

        // 扩展部门组
        if (keySet.contains(BgtBalanceQueryCondition.ORG_ORG_UNIT_GROUP)) {
            extendOrgUnitGroup(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_ORG_UNIT_GROUP),
                            groupFieldSb);
            appendField(groupFieldSb, "unit_group_id");
        }

        // 扩展岗位
        if (keySet.contains(BgtBalanceQueryCondition.ORG_ORG_POSITION)) {
            extendOrgPosition(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_ORG_POSITION),
                            groupFieldSb);

            appendField(groupFieldSb, "position_id");
        }

        // 扩展岗位组
        if (keySet.contains(BgtBalanceQueryCondition.ORG_ORG_POSITION_GROUP)) {
            extendOrgPositionGroup(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_ORG_POSITION_GROUP),
                            groupFieldSb);
            appendField(groupFieldSb, "position_group_id");
        }

        // 扩展员工
        if (keySet.contains(BgtBalanceQueryCondition.ORG_EMPLOYEE)) {
            extendEmployee(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_EMPLOYEE), groupFieldSb);
            appendField(groupFieldSb, "employee_id");
        }

        // 扩展员工组
        if (keySet.contains(BgtBalanceQueryCondition.ORG_EMPLOYEE_GROUP)) {
            extendEmployeeGroup(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_EMPLOYEE_GROUP),
                            groupFieldSb);
            appendField(groupFieldSb, "employee_group_id");
        }

        // 扩展员工级别
        if (keySet.contains(BgtBalanceQueryCondition.ORG_EMPLOYEE_LEVEL)) {
            extendEmployeeLevel(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_EMPLOYEE_LEVEL),
                            groupFieldSb);
            appendField(groupFieldSb, "employee_level_id");
        }

        // 扩展员工职务
        if (keySet.contains(BgtBalanceQueryCondition.ORG_EMPLOYEE_JOB)) {
            extendEmployeeJob(request, group, conditionMap.get(BgtBalanceQueryCondition.ORG_EMPLOYEE_JOB),
                            groupFieldSb);
            appendField(groupFieldSb, "employee_job_id");
        }

        // 扩展维度
        for (Long i = 1L; i <= 20L; i++) {
            if (keySet.contains(BgtBalanceQueryCondition.DIM_DIMENSION + i)) {
                extendDimension(request, group, conditionMap.get(BgtBalanceQueryCondition.DIM_DIMENSION + i), i,
                                groupFieldSb);
                appendField(groupFieldSb, "dimension" + i + "_id");
            }
        }
    }

    private void extendBgtCenter(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        if (BgtBalanceQueryCondition.RANGE_SUMMARY.equals(condition.getControlRuleRange())
                        || BgtBalanceQueryCondition.RANGE_ALL.equals(condition.getControlRuleRange())) {
            conditionMapper.extendBgtCenter(group, condition);
            conditionMapper.extendDeleteBgtCenter();
        }
    }

    private void extendBgtItem(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        if (BgtBalanceQueryCondition.RANGE_SUMMARY.equals(condition.getControlRuleRange())
                        || BgtBalanceQueryCondition.RANGE_ALL.equals(condition.getControlRuleRange())) {
            conditionMapper.extendBgtItem(group, condition);
            conditionMapper.extendDeleteBgtItem();
        }
    }

    private void extendBgtItemType(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        conditionMapper.extendBgtItemType(group, condition);
        conditionMapper.extendDeleteBgtItemType();
    }

    private void extendOrgUnit(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        //
        // 部门暂时不考虑上下级关系
        // ------------------------------------------------------------------------------
        return;
    }

    private void extendOrgUnitGroup(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        conditionMapper.extendOrgUnitGroup(group, condition);
        conditionMapper.extendDeleteOrgUnitGroup();
    }

    private void extendOrgPosition(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        //
        // 岗位暂时不考虑上下级关系
        // ------------------------------------------------------------------------------
        return;
    }

    private void extendOrgPositionGroup(IRequest request, BgtBalanceQueryGroup group,
                    BgtBalanceQueryCondition condition, StringBuffer groupFieldSb) {
        conditionMapper.extendOrgPositionGroup(group, condition);
        conditionMapper.extendDeleteOrgPositionGroup();
    }

    private void extendEmployee(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        return;
    }

    private void extendEmployeeGroup(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        conditionMapper.extendEmployeeGroup(group, condition);
        conditionMapper.extendDeleteEmployeeGroup();
    }

    private void extendEmployeeLevel(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        conditionMapper.extendEmployeeLevel(group, condition);
        conditionMapper.extendDeleteEmployeeLevel();
    }

    private void extendEmployeeJob(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer groupFieldSb) {
        conditionMapper.extendEmployeeJob(group, condition);
        conditionMapper.extendDeleteEmployeeJob();
    }

    private void extendDimension(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    Long dimSeq, StringBuffer groupFieldSb) {
        conditionMapper.extendDimension(group, condition, dimSeq);
        conditionMapper.extendDeleteDimension(dimSeq);
        conditionMapper.extendUpdateDimension();
    }

}

package com.hand.hec.bgt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.exp.mapper.ExpEmployeeLevelMapper;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.mapper.BgtBudgetItemMapper;
import com.hand.hec.bgt.mapper.BgtBudgetItemTypeMapper;
import com.hand.hec.bgt.mapper.BgtCenterMapper;
import com.hand.hec.bgt.mapper.BgtJournalBalanceMapper;
import com.hand.hec.bgt.service.IBgtBalanceQueryParamService;
import com.hand.hec.bgt.service.IBgtStructureService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.*;

/**
 * <p>
 * 预算余额查询参数Service Impl
 * </p>
 *
 * @author YHL 2019/03/22 15:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryParamServiceImpl implements IBgtBalanceQueryParamService {

    @Autowired
    private BgtJournalBalanceMapper journalBalanceMapper;

    @Autowired
    private BgtCenterMapper centerMapper;

    @Autowired
    private BgtBudgetItemMapper budgetItemMapper;

    @Autowired
    private BgtBudgetItemTypeMapper itemTypeMapper;

    @Autowired
    private ExpEmployeeMapper employeeMapper;

    @Autowired
    private ExpMoEmpGroupMapper empGroupMapper;

    @Autowired
    private ExpOrgPositionMapper positionMapper;

    @Autowired
    private ExpPositionGroupMapper positionGroupMapper;

    @Autowired
    private ExpEmployeeJobMapper employeeJobMapper;

    @Autowired
    private ExpEmployeeLevelMapper employeeLevelMapper;

    @Autowired
    private ExpOrgUnitMapper orgUnitMapper;

    @Autowired
    private ExpMoUnitGroupMapper unitGroupMapper;

    @Autowired
    private IBgtStructureService structureService;

    @Override
    public Boolean validateCompany(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper.checkCompanyForQuery(bgtBalanceQueryCondition.getParameterCode());
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该公司条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_COMPANY, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_COMPANY, param);
        return true;
    }

    @Override
    public Boolean validateBudgetOrganization(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper.checkBgtOrgForQuery(bgtOrgId);
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算组织条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_ORGANIZATION, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_ORGANIZATION, param);
        return true;
    }

    @Override
    public Boolean validateBudgetStructure(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper
                        .checkBgtStructureForQuery(bgtBalanceQueryCondition.getParameterCode(), bgtOrgId);
        // 根据预算表代码查询预算表
        BgtStructure bgtStructure = new BgtStructure();
        bgtStructure.setStructureCode(bgtBalanceQueryCondition.getParameterCode());
        Criteria criteria = new Criteria(bgtStructure);
        bgtStructure = structureService.selectOptions(request, bgtStructure, criteria).get(0);
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算表条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        // 预算表的参数的str1用于存放控制期段
        paramValue.setStr1(bgtStructure.getPeriodStrategy());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_STRUCTURE, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_STRUCTURE, param);
        return true;
    }

    @Override
    public Boolean validateBudgetScenario(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper
                        .checkBgtScenarioForQuery(bgtBalanceQueryCondition.getParameterCode(), bgtOrgId);
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算场景条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_SCENARIO, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_SCENARIO, param);
        return true;
    }

    @Override
    public Boolean validateBudgetVersion(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper.checkBgtVersionForQuery(bgtBalanceQueryCondition.getParameterCode(),
                        bgtOrgId);
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算版本条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_VERSION, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_VERSION, param);
        return true;
    }

    @Override
    public Boolean validateBudgetEntity(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, String entityFlag, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper.checkBgtEntityForQuery(bgtBalanceQueryCondition.getParameterCode(),
                        bgtOrgId);
        // 没有符合的预算记录
        if (balanceCount == 0) {
            String entityCheck = "Y";
            // 如果预算表勾选了预算实体，直接退出；如果未勾选预算实体，允许没有符合的预算记录
            return (!entityFlag.equals(entityCheck));
        }
        // 如果有符合的预算记录，将该预算实体条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_ENTITY, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_ENTITY, param);
        return true;
    }

    @Override
    public Boolean validateCurrency(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = journalBalanceMapper.checkCurrencyForQuery(bgtBalanceQueryCondition.getParameterCode());
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该币种条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_CURRENCY, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_CURRENCY, param);
        return true;
    }

    @Override
    public Boolean validateBudgetPeriod(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap) {
        // 预算期间序号（参数代码始值）
        Long internalPeriodNumLower = periodLower.getInternalPeriodNum();
        // 预算期间序号（参数代码止值）
        Long internalPeriodNumUpper = periodUpper.getInternalPeriodNum();

        // 预算余额记录条数
        Integer balanceCount =
                        journalBalanceMapper.checkBgtPeriodForQuery(bgtBalanceQueryCondition.getParameterValueFrom(),
                                        bgtBalanceQueryCondition.getParameterValueTo(), bgtOrgId);
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算期间条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        paramValue.setLong1(internalPeriodNumLower);
        paramValue.setLong2(internalPeriodNumUpper);
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_PERIOD, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_PERIOD, param);
        return true;
    }

    @Override
    public Boolean validateBudgetPeriodQuarter(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap) {
        // 如果预算期间季度不为空，且不在预算期间范围内，直接退出
        if (bgtBalanceQueryCondition.getParameterValueFrom() != null && Long
                        .parseLong(bgtBalanceQueryCondition.getParameterValueFrom()) > periodUpper.getQuarterNum()) {
            return false;
        }
        if (bgtBalanceQueryCondition.getParameterValueTo() != null && Long
                        .parseLong(bgtBalanceQueryCondition.getParameterValueTo()) < periodLower.getQuarterNum()) {
            return false;
        }
        // 如果预算期间季度符合预算期间，将该预算期间季度条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_PERIOD_QUARTER, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_PERIOD_QUARTER, param);
        return true;
    }

    @Override
    public Boolean validateBudgetPeriodYear(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap) {
        // 如果预算期间年度不为空，且不在预算期间范围内，直接退出
        if (bgtBalanceQueryCondition.getParameterValueFrom() != null && Long
                        .parseLong(bgtBalanceQueryCondition.getParameterValueFrom()) > periodUpper.getPeriodYear()) {
            return false;
        }
        if (bgtBalanceQueryCondition.getParameterValueTo() != null && Long
                        .parseLong(bgtBalanceQueryCondition.getParameterValueTo()) < periodLower.getPeriodYear()) {
            return false;
        }
        // 如果预算期间年度符合预算期间，将该预算期间年度条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_PERIOD_YEAR, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_PERIOD_YEAR, param);
        return true;
    }

    @Override
    public Boolean validateBudgetCenter(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 获取预算中心
        List<BgtCenter> bgtCenterList = centerMapper.getBgtCenterByBgtCenterCode(bgtOrgId,
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (bgtCenterList.isEmpty()) {
            return false;
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环预算中心
        for (BgtCenter center : bgtCenterList) {
            Integer count = journalBalanceMapper.checkBgtCenterForQuery(center.getCenterId(),
                            bgtBalanceQueryCondition.getControlRuleRange());
            balanceCount += count;
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算中心条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_CENTER, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_CENTER, param);
        return true;
    }

    @Override
    public Boolean validateBudgetItem(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, Map balanceQueryParamMap) {
        // 获取预算项目
        List<BgtBudgetItem> budgetItemList = budgetItemMapper.getBudgetItemListForQuery(bgtOrgId,
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (budgetItemList.isEmpty()) {
            return false;
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环预算项目
        for (BgtBudgetItem budgetItem : budgetItemList) {
            Integer count = journalBalanceMapper.checkBgtItemForQuery(budgetItem.getBudgetItemId(),
                            bgtBalanceQueryCondition.getControlRuleRange());
            balanceCount += count;
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算项目条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_ITEM, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_ITEM, param);
        return true;
    }

    @Override
    public Boolean validateBudgetItemType(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Long bgtOrgId, List<BgtBudgetItem> budgetItemList, Map balanceQueryParamMap) {
        // 获取预算项目类型
        List<BgtBudgetItemType> budgetItemTypeList = itemTypeMapper.getBudgetItemTypeListForQuery(bgtOrgId,
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (budgetItemTypeList.isEmpty()) {
            return false;
        }
        // 如果预算项目不为空
        if (!budgetItemList.isEmpty()) {
            // 预算项目类型与预算项目匹配的数量
            Integer count = 0;
            for (BgtBudgetItem budgetItem : budgetItemList) {
                for (BgtBudgetItemType budgetItemType : budgetItemTypeList) {
                    if (budgetItemType.getBudgetItemTypeId().equals(budgetItem.getBudgetItemTypeId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果预算项目类型与预算项目均不匹配，直接退出
            if (count == 0) {
                return false;
            }
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环预算项目类型
        for (BgtBudgetItemType budgetItemType : budgetItemTypeList) {
            // 获取预算项目类型对应的预算项目
            BgtBudgetItem item = new BgtBudgetItem();
            item.setBgtOrgId(bgtOrgId);
            item.setBudgetItemTypeId(budgetItemType.getBudgetItemTypeId());
            Criteria criteria = new Criteria(item);
            criteria.where(new WhereField(BgtBudgetItem.FIELD_BGT_ORG_ID, Comparison.EQUAL),
                            new WhereField(BgtBudgetItem.FIELD_BUDGET_ITEM_TYPE_ID, Comparison.EQUAL));
            budgetItemList = budgetItemMapper.selectOptions(item, criteria);
            if (budgetItemList.isEmpty()) {
                continue;
            }
            // 循环预算项目
            for (BgtBudgetItem budgetItem : budgetItemList) {
                Integer count = journalBalanceMapper.checkBgtItemForQuery(budgetItem.getBudgetItemId(),
                                bgtBalanceQueryCondition.getControlRuleRange());
                balanceCount += count;
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该预算项目类型条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.BGT_BUDGET_ITEM_TYPE, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.BGT_BUDGET_ITEM_TYPE, param);
        return true;
    }

    @Override
    public Boolean validateEmployee(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Map balanceQueryParamMap) {
        // 获取员工
        List<ExpEmployee> employeeList = employeeMapper.getEmployeeListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (employeeList.isEmpty()) {
            return false;
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环员工
        for (ExpEmployee employee : employeeList) {
            Integer count = journalBalanceMapper.checkEmployeeForQuery(employee.getEmployeeId());
            balanceCount += count;
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该员工条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_EMPLOYEE, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_EMPLOYEE, param);
        return true;
    }

    @Override
    public Boolean validateEmpGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    List<ExpEmployee> employeeList, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 获取员工组
        List<ExpMoEmpGroup> empGroupList = empGroupMapper.getEmpGroupListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (empGroupList.isEmpty()) {
            return false;
        }
        // 如果员工不为空
        if (!employeeList.isEmpty()) {
            // 员工组与员工匹配的数量
            Integer count = 0;
            for (ExpEmployee employee : employeeList) {
                for (ExpMoEmpGroup empGroup : empGroupList) {
                    if (empGroup.getEmployeeId().equals(employee.getEmployeeId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果员工组与员工均不匹配，直接退出
            if (count == 0) {
                return false;
            }
            // 循环员工组
            for (ExpMoEmpGroup empGroup : empGroupList) {
                Integer count1 = journalBalanceMapper.checkEmpGroupForQuery(empGroup.getMoEmpGroupId(),
                                empGroup.getEmployeeId());
                balanceCount += count1;
            }
        } else {
            // 循环员工组
            for (ExpMoEmpGroup empGroup : empGroupList) {
                Integer count = journalBalanceMapper.checkEmpGroupForQuery(empGroup.getMoEmpGroupId(), null);
                balanceCount += count;
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该员工组条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_EMPLOYEE_GROUP, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_EMPLOYEE_GROUP, param);
        return true;
    }

    @Override
    public Boolean validatePosition(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Map balanceQueryParamMap) {
        // 获取岗位
        List<ExpOrgPosition> positionList = positionMapper.getPositionListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (positionList.isEmpty()) {
            return false;
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环岗位
        for (ExpOrgPosition position : positionList) {
            Integer count = journalBalanceMapper.checkOrgPositionForQuery(position.getPositionId());
            balanceCount += count;
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该岗位条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_ORG_POSITION, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_ORG_POSITION, param);
        return true;
    }

    @Override
    public Boolean validatePositionGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    List<ExpOrgPosition> positionList, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 获取岗位组
        List<ExpPositionGroup> positionGroupList = positionGroupMapper.getPositionGroupListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (positionGroupList.isEmpty()) {
            return false;
        }
        // 如果岗位不为空
        if (!positionList.isEmpty()) {
            // 岗位组与岗位匹配的数量
            Integer count = 0;
            for (ExpOrgPosition position : positionList) {
                for (ExpPositionGroup positionGroup : positionGroupList) {
                    if (positionGroup.getPositionId().equals(position.getPositionId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果岗位组与岗位均不匹配，直接退出
            if (count == 0) {
                return false;
            }
            // 循环岗位组
            for (ExpPositionGroup positionGroup : positionGroupList) {
                Integer count1 = journalBalanceMapper.checkPositionGroupForQuery(positionGroup.getPositionGroupId(),
                                positionGroup.getPositionId());
                balanceCount += count1;
            }
        } else {
            // 循环岗位组
            for (ExpPositionGroup positionGroup : positionGroupList) {
                Integer count = journalBalanceMapper.checkPositionGroupForQuery(positionGroup.getPositionGroupId(),
                                null);
                balanceCount += count;
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该岗位组条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_ORG_POSITION_GROUP, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_ORG_POSITION_GROUP, param);
        return true;
    }

    @Override
    public Boolean validateEmployeeJob(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    List<ExpOrgPosition> positionList, Map balanceQueryParamMap) {
        // 获取员工职务
        List<ExpEmployeeJob> employeeJobList = employeeJobMapper.getEmployeeJobListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (employeeJobList.isEmpty()) {
            return false;
        }
        // 如果岗位不为空
        if (!positionList.isEmpty()) {
            // 员工职务与岗位匹配的数量
            Integer count = 0;
            for (ExpOrgPosition position : positionList) {
                for (ExpEmployeeJob employeeJob : employeeJobList) {
                    if (employeeJob.getPositionId().equals(position.getPositionId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果员工职务与岗位均不匹配，直接退出
            if (count == 0) {
                return false;
            }
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环员工职务
        for (ExpEmployeeJob employeeJob : employeeJobList) {
            // 获取员工职务对应的岗位
            ExpOrgPosition position = new ExpOrgPosition();
            position.setCompanyId(request.getCompanyId());
            position.setEmployeeJobId(employeeJob.getEmployeeJobId());
            Criteria criteria = new Criteria(position);
            criteria.where(new WhereField(ExpOrgPosition.FIELD_COMPANY_ID, Comparison.EQUAL),
                            new WhereField(ExpOrgPosition.FIELD_EMPLOYEE_JOB_ID, Comparison.EQUAL));
            positionList = positionMapper.selectOptions(position, criteria);
            if (positionList.isEmpty()) {
                continue;
            }
            // 循环岗位
            for (ExpOrgPosition expOrgPosition : positionList) {
                Integer count = journalBalanceMapper.checkOrgPositionForQuery(expOrgPosition.getPositionId());
                balanceCount += count;
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该员工职务条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_EMPLOYEE_JOB, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_EMPLOYEE_JOB, param);
        return true;
    }

    @Override
    public Boolean validateEmployeeLevel(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    List<ExpEmployeeJob> employeeJobList, Map balanceQueryParamMap) {
        // 获取员工级别
        List<ExpEmployeeLevel> employeeLevelList = employeeLevelMapper.getEmployeeLevelListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (employeeLevelList.isEmpty()) {
            return false;
        }
        // 如果员工职务不为空
        if (!employeeJobList.isEmpty()) {
            // 员工级别与员工职务匹配的数量
            Integer count = 0;
            for (ExpEmployeeJob employeeJob : employeeJobList) {
                for (ExpEmployeeLevel employeeLevel : employeeLevelList) {
                    if (employeeLevel.getLevelSeriesId().equals(employeeJob.getLevelSeriesId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果员工级别与员工职务均不匹配，直接退出
            if (count == 0) {
                return false;
            }
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环员工级别
        for (ExpEmployeeLevel employeeLevel : employeeLevelList) {
            // 获取员工级别对应的职务
            ExpEmployeeJob employeeJob = new ExpEmployeeJob();
            employeeJob.setMagOrgId(request.getMagOrgId());
            employeeJob.setLevelSeriesId(employeeLevel.getLevelSeriesId());
            Criteria criteria = new Criteria(employeeJob);
            criteria.where(new WhereField(ExpEmployeeJob.FIELD_MAG_ORG_ID, Comparison.EQUAL),
                            new WhereField(ExpEmployeeJob.FIELD_LEVEL_SERIES_ID, Comparison.EQUAL));
            employeeJobList = employeeJobMapper.selectOptions(employeeJob, criteria);
            if (employeeJobList.isEmpty()) {
                continue;
            }
            // 循环员工职务
            for (ExpEmployeeJob job : employeeJobList) {
                // 获取员工职务对应的岗位
                ExpOrgPosition position = new ExpOrgPosition();
                position.setCompanyId(request.getCompanyId());
                position.setEmployeeJobId(job.getEmployeeJobId());
                criteria = new Criteria(position);
                criteria.where(new WhereField(ExpOrgPosition.FIELD_COMPANY_ID, Comparison.EQUAL),
                                new WhereField(ExpOrgPosition.FIELD_EMPLOYEE_JOB_ID, Comparison.EQUAL));
                List<ExpOrgPosition> positionList = positionMapper.selectOptions(position, criteria);
                if (positionList.isEmpty()) {
                    continue;
                }
                // 循环岗位
                for (ExpOrgPosition expOrgPosition : positionList) {
                    Integer count = journalBalanceMapper.checkOrgPositionForQuery(expOrgPosition.getPositionId());
                    balanceCount += count;
                }
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该员工级别条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_EMPLOYEE_LEVEL, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_EMPLOYEE_LEVEL, param);
        return true;
    }

    @Override
    public Boolean validateOrgUnit(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    Map balanceQueryParamMap) {
        // 获取部门
        List<ExpOrgUnit> unitList = orgUnitMapper.getOrgUnitListForQuery(bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (unitList.isEmpty()) {
            return false;
        }
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 循环部门
        for (ExpOrgUnit unit : unitList) {
            Integer count = journalBalanceMapper.checkOrgUnitForQuery(unit.getUnitId());
            balanceCount += count;
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该部门条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_ORG_UNIT, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_ORG_UNIT, param);
        return true;
    }

    @Override
    public Boolean validateUnitGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
                    List<ExpOrgUnit> unitList, Map balanceQueryParamMap) {
        // 预算余额记录条数
        Integer balanceCount = 0;
        // 获取部门组
        List<ExpMoUnitGroup> unitGroupList = unitGroupMapper.getUnitGroupListForQuery(
                        bgtBalanceQueryCondition.getParameterCode(), bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        if (unitGroupList.isEmpty()) {
            return false;
        }
        // 如果部门不为空
        if (!unitList.isEmpty()) {
            // 部门组与部门匹配的数量
            Integer count = 0;
            for (ExpOrgUnit unit : unitList) {
                for (ExpMoUnitGroup unitGroup : unitGroupList) {
                    if (unitGroup.getUnitId().equals(unit.getUnitId())) {
                        count++;
                        break;
                    }
                }
            }
            // 如果部门组与部门均不匹配，直接退出
            if (count == 0) {
                return false;
            }
            // 循环部门组
            for (ExpMoUnitGroup unitGroup : unitGroupList) {
                Integer count1 = journalBalanceMapper.checkUnitGroupForQuery(unitGroup.getMoUnitGroupId(),
                                unitGroup.getUnitId());
                balanceCount += count1;
            }
        } else {
            // 循环部门组
            for (ExpMoUnitGroup unitGroup : unitGroupList) {
                Integer count = journalBalanceMapper.checkUnitGroupForQuery(unitGroup.getMoUnitGroupId(), null);
                balanceCount += count;
            }
        }
        // 如果没有符合的预算记录，直接退出
        if (balanceCount == 0) {
            return false;
        }
        // 如果有符合的预算记录，将该部门组条件放入预算余额查询参数Map
        BgtBalanceQueryParamValue paramValue = new BgtBalanceQueryParamValue(
                        bgtBalanceQueryCondition.getControlRuleRange(), bgtBalanceQueryCondition.getParameterCode(),
                        bgtBalanceQueryCondition.getParameterValueFrom(),
                        bgtBalanceQueryCondition.getParameterValueTo());
        BgtBalanceQueryParam param = new BgtBalanceQueryParam(BgtBalanceQueryParam.ORG_ORG_UNIT_GROUP, paramValue);
        balanceQueryParamMap.put(BgtBalanceQueryParam.ORG_ORG_UNIT_GROUP, param);
        return true;
    }
}

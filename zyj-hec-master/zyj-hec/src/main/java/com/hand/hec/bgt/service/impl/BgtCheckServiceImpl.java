package com.hand.hec.bgt.service.impl;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.SortType;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.exception.BgtCheckParamException;
import com.hand.hec.bgt.mapper.BgtCheckMapper;
import com.hand.hec.bgt.service.*;

/**
 * description
 *
 * @author mouse 2019/01/08 18:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCheckServiceImpl implements IBgtCheckService {

    @Autowired
    IBgtControlStrategyGroupService strategyGroupService;

    @Autowired
    IBgtControlStrategyService strategyService;

    @Autowired
    IBgtCtrlStrategyMpCondService strategyMpCondService;

    @Autowired
    IBgtControlRuleService controlRuleService;

    @Autowired
    IBgtControlRuleDetailService controlRuleDetailService;

    @Autowired
    IBgtCheckParamService paramService;

    @Autowired
    BgtCheckMapper checkMapper;

    @Autowired
    IBgtOrganizationService organizationService;

    @Autowired
    IBgtPeriodService periodService;

    /**
     * 预算检查
     *
     * @param reserve 预算占用行
     * @param request 请求
     * @author mouse 2019-01-30 10:18
     * @return com.hand.hec.bgt.dto.BgtCheckResult
     */
    @Override
    public BgtCheckResult check(BgtBudgetReserve reserve, String ignoreWarning, IRequest request) {
        BgtCheckResult checkResult = new BgtCheckResult();
        // 获取控制策略组
        List<BgtControlStrategyGroup> strategyGroupList = getControlStrategyGroup(reserve, request);
        // 根据控制策略组获取控制规则明细
        for (BgtControlStrategyGroup strategyGroup : strategyGroupList) {
            List<BgtControlRule> controlRuleList = getControlRuleList(strategyGroup, reserve, request);
            for (BgtControlRule controlRule : controlRuleList) {
                // 根据控制策略组获取控制策略和控制规则匹配组
                List<BgtControlStrategy> strategyList = getControlStrategy(strategyGroup, reserve, request);

                // 控制策略循环
                strategySign:
                for (BgtControlStrategy strategy : strategyList) {
                    // 根据控制策略获取控制策略匹配组
                    List<BgtCtrlStrategyMpCond> strategyMpCondList = getCtrlStrategyMpCond(strategy, reserve, request);
                    for (BgtCtrlStrategyMpCond strategyMpCond : strategyMpCondList) {
                        Map<String, BgtCheckParam> checkParamMap = new HashMap<String, BgtCheckParam>();
                        // 初始化控制期段、期间、季度、年度信息
                        initPeriodInfo(strategyMpCond, reserve, checkParamMap, request);

                        // 判断控制规则类型,如果当前控制规则不满足，应当跳出本次控制规则的循环
                        if (!validateControlRuleDetail(strategy, strategyMpCond, controlRule, reserve, checkParamMap,
                                request)) {
                            break strategySign;
                        }

                        Stack<String> paramNameStack = new Stack<String>();
                        checkParamMap.keySet().forEach((key) -> {
                            paramNameStack.add(key.toString());
                        });

                        BgtCheckCondition checkCondition = new BgtCheckCondition();

                        // 如果预算检查条件不满足
                        if (!validateCondition(strategy, strategyMpCond, paramNameStack, checkParamMap,
                                checkCondition)) {
                            //
                            // 当前预算控制规则未通过，判断当前检查结果与当前控制规则
                            // 1、如果检查结果为空，则以当前控制规则结果作为检查结果
                            // 2、如果检查结果为：ALLOWED，且当前控制规则结果也为ALLOWED，则不进行变更
                            // 3、如果检查结果为：ALLOWED，且当前控制规则结果为BLOCK，则修改为BLOCK的结果
                            // 4、如果当前检查结果为BLOCK，则直接返回，不继续检查
                            // ------------------------------------------------------------------------------
                            if (checkResult.getResultType() == null) {
                                checkResult.setResultType(strategy.getControlMethod());
                                checkResult.setMessage(strategy.getMessageCode());
                            } else if (BgtControlStrategy.CONTROL_METHOD_ALLOWED.equals(strategy.getControlMethod())
                                    && BgtControlStrategy.CONTROL_METHOD_ALLOWED.equals(checkResult.getResultType())) {

                            } else if (BgtControlStrategy.CONTROL_METHOD_BLOCK.equals(strategy.getControlMethod())) {
                                checkResult.setResultType(strategy.getControlMethod());
                                checkResult.setMessage(strategy.getMessageCode());
                            }

                            if (BgtControlStrategy.CONTROL_METHOD_BLOCK.equals(checkResult.getResultType())) {
                                checkResult.setIsPassed(false);
                                return checkResult;
                            }
                        }


                    }
                }
            }
        }

        // 如果当前检查结果为空或者检查结果为ALLOWED而且忽略警告，本次检查通过，否则本次检查不通过
        if (checkResult.getResultType() == null || ("Y".equals(ignoreWarning)
                && BgtControlStrategy.CONTROL_METHOD_ALLOWED.equals(checkResult.getResultType()))) {
            checkResult.setIsPassed(true);
        } else {
            checkResult.setIsPassed(false);
        }

        return checkResult;
    }

    /**
     * 获取当前预算组织下的有效控制策略组
     *
     * @param reserve 预算占用行
     * @param request 请求
     * @author mouse 2019-01-29 16:49
     * @return java.util.List<com.hand.hec.bgt.dto.BgtControlStrategyGroup>
     */
    private List<BgtControlStrategyGroup> getControlStrategyGroup(BgtBudgetReserve reserve, IRequest request) {
        BgtControlStrategyGroup queryGroup = new BgtControlStrategyGroup();
        queryGroup.setBgtOrgId(reserve.getBgtOrgId());
        queryGroup.setEnabledFlag("Y");
        Criteria criteria = new Criteria(queryGroup);
        return strategyGroupService.selectOptions(request, queryGroup, criteria);
    }

    /**
     * 获取当前预算控制策略组下的预算控制策略
     *
     * @param strategyGroup 预算控制策略组
     * @param reserve 预算占用行
     * @param request 请求
     * @author mouse 2019-01-29 16:51
     * @return java.util.List<com.hand.hec.bgt.dto.BgtControlStrategy>
     */
    private List<BgtControlStrategy> getControlStrategy(BgtControlStrategyGroup strategyGroup, BgtBudgetReserve reserve,
            IRequest request) {
        BgtControlStrategy queryStrategy = new BgtControlStrategy();
        queryStrategy.setControlStrategyGroupId(strategyGroup.getControlStrategyGroupId());
        Criteria criteria = new Criteria(queryStrategy);
        return strategyService.selectOptions(request, queryStrategy, criteria);
    }

    /**
     * 获取当前预算控制策略明细下的匹配条件
     *
     * @param strategy 控制策略
     * @param reserve 预算占用行
     * @param request 请求
     * @author mouse 2019-01-29 16:53
     * @return java.util.List<com.hand.hec.bgt.dto.BgtCtrlStrategyMpCond>
     */
    private List<BgtCtrlStrategyMpCond> getCtrlStrategyMpCond(BgtControlStrategy strategy, BgtBudgetReserve reserve,
            IRequest request) {
        BgtCtrlStrategyMpCond queryStrategyMpCond = new BgtCtrlStrategyMpCond();
        queryStrategyMpCond.setControlStrategyId(strategy.getControlStrategyId());
        Criteria criteria = new Criteria(queryStrategyMpCond);
        return strategyMpCondService.selectOptions(request, queryStrategyMpCond, criteria);
    }

    /**
     * 获取启用状态的预算控制规则列表
     *
     * @param strategyGroup 控制策略组
     * @param reserve 预算占用行
     * @param request 请求
     * @author mouse 2019-01-29 17:09
     * @return java.util.List<com.hand.hec.bgt.dto.BgtControlRule>
     */
    private List<BgtControlRule> getControlRuleList(BgtControlStrategyGroup strategyGroup, BgtBudgetReserve reserve,
            IRequest request) {
        BgtControlRule queryControlRule = new BgtControlRule();
        queryControlRule.setStartDate(new Date());
        queryControlRule.setEndDate(new Date());
        queryControlRule.setBgtOrgId(reserve.getBgtOrgId());
        queryControlRule.setBudgetStrategyGroupId(strategyGroup.getControlStrategyGroupId());
        Criteria criteria = new Criteria(queryControlRule);
        criteria.where(new WhereField[]{new WhereField(BgtControlRule.FIELD_BGT_ORG_ID),
                new WhereField(BgtControlRule.FIELD_BUDGET_STRATEGY_GROUP_ID),
                new WhereField(BgtControlRule.FIELD_START_DATE), new WhereField(BgtControlRule.FIELD_END_DATE)})
                .sort(BgtControlRule.FIELD_PRIORITY, SortType.ASC);
        return controlRuleService.selectOptions(request, queryControlRule, criteria, 0, 0);
    }

    /**
     * 初始化期间信息，包括期间、季度、年度
     *
     * @param reserve 预算占用行
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-29 17:18
     * @return void
     */
    private void initPeriodInfo(BgtCtrlStrategyMpCond strategyMpCond, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        // 初始化控制期段
        BgtCheckParamValue paramValue = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL, null,
                strategyMpCond.getPeriodStrategy());
        BgtCheckParam param = new BgtCheckParam();
        param.setParamType(BgtCheckParam.BGT_CONTROL_PERIOD_STRATEGY);
        param.setParamValue(paramValue);
        checkParamMap.put(BgtCheckParam.BGT_CONTROL_PERIOD_STRATEGY, param);

        BgtOrganization organization = new BgtOrganization();
        organization.setBgtOrgId(reserve.getBgtOrgId());
        organization.setEnabledFlag("Y");
        organization = organizationService.selectByPrimaryKey(request, organization);

        if (organization == null) {
            throw new BgtCheckParamException("BGT", "bgt_check.bgt_organization_error", null);
        }

        BgtPeriod period = new BgtPeriod();
        period.setPeriodName(reserve.getPeriodName());
        period.setPeriodSetId(organization.getPeriodSetId());
        List<BgtPeriod> periodList = periodService.select(request, period, 0, 0);

        if (periodList.size() == 0) {
            throw new BgtCheckParamException("BGT", "bgt_check.bgt_period_error", null);
        }

        period = periodList.get(0);

        // 设置期间
        BgtCheckParamValue periodValue = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                period.getPeriodId(), period.getPeriodName());
        periodValue.setLong1(period.getInternalPeriodNum());
        BgtCheckParam periodParam = new BgtCheckParam();
        periodParam.setParamType(BgtCheckParam.BGT_BUDGET_PERIOD);
        periodParam.setParamValue(periodValue);

        // 设置季度
        BgtCheckParamValue quarterValue = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                period.getQuarterNum(), period.getQuarterNum().toString());
        BgtCheckParam quarterParam = new BgtCheckParam();
        quarterParam.setParamType(BgtCheckParam.BGT_BUDGET_QUARTER);
        quarterParam.setParamValue(quarterValue);

        // 设置年度
        BgtCheckParamValue yearValue = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                period.getPeriodYear(), period.getPeriodYear().toString());
        BgtCheckParam yearParam = new BgtCheckParam();
        yearParam.setParamType(BgtCheckParam.BGT_BUDGET_YEAR);
        yearParam.setParamValue(yearValue);

        checkParamMap.put(BgtCheckParam.BGT_BUDGET_PERIOD, periodParam);
        checkParamMap.put(BgtCheckParam.BGT_BUDGET_QUARTER, quarterParam);
        checkParamMap.put(BgtCheckParam.BGT_BUDGET_YEAR, yearParam);
    }

    /**
     * 验证单条预算控制规则是否通过
     *
     * @param strategy 控制策略
     * @param strategyMpCond 控制策略匹配条件
     * @param controlRule 控制规则
     * @param reserve 预算占用行
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 10:09
     * @return java.lang.Boolean
     */
    private Boolean validateControlRuleDetail(BgtControlStrategy strategy, BgtCtrlStrategyMpCond strategyMpCond,
            BgtControlRule controlRule, BgtBudgetReserve reserve, Map checkParamMap, IRequest request) {
        // 获取控制规则明细
        BgtControlRuleDetail queryControlRuleDetail = new BgtControlRuleDetail();
        queryControlRuleDetail.setControlRuleId(controlRule.getControlRuleId());
        Criteria criteria = new Criteria(queryControlRuleDetail);
        criteria.where(new WhereField[]{new WhereField(BgtControlRuleDetail.FIELD_CONTROL_RULE_ID)})
                .sort(BgtControlRuleDetail.FIELD_CONTROL_RULE_DETAIL_ID, SortType.ASC);
        List<BgtControlRuleDetail> controlRuleDetailList = controlRuleDetailService
                .selectOptions(request, queryControlRuleDetail, criteria, 0, 0);
        Boolean validateResult = false;

        //自定义维度序号
        int dimensionSeq = 1;
        // 循环验证控制规则明细
        for (BgtControlRuleDetail controlRuleDetail : controlRuleDetailList) {
            String ruleParameterType = "BGT_CONTROL_PARAMETER_DIM";
            if (controlRuleDetail.getRuleParameterType().equals(ruleParameterType)) {
                controlRuleDetail.setRuleParameter("DIMENSION_" + dimensionSeq);
                dimensionSeq++;
            }
            if (!validateControlRuleDetail(controlRuleDetail, reserve, checkParamMap, request)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 验证单个条件组合下是否超预算
     *
     * @param strategy 控制策略
     * @param strategyMpCond 控制策略匹配条件
     * @param paramNameStack 参数名称堆栈
     * @param paramMap 参数Map
     * @param checkCondition 单条预算检查条件
     * @author mouse 2019-01-30 10:12
     * @return java.lang.Boolean
     */
    private Boolean validateCondition(BgtControlStrategy strategy, BgtCtrlStrategyMpCond strategyMpCond,
            Stack<String> paramNameStack, Map<String, BgtCheckParam> paramMap, BgtCheckCondition checkCondition) {
        String paramName = paramNameStack.pop();
        BgtCheckParam param = paramMap.get(paramName);

        List<BgtCheckParamValue> paramValueList = param.getParamValues();
        for (BgtCheckParamValue paramValue : paramValueList) {
            checkCondition.getCheckParamValueMap().put(paramName, paramValue);

            if (paramNameStack.size() != 0) {
                // 如果当前参数名称堆栈还有更多元素，则继续递归，生成condition
                // 重新生成一个参数名称stack，用于遍历参数使用
                Stack<String> nextParamNameList = new Stack<String>();
                paramNameStack.forEach((key) -> {
                    nextParamNameList.add(key.toString());
                });

                // 如果当前验证失败，返回false
                if (!validateCondition(strategy, strategyMpCond, nextParamNameList, paramMap, checkCondition)) {
                    return false;
                }
            } else {
                // 如果当前参数名称堆栈已经没有更多元素，说明所有的参数都已经装载进入condition，可以执行验证程序

                BgtCheckBalanceResult balanceResult = null;
                BgtCheckReserveResult reserveResult = null;
                // 判断预算控制策略匹配项
                // 如果是进行金额、数量绝对值判断，无需进行balance的汇总
                if (BgtCtrlStrategyMpCond.MANNER_PERCENTAGE.equals(strategyMpCond.getManner())
                        || strategyMpCond.getManner() == null) {
                    balanceResult = checkMapper.getBalanceAmount(checkCondition);
                }

                reserveResult = checkMapper.getReserveAmount(checkCondition);


                BigDecimal balanceAmount = null;
                BigDecimal balanceFunctionalAmount = null;
                BigDecimal balanceQuantity = null;

                BigDecimal calBalanceAmount = null;
                BigDecimal calBalanceFunctionalAmount = null;
                BigDecimal calBalanceQuantity = null;

                BigDecimal reserveAmount = reserveResult.getTotalAmount();
                BigDecimal reserveFunctionalAmount = reserveResult.getTotalFunctionalAmount();
                BigDecimal reserveQuantity = reserveResult.getTotalQuantity();

                //
                // 进行预算检查判断
                // 1、判定预算表的编制期段类型和匹配条件类型，设置对应的预算金额
                // 2、进行比对，并返回结果
                // ------------------------------------------------------------------------------
                if (BgtCtrlStrategyMpCond.MANNER_FIXED_AMOUNT.equals(strategyMpCond.getManner())) {
                    // 固定金额：
                    balanceAmount = strategyMpCond.getValue();
                    balanceFunctionalAmount = strategyMpCond.getValue();
                    balanceQuantity = strategyMpCond.getValue();
                } else if (balanceResult != null) {
                    // 比例或自定义函数，判定预算表的编制期段
                    String structureStrategy = checkCondition.getCheckParamValueMap()
                            .get(BgtCheckParam.BGT_BUDGET_STRUCTURE).getStr1();
                    // 编制期段为月份，则预算额取月度金额汇总
                    if (BgtStructure.BUDGET_PERIOD_MONTH.equals(structureStrategy)) {
                        balanceAmount = balanceResult.getTotalPeriodAmount();
                        balanceFunctionalAmount = balanceResult.getTotalPeriodFunctionalAmount();
                        balanceQuantity = balanceResult.getTotalPeriodQuantity();
                    } else if (BgtStructure.BUDGET_PERIOD_QUARTER.equals(structureStrategy)) {
                        balanceAmount = balanceResult.getTotalQuarterAmount();
                        balanceFunctionalAmount = balanceResult.getTotalQuarterFunctionalAmount();
                        balanceQuantity = balanceResult.getTotalQuarterQuantity();
                    } else if (BgtStructure.BUDGET_PERIOD_YEAR.equals(structureStrategy)) {
                        balanceAmount = balanceResult.getTotalYearAmount();
                        balanceFunctionalAmount = balanceResult.getTotalYearFunctionalAmount();
                        balanceQuantity = balanceResult.getTotalYearQuantity();
                    }
                }

                if (balanceResult != null) {
                    if (strategyMpCond.getManner() == null) {
                        return true;
                    }

                    calBalanceAmount = balanceAmount.divide(new BigDecimal(100));
                    calBalanceFunctionalAmount = balanceFunctionalAmount.divide(new BigDecimal(100));
                    calBalanceQuantity = balanceQuantity.divide(new BigDecimal(100));

                    if (BgtCtrlStrategyMpCond.OPERATOR_PLUS.equals(strategyMpCond.getOperator())) {
                        calBalanceAmount = calBalanceAmount.add(strategyMpCond.getValue());
                        calBalanceFunctionalAmount = calBalanceFunctionalAmount.add(strategyMpCond.getValue());
                        calBalanceQuantity = calBalanceQuantity.add(strategyMpCond.getValue());
                    } else if (BgtCtrlStrategyMpCond.OPERATOR_MINUS.equals(strategyMpCond.getOperator())) {
                        calBalanceAmount = calBalanceAmount.subtract(strategyMpCond.getValue());
                        calBalanceFunctionalAmount = calBalanceFunctionalAmount.subtract(strategyMpCond.getValue());
                        calBalanceQuantity = calBalanceQuantity.subtract(strategyMpCond.getValue());
                    } else if (BgtCtrlStrategyMpCond.OPERATOR_MULTIPLY.equals(strategyMpCond.getOperator())) {
                        calBalanceAmount = calBalanceAmount.multiply(strategyMpCond.getValue());
                        calBalanceFunctionalAmount = calBalanceFunctionalAmount.multiply(strategyMpCond.getValue());
                        calBalanceQuantity = calBalanceQuantity.multiply(strategyMpCond.getValue());
                    } else if (BgtCtrlStrategyMpCond.OPERATOR_DIVIDE.equals(strategyMpCond.getOperator())) {
                        calBalanceAmount = calBalanceAmount.divide(strategyMpCond.getValue());
                        calBalanceFunctionalAmount = calBalanceFunctionalAmount.divide(strategyMpCond.getValue());
                        calBalanceQuantity = calBalanceQuantity.divide(strategyMpCond.getValue());
                    }

                    BigDecimal compareBalance = BgtCtrlStrategyMpCond.OBJECT_AMOUNT.equals(strategyMpCond.getObject()) ?
                            calBalanceAmount :
                            calBalanceQuantity;
                    BigDecimal compareReserve = BgtCtrlStrategyMpCond.OBJECT_AMOUNT.equals(strategyMpCond.getObject()) ?
                            reserveAmount :
                            reserveQuantity;

                    if (BgtCtrlStrategyMpCond.RANGE_LESS_THEN.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) < 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (BgtCtrlStrategyMpCond.RANGE_LESS_THEN_EQUAL.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) <= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (BgtCtrlStrategyMpCond.RANGE_GREAT_THEN.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (BgtCtrlStrategyMpCond.RANGE_GREAT_THEN_EQUAL.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (BgtCtrlStrategyMpCond.RANGE_EQUAL.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (BgtCtrlStrategyMpCond.RANGE_NOT_EQUAL.equals(strategyMpCond.getRange())) {
                        if (compareBalance.compareTo(compareReserve) != 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (strategyMpCond.getManner() == null) {
                        //配置了零预算控制策略，即预算为0
                        return false;
                    }
                    else{
                        //未配置零预算控制策略，即预算为无穷大
                        return true;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 验证当前预算占用行是否匹配预算控制中的明细项
     *
     * @param controlRuleDetail 控制规则明细项
     * @param reserve 预算占用行
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 10:14
     * @return java.lang.Boolean
     */
    private Boolean validateControlRuleDetail(BgtControlRuleDetail controlRuleDetail, BgtBudgetReserve reserve,
            Map checkParamMap, IRequest request) {
        switch (controlRuleDetail.getRuleParameter()) {
            // 预算币种、预算表、预算版本、预算场景不需要进行比对，返回true
            case BgtCheckParam.BGT_BUDGET_CURRENCY:
                if (!paramService.validateBudgetCurrency(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_STRUCTURE:
                if (!paramService.validateBudgetStructure(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_VERSION:
                if (!paramService.validateBudgetVersion(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_SCENARIO:
                if (!paramService.validateBudgetScenario(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_ENTITY:
                if (!paramService.validateBudgetEntity(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_CENTER:
                if (!paramService.validateBudgetCenter(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_ITEM:
                if (!paramService.validateBudgetItem(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_ITEM_TYPE:
                if (!paramService.validateBudgetItemType(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_PERIOD:
                if (!paramService.validateBudgetPeriod(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_QUARTER:
                if (!paramService.validateBudgetQuarter(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.BGT_BUDGET_YEAR:
                if (!paramService.validateBudgetYear(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_1:
                if (!paramService.validateDimension(1L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_2:
                if (!paramService.validateDimension(2L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_3:
                if (!paramService.validateDimension(3L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_4:
                if (!paramService.validateDimension(4L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_5:
                if (!paramService.validateDimension(5L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_6:
                if (!paramService.validateDimension(6L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_7:
                if (!paramService.validateDimension(7L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_8:
                if (!paramService.validateDimension(8L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_9:
                if (!paramService.validateDimension(9L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_10:
                if (!paramService.validateDimension(10L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_11:
                if (!paramService.validateDimension(11L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_12:
                if (!paramService.validateDimension(12L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_13:
                if (!paramService.validateDimension(13L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_14:
                if (!paramService.validateDimension(14L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_15:
                if (!paramService.validateDimension(15L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_16:
                if (!paramService.validateDimension(16L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_17:
                if (!paramService.validateDimension(17L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_18:
                if (!paramService.validateDimension(18L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_19:
                if (!paramService.validateDimension(19L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.DIM_DIMENSION_20:
                if (!paramService.validateDimension(20L, controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_COMPANY:
                if (!paramService.validateCompany(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_EMPLOYEE:
                if (!paramService.validateEmployee(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_EMPLOYEE_GROUP:
                if (!paramService.validateEmployeeGroup(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_EMPLOYEE_JOB:
                if (!paramService.validateEmployeeJob(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_EMPLOYEE_LEVEL:
                if (!paramService.validateEmployeeLevel(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_ORG_POSITION:
                if (!paramService.validatePosition(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_ORG_POSITION_GROUP:
                if (!paramService.validatePositionGroup(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_ORG_UNIT:
                if (!paramService.validateUnit(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_ORG_UNIT_GROUP:
                if (!paramService.validateUnitGroup(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            case BgtCheckParam.ORG_ORG_UNIT_LEVEL:
                if (!paramService.validateUnitLevel(controlRuleDetail, reserve, checkParamMap, request)) {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }
}

package com.hand.hec.bgt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.exp.service.IExpEmployeeLevelService;
import com.hand.hap.exp.service.IExpEmployeeService;

import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.exception.BgtCheckParamException;
import com.hand.hec.bgt.service.*;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.service.*;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hec.fnd.service.IFndDimensionService;
import com.hand.hec.fnd.service.IFndDimensionValueService;
import com.hand.hap.gld.service.IGldCurrencyService;

/**
 * description
 *
 * @author mouse 2019/01/09 10:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCheckParamServiceImpl implements IBgtCheckParamService {

    @Autowired
    IBgtEntityService entityService;

    @Autowired
    IBgtCenterService centerService;

    @Autowired
    IBgtBudgetItemService itemService;

    @Autowired
    IBgtBudgetItemTypeService itemTypeService;

    @Autowired
    IBgtPeriodService periodService;

    @Autowired
    IBgtOrganizationService organizationService;

    @Autowired
    IFndDimensionService dimensionService;

    @Autowired
    IFndDimensionValueService dimensionValueService;

    @Autowired
    IExpEmployeeService employeeService;

    @Autowired
    IExpMoEmpGroupService empGroupService;

    @Autowired
    IExpEmployeeJobService employeeJobService;

    @Autowired
    IExpEmployeeLevelService employeeLevelService;

    @Autowired
    IExpOrgPositionService positionService;

    @Autowired
    IExpPositionGroupService positionGroupService;

    @Autowired
    IExpOrgUnitService unitService;

    @Autowired
    IExpMoUnitGroupService unitGroupService;

    @Autowired
    IExpOrgUnitLevelService unitLevelService;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IGldCurrencyService currencyService;

    @Autowired
    IBgtStructureService structureService;

    @Autowired
    IBgtVersionService versionService;

    @Autowired
    IBgtScenarioService scenarioService;

    /**
     * 验证预算币种
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetCurrency(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {

        List<GldCurrency> currencyList = currencyService
                .checkBgtCurrency(detail.getSummaryOrDetail(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (currencyList == null || currencyList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
            for (GldCurrency currency : currencyList) {
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(),
                        currency.getCurrencyId(), currency.getCurrencyCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_CURRENCY, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_CURRENCY, param);

            return true;
        }
    }

    /**
     * 验证预算表
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetStructure(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtStructure> structureList = structureService
                .checkBgtStructure(detail.getSummaryOrDetail(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (structureList == null || structureList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
            for (BgtStructure structure : structureList) {
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(),
                        structure.getStructureId(), structure.getStructureCode());
                // 预算表的参数的attr1用于存放控制期段
                value.setStr1(structure.getPeriodStrategy());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_STRUCTURE, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_STRUCTURE, param);
            return true;
        }
    }

    /**
     * 验证预算版本
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetVersion(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtVersion> versionList = versionService
                .checkBgtVersion(detail.getSummaryOrDetail(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (versionList == null || versionList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
            for (BgtVersion version : versionList) {
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(), version.getVersionId(),
                        version.getVersionCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_VERSION, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_VERSION, param);
            return true;
        }
    }

    /**
     * 验证预算场景
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetScenario(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtScenario> scenarioList = scenarioService
                .checkBgtScenario(detail.getSummaryOrDetail(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (scenarioList == null || scenarioList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
            for (BgtScenario scenario : scenarioList) {
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(),
                        scenario.getScenarioId(), scenario.getScenarioCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_SCENARIO, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_SCENARIO, param);
            return true;
        }
    }

    /**
     * 验证预算实体
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetEntity(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtEntity> entityList = entityService
                .checkBgtEntity(detail.getSummaryOrDetail(), reserve.getBgtOrgId(), detail.getFiltrateMethod(),
                        reserve.getBgtEntityId(), detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (entityList == null || entityList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (BgtEntity entity : entityList) {
                // 如果当前控制规则为汇总控制，且当前为明细类型,则调过当前行的参数新增
                if (BgtCheckParamValue.PARAM_VALUE_TYPE_SUMMARY.equals(detail.getSummaryOrDetail())
                        && BgtEntity.ENTITY_TYPE_DETAILS.equals(entity.getEntityType())) {
                    continue;
                }
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(), entity.getEntityId(),
                        entity.getEntityCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_ENTITY, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_ENTITY, param);
            return true;
        }
    }

    /**
     * 验证预算中心
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetCenter(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtCenter> centerList = centerService
                .checkBgtCenter(detail.getSummaryOrDetail(), reserve.getBgtOrgId(), detail.getFiltrateMethod(),
                        reserve.getBgtCenterId(), detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (centerList == null || centerList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (BgtCenter center : centerList) {
                // 如果当前控制规则为汇总控制，且当前为明细类型,则调过当前行的参数新增
                if (BgtCheckParamValue.PARAM_VALUE_TYPE_SUMMARY.equals(detail.getSummaryOrDetail())
                        && BgtCenter.CENTER_TYPE_DETAILS.equals(center.getCenterType())) {
                    continue;
                }
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(), center.getCenterId(),
                        center.getCenterCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_CENTER, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_CENTER, param);
            return true;
        }
    }

    /**
     * 验证预算项目
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetItem(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtBudgetItem> itemList = itemService
                .checkBgtItem(detail.getSummaryOrDetail(), reserve.getBgtOrgId(), detail.getFiltrateMethod(),
                        reserve.getBudgetItemId(), detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (itemList == null || itemList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (BgtBudgetItem item : itemList) {
                if (BgtCheckParamValue.PARAM_VALUE_TYPE_SUMMARY.equals(detail.getSummaryOrDetail()) && "N"
                        .equals(item.getSummaryFlag())) {
                    continue;
                }
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(), item.getBudgetItemId(),
                        item.getBudgetItemCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_ITEM, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_ITEM, param);
            return true;
        }
    }

    /**
     * 验证预算项目类型
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetItemType(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        BgtBudgetItem item = new BgtBudgetItem();
        item.setBudgetItemId(reserve.getBudgetItemId());
        item = itemService.selectByPrimaryKey(request, item);

        BgtBudgetItemType itemType = new BgtBudgetItemType();
        itemType.setBudgetItemTypeId(item.getBudgetItemTypeId());
        itemType = itemTypeService.selectByPrimaryKey(request, itemType);

        if (itemType == null) {
            throw new BgtCheckParamException("BGT", "bgt_check.budget_item_type_error", null);
        }

        List<BgtBudgetItemType> itemTypeList = itemTypeService
                .checkBgtItemType(detail.getSummaryOrDetail(), reserve.getBgtOrgId(), itemType.getBudgetItemTypeId(),
                        detail.getFiltrateMethod(), detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (itemTypeList == null || itemTypeList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (BgtBudgetItemType bgtItemType : itemTypeList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        bgtItemType.getBudgetItemTypeId(), bgtItemType.getBudgetItemTypeCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_ITEM_TYPE, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_ITEM_TYPE, param);
            return true;
        }
    }

    /**
     * 验证预算期间
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetPeriod(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<BgtPeriod> periodList = periodService
                .checkBgtPeriod(detail.getSummaryOrDetail(), reserve.getBgtOrgId(), detail.getFiltrateMethod(),
                        reserve.getPeriodName(), detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (periodList == null || periodList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (BgtPeriod period : periodList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        period.getPeriodId(), period.getPeriodName());
                value.setLong1(period.getInternalPeriodNum());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_PERIOD, valueList);
            checkParamMap.put(BgtCheckParam.BGT_BUDGET_PERIOD, param);
            return true;
        }
    }

    /**
     * 验证预算季度
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetQuarter(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
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

        //控制规则取值方式
        String filtrateMethod = "INCLUDE";
        if (detail.getParameterLowerLimit() != null && Long.parseLong(detail.getParameterLowerLimit()) > period
                .getQuarterNum()) {
            //根据控制规则取值方式为包含还是排除返回值
            if (detail.getFiltrateMethod().equals(filtrateMethod)) {
                return false;
            } else {
                return true;
            }
        }

        if (detail.getParameterUpperLimit() != null && Long.parseLong(detail.getParameterUpperLimit()) < period
                .getQuarterNum()) {
            //根据控制规则取值方式为包含还是排除返回值
            if (detail.getFiltrateMethod().equals(filtrateMethod)) {
                return false;
            } else {
                return true;
            }
        }
        List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
        BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                period.getQuarterNum(), period.getQuarterNum().toString());
        valueList.add(value);
        BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_QUARTER, valueList);
        checkParamMap.put(BgtCheckParam.BGT_BUDGET_QUARTER, param);
        return true;
    }

    /**
     * 验证预算年度
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateBudgetYear(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
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

        //控制规则取值方式
        String filtrateMethod = "INCLUDE";
        if (detail.getParameterLowerLimit() != null && Long.parseLong(detail.getParameterLowerLimit()) > period
                .getPeriodYear()) {
            //根据控制规则取值方式为包含还是排除返回值
            if (detail.getFiltrateMethod().equals(filtrateMethod)) {
                return false;
            } else {
                return true;
            }
        }

        if (detail.getParameterUpperLimit() != null && Long.parseLong(detail.getParameterUpperLimit()) < period
                .getPeriodYear()) {
            //根据控制规则取值方式为包含还是排除返回值
            if (detail.getFiltrateMethod().equals(filtrateMethod)) {
                return false;
            } else {
                return true;
            }
        }

        List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();
        BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                period.getPeriodYear(), period.getPeriodYear().toString());
        valueList.add(value);
        BgtCheckParam param = new BgtCheckParam(BgtCheckParam.BGT_BUDGET_YEAR, valueList);
        checkParamMap.put(BgtCheckParam.BGT_BUDGET_YEAR, param);
        return true;
    }

    /**
     * 验证维度
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateDimension(Long dimensionSequence, BgtControlRuleDetail detail, BgtBudgetReserve reserve,
            Map checkParamMap, IRequest request) {
        FndDimension dimension = new FndDimension();
        dimension.setDimensionSequence(dimensionSequence);
        dimension.setEnabledFlag("Y");
        List<FndDimension> dimensionList = dimensionService.select(request, dimension, 0, 0);
        if (dimensionList.size() != 1) {
            throw new BgtCheckParamException("BGT", "bgt_check.fnd_dimension_error", null);
        }

        dimension = dimensionList.get(0);

        Long dimensionValueId = Reflect.on(reserve).call("getDimension" + dimensionSequence + "Id").get();

        List<FndDimensionValue> dimValueList = dimensionValueService
                .checkFndDimension(detail.getSummaryOrDetail(), dimension.getDimensionId(), detail.getFiltrateMethod(),
                        dimensionValueId, detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (dimValueList == null || dimValueList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (FndDimensionValue dimValue : dimValueList) {
                if (BgtCheckParamValue.PARAM_VALUE_TYPE_SUMMARY.equals(detail.getSummaryOrDetail()) && "N"
                        .equals(dimValue.getSummaryFlag())) {
                    continue;
                }
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(),
                        dimValue.getDimensionValueId(), dimValue.getDimensionValueCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.DIM_DIMENSION + dimensionSequence, valueList);
            checkParamMap.put(BgtCheckParam.DIM_DIMENSION + dimensionSequence, param);
            return true;
        }
    }

    /**
     * 验证员工
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateEmployee(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpEmployee> employeeList = employeeService
                .checkExpEmployee(detail.getSummaryOrDetail(), reserve.getEmployeeId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());

        if (employeeList == null || employeeList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpEmployee employee : employeeList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        employee.getEmployeeId(), employee.getEmployeeCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_EMPLOYEE, valueList);
            checkParamMap.put(BgtCheckParam.ORG_EMPLOYEE, param);
            return true;
        }
    }

    /**
     * 验证员工组
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateEmployeeGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpMoEmpGroup> empGroupList = empGroupService
                .checkExpMoEmpGroup(detail.getSummaryOrDetail(), reserve.getEmployeeId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (empGroupList == null || empGroupList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpMoEmpGroup employeeGroup : empGroupList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        employeeGroup.getMoEmpGroupId(), employeeGroup.getMoEmpGroupCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_EMPLOYEE_GROUP, valueList);
            checkParamMap.put(BgtCheckParam.ORG_EMPLOYEE_GROUP, param);
            return true;
        }
    }

    /**
     * 验证职务
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateEmployeeJob(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpEmployeeJob> empJobList = employeeJobService
                .checkExpEmpJob(detail.getSummaryOrDetail(), reserve.getEmployeeId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (empJobList == null || empJobList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpEmployeeJob employeeJob : empJobList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        employeeJob.getEmployeeJobId(), employeeJob.getEmployeeJobCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_EMPLOYEE_JOB, valueList);
            checkParamMap.put(BgtCheckParam.ORG_EMPLOYEE_JOB, param);
            return true;
        }
    }

    /**
     * 验证员工级别
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateEmployeeLevel(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpEmployeeLevel> empLevelList = employeeLevelService
                .checkExpEmpLevel(detail.getSummaryOrDetail(), reserve.getEmployeeId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (empLevelList == null || empLevelList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpEmployeeLevel employeeLevel : empLevelList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        employeeLevel.getEmployeeLevelsId(), employeeLevel.getEmployeeLevelsCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_EMPLOYEE_LEVEL, valueList);
            checkParamMap.put(BgtCheckParam.ORG_EMPLOYEE_LEVEL, param);
            return true;
        }
    }

    /**
     * 验证岗位
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validatePosition(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpOrgPosition> positionList = positionService
                .checkExpPosition(detail.getSummaryOrDetail(), reserve.getPositionId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (positionList == null || positionList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpOrgPosition position : positionList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        position.getPositionId(), position.getPositionCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_ORG_POSITION, valueList);
            checkParamMap.put(BgtCheckParam.ORG_ORG_POSITION, param);
            return true;
        }
    }

    /**
     * 验证岗位组
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validatePositionGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpPositionGroup> positionGroupList = positionGroupService
                .checkExpPositionGroup(detail.getSummaryOrDetail(), reserve.getPositionId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (positionGroupList == null || positionGroupList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpPositionGroup positionGroup : positionGroupList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        positionGroup.getPositionGroupId(), positionGroup.getPositionGroupCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_ORG_POSITION_GROUP, valueList);
            checkParamMap.put(BgtCheckParam.ORG_ORG_POSITION_GROUP, param);
            return true;
        }
    }

    /**
     * 验证部门
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateUnit(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpOrgUnit> unitList = unitService
                .checkExpOrgUnit(detail.getSummaryOrDetail(), reserve.getUnitId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (unitList == null || unitList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpOrgUnit unit : unitList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        unit.getUnitId(), unit.getUnitCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_ORG_UNIT, valueList);
            checkParamMap.put(BgtCheckParam.ORG_ORG_UNIT, param);
            return true;
        }
    }

    /**
     * 验证部门组
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateUnitGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpMoUnitGroup> unitGroupList = unitGroupService
                .checkExpOrgUnitGroup(detail.getSummaryOrDetail(), reserve.getUnitId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (unitGroupList == null || unitGroupList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpMoUnitGroup unitGroup : unitGroupList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        unitGroup.getMoUnitGroupId(), unitGroup.getMoUnitGroupCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_ORG_UNIT_GROUP, valueList);
            checkParamMap.put(BgtCheckParam.ORG_ORG_UNIT_GROUP, param);
            return true;
        }
    }

    /**
     * 验证部门级别
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateUnitLevel(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<ExpOrgUnitLevel> unitLevelList = unitLevelService
                .checkExpOrgUnitLevel(detail.getSummaryOrDetail(), reserve.getUnitId(), detail.getFiltrateMethod(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (unitLevelList == null || unitLevelList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (ExpOrgUnitLevel unitLevel : unitLevelList) {
                BgtCheckParamValue value = new BgtCheckParamValue(BgtCheckParamValue.PARAM_VALUE_TYPE_DETAIL,
                        unitLevel.getOrgUnitLevelId(), unitLevel.getOrgUnitLevelCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_ORG_UNIT_LEVEL, valueList);
            checkParamMap.put(BgtCheckParam.ORG_ORG_UNIT_LEVEL, param);
            return true;
        }
    }

    /**
     * 验证公司
     *
     * @param detail 预算控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求
     * @author mouse 2019-01-30 14:46
     * @return java.lang.Boolean
     */
    @Override
    public Boolean validateCompany(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
            IRequest request) {
        List<FndCompany> companyList = companyService
                .checkFndCompany(detail.getFiltrateMethod(), detail.getSummaryOrDetail(), reserve.getCompanyId(),
                        detail.getParameterLowerLimit(), detail.getParameterUpperLimit());
        if (companyList == null || companyList.size() == 0) {
            return false;
        } else {
            List<BgtCheckParamValue> valueList = new ArrayList<BgtCheckParamValue>();

            for (FndCompany company : companyList) {
                BgtCheckParamValue value = new BgtCheckParamValue(detail.getRuleParameterType(), company.getCompanyId(),
                        company.getCompanyCode());
                valueList.add(value);
            }

            BgtCheckParam param = new BgtCheckParam(BgtCheckParam.ORG_COMPANY, valueList);
            checkParamMap.put(BgtCheckParam.ORG_COMPANY, param);

            return true;
        }
    }
}

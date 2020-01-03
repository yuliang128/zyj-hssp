package com.hand.hec.bgt.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hand.hap.core.BaseConstants;
import com.hand.hec.bgt.exception.BgtBalanceQueryNoResult;
import com.hand.hec.bgt.mapper.BgtBudgetReserveInitMapper;
import com.hand.hec.bgt.mapper.BgtJournalBalanceInitMapper;
import com.hand.hec.bgt.service.*;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.mapper.BgtBudgetItemMapper;
import com.hand.hec.bgt.mapper.BgtPeriodMapper;
import com.hand.hec.exp.mapper.ExpEmployeeJobMapper;
import com.hand.hec.exp.mapper.ExpOrgPositionMapper;
import com.hand.hec.exp.mapper.ExpOrgUnitMapper;

/**
 * <p>
 * 预算余额查询serviceImpl
 * </p>
 *
 * @author YHL 2019/03/14 10:55
 */
@Service
public class BgtBalanceQueryServiceImpl implements IBgtBalanceQueryService {

    @Autowired
    private IBgtBalanceQueryParamService paramService;

    @Autowired
    private IBgtOrganizationService organizationService;

    @Autowired
    private IBgtStructureService structureService;

    @Autowired
    private BgtPeriodMapper periodMapper;

    @Autowired
    private BgtBudgetItemMapper itemMapper;

    @Autowired
    private ExpEmployeeMapper employeeMapper;

    @Autowired
    private ExpOrgPositionMapper positionMapper;

    @Autowired
    private ExpEmployeeJobMapper employeeJobMapper;

    @Autowired
    private ExpOrgUnitMapper unitMapper;

    @Autowired
    private IBgtJournalBalanceInitService balanceInitService;

    @Autowired
    private IBgtBudgetReserveInitService reserveInitService;

    @Autowired
    private IBgtBalanceQueryConditionService conditionService;

    @Autowired
    private IBgtBalanceQueryDetailTempService detailTempService;

    @Autowired
    private IBgtBalanceQuerySummaryTempService summaryTempService;

    @Autowired
    private IFndDimensionService dimensionService;

    @Autowired
    private IBgtBalanceQueryResultService resultService;

    @Autowired
    private IBgtPeriodService periodService;

    @Autowired
    private IBgtBalanceQueryIdTempService idTempService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void doQuery(IRequest request, BgtBalanceQueryGroup queryGroup) {
        StringBuffer filterFieldSb = new StringBuffer();
        String filterField = null;

        StringBuffer groupFieldSb = new StringBuffer();
        String groupField = null;
        //
        // 预算余额查询步骤:
        // 1. 根据预算组织、预算表、预算版本、预算场景、预算实体、币种、期段信息生成初始化数据
        // 2. 根据传入的控制参数，循环生成对应的ID数据
        // ----a. 根据初始化数据表，生成所有的ID信息
        // ----b. 根据父子关系，生成所有的父级ID信息
        // ----c. 根据上下限关系，清理掉一部分ID信息
        // ----d. 根据剩余的ID信息，清理掉一部分初始化数据
        // 3. 根据初始化数据插入明细查询字段组合的数据
        // ----a. 插入balance数据到临时表
        // ----b. 插入reserve数据到临时表
        // ----c. 插入sum数据到结果表
        // 4. 根据控制配置，决定结果是否向上汇总到汇总层级或者设置对应的类型数据
        // 5. 如果发生了向上汇总，再次进行group处理
        // 6. 根据传入的控制参数，最终在进行一次group处理
        // 7. 根据预算汇总标志决定是否需要进行预算汇总
        // ------------------------------------------------------------------------------

        //
        // 清除历史临时数据
        // ------------------------------------------------------------------------------
        cleanTemp(request);
        // 清除上次查询结果
        resultService.deleteBalanceResult(request);
        try {
            this.initGroupField(groupFieldSb, queryGroup);
            this.initGroupField(filterFieldSb, queryGroup);
            initTempData(request, queryGroup);
            filterTempData(request, queryGroup, filterFieldSb);
            if (filterFieldSb.length() != 0) {
                filterField = filterFieldSb.toString();
            }
            initDetailResult(request, queryGroup, filterField);
            extendSummary(request, queryGroup, groupFieldSb);
            if (groupFieldSb.length() != 0) {
                groupField = groupFieldSb.toString();
            }
            groupResult(request, queryGroup, groupField);
            // 期间汇总
            if (queryGroup.getPeriodSummary().equals(BaseConstants.YES)) {
                periodSummary(request, queryGroup);
            }
        } catch (BgtBalanceQueryNoResult e) {

        } finally {
            //
            // 清除本次临时数据
            // ------------------------------------------------------------------------------
            cleanTemp(request);
        }
    }

    private void initTempData(IRequest request, BgtBalanceQueryGroup queryGroup) {
        balanceInitService.initBalanceData(request, queryGroup);
        reserveInitService.initReserveData(request, queryGroup);
    }

    private void filterTempData(IRequest request, BgtBalanceQueryGroup queryGroup, StringBuffer filterFieldSb) {

        conditionService.filterPeriod(request, queryGroup);

        if (queryGroup.getBgtConditions() != null) {
            queryGroup.getBgtConditions().forEach(condition -> {
                conditionService.filterCondition(request, queryGroup, condition, filterFieldSb);
            });
        }

        if (queryGroup.getOrgConditions() != null) {
            queryGroup.getOrgConditions().forEach(condition -> {
                conditionService.filterCondition(request, queryGroup, condition, filterFieldSb);
            });
        }

        if (queryGroup.getDimConditions() != null) {
            queryGroup.getDimConditions().forEach(condition -> {
                conditionService.filterCondition(request, queryGroup, condition, filterFieldSb);
            });
        }
    }

    private void initDetailResult(IRequest request, BgtBalanceQueryGroup queryGroup, String filterField) {
        // 获取预算表
        BgtStructure structure = new BgtStructure();
        structure.setStructureId(queryGroup.getStructureId());
        structure = structureService.selectByPrimaryKey(request, structure);

        String periodStrategy = structure.getPeriodStrategy();

        // 生成最明细的数据
        detailTempService.generateBalanceDetail(request, queryGroup, filterField, periodStrategy);
        detailTempService.generateReserveDetail(request, queryGroup, filterField, periodStrategy);

        // 将明细数据按照查询条件汇总
        summaryTempService.generateSummary(request, queryGroup, filterField);
    }

    private void extendSummary(IRequest request, BgtBalanceQueryGroup queryGroup, StringBuffer groupFieldSb) {
        Map<String, BgtBalanceQueryCondition> conditionMap = new HashMap<>();;
        if (queryGroup.getBgtConditions() != null) {
            queryGroup.getBgtConditions().forEach(condition -> {
                conditionMap.put(condition.getParameterCode(), condition);
            });
        }


        if (queryGroup.getOrgConditions() != null) {
            queryGroup.getOrgConditions().forEach(condition -> {
                conditionMap.put(condition.getParameterCode(), condition);
            });
        }


        if (queryGroup.getDimConditions() != null) {
            List<FndDimension> dimensions = dimensionService.selectAll(request);

            queryGroup.getDimConditions().forEach(condition -> {
                dimensions.forEach(dimension -> {
                    if (dimension.getDimensionCode().equals(condition.getParameterCode())) {
                        conditionMap.put("DIMENSION_" + dimension.getDimensionSequence(), condition);
                    }
                });
            });
        }

        conditionService.extendCondition(request, queryGroup, conditionMap, groupFieldSb);
    }

    private void groupResult(IRequest request, BgtBalanceQueryGroup queryGroup, String groupField) {
        resultService.groupResult(request, queryGroup, groupField);
    }


    private void periodSummary(IRequest request, BgtBalanceQueryGroup queryGroup) {
        Long minPeriodNum = resultService.getMinPeriodNum(request);
        Long maxPeriodNum = resultService.getMaxPeriodNum(request);

        BgtPeriod period = new BgtPeriod();

        period.setInternalPeriodNum(minPeriodNum);
        period.setBgtOrgId(queryGroup.getBgtOrgId());
        List<BgtPeriod> minPeriodList = periodService.select(request, period, 0, 0);

        period.setInternalPeriodNum(maxPeriodNum);
        period.setBgtOrgId(queryGroup.getBgtOrgId());
        List<BgtPeriod> maxPeriodList = periodService.select(request, period, 0, 0);

        BgtPeriod minPeriod = minPeriodList.get(0);
        BgtPeriod maxPeriod = maxPeriodList.get(0);

        String periodDesc = minPeriod.getPeriodName() + " ~ " + maxPeriod.getPeriodName();

        resultService.periodSummary(request, queryGroup, periodDesc);
    }

    private void cleanTemp(IRequest request) {
        balanceInitService.clearBalanceData(request);
        reserveInitService.cleanReserveData(request);
        idTempService.cleanIdTemp(request);
        detailTempService.cleanDetailTemp(request);
        summaryTempService.cleanSummaryTemp(request);
    }

    private void initGroupField(StringBuffer buf, BgtBalanceQueryGroup group) {
        if (group.getCompanyId() != null) {
            buf.append("," + "company_id");
        }
        if (group.getBgtEntityId() != null) {
            buf.append("," + "bgt_entity_id");
        }
        if (group.getCurrencyCode() != null) {
            buf.append("," + "currency");
        }
        buf.append("," + "period_year");
        buf.append("," + "period_quarter");
        buf.append("," + "period_name");
    }
}

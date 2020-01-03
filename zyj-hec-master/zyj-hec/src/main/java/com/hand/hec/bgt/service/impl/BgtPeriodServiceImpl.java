package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.exception.BgtPeriodException;
import com.hand.hec.bgt.mapper.BgtPeriodMapper;
import com.hand.hec.bgt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算期间ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtPeriodServiceImpl extends BaseServiceImpl<BgtPeriod> implements IBgtPeriodService {

    @Autowired
    BgtPeriodMapper periodMapper;

    @Autowired
    IBgtPeriodRuleService ruleService;

    @Autowired
    IBgtPeriodSetService setService;

    @Autowired
    IBgtOrganizationService organizationService;

    @Autowired
    IBgtOrgPeriodStatusService orgPeriodStatusService;

    @Autowired
    IBgtEntityPeriodStatusService entityPeriodStatusService;

    @Autowired
    IBgtEntityService entityService;

    @Override
    public List<BgtPeriod> checkBgtPeriod(String controlType, Long bgtOrgId, String filtrateMethod, String periodName,
                    String controlPeriodFrom, String controlPeriodTo) {
        return periodMapper.checkBgtPeriod(controlType, bgtOrgId, filtrateMethod, periodName, controlPeriodFrom,
                        controlPeriodTo);
    }

    @Override
    public int checkPeriodExist(Long periodSetId, Long yearFrom, Long yearTo) {
        return periodMapper.checkPeriodExist(periodSetId, yearFrom, yearTo);
    }

    @Override
    public int checkPeriodUsed(Long periodSetId, Long yearFrom, Long yearTo) {
        int orgUsed = periodMapper.checkOrgPeriodUsed(periodSetId, yearFrom, yearTo);
        int entityUsed = periodMapper.checkEntityPeriodUsed(periodSetId, yearFrom, yearTo);
        return orgUsed == 0 ? entityUsed : orgUsed;
    }

    @Override
    public void batchSubmit(IRequest request, BgtPeriod period) {
        Long periodSetId = period.getPeriodSetId();
        Long yearFrom = period.getYearFrom();
        Long yearTo = period.getYearTo();

        if (yearFrom == null) {
            Date date = new Date();
            yearFrom = Integer.valueOf(date.getYear()).longValue();
        }
        if (yearTo == null) {
            Date date = new Date();
            yearTo = Integer.valueOf(date.getYear()).longValue();
        }
        if (yearFrom > yearTo) {
            throw new BgtPeriodException("BGT", "CREATE_YEAR_ERROR", null);
        }

        BgtPeriodRule rule = new BgtPeriodRule();
        rule.setPeriodSetId(periodSetId);
        Criteria criteria = new Criteria(rule);
        criteria.where(new WhereField(BgtPeriodRule.FIELD_PERIOD_SET_ID, Comparison.EQUAL));
        List<BgtPeriodRule> periodRules = ruleService.selectOptions(request, rule, criteria);

        BgtPeriodSet bgtPeriodSet = new BgtPeriodSet();
        bgtPeriodSet.setPeriodSetId(periodSetId);
        bgtPeriodSet = setService.selectByPrimaryKey(request, bgtPeriodSet);

        Long vPeriodYear = yearFrom;

        while (vPeriodYear <= yearTo) {
            Long vYear = vPeriodYear;
            Date lastEndDate = null;
            for (BgtPeriodRule bgtPeriodRule : periodRules) {

                String periodName = "";
                Calendar calendar = Calendar.getInstance();

                // 设置开始日期，2月分开讨论
                Date startDate = new Date();
                if (bgtPeriodRule.getMonthFrom().intValue() == 2 && bgtPeriodRule.getDateFrom() >= 28) {
                    calendar.set(vYear.intValue(), 2, 1);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    startDate = calendar.getTime();
                } else {
                    calendar.set(vYear.intValue(), bgtPeriodRule.getMonthFrom().intValue() - 1,
                                    bgtPeriodRule.getDateFrom().intValue());
                    startDate = calendar.getTime();
                }

                // 设置结束日期，2月分开讨论
                Date endDate = new Date();
                if (bgtPeriodRule.getMonthTo().intValue() == 2 && bgtPeriodRule.getDateTo() >= 28) {
                    calendar.set(vYear.intValue(), 2, 1);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    endDate = calendar.getTime();
                } else {
                    calendar.set(vYear.intValue(), bgtPeriodRule.getMonthTo().intValue() - 1,
                                    bgtPeriodRule.getDateTo().intValue());
                    endDate = calendar.getTime();
                }

                // 如果结束日期 < 开始日期 ， 则表明是垮年
                if (endDate.getTime() < startDate.getTime()) {
                    calendar.set(Calendar.YEAR, vYear.intValue() + 1);
                    endDate = calendar.getTime();
                    vYear += 1;
                }

                // 添加前缀或后缀
                if (bgtPeriodSet.getPeriodAdditionalFlag().equalsIgnoreCase("P")) {
                    periodName = bgtPeriodRule.getPeriodAdditionalName() + "-" + vYear;
                } else {
                    periodName = vYear + "-" + bgtPeriodRule.getPeriodAdditionalName();
                }

                BgtPeriod bgtPeriod = new BgtPeriod();
                bgtPeriod.setPeriodSetId(periodSetId);
                bgtPeriod.setPeriodYear(vPeriodYear);
                bgtPeriod.setPeriodNum(bgtPeriodRule.getPeriodNum());
                int periodCount = periodMapper.selectCount(bgtPeriod);

                if (periodCount > 0) {
                    bgtPeriod.setStartDate(startDate);
                    bgtPeriod.setEndDate(endDate);
                    bgtPeriod.setQuarterNum(bgtPeriodRule.getQuarterNum());
                    bgtPeriod.setPeriodName(periodName);
                    periodMapper.updateByPeriodSetId(bgtPeriod);
                } else {
                    bgtPeriod.setStartDate(startDate);
                    bgtPeriod.setEndDate(endDate);
                    bgtPeriod.setQuarterNum(bgtPeriodRule.getQuarterNum());
                    bgtPeriod.setPeriodName(periodName);
                    bgtPeriod.setInternalPeriodNum(vPeriodYear * 10000 + bgtPeriodRule.getPeriodNum());
                    self().insertSelective(request, bgtPeriod);
                }

                lastEndDate = endDate;

            }
            vPeriodYear += 1;
        }

    }

    /**
     * <p>
     * 根据预算组织ID+期间获取年度
     * <p/>
     *
     * @param bgtOrgId 预算组织ID
     * @param periodName 期间
     * @return the return
     * @author yang.duan 2019/3/7 19:34
     */
    @Override
    public BgtPeriod getBgtPeriodYear(Long bgtOrgId, String periodName) {
        return periodMapper.getBgtPeriodYear(bgtOrgId, periodName);
    }

    @Override
    public List<BgtPeriod> getBgtPeriodOption(IRequest request, Long bgtOrgId) {
        return periodMapper.getBgtPeriodOption(bgtOrgId);
    }

    @Override
    public List<BgtPeriod> getQuarterOption(IRequest request) {
        return periodMapper.getQuarterOption();
    }

    @Override
    public List<BgtPeriod> getPeriodYearOption(IRequest request, Long bgtOrgId) {
        return periodMapper.getPeriodYearOption(bgtOrgId);
    }

    /**
     * <p>
     * 获取预算期间
     * <p/>
     *
     * @param date
     * @param bgtEntityId
     * @param companyId
     * @param status
     * @return 预算期间
     * @author yang.duan 2019/3/20 20:52
     */
    @Override
    public String getBgtPeriodName(Date date, Long bgtEntityId, Long companyId, String status) {
        return periodMapper.getBgtPeriodName(date, bgtEntityId, companyId, status);
    }

    @Override
    public Boolean checkPeriodOpen(IRequest request, Long bgtOrgId, Long bgtEntityId, String periodName) {
        BgtOrganization org = new BgtOrganization();
        org.setBgtOrgId(bgtOrgId);
        org = organizationService.selectByPrimaryKey(request, org);

        BgtPeriod period = new BgtPeriod();
        period.setPeriodSetId(org.getPeriodSetId());
        period.setPeriodName(periodName);

        List<BgtPeriod> periodList = self().select(request, period, 0, 0);

        if (periodList == null || periodList.size() == 0) {
            throw new BgtPeriodException("BGT", "bgt_period.period_not_exists", null);
        }

        period = periodList.get(0);

        //
        // 判断预算实体级别预算期间的状态
        // ------------------------------------------------------------------------------
        if (bgtEntityId != null) {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            entityPeriodStatus.setBgtEntityId(bgtEntityId);
            entityPeriodStatus.setPeriodName(periodName);
            List<BgtEntityPeriodStatus> entityPeriodStatusList =
                            entityPeriodStatusService.select(request, entityPeriodStatus, 0, 0);
            if (entityPeriodStatusList != null && entityPeriodStatusList.size() != 0) {
                entityPeriodStatus = entityPeriodStatusList.get(0);
                if (BgtEntityPeriodStatus.PERIOD_OPENED_STATUS.equals(entityPeriodStatus.getBgtPeriodStatusCode())) {
                    return true;
                } else if (BgtEntityPeriodStatus.PERIOD_CLOSED_STATUS
                                .equals(entityPeriodStatus.getBgtPeriodStatusCode())) {
                    return false;
                }
            }
        }

        // 预算组织ID为空，从预算实体查询出对应的预算组织
        if (bgtOrgId == null && bgtEntityId != null) {
            BgtEntity entity = new BgtEntity();
            entity.setEntityId(bgtEntityId);
            entity = entityService.selectByPrimaryKey(request, entity);
            bgtOrgId = entity.getBgtOrgId();
        }

        if (bgtOrgId == null) {
            throw new BgtPeriodException("BGT", "bgt_period.bgt_org_id_is_null", null);
        }

        //
        // 判断预算组织级别的预算期间的状态
        // ------------------------------------------------------------------------------
        BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
        orgPeriodStatus.setBgtOrgId(bgtOrgId);
        orgPeriodStatus.setPeriodName(periodName);
        List<BgtOrgPeriodStatus> orgPeriodStatusList = orgPeriodStatusService.select(request, orgPeriodStatus, 0, 0);

        //
        // 如果没有当前预算期间的控制信息，默认是打开状态
        // ------------------------------------------------------------------------------
        if (orgPeriodStatusList == null || orgPeriodStatusList.size() == 0) {
            return true;
        }

        //
        // 判断预算组织级别的期间状态
        // ------------------------------------------------------------------------------
        orgPeriodStatus = orgPeriodStatusList.get(0);
        if (BgtOrgPeriodStatus.PERIOD_OPENED_STATUS.equals(orgPeriodStatus.getBgtPeriodStatusCode())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String queryQuarterPeriodName(BgtPeriod bgtPeriod) {
        return periodMapper.queryQuarterPeriodName(bgtPeriod);
    }

    @Override
    public String queryYearPeriodName(BgtPeriod bgtPeriod) {
        return periodMapper.queryYearPeriodName(bgtPeriod);
    }

    @Override
    public List<String> getPeriodNamesForSummary(IRequest request, BgtPeriod bgtPeriod) {
        return periodMapper.getPeriodNamesForSummary(bgtPeriod);
    }
}

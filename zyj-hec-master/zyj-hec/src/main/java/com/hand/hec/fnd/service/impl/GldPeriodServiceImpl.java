package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.SortType;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.dto.GldPeriodRule;
import com.hand.hec.fnd.exception.GldPeriodException;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.mapper.GldPeriodRuleMapper;
import com.hand.hec.fnd.mapper.GldPeriodSetMapper;
import com.hand.hec.fnd.service.IGldPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 会计期间ServiceImpl
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldPeriodServiceImpl extends BaseServiceImpl<GldPeriod> implements IGldPeriodService {

    @Autowired
    public GldPeriodMapper mapper;

    @Autowired
    public GldPeriodRuleMapper gldPeriodRuleMapper;

    @Autowired
    public GldPeriodSetMapper gldPeriodSetMapper;

    @Override
    public List<GldPeriod> checkPeriodExists(IRequest request, GldPeriod dto) {
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        Long yearFrom = null == dto.getYearFrom() ? year.longValue() : dto.getYearFrom();
        Long yearTo = null == dto.getYearTo() ? year.longValue() : dto.getYearTo();
        int count = mapper.checkPeriodExists(dto.getPeriodSetId(), yearFrom, yearTo, null);
        dto.setCount(count);
        List<GldPeriod> dtos = new ArrayList<>();
        dtos.add(dto);
        return dtos;
    }

    @Override
    public List<GldPeriod> checkPeriodUsed(IRequest request, GldPeriod dto) {
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        Long yearFrom = null == dto.getYearFrom() ? year.longValue() : dto.getYearFrom();
        Long yearTo = null == dto.getYearTo() ? year.longValue() : dto.getYearTo();
        int count = mapper.checkPeriodUsed(dto.getPeriodSetId(), yearFrom, yearTo);
        dto.setCount(count);
        List<GldPeriod> dtos = new ArrayList<>();
        dtos.add(dto);
        return dtos;
    }

    public List<GldPeriod> createDto(List<GldPeriod> dto) throws GldPeriodException {
        ArrayList list = new ArrayList();
        list.add(28);
        list.add(29);
        list.add(30);
        list.add(31);
        GldPeriod oldPeriod = dto.get(0);
        dto.clear();
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Long yearFrom = null == oldPeriod.getYearFrom() ? year.longValue() : oldPeriod.getYearFrom();
        Long yearTo = null == oldPeriod.getYearTo() ? year.longValue() : oldPeriod.getYearTo();
        if (yearFrom.compareTo(yearTo) == 1) {
            throw new GldPeriodException(GldPeriodException.FND2120_CREATE_YEAR_ERROR, null);
        }
        String periodAdditionalFlag = gldPeriodSetMapper.periodAdditionalFlagQuery(oldPeriod.getPeriodSetId());
        GldPeriodRule periodRule = new GldPeriodRule();
        periodRule.setPeriodSetId(oldPeriod.getPeriodSetId());
        Criteria ruleCri = new Criteria(periodRule);
        ruleCri.where(new WhereField(GldPeriodRule.FIELD_PERIOD_SET_ID, Comparison.EQUAL));
        ruleCri.sort(GldPeriodRule.FIELD_PERIOD_NUM, SortType.ASC);
        List<GldPeriodRule> periodRuleList = gldPeriodRuleMapper.selectOptions(periodRule, ruleCri);

        for (Long periodYear = yearFrom; periodYear.compareTo(yearTo) < 1;) {
            Date lastEndDate = null;
            Long ruleYear = periodYear;
            for (GldPeriodRule rule : periodRuleList) {
                GldPeriod period = new GldPeriod();
                cal.set(ruleYear.intValue(), (rule.getMonthFrom().intValue() - 1), rule.getDateFrom().intValue());
                Date startDate = cal.getTime();
                cal.set(ruleYear.intValue(), (rule.getMonthTo().intValue() - 1), rule.getDateTo().intValue());
                Date endDate = cal.getTime();
                // 2月考虑闰年
                if (rule.getMonthFrom().intValue() == 2 && list.contains(rule.getDateFrom().intValue())) {
                    cal.set(ruleYear.intValue(), 2, 1);
                    cal.add(Calendar.DATE, -1);
                    startDate = cal.getTime();
                }
                if (rule.getMonthTo().intValue() == 2 && list.contains(rule.getDateTo().intValue())) {
                    cal.set(ruleYear.intValue(), 2, 1);
                    cal.add(Calendar.DATE, -1);
                    endDate = cal.getTime();
                }

                if (startDate.after(endDate)) {
                    cal.setTime(endDate);
                    cal.add(Calendar.MONTH, 12);
                    endDate = cal.getTime();
                    ruleYear = ruleYear + 1;
                }
                // 调整期间
                if (!rule.getAdjustmentFlag().isEmpty() && rule.getAdjustmentFlag().equals('Y')) {
                    boolean exist = null != lastEndDate
                                    && (!lastEndDate.equals(startDate) || !lastEndDate.equals(endDate));
                    if (exist) {
                        throw new GldPeriodException(GldPeriodException.GLD_CREATE_ADJUSTMENT_PERIOD_ERROR, null);
                    }
                } else {
                    // 非调整期间
                    if (null != lastEndDate) {
                        cal.setTime(lastEndDate);
                        cal.add(Calendar.DATE, 1);
                        if (!startDate.equals(cal.getTime())) {
                            SimpleDateFormat format = new SimpleDateFormat("MM-DD");
                            if (!format.format(startDate).equals(format.format(cal.getTime()))) {
                                throw new GldPeriodException(GldPeriodException.GLD_CREATE_PERIOD_ERROR, null);
                            }
                            cal.setTime(startDate);
                            cal.add(Calendar.MONTH, 12);
                            startDate = cal.getTime();
                            cal.setTime(endDate);
                            cal.add(Calendar.MONTH, 12);
                            endDate = cal.getTime();
                            ruleYear = ruleYear + 1;
                        }
                    }
                }
                String periodName = ruleYear.toString() + "-" + rule.getPeriodAdditionalName();
                if (periodAdditionalFlag.equals("P")) {
                    periodName = rule.getPeriodAdditionalName() + "-" + ruleYear.toString();
                }
                period.setPeriodSetId(oldPeriod.getPeriodSetId());
                period.setPeriodYear(periodYear);
                period.setPeriodNum(rule.getPeriodNum());
                period.setPeriodName(periodName);
                period.setAdjustmentFlag(rule.getAdjustmentFlag());
                period.setInternalPeriodNum(periodYear * 10000 + rule.getPeriodNum());
                period.setStartDate(startDate);
                period.setEndDate(endDate);
                period.setQuarterNum(rule.getQuarterNum());
                lastEndDate = endDate;
                dto.add(period);
            }
            periodYear++;
        }
        return dto;
    }

    @Override
    public List<GldPeriod> createPeriod(IRequest request, List<GldPeriod> dto) throws GldPeriodException {
        dto = this.createDto(dto);
        for (GldPeriod period : dto) {
            int count = mapper.checkPeriodExists(period.getPeriodSetId(), period.getPeriodYear(),
                            period.getPeriodYear(), period.getInternalPeriodNum());
            if (count == 0) {
                self().insertSelective(request, period);
            } else {
                int codeCount = mapper.checkPeriodInStatus(period.getPeriodSetId(), period.getInternalPeriodNum());
                Long periodId = mapper.periodIdQuery(period.getPeriodSetId(), period.getPeriodYear(),
                                period.getPeriodNum());
                if (codeCount == 0 && null != periodId) {
                    period.setPeriodId(periodId);
                    self().updateByPrimaryKeySelective(request, period);
                }
            }
        }
        return dto;
    }

    /**
     * 获取会计期间
     *
     * @Author Tagin
     * @Date 2019-02-20 17:20
     * @param date 日期
     * @param accEntityId 核算主体ID
     * @param status 状态 "O" 或者 为空
     * @return java.lang.String
     * @Version 1.0
     **/
    @Override
    public String getPeriodName(IRequest iRequest, Date date, Long accEntityId, String status) {
        return mapper.getPeriodName(date, accEntityId, status);
    }


	@Override
	public Long queryInternalPeriodNum(IRequest request,String periodName, String periodSetCode) {
		GldPeriod gldPeriodCondition = new GldPeriod();
		gldPeriodCondition.setPeriodName(periodName);
		gldPeriodCondition.setPeriodSetCode(periodSetCode);

		List<GldPeriod> gldPeriodList = mapper.queryGldPeriod(gldPeriodCondition);
		if(gldPeriodList == null || gldPeriodList.isEmpty()){
			return null;
		}


		return gldPeriodList.get(0).getInternalPeriodNum();

	}


	@Override
	public Long queryLastInternalPeriodNum(IRequest request, GldPeriod gldPeriod) {
		return mapper.queryLastInternalPeriodNum(gldPeriod);
	}

	@Override
	public Long queryNextInternalPeriodNum(IRequest request,String periodSetCode, Long internalPeriodNum) {
		return mapper.queryNextInternalPeriodNum(periodSetCode, internalPeriodNum);
	}


	@Override
	public List<GldPeriod> queryGldPeriod(IRequest request, GldPeriod record) {
		return mapper.queryGldPeriod(record);
	}

    @Override
    public GldPeriod queryPeriodByAccEntityAndPeriodName(IRequest iRequest, Long accEntityId, String periodName) {
        return mapper.queryPeriodByAccEntityAndPeriodName(accEntityId,periodName);
    }

}

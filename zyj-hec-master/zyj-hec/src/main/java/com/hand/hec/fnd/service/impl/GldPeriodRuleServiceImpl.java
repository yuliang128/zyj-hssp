package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldPeriodRule;
import com.hand.hec.fnd.exception.GldPeriodRuleException;
import com.hand.hec.fnd.mapper.GldPeriodRuleMapper;
import com.hand.hec.fnd.mapper.GldPeriodSetMapper;
import com.hand.hec.fnd.service.IGldPeriodRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * <p>
 * 会计期间规则ServiceImpl
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldPeriodRuleServiceImpl extends BaseServiceImpl<GldPeriodRule> implements IGldPeriodRuleService {

	@Autowired
	public GldPeriodRuleMapper mapper;

	@Autowired
	public GldPeriodSetMapper periodSetMapper;

	private static Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

	@Override
	public void periodAdditionalNameCheck(List<GldPeriodRule> dto) throws GldPeriodRuleException {
		Pattern pattern = NUMBER_PATTERN;
		for (GldPeriodRule rule : dto) {
			String periodAdditionalName = rule.getPeriodAdditionalName();
			if (pattern.matcher(periodAdditionalName).matches() && periodAdditionalName.length() < 2) {
				periodAdditionalName = "0" + periodAdditionalName;
			}
			Long count = mapper.periodAdditionalNameQuery(rule.getPeriodSetId(), periodAdditionalName);
			if (count > 0) {
				throw new GldPeriodRuleException(GldPeriodRuleException.SYS_DUP_VAL_ON_INDEX, null);
			}
		}

	}

	@Override
	public void periodRuleCheck(List<GldPeriodRule> dto) throws GldPeriodRuleException {

		ArrayList list = new ArrayList();
		list.add(28);
		list.add(29);
		list.add(30);
		list.add(31);

		//上一规则的日期到
		Date lastEndDate = null;
		Date firstDate = null;
		Date finalDate = null;
		Calendar cal = Calendar.getInstance();
		for (GldPeriodRule rule : dto) {
			cal.set(cal.get(Calendar.YEAR), (rule.getMonthFrom().intValue() - 1), rule.getDateFrom().intValue());
			Date startDate = cal.getTime();
			cal.set(cal.get(Calendar.YEAR), (rule.getMonthTo().intValue() - 1), rule.getDateTo().intValue());
			Date endDate = cal.getTime();
			//2月考虑闰年
			if (rule.getMonthFrom().intValue() == 2 && list.contains(rule.getDateFrom().intValue())) {
				cal.set(cal.get(Calendar.YEAR), 2, 1);
				cal.add(Calendar.DATE, -1);
				startDate = cal.getTime();
			}
			if (rule.getMonthTo().intValue() == 2 && list.contains(rule.getDateTo().intValue())) {
				cal.set(cal.get(Calendar.YEAR), 2, 1);
				cal.add(Calendar.DATE, -1);
				endDate = cal.getTime();
			}

			if (startDate.after(endDate)) {
				cal.setTime(endDate);
				cal.add(Calendar.MONTH, 12);
				endDate = cal.getTime();
			}
			//调整期间
			if (!rule.getAdjustmentFlag().isEmpty() && rule.getAdjustmentFlag().equals("Y")) {
				boolean exist = null != lastEndDate && (!lastEndDate.equals(startDate) || !lastEndDate.equals(endDate));
				if (exist) {
					throw new GldPeriodRuleException(GldPeriodRuleException.GLD_CREATE_ADJUSTMENT_PERIOD_ERROR, null);
				}
			} else {
				//非调整期间
				if (null != lastEndDate) {
					cal.setTime(lastEndDate);
					cal.add(Calendar.DATE, 1);
					if (!startDate.equals(cal.getTime())) {
						SimpleDateFormat format = new SimpleDateFormat("MM-DD");
						if (!format.format(startDate).equals(format.format(cal.getTime()))) {
							throw new GldPeriodRuleException(GldPeriodRuleException.GLD_CREATE_PERIOD_ERROR, null);
						}
						cal.setTime(startDate);
						cal.add(Calendar.MONTH, 12);
						startDate = cal.getTime();
						cal.setTime(endDate);
						cal.add(Calendar.MONTH, 12);
						endDate = cal.getTime();
					}
				}
				if (dto.lastIndexOf(rule) == 0) {
					firstDate = startDate;
				}
				finalDate = endDate;
			}
			lastEndDate = endDate;
		}
		Long totalPeriodNum = periodSetMapper.totalPeriodNumQuery(dto.get(0).getPeriodSetId());
		if (totalPeriodNum.intValue() != (dto.size())) {
			throw new GldPeriodRuleException(GldPeriodRuleException.GLD_PERIOD_RULE_CHECK_ERROR, null);
		}
		if (null != firstDate) {
			cal.setTime(firstDate);
			cal.add(Calendar.MONTH, 12);
			cal.add(Calendar.DATE, -1);
			if (!cal.getTime().equals(finalDate)) {
				throw new GldPeriodRuleException(GldPeriodRuleException.GLD_PERIOD_DATE_ERROR, null);
			}
		}
	}
}
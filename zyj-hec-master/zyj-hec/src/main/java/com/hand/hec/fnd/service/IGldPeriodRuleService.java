package com.hand.hec.fnd.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldPeriodRule;
import com.hand.hec.fnd.exception.GldPeriodRuleException;

import java.util.List;


/**
 * <p>
 * 会计期间规则Service接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:46
 */
public interface IGldPeriodRuleService extends IBaseService<GldPeriodRule>, ProxySelf<IGldPeriodRuleService> {

	/**
	 * 检查期间名称附加
	 *
	 * @param dto
	 * @author LJK 2019-01-18 14:42
	 * @return void
	 * @throws GldPeriodRuleException exception
	 *
	 */
	void periodAdditionalNameCheck(List<GldPeriodRule> dto) throws GldPeriodRuleException;

	/**
	 * 会计期间规则检查
	 *
	 * @param dto 会计期间规则实体
	 * @author LJK 2019-01-24 14:40
	 * @throws GldPeriodRuleException exception
	 * @return
	 */
	void periodRuleCheck(List<GldPeriodRule> dto) throws GldPeriodRuleException;
}
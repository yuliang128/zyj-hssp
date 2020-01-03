package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtPeriodRule;
import com.hand.hec.bgt.service.IBgtPeriodRuleService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算期间规则ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtPeriodRuleServiceImpl extends BaseServiceImpl<BgtPeriodRule> implements IBgtPeriodRuleService{

}
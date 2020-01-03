package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentRule;
import com.hand.hec.csh.service.ICshPaymentRuleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRuleServiceImpl extends BaseServiceImpl<CshPaymentRule> implements ICshPaymentRuleService{

}
package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflBusinessRule;
import com.hand.hec.wfl.service.IWflBusinessRuleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflBusinessRuleServiceImpl extends BaseServiceImpl<WflBusinessRule> implements IWflBusinessRuleService{

}
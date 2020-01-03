package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndBusinessRuleServiceImpl extends BaseServiceImpl<FndBusinessRule> implements IFndBusinessRuleService {

}
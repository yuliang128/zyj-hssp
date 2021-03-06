package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleRefJe;
import com.hand.hec.acc.service.IAccRuleRefJeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleRefJeServiceImpl extends BaseServiceImpl<AccRuleRefJe> implements IAccRuleRefJeService{

}
package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleRefJeBizAsgn;
import com.hand.hec.acc.service.IAccRuleRefJeBizAsgnService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleRefJeBizAsgnServiceImpl extends BaseServiceImpl<AccRuleRefJeBizAsgn> implements IAccRuleRefJeBizAsgnService{

}
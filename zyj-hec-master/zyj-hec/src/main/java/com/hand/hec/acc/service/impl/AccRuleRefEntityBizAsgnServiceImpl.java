package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleRefEntityBizAsgn;
import com.hand.hec.acc.service.IAccRuleRefEntityBizAsgnService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleRefEntityBizAsgnServiceImpl extends BaseServiceImpl<AccRuleRefEntityBizAsgn> implements IAccRuleRefEntityBizAsgnService{

}
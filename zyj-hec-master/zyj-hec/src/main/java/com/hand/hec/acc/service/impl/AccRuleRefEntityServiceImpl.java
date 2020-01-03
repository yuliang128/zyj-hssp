package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleRefEntity;
import com.hand.hec.acc.service.IAccRuleRefEntityService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleRefEntityServiceImpl extends BaseServiceImpl<AccRuleRefEntity> implements IAccRuleRefEntityService{

}
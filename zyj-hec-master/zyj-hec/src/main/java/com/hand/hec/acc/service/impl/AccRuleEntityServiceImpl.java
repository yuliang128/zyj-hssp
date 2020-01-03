package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleEntity;
import com.hand.hec.acc.service.IAccRuleEntityService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleEntityServiceImpl extends BaseServiceImpl<AccRuleEntity> implements IAccRuleEntityService{

}
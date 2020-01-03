package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleField;
import com.hand.hec.acc.service.IAccRuleFieldService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleFieldServiceImpl extends BaseServiceImpl<AccRuleField> implements IAccRuleFieldService{

}
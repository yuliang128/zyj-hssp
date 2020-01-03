package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleJeField;
import com.hand.hec.acc.service.IAccRuleJeFieldService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleJeFieldServiceImpl extends BaseServiceImpl<AccRuleJeField> implements IAccRuleJeFieldService{

}
package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldSobRule;
import com.hand.hec.gld.service.IGldSobRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRuleServiceImpl extends BaseServiceImpl<GldSobRule> implements IGldSobRuleService {

}
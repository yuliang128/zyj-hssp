package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSobRuleGroup;
import com.hand.hec.gld.service.IGldSobRuleGroupService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRuleGroupServiceImpl extends BaseServiceImpl<GldSobRuleGroup> implements IGldSobRuleGroupService{

}
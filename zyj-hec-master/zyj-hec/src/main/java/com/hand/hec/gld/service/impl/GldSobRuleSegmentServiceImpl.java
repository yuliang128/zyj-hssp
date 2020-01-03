package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSobRuleSegment;
import com.hand.hec.gld.service.IGldSobRuleSegmentService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRuleSegmentServiceImpl extends BaseServiceImpl<GldSobRuleSegment> implements IGldSobRuleSegmentService{

}
package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndBusinessRuleDetail;
import com.hand.hec.fnd.service.IFndBusinessRuleDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndBusinessRuleDetailServiceImpl extends BaseServiceImpl<FndBusinessRuleDetail> implements IFndBusinessRuleDetailService {
}
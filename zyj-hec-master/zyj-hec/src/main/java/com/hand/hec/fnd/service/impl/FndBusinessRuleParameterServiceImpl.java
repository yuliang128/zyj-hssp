package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndBusinessRuleParameter;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.service.IFndBusinessRuleParameterService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndBusinessRuleParameterServiceImpl extends BaseServiceImpl<FndBusinessRuleParameter> implements IFndBusinessRuleParameterService {

}
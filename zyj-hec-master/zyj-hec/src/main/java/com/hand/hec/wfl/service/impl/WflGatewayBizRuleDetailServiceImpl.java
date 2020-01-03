package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflGatewayBizRuleDetail;
import com.hand.hec.wfl.service.IWflGatewayBizRuleDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflGatewayBizRuleDetailServiceImpl extends BaseServiceImpl<WflGatewayBizRuleDetail> implements IWflGatewayBizRuleDetailService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
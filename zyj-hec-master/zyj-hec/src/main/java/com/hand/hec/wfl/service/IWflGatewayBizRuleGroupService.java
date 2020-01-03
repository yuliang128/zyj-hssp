package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflGatewayBizRuleGroup;

public interface IWflGatewayBizRuleGroupService extends IBaseService<WflGatewayBizRuleGroup>, ProxySelf<IWflGatewayBizRuleGroupService>{

    void setRequest(IRequest request);

    IRequest getRequest();
}
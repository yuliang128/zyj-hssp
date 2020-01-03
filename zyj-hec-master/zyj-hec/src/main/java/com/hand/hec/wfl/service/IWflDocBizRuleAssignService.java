package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflDocBizRuleAssign;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IWflDocBizRuleAssignService extends IBaseService<WflDocBizRuleAssign>, ProxySelf<IWflDocBizRuleAssignService>{
    List<WflDocBizRuleAssign> queryWflDocBizRuleAssign(IRequest context, WflDocBizRuleAssign wflDocBizRuleAssign);
}
package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflDeliverBizRuleAssign;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IWflDeliverBizRuleAssignService extends IBaseService<WflDeliverBizRuleAssign>, ProxySelf<IWflDeliverBizRuleAssignService>{

    List<WflDeliverBizRuleAssign> queryWflDeliverBizRuleAssign(CompositeMap context, WflDeliverBizRuleAssign wflDeliverBizRuleAssign);
}
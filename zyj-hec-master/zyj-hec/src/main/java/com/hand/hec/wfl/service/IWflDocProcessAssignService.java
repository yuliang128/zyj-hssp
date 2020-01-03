package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflDocProcessAssign;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IWflDocProcessAssignService extends IBaseService<WflDocProcessAssign>, ProxySelf<IWflDocProcessAssignService>{
    List<WflDocProcessAssign> queryWflDocProcessAssign(IRequest context, WflDocProcessAssign wflDocProcessAssign);
}
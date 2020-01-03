package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflEvent;
import com.hand.hec.wfl.dto.WflFlow;

import java.util.List;

public interface IWflFlowService extends IBaseService<WflFlow>, ProxySelf<IWflFlowService>{
    public List<WflFlow> processFlowQuery(Long processId, IRequest request);
}
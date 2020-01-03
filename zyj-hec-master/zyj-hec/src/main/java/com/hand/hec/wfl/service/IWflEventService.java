package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflEvent;

import java.util.List;

public interface IWflEventService extends IBaseService<WflEvent>, ProxySelf<IWflEventService>{
    public List<WflEvent> processEventQuery(Long processId, IRequest request);
}
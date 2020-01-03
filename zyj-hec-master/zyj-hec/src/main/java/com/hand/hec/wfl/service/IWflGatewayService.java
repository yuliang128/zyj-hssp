package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflEvent;
import com.hand.hec.wfl.dto.WflGateway;

import java.util.List;

public interface IWflGatewayService extends IBaseService<WflGateway>, ProxySelf<IWflGatewayService> {

    public List<WflGateway> processGatewayQuery(Long processId, IRequest request);

    void setRequest(IRequest request);

    IRequest getRequest();
}

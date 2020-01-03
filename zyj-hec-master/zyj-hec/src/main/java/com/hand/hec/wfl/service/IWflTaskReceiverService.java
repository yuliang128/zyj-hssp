package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflTaskReceiver;

import java.util.List;

public interface IWflTaskReceiverService extends IBaseService<WflTaskReceiver>, ProxySelf<IWflTaskReceiverService> {

    void setRequest(IRequest request);

    IRequest getRequest();
}

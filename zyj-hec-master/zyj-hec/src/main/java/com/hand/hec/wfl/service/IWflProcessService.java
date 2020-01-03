package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.exception.WflProcessGenerateException;
import uncertain.composite.CompositeMap;

import java.util.List;
import java.util.Map;

public interface IWflProcessService extends IBaseService<WflProcess>, ProxySelf<IWflProcessService> {
    List<Map> queryAllElement(IRequest context, Long processId);

    List<WflProcess> queryWflProcess(IRequest context, WflProcess wflProcess);

    List<WflProcess> queryProcessNameLov(IRequest context, WflProcess wflProcess);

    List<WflProcess> queryProcessCodeAndName(IRequest context, Long processId);

    void generateProcess(IRequest context, WflProcess wflProcess, String forceGenerate)
                    throws WflProcessGenerateException;

    void changeConfigType(IRequest context, Long processId, String configType);

    void publishProcess(IRequest context, WflProcess wflProcess);

    void setRequest(IRequest request);

    IRequest getRequest();
}

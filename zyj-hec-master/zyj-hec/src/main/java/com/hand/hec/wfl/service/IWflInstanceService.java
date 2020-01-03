package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInstance;

import java.util.List;
import java.util.Map;

public interface IWflInstanceService extends IBaseService<WflInstance>, ProxySelf<IWflInstanceService>{
    List<Map> getArrivedElement(Long instanceId);

    List<Map> getAllElement(Long instanceId);
}
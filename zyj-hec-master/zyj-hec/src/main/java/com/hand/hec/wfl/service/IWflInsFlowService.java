package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsFlow;

import java.util.List;

public interface IWflInsFlowService extends IBaseService<WflInsFlow>, ProxySelf<IWflInsFlowService>{
    default List<WflInsFlow> getNextFlow(String fromElementType, Long fromElementId) {
        return null;
    }

    List<WflInsFlow> getPreFlow(String toElementType, Long toElementId);
}
package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import uncertain.composite.CompositeMap;

public interface IWflInsTaskReceiverService extends IBaseService<WflInsTaskReceiver>, ProxySelf<IWflInsTaskReceiverService>{
    void backupReceiver(IRequest context, WflInsTaskReceiver receiver);
}
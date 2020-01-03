package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;
import uncertain.composite.CompositeMap;

public interface IWflInsTaskRecipientService extends IBaseService<WflInsTaskRecipient>, ProxySelf<IWflInsTaskRecipientService>{
    void backupRecipient(IRequest context, WflInsTaskRecipient recipient);

    boolean isRepeatedApprove(IRequest context, WflInsTaskRecipient recipient);
}
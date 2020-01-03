package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.WflInsEvent;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVersionProcess;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/29 \* Time: 16:45 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflEventEngineService extends ProxySelf<IWflEventEngineService> {


    WflInsEvent arriveEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process);

    WflInsEvent passEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process);

    WflInsEvent startProcess(IRequest context, WflInstance instance, WflVersionProcess process);

    WflInsEvent stopProcess(IRequest context, WflInstance instance, WflInsEvent insEvent, WflVersionProcess process);

    void interruptEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process);
}

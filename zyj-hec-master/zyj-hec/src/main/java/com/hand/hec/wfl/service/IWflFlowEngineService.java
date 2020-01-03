package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.WflInsFlow;
import com.hand.hec.wfl.dto.WflVersionProcess;
import uncertain.composite.CompositeMap;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/4/19 \* Time: 11:37 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflFlowEngineService extends ProxySelf<IWflFlowEngineService> {

    WflInsFlow arriveFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process);

    WflInsFlow passFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process);

    WflInsFlow unreachFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process);

    void interruptFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process);
}

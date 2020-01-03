package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.WflInsGateway;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVersionProcess;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/29 \* Time: 16:46 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflGatewayEngineService extends ProxySelf<IWflGatewayEngineService> {

    WflInsGateway getSourceInsGateway(IRequest context, WflInstance instance, WflInsGateway targetInsGateway,
                    WflVersionProcess process);

    WflInsGateway arriveGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process);

    WflInsGateway passGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process);

    void nextProcess(IRequest context, WflInstance instance, WflInsGateway insGateway, WflVersionProcess process);

    void interruptGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process);

    void initElementMap(IRequest context, WflInstance instance, WflVersionProcess process);

    WflInsGateway getParallelGatewayInPath(IRequest context, WflInstance instance, Map element,
                    WflVersionProcess process);
}

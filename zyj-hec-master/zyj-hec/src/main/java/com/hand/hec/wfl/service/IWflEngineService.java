package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.*;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/19 \* Time: 10:04 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflEngineService extends ProxySelf<IWflEngineService> {

    WflInstance initInstance(IRequest context, String docCategory, String docId, Long processId);

    WflInstance startupInstance(IRequest context, String docCategory, String docId);

    void startupInstance(IRequest context, WflInstance instance, WflVersionProcess process);

    void shutdownInstance(IRequest context, WflInstance instance, WflVersionProcess process);

    void interruptInstance(IRequest context, WflInstance instance, WflVersionProcess process);

    void nextProcess(IRequest context, WflInstance instance, WflInsFlow insFlow, WflVersionProcess process);

    void generateInsTaskReceiver(IRequest context, WflInstance instance, WflVersionProcess process, WflInsTask insTask);

    void generateReceiver(IRequest context, WflInstance instance, WflVersionProcess process,
                    List<WflInsTask> insTaskList);

    WflInstance createInstance(IRequest context, WflVersionProcess process, String docCategory, String docId);

}

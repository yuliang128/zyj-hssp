package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.*;
import uncertain.composite.CompositeMap;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/29 \* Time: 16:36 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflTaskEngineService extends ProxySelf<IWflTaskEngineService> {

    WflInsTask arriveTask(IRequest context, WflInsTask insTask, WflVersionProcess process);

    WflInsTask passTask(IRequest context, WflInsTask insTask, WflVersionProcess process);

    void generateHierarchy(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void generateRecipient(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void enterTask(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void nextProcess(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    String getTaskResult(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void backupTaskReceiver(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void approve(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, WflTaskAction action,
                    String note);

    void deliver(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, Long targetUserId, String note);

    void addApprover(IRequest context, WflInstance instance, WflInsTaskRecipient recipient,
                    List<WflAddApprover> approverList, String note);

    void autoApprove(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, String note,
                    WflVersionProcess process);

    void sendBack(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process);

    void sendTo(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionTask targetTask,
                    WflVersionProcess process);

    void addTask(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionTask targetTask,
                    WflVersionProcess process);

    void interruptTask(IRequest context, WflInsTask insTask, WflVersionProcess process);

}

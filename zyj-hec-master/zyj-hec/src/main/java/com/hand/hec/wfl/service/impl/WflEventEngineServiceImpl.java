package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/4/18 \* Time: 17:03 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflEventEngineServiceImpl implements IWflEventEngineService {

    @Autowired
    IWflEventService wflEventService;

    @Autowired
    IWflInsEventService wflInsEventService;

    @Autowired
    IWflInstanceService wflInstanceService;

    @Autowired
    IWflEngineService wflEngineService;

    @Override
    public WflInsEvent arriveEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process) {
        insEvent.setArrivalStatus(WflInsEvent.ARRIVAL_STATUS_ARRIVED);
        insEvent.setArrivalDate(new Date());
        insEvent = wflInsEventService.updateByPrimaryKey(context, insEvent);
        return insEvent;
    }

    @Override
    public WflInsEvent passEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process) {
        insEvent.setArrivalStatus(WflInsEvent.ARRIVAL_STATUS_PASSED);
        insEvent = wflInsEventService.updateByPrimaryKey(context, insEvent);
        return insEvent;
    }

    @Override
    public WflInsEvent startProcess(IRequest context, WflInstance instance, WflVersionProcess process) {
        // 开始流程
        // 1、找到开始事件
        // 2、更新开始事件的抵达状态和抵达时间
        // 3、

        int startEventCount = 0;
        WflVersionEvent startEvent = null;

        if (process.getEventMap() != null) {
            for (WflVersionEvent event : process.getEventMap().values()) {
                if (WflEvent.EVENT_TYPE_START.equals(event.getEventType())) {
                    startEvent = event;
                    startEventCount++;
                }
            }
        }
        if (startEventCount == 0) {
            throw new RuntimeException("没有找到开始事件，请联系管理员!ProcessId:" + instance.getProcessId() + ",InstanceId:"
                            + instance.getInstanceId());
        } else if (startEventCount > 1) {
            throw new RuntimeException("当前工作流程多于一个开始事件，请联系管理员!ProcessId:" + instance.getProcessId() + ",InstanceId:"
                            + instance.getInstanceId());
        }

        WflInsEvent insEvent = new WflInsEvent();
        insEvent.setInstanceId(instance.getInstanceId());
        insEvent.setEventId(startEvent.getEventId());

        List<WflInsEvent> insEventList = wflInsEventService.selectOptions(context, insEvent, new Criteria(insEvent));

        if (insEventList.size() == 0) {
            throw new RuntimeException("没有找到开始事件的实例，请联系管理员!ProcessId:" + instance.getProcessId() + ",InstanceId:"
                            + instance.getInstanceId());
        }

        if (insEventList.size() > 1) {
            throw new RuntimeException("当前工作流程多于一个开始事件的实例，请联系管理员!ProcessId:" + instance.getProcessId() + ",InstanceId:"
                            + instance.getInstanceId());
        }

        insEvent = insEventList.get(0);
        insEvent.setArrivalStatus(WflInsEvent.ARRIVAL_STATUS_ARRIVED);
        insEvent.setArrivalDate(new Date());
        insEvent.setArrivalStatus(WflInsEvent.ARRIVAL_STATUS_PASSED);

        wflInsEventService.updateByPrimaryKey(context, insEvent);

        return insEvent;
    }

    @Override
    public WflInsEvent stopProcess(IRequest context, WflInstance instance, WflInsEvent insEvent,
                    WflVersionProcess process) {
        // 结束流程：
        // 1、设置结束事件节点状态ARRIVED
        // 2、设置工作流状态为FINISHED
        // 3、设置结束事件节点为PASSED

        insEvent = self().passEvent(context, insEvent, process);
        wflEngineService.shutdownInstance(context, instance, process);

        return insEvent;
    }

    @Override
    public void interruptEvent(IRequest context, WflInsEvent insEvent, WflVersionProcess process) {

    }
}

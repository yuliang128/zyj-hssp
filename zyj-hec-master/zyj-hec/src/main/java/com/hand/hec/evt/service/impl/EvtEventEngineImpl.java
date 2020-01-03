package com.hand.hec.evt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hec.evt.dto.EvtEvent;
import com.hand.hec.evt.dto.EvtEventHandle;
import com.hand.hec.evt.exception.EvtEventException;
import com.hand.hec.evt.service.IEvtEventEngine;
import com.hand.hec.evt.service.IEvtEventHandleService;
import com.hand.hec.evt.service.IEvtEventHandler;
import com.hand.hec.evt.service.IEvtEventService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 事件引擎
 *
 * @author mouse 2019/04/09 15:20
 */
public class EvtEventEngineImpl implements IEvtEventEngine {

    @Autowired
    IEvtEventService eventService;

    @Autowired
    IEvtEventHandleService handleService;

    @Autowired
    ApplicationContext context;

    @Override
    public void fireEvent(IRequest request, String eventCode, Map param) {
        //
        // 查询出当前事件代码对应的事件列表
        // ------------------------------------------------------------------------------
        EvtEvent event = new EvtEvent();
        event.setEventCode(eventCode);
        List<EvtEvent> eventList = eventService.select(request, event, 0, 0);

        //
        // 如果未找到对应的事件列表，则抛出异常信息
        // ------------------------------------------------------------------------------
        if (eventList == null || eventList.size() == 0) {
            throw new EvtEventException("SYS", "evt_event.event_not_exists", null);
        }

        event = eventList.get(0);


        //
        // 查询出当前事件对应的处理列表
        // ------------------------------------------------------------------------------
        EvtEventHandle queryHandle = new EvtEventHandle();
        queryHandle.setEventId(event.getEventId());
        List<EvtEventHandle> handleList = handleService.select(request, queryHandle, 0, 0);

        if (handleList == null || handleList.size() == 0) {
            return;
        }

        handleList.forEach(handle -> {
            //
            // 根据handle配置的bean名称找到对应的Bean并调用handle方法
            // ------------------------------------------------------------------------------
            Object handlerObj = context.getBean(handle.getBeanName());
            if (handlerObj == null) {
                throw new EvtEventException("SYS", "evt_event.handle_bean_not_exists", null);
            }

            IEvtEventHandler handler = (IEvtEventHandler) handlerObj;
            handler.handle(param);
        });
    }
}

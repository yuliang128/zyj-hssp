package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflInsFlow;
import com.hand.hec.wfl.dto.WflVersionProcess;
import com.hand.hec.wfl.service.IWflFlowEngineService;
import com.hand.hec.wfl.service.IWflInsFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/4/19 \* Time: 11:49 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflFlowEngineServiceImpl implements IWflFlowEngineService {

    @Autowired
    IWflInsFlowService wflInsFlowService;

    @Override
    public WflInsFlow arriveFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process) {
        insFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_ARRIVED);
        insFlow.setArrivalDate(new Date());
        insFlow = wflInsFlowService.updateByPrimaryKey(context, insFlow);
        return insFlow;
    }

    @Override
    public WflInsFlow passFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process) {
        insFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_PASSED);
        insFlow = wflInsFlowService.updateByPrimaryKey(context, insFlow);
        return insFlow;
    }

    @Override
    public WflInsFlow unreachFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process) {
        insFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_UNREACHABLE);
        insFlow = wflInsFlowService.updateByPrimaryKey(context, insFlow);
        return insFlow;
    }

    @Override
    public void interruptFlow(IRequest context, WflInsFlow insFlow, WflVersionProcess process) {

    }
}

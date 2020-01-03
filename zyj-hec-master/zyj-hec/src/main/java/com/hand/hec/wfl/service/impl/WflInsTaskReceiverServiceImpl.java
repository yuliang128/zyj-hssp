package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflInsTaskReceiverHt;
import com.hand.hec.wfl.service.IWflInsTaskReceiverHtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.service.IWflInsTaskReceiverService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskReceiverServiceImpl extends BaseServiceImpl<WflInsTaskReceiver> implements IWflInsTaskReceiverService{
    @Autowired
    IWflInsTaskReceiverHtService wflInsTaskReceiverHtService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public void backupReceiver(IRequest context, WflInsTaskReceiver receiver) {

        WflInsTaskReceiverHt wflInsTaskReceiverHt = new WflInsTaskReceiverHt(receiver);
        wflInsTaskReceiverHtService.insertSelective(context, wflInsTaskReceiverHt);
        deleteByPrimaryKey(receiver);
    }
}
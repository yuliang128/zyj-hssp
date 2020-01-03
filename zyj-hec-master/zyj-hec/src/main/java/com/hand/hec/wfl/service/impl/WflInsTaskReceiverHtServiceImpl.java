package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskReceiverHt;
import com.hand.hec.wfl.service.IWflInsTaskReceiverHtService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskReceiverHtServiceImpl extends BaseServiceImpl<WflInsTaskReceiverHt> implements IWflInsTaskReceiverHtService{

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
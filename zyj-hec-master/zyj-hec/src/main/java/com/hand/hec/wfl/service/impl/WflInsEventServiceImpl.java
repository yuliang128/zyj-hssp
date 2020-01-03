package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflInsEvent;
import com.hand.hec.wfl.service.IWflInsEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsEventServiceImpl extends BaseServiceImpl<WflInsEvent> implements IWflInsEventService{

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflTaskAction;
import com.hand.hec.wfl.service.IWflTaskActionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflTaskActionServiceImpl extends BaseServiceImpl<WflTaskAction> implements IWflTaskActionService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
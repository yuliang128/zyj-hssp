package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflProcessAction;
import com.hand.hec.wfl.service.IWflProcessActionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflProcessActionServiceImpl extends BaseServiceImpl<WflProcessAction> implements IWflProcessActionService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
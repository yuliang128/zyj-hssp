package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskRecipientHt;
import com.hand.hec.wfl.service.IWflInsTaskRecipientHtService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskRecipientHtServiceImpl extends BaseServiceImpl<WflInsTaskRecipientHt> implements IWflInsTaskRecipientHtService{

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
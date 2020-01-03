package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflInsTaskRecipientHt;
import com.hand.hec.wfl.mapper.WflInsTaskRecipientMapper;
import com.hand.hec.wfl.service.IWflInsTaskRecipientHtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;
import com.hand.hec.wfl.service.IWflInsTaskRecipientService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskRecipientServiceImpl extends BaseServiceImpl<WflInsTaskRecipient> implements IWflInsTaskRecipientService{
    @Autowired
    IWflInsTaskRecipientHtService wflInsTaskRecipientHtService;

    @Autowired
    WflInsTaskRecipientMapper wflInsTaskRecipientMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public void backupRecipient(IRequest context, WflInsTaskRecipient recipient) {
        WflInsTaskRecipientHt insTaskRecipientHt = new WflInsTaskRecipientHt(recipient);
        wflInsTaskRecipientHtService.insertSelective(context, insTaskRecipientHt);
        deleteByPrimaryKey(recipient);
    }

    @Override
    public boolean isRepeatedApprove(IRequest context, WflInsTaskRecipient recipient) {
        Long approveCount = wflInsTaskRecipientMapper.getRecipientApproveCount(recipient);
        if (approveCount > 0) {
            return true;
        } else {
            return false;
        }
    }
}
package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscWorkflowActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkflowAction;
import com.hand.hec.ssc.service.ISscWorkflowActionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowActionServiceImpl extends BaseServiceImpl<SscWorkflowAction> implements ISscWorkflowActionService{
    @Autowired
    private SscWorkflowActionMapper sscWorkflowActionMapper;

    @Override
    public Long getActionId(Long dispatchRecordId, String actionType) {
        return sscWorkflowActionMapper.getActionId(dispatchRecordId,actionType);
    }

    @Override
    public List<SscWorkflowAction> getActionByDispatchRecordId(IRequest iRequest,Long dispatchRecordId) {
        return sscWorkflowActionMapper.getActionByDispatchRecordId(dispatchRecordId);
    }
}
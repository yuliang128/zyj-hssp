package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkflowNodeAction;
import com.hand.hec.ssc.service.ISscWorkflowNodeActionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowNodeActionServiceImpl extends BaseServiceImpl<SscWorkflowNodeAction> implements ISscWorkflowNodeActionService{

}
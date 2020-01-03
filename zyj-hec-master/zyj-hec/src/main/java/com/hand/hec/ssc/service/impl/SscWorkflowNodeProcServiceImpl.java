package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkflowNodeProc;
import com.hand.hec.ssc.service.ISscWorkflowNodeProcService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowNodeProcServiceImpl extends BaseServiceImpl<SscWorkflowNodeProc> implements ISscWorkflowNodeProcService{

}
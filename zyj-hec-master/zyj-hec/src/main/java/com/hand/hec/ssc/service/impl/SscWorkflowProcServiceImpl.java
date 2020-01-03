package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkflowProc;
import com.hand.hec.ssc.service.ISscWorkflowProcService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowProcServiceImpl extends BaseServiceImpl<SscWorkflowProc> implements ISscWorkflowProcService{

}
package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscWorkflow;
import com.hand.hec.ssc.dto.SscWorkflowNode;
import com.hand.hec.ssc.mapper.SscWorkflowMapper;
import com.hand.hec.ssc.service.ISscWorkflowActionService;
import com.hand.hec.ssc.service.ISscWorkflowNodeService;
import com.hand.hec.ssc.service.ISscWorkflowProcService;
import com.hand.hec.ssc.service.ISscWorkflowService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowServiceImpl extends BaseServiceImpl<SscWorkflow> implements ISscWorkflowService {

    @Autowired
    SscWorkflowMapper sscWorkflowMapper;

    @Autowired
    ISscWorkflowNodeService sscWorkflowNodeService;

    @Autowired
    ISscWorkflowActionService sscWorkflowActionService;

    @Autowired
    ISscWorkflowProcService sscWorkflowProcService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSubmit(List<SscWorkflow> sscWorkflowList, IRequest request) {
        if(sscWorkflowList.isEmpty()){
            return true;
        }
        //共享工作流程存储
        this.batchUpdate(request,sscWorkflowList);

        //流程节点
        sscWorkflowNodeService.batchUpdate(request,sscWorkflowList.get(0).getNodes());

        //流程动作
        sscWorkflowActionService.batchUpdate(request,sscWorkflowList.get(0).getActions());

        //流程过程
        sscWorkflowProcService.batchUpdate(request,sscWorkflowList.get(0).getProcs());

        return true;
    }

    @Override
    public List<SscWorkflow> querySscWorkflow(@Param("workflowCode") String workflowCode, @Param("description") String description, @Param("docCategory") String docCategory, IRequest iRequest) {
        return sscWorkflowMapper.querySscWorkflow(workflowCode,description,docCategory);
    }
}
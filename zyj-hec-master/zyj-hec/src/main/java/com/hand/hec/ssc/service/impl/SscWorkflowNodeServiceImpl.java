package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.dto.SscWorkflowNode;
import com.hand.hec.ssc.mapper.SscWorkflowNodeMapper;
import com.hand.hec.ssc.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowNodeServiceImpl extends BaseServiceImpl<SscWorkflowNode> implements ISscWorkflowNodeService{

    @Autowired
    private ISscWorkflowNodeActionService sscWorkflowNodeActionService;

    @Autowired
    private ISscWorkflowNodeProcService sscWorkflowNodeProcService;

    @Autowired
    private ISscNodeAsgnWorkCenterService sscNodeAsgnWorkCenterService;

    @Autowired
    private SscWorkflowNodeMapper sscWorkflowNodemapper;

    @Autowired
    private ISscWorkflowNodeRuleService sscWorkflowNodeRuleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSubmit(List<SscWorkflowNode> sscWorkflowNodeList, IRequest request) {

        //共享流程节点存储
        this.batchUpdate(request,sscWorkflowNodeList);

        //节点动作
        sscWorkflowNodeActionService.batchUpdate(request,sscWorkflowNodeList.get(0).getNodeActions());

        //节点过程
        sscWorkflowNodeProcService.batchUpdate(request,sscWorkflowNodeList.get(0).getNodeProcs());

        //节点分配工作中心
        sscNodeAsgnWorkCenterService.batchUpdate(request,sscWorkflowNodeList.get(0).getCenters());

        return true;
    }

    @Override
    public List<SscWorkflowNode> querySscWorkflowNode(@Param("workflowId") Long workflowId, @Param("nodeId") Long nodeId, IRequest request) {
        return sscWorkflowNodemapper.querySscWorkflowNode(workflowId,nodeId);
    }

    @Override
    public Long getNextNodeId(IRequest iRequest, Long workflowId, SscTaskPool sscTaskPool, Long currentNodeSequence){
        List<SscWorkflowNode> sscWorkflowNodes = new ArrayList<>();
        sscWorkflowNodes = sscWorkflowNodemapper.getAllNextNode(workflowId,currentNodeSequence);
        if(!sscWorkflowNodes.isEmpty() && sscWorkflowNodes != null){
            for(SscWorkflowNode sscWorkflowNode : sscWorkflowNodes){
               boolean flag = sscWorkflowNodeRuleService.validateBusinessRule(iRequest,sscTaskPool,sscWorkflowNode.getNodeId());
               if(flag){
                   return sscWorkflowNode.getNodeId();
               } else {
                   return 0L;
               }
            }
        }
        return 0L;
    }

    @Override
    public Long getPreNodeId(IRequest iRequest, Long workflowId, SscTaskPool sscTaskPool, Long currentNodeSequence) {
        List<SscWorkflowNode> sscWorkflowNodes = new ArrayList<>();
        sscWorkflowNodes = sscWorkflowNodemapper.getAllPreNode(workflowId,currentNodeSequence);
        if(!sscWorkflowNodes.isEmpty() && sscWorkflowNodes != null){
            for(SscWorkflowNode sscWorkflowNode : sscWorkflowNodes){
                boolean flag = sscWorkflowNodeRuleService.validateBusinessRule(iRequest,sscTaskPool,sscWorkflowNode.getNodeId());
                if(flag){
                    return sscWorkflowNode.getNodeId();
                } else {
                    return 0L;
                }
            }
        }
        return 0L;
    }
}
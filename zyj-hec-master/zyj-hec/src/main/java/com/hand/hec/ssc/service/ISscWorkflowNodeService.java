package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.dto.SscWorkflowNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkflowNodeService extends IBaseService<SscWorkflowNode>, ProxySelf<ISscWorkflowNodeService> {


    /**
     * 保存共享节点，节点动作，节点过程，节点工作中心等信息
     *
     * @param sscWorkflowNodeList 共享节点信息
     * @param request 上下文
     * @author ngls.luhui 2019-03-18 14:44
     * @return true/false
     */
    Boolean batchSubmit(List<SscWorkflowNode> sscWorkflowNodeList, IRequest request);

    /**
     * 根据workflowId，nodeId 查询出共享工作流程节点
     *@Param workflowId,nodeId
     * @return SscWorkflowNode
     * @author bo.zhang 2019-03-20 19:24:36
     */
    List<SscWorkflowNode> querySscWorkflowNode(@Param("workflowId") Long workflowId,
                                                      @Param("nodeId") Long nodeId,IRequest request);

    /**
     * 共享任务工作台获取下一节点
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/26 10:06
     * @param iRequest 请求上下文
     * @param workflowId 工作流Id
     * @param sscTaskPool 共享作业池数据
     * @param currentNodeSequence 当前节点序号
     * @return Long
     * @Version 1.0
     **/
    Long getNextNodeId(IRequest iRequest, Long workflowId, SscTaskPool sscTaskPool, Long currentNodeSequence);

    /**
     * 共享任务工作台获取上一节点
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/26 10:06
     * @param iRequest 请求上下文
     * @param workflowId 工作流Id
     * @param sscTaskPool 共享作业池数据
     * @param currentNodeSequence 当前节点序号
     * @return Long
     * @Version 1.0
     **/
    Long getPreNodeId(IRequest iRequest, Long workflowId, SscTaskPool sscTaskPool, Long currentNodeSequence);
}

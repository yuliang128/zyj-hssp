package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkflowService extends IBaseService<SscWorkflow>, ProxySelf<ISscWorkflowService>{
    
    
    /**
     * 用于保存共享工作流程的基本信息,及其流程节点,流程动作,流程过程
     *
     * @param sscWorkflowList 共享工作流程list
     * @author ngls.luhui 2019-03-15 15:59
     * @return true/false
     */
    Boolean batchSubmit(List<SscWorkflow> sscWorkflowList, IRequest request);

    /**
     *根据流程代码，秒速，单据类别查询
     *@Param workflowCode,description,docCategory
     * @return SscWorkflow
     * @author bo.zhang 2019-03-20 19:24:36
     */
    List<SscWorkflow> querySscWorkflow(@Param("workflowCode") String workflowCode,
                                              @Param("description") String description,
                                              @Param("docCategory") String docCategory,
                                              IRequest iRequest);
}
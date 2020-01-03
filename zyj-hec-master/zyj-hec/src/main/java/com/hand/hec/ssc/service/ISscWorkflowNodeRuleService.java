package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.dto.SscWorkflowNodeRule;

public interface ISscWorkflowNodeRuleService
                extends IBaseService<SscWorkflowNodeRule>, ProxySelf<ISscWorkflowNodeRuleService> {

    /**
     * 校验后续节点是否有效
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/26 10:58
     * @param iRequest 请求上下文
     * @param sscTaskPool 共享作业池数据
     * @param nodeId 节点Id
     * @return boolean
     * @Version 1.0
     **/
    boolean validateBusinessRule(IRequest iRequest, SscTaskPool sscTaskPool, Long nodeId);
}

package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkflowAction;

import java.util.List;

public interface ISscWorkflowActionService extends IBaseService<SscWorkflowAction>, ProxySelf<ISscWorkflowActionService>{

    /**
     *根据动作类型和派工记录Id获取ActionId
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 16:52
     *@param dispatchRecordId 派工记录Id
     *@param actionType 动作类型
     *@return
     *@Version 1.0
     **/
    Long getActionId(Long dispatchRecordId, String actionType);

    /**
     *根据派工记录Id获取Action
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 16:52
     *@param iRequest 请求上下文
     *@param dispatchRecordId 派工记录Id
     *@return
     *@Version 1.0
     **/
    List<SscWorkflowAction> getActionByDispatchRecordId(IRequest iRequest,Long dispatchRecordId);
}
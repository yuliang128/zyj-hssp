package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsTask;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;
import com.hand.hec.wfl.dto.WflTaskAction;

import java.util.List;

public interface IWflInsTaskService extends IBaseService<WflInsTask>, ProxySelf<IWflInsTaskService> {
    Long getPeopleAgreeCount(IRequest context, Long insTaskId);

    Long getPeopleRejectCount(IRequest context, Long insTaskId);

    Long getRuleAgreeCount(IRequest context, Long insTaskId);

    Long getRuleRejectCount(IRequest context, Long insTaskId);

    Long getLineAgreeCount(IRequest context, Long insTaskId);

    Long getLineRejectCount(IRequest context, Long insTaskId);

    Long getAllPeopleCount(IRequest context, Long insTaskId);

    Long getAllRuleCount(IRequest context, Long insTaskId);

    List<WflTaskAction> getTaskActionList(IRequest context, Long processId, Long insTaskId);

}

package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;
import com.hand.hec.wfl.dto.WflProcessAction;
import com.hand.hec.wfl.dto.WflTaskAction;
import com.hand.hec.wfl.mapper.WflInsTaskMapper;
import com.hand.hec.wfl.service.IWflProcessActionService;
import com.hand.hec.wfl.service.IWflTaskActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTask;
import com.hand.hec.wfl.service.IWflInsTaskService;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.core.IRequest;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskServiceImpl extends BaseServiceImpl<WflInsTask> implements IWflInsTaskService {

    @Autowired
    WflInsTaskMapper wflInsTaskMapper;

    @Autowired
    IWflProcessActionService processActionService;

    @Autowired
    IWflTaskActionService taskActionService;

    @Autowired
    IWflInsTaskService insTaskService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public Long getPeopleAgreeCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getPeopleAgreeCount(insTaskId);
    }

    @Override
    public Long getPeopleRejectCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getPeopleRejectCount(insTaskId);
    }

    @Override
    public Long getRuleAgreeCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getRuleAgreeCount(insTaskId);
    }

    @Override
    public Long getRuleRejectCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getRuleRejectCount(insTaskId);
    }

    @Override
    public Long getLineAgreeCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getLineAgreeCount(insTaskId);
    }

    @Override
    public Long getLineRejectCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getLineRejectCount(insTaskId);
    }

    @Override
    public Long getAllPeopleCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getAllPeopleCount(insTaskId);
    }

    @Override
    public Long getAllRuleCount(IRequest context, Long insTaskId) {
        return wflInsTaskMapper.getAllRuleCount(insTaskId);
    }

    @Override
    public List<WflTaskAction> getTaskActionList(IRequest context, Long processId, Long insTaskId) {

        WflInsTask insTask = new WflInsTask();
        insTask.setInsTaskId(insTaskId);
        insTask = insTaskService.selectByPrimaryKey(context, insTask);

        WflTaskAction queryTaskAction = new WflTaskAction();
        queryTaskAction.setTaskId(insTask.getTaskId());
        queryTaskAction.setEnabledFlag("Y");
        Criteria criteria = new Criteria(queryTaskAction);

        List<WflTaskAction> tasksActionList = taskActionService.selectOptions(context, queryTaskAction, criteria, 0, 0);


        return tasksActionList;
    }

}

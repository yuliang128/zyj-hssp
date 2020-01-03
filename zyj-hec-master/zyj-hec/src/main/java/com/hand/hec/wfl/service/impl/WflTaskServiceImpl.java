package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.mapper.WflTaskMapper;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.core.IRequest;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflTaskServiceImpl extends BaseServiceImpl<WflTask> implements IWflTaskService {

    ThreadLocal<IRequest> requestLocal = new ThreadLocal<IRequest>();

    @Autowired
    private WflTaskMapper wflTaskMapper;

    @Autowired
    private IWflTaskReceiverService receiverService;

    @Autowired
    private IWflUserTaskService userTaskService;

    @Autowired
    private IWflSubProcessTaskService subProcessTaskService;

    @Autowired
    private IWflTaskActionService actionService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflTask> taskNameLov(Long processId) {
        return wflTaskMapper.taskNameLov(processId);
    }

    @Override
    public List<WflTask> queryWflTask(IRequest context, WflTask wflTask) {
        return wflTaskMapper.queryWflTask(wflTask);
    }

    @Override
    public List<WflTask> queryTaskNameLov(IRequest context, WflTask wflTask) {
        return wflTaskMapper.queryTaskNameLov(wflTask);
    }

    @Override
    public List<WflTask> queryCodeAndNameById(IRequest context, Long taskId) {
        return wflTaskMapper.queryCodeAndNameById(taskId);
    }

    @Override
    public String getTaskRelation(IRequest context, WflTask task, WflTask targetTask) {
        List<WflTask> afterTaskList = wflTaskMapper.queryLinkedTask(task.getTaskId());
        for (WflTask afterTask : afterTaskList) {
            if (targetTask.getTaskId().equals(afterTask.getTaskId())) {
                return WflTask.TASK_RELATION_AFTER;
            }
        }

        List<WflTask> beforeTaskList = wflTaskMapper.queryLinkedTask(targetTask.getTaskId());
        for (WflTask beforeTask : beforeTaskList) {
            if (task.getTaskId().equals(beforeTask.getTaskId())) {
                return WflTask.TASK_RELATION_BEFORE;
            }
        }
        return WflTask.TASK_RELATION_UNREACHABLE;
    }

    @Override
    public List<WflTask> getLinedTask(IRequest context, WflTask task) {
        List<WflTask> afterTaskList = wflTaskMapper.queryLinkedTask(task.getTaskId());
        return afterTaskList;
    }

    @Override
    public List<WflTask> queryByProcessId(Long processId) {
        return wflTaskMapper.queryByProcessId(processId);
    }

    @Override
    public int deleteByPrimaryKey(WflTask task) {
        //
        // 任务删除之前需要删除以下内容：
        // 1. 任务接受者
        // 2. 用户任务
        // 3. 子流程任务
        // 4. 任务动作
        // ------------------------------------------------------------------------------

        //
        // 删除任务接受者
        // ------------------------------------------------------------------------------
        WflTaskReceiver queryReceiver = new WflTaskReceiver();
        queryReceiver.setTaskId(task.getTaskId());
        List<WflTaskReceiver> receiverList = receiverService.select(getRequest(), queryReceiver, 0, 0);
        if (receiverList != null && receiverList.size() != 0) {
            receiverService.setRequest(getRequest());
            receiverService.batchDelete(receiverList);
        }

        //
        // 删除用户任务
        // ------------------------------------------------------------------------------
        WflUserTask queryUserTask = new WflUserTask();
        queryUserTask.setTaskId(task.getTaskId());
        List<WflUserTask> userTaskList = userTaskService.select(getRequest(), queryUserTask, 0, 0);
        if (userTaskList != null && userTaskList.size() != 0) {
            userTaskService.batchDelete(userTaskList);
        }

        //
        // 删除子流程任务
        // ------------------------------------------------------------------------------
        WflSubProcessTask querySubProcessTask = new WflSubProcessTask();
        querySubProcessTask.setTaskId(task.getTaskId());
        List<WflSubProcessTask> subProcessTaskList =
                        subProcessTaskService.select(getRequest(), querySubProcessTask, 0, 0);
        if (subProcessTaskList != null && subProcessTaskList.size() != 0) {
            subProcessTaskService.batchDelete(subProcessTaskList);
        }

        //
        // 删除任务动作
        // ------------------------------------------------------------------------------
        WflTaskAction queryAction = new WflTaskAction();
        queryAction.setTaskId(task.getTaskId());
        List<WflTaskAction> actionList = actionService.select(getRequest(), queryAction, 0, 0);
        if (actionList != null && actionList.size() != 0) {
            actionService.batchDelete(actionList);
        }

        return super.deleteByPrimaryKey(task);
    }

    @Override
    public void setRequest(IRequest request) {
        requestLocal.set(request);
    }

    @Override
    public IRequest getRequest() {
        return requestLocal.get();
    }

}

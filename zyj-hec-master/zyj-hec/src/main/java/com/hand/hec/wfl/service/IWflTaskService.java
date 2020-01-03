package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflTask;

import java.util.List;

public interface IWflTaskService extends IBaseService<WflTask>, ProxySelf<IWflTaskService> {
    public List<WflTask> taskNameLov(Long processId);

    List<WflTask> queryWflTask(IRequest context, WflTask wflTask);

    List<WflTask> queryTaskNameLov(IRequest context, WflTask wflTask);

    List<WflTask> queryCodeAndNameById(IRequest context, Long taskId);

    String getTaskRelation(IRequest context, WflTask task, WflTask targetTask);

    List<WflTask> getLinedTask(IRequest context, WflTask task);

    List<WflTask> queryByProcessId(Long processId);

    void setRequest(IRequest request);

    IRequest getRequest();
}

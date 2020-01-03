package com.hand.hec.wfl.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.wfl.dto.WflInsTask;
import com.hand.hec.wfl.mapper.WflTodoMapper;
import com.hand.hec.wfl.service.IWflInsTaskService;
import com.hand.hec.wfl.service.IWflTaskActionService;
import com.hand.hec.wfl.service.IWflTaskService;
import com.hand.hec.wfl.service.IWflTodoService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/03/08 18:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflTodoServiceImpl implements IWflTodoService {

    @Autowired
    WflTodoMapper todoMapper;

    @Autowired
    IWflInsTaskService taskService;

    @Override
    public List<Map> getTodoList(IRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Map> todoList = todoMapper.getTodoList();
        for (Map todo : todoList) {
            String taskName = "";
            WflInsTask task = new WflInsTask();
            task.setInstanceId(Long.parseLong(todo.get("instance_id").toString()));
            Criteria criteria = new Criteria(task);
            List<WflInsTask> taskList = taskService.selectOptions(request, task, criteria, 0, 0);
            for (WflInsTask insTask : taskList) {
                taskName += insTask.getTaskName();
            }

            todo.put("task_name", taskName);
        }

        return todoList;
    }
}

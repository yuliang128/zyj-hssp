package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.*;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WflTaskController extends BaseController {

    @Autowired
    private IWflTaskService service;

    @Autowired
    private IWflUserTaskService userTaskService;

    @Autowired
    private IWflTaskReceiverService receiverService;

    @Autowired
    private IWflTaskRcvBizDetailService bizDetailService;

    @Autowired
    private IWflTaskActionService actionService;

    @Autowired
    private IWflProcessService processService;

    @RequestMapping(value = "/wfl/task/query")
    @ResponseBody
    public ResponseData query(WflTask dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflTask.FIELD_TASK_ID),
                new WhereField(WflTask.FIELD_PROCESS_ID), new WhereField(WflTask.FIELD_TASK_CODE, Comparison.LIKE),
                new WhereField(WflTask.FIELD_TASK_NAME, Comparison.LIKE), new WhereField(WflTask.FIELD_TASK_TYPE)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/wfl/task/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflTask> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        List<WflTask> tasks = service.batchUpdate(requestCtx, dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData(tasks);
    }


    @RequestMapping(value = "/wfl/task/save-detail")
    @ResponseBody
    public ResponseData saveDetail(@RequestBody List<WflTask> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);

        dto.forEach(task -> {
            // 保存userTask
            task.getUserTask().forEach(userTask -> {
                switch (userTask.get__status()) {
                    case DTOStatus.ADD:
                        userTaskService.insertSelective(requestCtx, userTask);
                        break;
                    case DTOStatus.UPDATE:
                        userTaskService.updateByPrimaryKey(requestCtx, userTask);
                        break;
                    default:
                }
            });

            // 保存接受者
            if (task.getReceivers() != null) {
                task.getReceivers().forEach(receiver -> {
                    switch (receiver.get__status()) {
                        case DTOStatus.ADD:
                            receiverService.insertSelective(requestCtx, receiver);
                            break;
                        case DTOStatus.UPDATE:
                            receiverService.updateByPrimaryKey(requestCtx, receiver);
                            break;
                        default:
                    }

                    // 保存接收者权限规则
                    if (receiver.getReceiverBizRules() != null) {
                        receiver.getReceiverBizRules().forEach(bizRule -> {
                            bizRule.setReceiverId(receiver.getReceiverId());

                            switch (bizRule.get__status()) {
                                case DTOStatus.ADD:
                                    bizDetailService.insertSelective(requestCtx, bizRule);
                                    break;
                                case DTOStatus.UPDATE:
                                    bizDetailService.updateByPrimaryKey(requestCtx, bizRule);
                                    break;
                                default:
                            }
                        });
                    }

                });
            }


            // 保存工作流动作
            if (task.getActions() != null) {
                task.getActions().forEach(action -> {
                    switch (action.get__status()) {
                        case DTOStatus.ADD:
                            actionService.insertSelective(requestCtx, action);
                            break;
                        case DTOStatus.UPDATE:
                            actionService.updateByPrimaryKey(requestCtx, action);
                            break;
                        default:
                    }
                });
            }
        });

        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/task/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflTask> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        service.batchDelete(dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData();
    }

    @RequestMapping(value = {"wfl/WFL1010/wfl_task_receiver", "wfl/WFL1010/wfl_user_task",
            "wfl/WFL1010/wfl_task_action"})
    public ModelAndView getDefaultBusinessRule(@RequestParam Long taskId, @RequestParam String detailType,
                    HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        WflTask task = new WflTask();
        task.setTaskId(taskId);

        task = service.selectByPrimaryKey(requestContext, task);
        String viewName = "";
        switch (detailType) {
            case "receiver":
                viewName = "wfl/WFL1010/wfl_task_receiver";
                break;
            case "task":
                viewName = "wfl/WFL1010/wfl_user_task";
                break;
            case "action":
                viewName = "wfl/WFL1010/wfl_task_action";
                break;
            case "bizRule":
                viewName = "wfl/WFL1010/wfl_gateway_biz_rule";
                break;
            default:
                viewName = "wfl/WFL1010/wfl_task_receiver";
                break;
        }

        ModelAndView modelAndView = new ModelAndView(viewName);


        modelAndView.addObject("taskInfo", task);

        return modelAndView;
    }

    @RequestMapping(value = "/wfl/task/taskNameLov")
    @ResponseBody
    public ResponseData taskNameLov(@RequestParam Long processId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.taskNameLov(processId));
    }
}

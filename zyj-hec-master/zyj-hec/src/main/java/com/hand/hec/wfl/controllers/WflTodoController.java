package com.hand.hec.wfl.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.SortType;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.exception.WflAddApproverException;
import com.hand.hec.wfl.exception.WflTaskActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.service.IResourceService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.wfl.service.*;

@Controller
public class WflTodoController extends BaseController {

    @Autowired
    private IWflMonitorService service;

    @Autowired
    private IWflInstanceService instanceService;

    @Autowired
    private IWflProcessService processService;

    @Autowired
    private IWflPageService pageService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IWflReceiverUtilService utilService;

    @Autowired
    private IWflInsTaskService insTaskService;

    @Autowired
    private IWflInsTaskRecipientService insTaskRecipientService;

    @Autowired
    private IWflTaskEngineService taskEngineService;

    @Autowired
    private IWflTaskActionService actionService;

    @Autowired
    private IWflTodoService todoService;

    private WflInstance getInstance(IRequest requestCtx, Long instanceId) {
        WflInstance instance = new WflInstance();
        instance.setInstanceId(instanceId);
        instance = instanceService.selectByPrimaryKey(requestCtx, instance);

        if (instance == null) {
            throw new WflTaskActionException("WFL", "wfl_todo.instance_not_exists", null);
        }

        return instance;
    }

    private WflInsTaskRecipient getRecipient(IRequest requestCtx, Long insRecipientId) {
        WflInsTaskRecipient recipient = new WflInsTaskRecipient();
        recipient.setInsRecipientId(insRecipientId);
        recipient = insTaskRecipientService.selectByPrimaryKey(requestCtx, recipient);

        if (recipient == null) {
            throw new WflTaskActionException("WFL", "wfl_todo.recipient_not_exists", null);
        }
        return recipient;
    }

    private WflTaskAction getAction(IRequest requestCtx, WflInsTaskRecipient recipient, Long actionId) {
        WflInsTask insTask = new WflInsTask();
        insTask.setInsTaskId(recipient.getInsTaskId());
        insTask = insTaskService.selectByPrimaryKey(requestCtx, insTask);

        WflTaskAction action = new WflTaskAction();
        action.setActionId(actionId);
        action.setTaskId(insTask.getTaskId());
        List<WflTaskAction> actionList = actionService.select(requestCtx, action, 0, 0);
        if (actionList == null || actionList.size() == 0) {
            throw new WflTaskActionException("WFL", "wfl_task_action.action_not_exists", null);
        }
        action = actionList.get(0);

        return action;
    }

    @RequestMapping(value = {"/wfl/todo/approve", "/wfl/todo/send-back", "/wfl/todo/send-to", "/wfl/todo/add-task"})
    @ResponseBody
    public ResponseData approve(HttpServletRequest request, @RequestParam Long instanceId,
                    @RequestParam Long insRecipientId, @RequestParam Long actionId, @RequestParam String commentText) {
        IRequest requestCtx = createRequestContext(request);

        WflInstance instance = getInstance(requestCtx, instanceId);
        WflInsTaskRecipient recipient = getRecipient(requestCtx, insRecipientId);
        WflTaskAction action = getAction(requestCtx, recipient, actionId);

        taskEngineService.approve(requestCtx, instance, recipient, action, commentText);
        return new ResponseData(true);
    }


    @RequestMapping(value = "/wfl/todo/deliver")
    @ResponseBody
    public ResponseData deliver(HttpServletRequest request, @RequestParam Long instanceId,
                    @RequestParam Long insRecipientId, @RequestParam Long actionId, @RequestParam Long targetUserId,
                    @RequestParam String commentText) {
        IRequest requestCtx = createRequestContext(request);

        WflInstance instance = getInstance(requestCtx, instanceId);
        WflInsTaskRecipient recipient = getRecipient(requestCtx, insRecipientId);
        WflTaskAction action = getAction(requestCtx, recipient, actionId);

        taskEngineService.deliver(requestCtx, instance, recipient, targetUserId, commentText);
        return new ResponseData(true);
    }


    @RequestMapping(value = "/wfl/todo/add-approver")
    @ResponseBody
    public ResponseData addApprover(HttpServletRequest request, @RequestBody List<WflAddApprover> approverList,
                    @RequestParam Long instanceId, @RequestParam Long insRecipientId, @RequestParam Long actionId,
                    @RequestParam String commentText) {
        if (approverList == null || approverList.size() == 0) {
            throw new WflAddApproverException("WFL", "wfl_add_approver.no_adder_approver", null);
        }

        IRequest requestCtx = createRequestContext(request);

        WflInstance instance = getInstance(requestCtx, instanceId);
        WflInsTaskRecipient recipient = getRecipient(requestCtx, insRecipientId);
        WflTaskAction action = getAction(requestCtx, recipient, actionId);

        taskEngineService.addApprover(requestCtx, instance, recipient, approverList, commentText);
        return new ResponseData(true);
    }


    @RequestMapping(value = "/wfl/public/wfl_approve.screen")
    public ModelAndView jumpWflApprove(HttpServletRequest request, Long instanceId, Long insRecipientId) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView approveView = new ModelAndView("/wfl/public/wfl_approve");

        // 获取工作流实例
        WflInstance instance = getInstance(requestCtx, instanceId);

        // 获取工作流接受者
        WflInsTaskRecipient insTaskRecipient = getRecipient(requestCtx, insRecipientId);

        // 获取所有的按钮
        List<WflTaskAction> actionList = insTaskService.getTaskActionList(requestCtx, instance.getProcessId(),
                        insTaskRecipient.getInsTaskId());

        approveView.addObject("actionList", actionList);

        return approveView;
    }

    @RequestMapping(value = "/wfl/todo/query")
    public ResponseData getTodoList(HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        return new ResponseData(todoService.getTodoList(requestCtx, page, pageSize));
    }

}

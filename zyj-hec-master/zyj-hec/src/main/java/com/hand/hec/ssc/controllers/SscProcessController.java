package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscProcess;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.dto.SscWorkflowAction;
import com.hand.hec.ssc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 操作记录控制器
 *
 * @author luhui 2019-03-27
 */

@Controller
public class SscProcessController extends BaseController {

    @Autowired
    private ISscProcessService service;

    @Autowired
    private ISscCoreService sscCoreService;

    @Autowired
    private ISscWorkflowActionService sscWorkflowActionService;

    @Autowired
    private ISscTaskPoolService sscTaskPoolService;

    @RequestMapping(value = "/ssc/process/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscProcess dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/ssc/process/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscProcess> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/ssc/process/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SscProcess> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "ssc/SSC2010/ssc_process.ftl")
    public ModelAndView sscProcessView(HttpServletRequest request, @RequestParam Long dispatchRecordId) {
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("ssc/SSC2010/ssc_process");
        List<SscWorkflowAction> sscWorkflowActions = new ArrayList<>();
        sscWorkflowActions = sscWorkflowActionService.getActionByDispatchRecordId(iRequest,dispatchRecordId);
        List<SscTaskPool> sscTaskPools = new ArrayList<>();
        sscTaskPools = sscTaskPoolService.getTaskRecordInfo(iRequest,null,dispatchRecordId);
        String processService = null;
        processService = sscTaskPoolService.queryServiceNameBydispatchId(iRequest,dispatchRecordId);
        modelAndView.addObject("processService", processService);
        modelAndView.addObject("actions", sscWorkflowActions);
        modelAndView.addObject("docInfo", sscTaskPools);
        return modelAndView;
    }

    @RequestMapping(value = "/ssc/process/do-process")
    public ResponseData doProcess(HttpServletRequest request, @RequestParam Long dispatchRecordId,
                    @RequestParam Long actionId, @RequestParam String opinion) {
        IRequest iRequest = createRequestContext(request);
        sscCoreService.doProcess(iRequest,dispatchRecordId,actionId,opinion);
        return new ResponseData();
    }

    @RequestMapping(value = "ssc/SSC2010/ssc_view.screen")
    public ModelAndView sscViewView(HttpServletRequest request, @RequestParam Long dispatchRecordId,@RequestParam Long taskId) {
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("ssc/SSC2010/ssc_view");
        List<SscTaskPool> sscTaskPools = new ArrayList<>();
        sscTaskPools = sscTaskPoolService.getTaskRecordInfo(iRequest,taskId,dispatchRecordId);
        String processService = null;
        processService = sscTaskPoolService.queryServiceNameBydispatchId(iRequest,dispatchRecordId);
        modelAndView.addObject("processService", processService);
        modelAndView.addObject("docInfo", sscTaskPools);
        return modelAndView;
    }

    @RequestMapping(value = "/ssc/process/process-query")
    @ResponseBody
    public ResponseData processQuery( @RequestParam Long dispatchRecordId,@RequestParam Long taskId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.processQuery(requestContext, dispatchRecordId, taskId, page, pageSize));
    }

}

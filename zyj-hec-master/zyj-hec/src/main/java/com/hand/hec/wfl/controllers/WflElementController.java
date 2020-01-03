package com.hand.hec.wfl.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class WflElementController extends BaseController {

    @Autowired
    private IWflElementService service;

    @Autowired
    private IWflTaskService wflTaskService;

    @Autowired
    private IWflEventService wflEventService;

    @Autowired
    private IWflFlowService wflFlowService;

    @Autowired
    private IWflGatewayService wflGatewayService;


    @RequestMapping(value = "/wfl/wflElement/query")
    @ResponseBody
    public ResponseData query(WflElement dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果processId为空，返回空值
        if(dto.getProcessId() == null){
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/wfl/wflElement/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflElement> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/wflElement/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflElement> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/wfl/wflElement/saveAll")
    @ResponseBody
    public void saveAll(@RequestBody List<WflElement> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
        }
        IRequest requestCtx = createRequestContext(request);
        WflElement element = dto.get(0);

        List<WflEvent> eventList = element.getEvent();
        List<WflTask> taskList = element.getTask();
        List<WflGateway> gatewayList = element.getGateway();
        List<WflFlow> flowList = element.getFlow();

        wflTaskService.batchUpdate(requestCtx, taskList);
        wflEventService.batchUpdate(requestCtx, eventList);
        wflGatewayService.batchUpdate(requestCtx, gatewayList);

        saveElementFlows(requestCtx, flowList);

    }

    private void saveElementFlows(IRequest requestCtx, List<WflFlow> wflFlows) {
        Iterator it = wflFlows.iterator();
        Long processId = null;
        List<WflTask> tasks = new ArrayList<WflTask>();
        List<WflEvent> events = new ArrayList<WflEvent>();
        List<WflGateway> gateways = new ArrayList<WflGateway>();
        for (int i = 0; i < wflFlows.size(); i++) {
            processId = wflFlows.get(i).getProcessId();
            if (processId != null) {
                break;
            }
        }
        if (processId != null) {
            tasks = wflTaskService.queryByProcessId(processId);
            events = wflEventService.processEventQuery(processId, requestCtx);
            gateways = wflGatewayService.processGatewayQuery(processId, requestCtx);
        }
        while (it.hasNext()) {
            WflFlow wf = (WflFlow) it.next();
            String fromElementType = wf.getFromElementType();
            String toElementType = wf.getToElementType();
            if (fromElementType != null && wf.getFromElementName() != null && wf.getFromElementId() == null) {
                String fromElementCode = wf.getFromElementCode();
                switch (fromElementType) {
                    case "EVENT":
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i).getEventCode().equalsIgnoreCase(fromElementCode)) {
                                wf.setFromElementId(events.get(i).getEventId());
                            }
                        }
                        break;
                    case "TASK":
                        for (int i = 0; i < tasks.size(); i++) {
                            if (tasks.get(i).getTaskCode().equalsIgnoreCase(fromElementCode)) {
                                wf.setFromElementId(tasks.get(i).getTaskId());
                            }
                        }
                        break;
                    case "GATEWAY":
                        for (int i = 0; i < gateways.size(); i++) {
                            if (gateways.get(i).getGatewayCode().equalsIgnoreCase(fromElementCode)) {
                                wf.setFromElementId(gateways.get(i).getGatewayId());
                            }
                        }
                        break;
                }
            }
            if (toElementType != null && wf.getToElementName() != null && wf.getToElementId() == null) {
                String toElementCode = wf.getToElementCode();
                switch (toElementType) {
                    case "EVENT":
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i).getEventCode().equalsIgnoreCase(toElementCode)) {
                                wf.setToElementId(events.get(i).getEventId());
                            }
                        }
                        break;
                    case "TASK":
                        for (int i = 0; i < tasks.size(); i++) {
                            if (tasks.get(i).getTaskCode().equalsIgnoreCase(toElementCode)) {
                                wf.setToElementId(tasks.get(i).getTaskId());
                            }
                        }
                        break;
                    case "GATEWAY":
                        for (int i = 0; i < gateways.size(); i++) {
                            if (gateways.get(i).getGatewayCode().equalsIgnoreCase(toElementCode)) {
                                wf.setToElementId(gateways.get(i).getGatewayId());
                            }
                        }
                        break;
                }
            }
            wf.setFromElementCode("");
            wf.setToElementCode("");
        }
        wflFlowService.batchUpdate(requestCtx, wflFlows);

    }
}

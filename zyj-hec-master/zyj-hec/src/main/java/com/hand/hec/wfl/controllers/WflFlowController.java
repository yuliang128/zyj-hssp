package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import uncertain.composite.CompositeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
public class WflFlowController extends BaseController {

    @Autowired
    private IWflFlowService service;

    @Autowired
    private IWflTaskService taskService;
    @Autowired
    private IWflEventService eventService;
    @Autowired
    private IWflGatewayService gatewayService;

    @Autowired
    private IWflProcessService processService;

    @RequestMapping(value = "/wfl/flow/query")
    @ResponseBody
    public ResponseData query(WflFlow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果processId为空，返回空值
        if (dto.getProcessId() == null) {
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflFlow.FIELD_FLOW_ID),
                new WhereField(WflFlow.FIELD_PROCESS_ID), new WhereField(WflFlow.FIELD_FLOW_CODE),
                new WhereField(WflFlow.FIELD_FLOW_NAME)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/wfl/flow/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflFlow> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        List<WflFlow> flows = service.batchUpdate(requestCtx, dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData(flows);
    }

    @RequestMapping(value = "/wfl/flow/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflFlow> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.batchDelete(dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/wfl/processFlow/query")
    @ResponseBody
    public ResponseData processFlowQuery(@Param("processId") Long processId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.processFlowQuery(processId, requestContext));
    }

    @RequestMapping(value = "/wfl/insertFlows")
    @ResponseBody
    public void insertFlows(IRequest requestCtx, List<WflFlow> wflFlows) {
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
            tasks = taskService.queryByProcessId(processId);
            events = eventService.processEventQuery(processId, requestCtx);
            gateways = gatewayService.processGatewayQuery(processId, requestCtx);

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
                    default:
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
                    default:
                }
            }
            wf.setFromElementCode("");
            wf.setToElementCode("");
        }

        service.batchUpdate(requestCtx, wflFlows);


        if (wflFlows != null && wflFlows.size() != 0) {
            processService.changeConfigType(requestCtx, wflFlows.get(0).getProcessId(),
                            WflProcess.CONFIG_TYPE_STANDARD);
        }
    }
}

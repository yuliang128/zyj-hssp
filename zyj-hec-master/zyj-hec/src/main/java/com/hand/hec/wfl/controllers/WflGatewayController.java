package com.hand.hec.wfl.controllers;

import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.IWflProcessService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflGateway;
import com.hand.hec.wfl.service.IWflGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class WflGatewayController extends BaseController {

    @Autowired
    private IWflGatewayService service;

    @Autowired
    private IWflProcessService processService;

    @RequestMapping(value = "/wfl/gateway/query")
    @ResponseBody
    public ResponseData query(WflGateway dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果processId为空，返回空值
        if (dto.getProcessId() == null) {
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/wfl/gateway/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflGateway> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        // 设置
        service.setRequest(requestCtx);
        List<WflGateway> gateways = service.batchUpdate(requestCtx, dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData(gateways);
    }

    @RequestMapping(value = "/wfl/gateway/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflGateway> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        service.batchDelete(dto);

        if (dto != null && dto.size() != 0) {
            processService.changeConfigType(requestCtx, dto.get(0).getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData();
    }


    @RequestMapping(value = "/wfl/process-gateway/query")
    @ResponseBody
    public ResponseData processGatewayQuery(@RequestParam Long processId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.processGatewayQuery(processId, requestContext));
    }
}

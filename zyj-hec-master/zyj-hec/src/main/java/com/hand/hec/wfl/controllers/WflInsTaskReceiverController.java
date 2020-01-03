package com.hand.hec.wfl.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.service.IWflInsTaskReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class WflInsTaskReceiverController extends BaseController{

    @Autowired
    private IWflInsTaskReceiverService service;


    @RequestMapping(value = "/wfl/insTaskReceiver/query")
    @ResponseBody
    public ResponseData query(WflInsTaskReceiver dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/wfl/insTaskReceiver/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflInsTaskReceiver> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/insTaskReceiver/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<WflInsTaskReceiver> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }
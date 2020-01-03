package com.hand.hec.wfl.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflInsTaskReceiverHt;
import com.hand.hec.wfl.service.IWflInsTaskReceiverHtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class WflInsTaskReceiverHtController extends BaseController{

    @Autowired
    private IWflInsTaskReceiverHtService service;


    @RequestMapping(value = "/wfl/insTaskReceiverHt/query")
    @ResponseBody
    public ResponseData query(WflInsTaskReceiverHt dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/wfl/insTaskReceiverHt/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflInsTaskReceiverHt> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/insTaskReceiverHt/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<WflInsTaskReceiverHt> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }
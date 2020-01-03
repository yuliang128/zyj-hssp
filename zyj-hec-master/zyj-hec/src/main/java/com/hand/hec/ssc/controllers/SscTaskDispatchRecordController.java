package com.hand.hec.ssc.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;
import com.hand.hec.ssc.service.ISscCoreService;
import com.hand.hec.ssc.service.ISscTaskDispatchRecordService;

/**
 *
 * 任务分派记录控制器
 *
 * @author luhui 2019-03-27
 */

@Controller
@RequestMapping(value = "/ssc/task-dispatch-record")
public class SscTaskDispatchRecordController extends BaseController{

    @Autowired
    private ISscTaskDispatchRecordService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscTaskDispatchRecord dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscTaskDispatchRecord> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscTaskDispatchRecord> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/doRejectReturn")
    @ResponseBody
    public ResponseData doRejectReturn(@RequestBody List<SscTaskDispatchRecord> sscTaskDispatchRecordList, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.rejectReturn(requestCtx,sscTaskDispatchRecordList));
    }

    @RequestMapping(value = "/doAgreetReturn")
    @ResponseBody
    public ResponseData doAgreetReturn(@RequestBody List<SscTaskDispatchRecord> sscTaskDispatchRecordList, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.agreeReturn(requestCtx,sscTaskDispatchRecordList));
    }

    @RequestMapping(value = "/conAndAsgn")
    @ResponseBody
    public ResponseData conAndAsgn(@RequestBody List<SscTaskDispatchRecord> sscTaskDispatchRecordList, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.conAndAsgn(requestCtx,sscTaskDispatchRecordList));
    }

    @RequestMapping(value = "/conAndAgenQuery",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData conAndAgenQuery(SscTaskDispatchRecord dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.conAndAgenQuery(requestContext,page,pageSize));
    }

    @RequestMapping(value = "/doForceReturn")
    @ResponseBody
    public ResponseData doForceReturn(@RequestBody List<SscTaskDispatchRecord> sscTaskDispatchRecordList, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.forceReturn(requestCtx,sscTaskDispatchRecordList));
    }

    @RequestMapping(value = "/doDispatch")
    @ResponseBody
    public ResponseData doDispatch(@RequestBody List<SscTaskDispatchRecord> sscTaskDispatchRecordList, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.dispatch(requestCtx,sscTaskDispatchRecordList));
    }
}
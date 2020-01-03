package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtPeriodStatus;
import com.hand.hec.bgt.service.IBgtPeriodStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;
/**
 * <p>
 * 预算期间状态Controller
 * </p>
 * 
 * @author mouse 2019/01/07 16:11
 */
@RequestMapping(value = "/bgt/period-status")
@Controller
public class BgtPeriodStatusController extends BaseController{

    @Autowired
    private IBgtPeriodStatusService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtPeriodStatus> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtPeriodStatus> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryOpen")
    @ResponseBody
    public ResponseData queryOpen(BgtPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryOpenPeriod(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/queryClosed")
    @ResponseBody
    public ResponseData queryClosed(BgtPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryClosePeriod(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/closePeriod")
    @ResponseBody
    public ResponseData closePeriod(@RequestBody BgtPeriodStatus dto,  HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.closePeriod(requestCtx, dto));
    }

    @RequestMapping(value = "/openPeriod")
    @ResponseBody
    public ResponseData openPeriod(@RequestBody BgtPeriodStatus dto,  HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.openPeriod(requestCtx, dto));
    }
}
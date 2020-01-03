package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkRecord;
import com.hand.hec.ssc.service.ISscWorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 员工工作记录控制器
 *
 * @author zhaohui 2019/04/01 15:12
 */

@Controller
public class SscWorkRecordController extends BaseController{

    @Autowired
    private ISscWorkRecordService service;

    @RequestMapping(value = "/ssc/work-record/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkRecord dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/ssc/work-record/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkRecord> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/ssc/work-record/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkRecord> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/ssc/work-record/start-work",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData changeWork(HttpServletRequest request,String workStatus) {
        IRequest requestContext = createRequestContext(request);
        service.startWork(requestContext);
        return new ResponseData();
    }

    @RequestMapping(value = "/ssc/work-record/pause-work",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData pauseWork(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.pauseWork(requestContext);
        return new ResponseData();
    }

    @RequestMapping(value = "/ssc/work-record/stop-work",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData stopWork(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.stopWork(requestContext);
        return new ResponseData();
    }

    @RequestMapping(value = "ssc/SSC2010/ssc_my_task.screen")
    public ModelAndView sscMyTaskView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("ssc/SSC2010/ssc_my_task");
        String workStatus = null;
        workStatus = service.getWorkStatus(iRequest);
        modelAndView.addObject("workStatus",workStatus);
        return modelAndView;
    }
}
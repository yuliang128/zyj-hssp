package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscNodeAssignWorker;
import com.hand.hec.ssc.service.ISscNodeAssignWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * SscNodeAssignWorker控制器
 *
 * @author zhangbo 2019-03-20
 */

@Controller
@RequestMapping(value = "/ssc/node-assign-worker")
public class SscNodeAssignWorkerController extends BaseController{

    @Autowired
    private ISscNodeAssignWorkerService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscNodeAssignWorker dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySscNodeAssignWorker(dto.getNodeId(),requestContext));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscNodeAssignWorker> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscNodeAssignWorker> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
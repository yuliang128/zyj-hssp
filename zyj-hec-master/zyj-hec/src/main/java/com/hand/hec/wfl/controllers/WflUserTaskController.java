package com.hand.hec.wfl.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflUserTask;
import com.hand.hec.wfl.service.IWflUserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

    @Controller
    public class WflUserTaskController extends BaseController{

    @Autowired
    private IWflUserTaskService service;


    @RequestMapping(value = "/wfl/user-task/query")
    @ResponseBody
    public ResponseData query(WflUserTask dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果taskId为空，返回空值
        if(dto.getTaskId() == null){
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/wfl/user-task/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflUserTask> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/user-task/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<WflUserTask> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }
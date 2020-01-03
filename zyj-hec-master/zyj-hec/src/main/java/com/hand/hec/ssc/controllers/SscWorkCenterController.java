package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkCenter;
import com.hand.hec.ssc.service.ISscWorkCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * SscWorkCenter控制器
 *
 * @author bo.zhang 2019-03-15
 */

@Controller
@RequestMapping(value = "/ssc/work-center")
public class SscWorkCenterController extends BaseController{

    @Autowired
    private ISscWorkCenterService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySscWorkCenter(requestContext,dto.getWorkCenterCode(),dto.getDescription()));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkCenter> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkCenter> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/querySscWorkCenter")
    @ResponseBody
    public ResponseData querySscWorkCenter(SscWorkCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectSscWorkCenter(requestContext, dto.getName(), dto.getDescription(), dto.getWorkTeamName()));
    }
    /**
     * 当前员工为工作组负责人的工作组对应的工作中心
     *
     * @param dto
     * @author ngls.luhui 2019-03-25 18:50
     * @return 
     */
    @RequestMapping(value = "/combox",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData combox(SscWorkCenter dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.combox(dto,requestContext));

    }
}
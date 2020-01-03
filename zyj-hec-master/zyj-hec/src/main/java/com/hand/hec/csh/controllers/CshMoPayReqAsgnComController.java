package com.hand.hec.csh.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.csh.dto.CshMoPayReqAsgnCom;
import com.hand.hec.csh.service.ICshMoPayReqAsgnComService;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/csh/mo-pay-req-asgn-com")
public class CshMoPayReqAsgnComController extends BaseController{

    @Autowired
    private ICshMoPayReqAsgnComService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoPayReqAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoPayReqAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoPayReqAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryByTypeId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByTypeId(CshMoPayReqAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<CshMoPayReqAsgnCom> list = service.queryByTypeId(requestContext,dto,page,pageSize);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/queryComByTypeId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryComByTypeId(Long moPaymentReqTypeId,Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryComByTypeId(requestContext,moPaymentReqTypeId,magOrgId,page,pageSize));
    }

    @RequestMapping(value = "/batchAssignCom")
    @ResponseBody
    public ResponseData batchAssignCom(@RequestBody List<CshMoPayReqAsgnCom> dto, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssignCom(requestCtx, dto));
    }
}
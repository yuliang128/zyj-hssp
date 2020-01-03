package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkCenterCompany;
import com.hand.hec.ssc.service.ISscWorkCenterCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * SscWorkCenterCompany控制器
 *
 * @author zhangbo 2019-03-15
 */

@Controller
@RequestMapping(value = "/ssc/work-center-company")
public class SscWorkCenterCompanyController extends BaseController{

    @Autowired
    private ISscWorkCenterCompanyService service;
    @Autowired
    private IFndCompanyService iFndCompanyService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkCenterCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectSscWorkCenterCompany(requestContext,dto.getScopeId()));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkCenterCompany> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkCenterCompany> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/batchQuery")
    @ResponseBody
    public ResponseData batchQuery(SscWorkCenterCompany dto,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(iFndCompanyService.workCenterDocTypeFndCompany(requestContext,dto.getMagOrgId(),dto.getScopeId()));
    }
}
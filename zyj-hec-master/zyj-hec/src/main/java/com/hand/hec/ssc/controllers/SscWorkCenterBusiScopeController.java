package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkCenterBusiScope;
import com.hand.hec.ssc.service.ISscWorkCenterBusiScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * SscWorkCenterBusiScope控制器
 *
 * @author bo.zhang 2019-03-15
 */

@Controller
public class SscWorkCenterBusiScopeController extends BaseController{

    @Autowired
    private ISscWorkCenterBusiScopeService service;

    @Autowired
    private IFndManagingOrganizationService iFndManagingOrganizationService;

    @RequestMapping(value = "/ssc/work-center-busi-scope/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkCenterBusiScope dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySscWorkCenterBusiScope(requestContext,dto.getWorkCenterId()));
    }

    @RequestMapping(value = "/ssc/work-center-busi-scope/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkCenterBusiScope> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/ssc/work-center-busi-scope/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkCenterBusiScope> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "ssc/SSC1010/ssc_work_center_busi_scope.screen")
    public ModelAndView sscWorkCenterBusiScopeView(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        List<FndManagingOrganization> fndManagingOrganizationList =
                iFndManagingOrganizationService.selectMagOrg(requestContext);
        ModelAndView view = new ModelAndView("ssc/SSC1010/ssc_work_center_busi_scope");
        view.addObject("fndManagingOrganizationList",fndManagingOrganizationList);
        return view;
    }
}
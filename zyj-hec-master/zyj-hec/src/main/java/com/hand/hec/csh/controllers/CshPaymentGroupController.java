package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentGroup;
import com.hand.hec.csh.service.ICshPaymentGroupService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * CshPaymentGroup
 *
 * @author zhangbo 2019-02-27
 */

@Controller
public class CshPaymentGroupController extends BaseController{

    @Autowired
    private ICshPaymentGroupService service;
    @Autowired
    private IFndManagingOrganizationService iFndManagingOrganizationService;

    @RequestMapping(value = "/csh/payment-group/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectCshPaymentGroup(dto.getMagOrgId(),dto.getGroupCode(),dto.getDescription(),requestContext));
    }

    @RequestMapping(value = "/csh/payment-group/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentGroup> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-group/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentGroup> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1240/csh_payment_group.screen")
    public ModelAndView cshPaymentGroupView(HttpServletRequest request ){
        IRequest requestContext = createRequestContext(request);
        List<FndManagingOrganization> fndManagingOrganizationList =
                iFndManagingOrganizationService.queryMagOrg(requestContext);
        FndManagingOrganization fndManagingOrganization = iFndManagingOrganizationService.queryDefaultMagOrg(requestContext);
        ModelAndView view = new ModelAndView("csh/CSH1240/csh_payment_group");
        view.addObject("fndManagingOrganizationList",fndManagingOrganizationList);
        view.addObject("fndManagingOrganization",fndManagingOrganization);
        return view;
    }
}
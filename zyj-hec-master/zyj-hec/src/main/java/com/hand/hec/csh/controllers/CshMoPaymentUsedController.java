package com.hand.hec.csh.controllers;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.csh.dto.CshMoPaymentUsed;
import com.hand.hec.csh.service.ICshMoPaymentUsedService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 付款用途Controller
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */


@Controller
@RequestMapping(value = "/csh/mo-payment-used")
public class CshMoPaymentUsedController extends BaseController {

    @Autowired
    private ICshMoPaymentUsedService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoPaymentUsed dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoPaymentUsed> dto, BindingResult result,
                    HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshMoPaymentUsed> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, Long accountSetId, Long accountId) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganization = fndManagingOrganizationService
                .queryDefaultMagOrg(requestContext);

        ModelAndView view = new ModelAndView("csh/CSH1075/csh_mo_payment_used");
        view.addObject("fndManagingOrganization", fndManagingOrganization);
        return view;
    }

    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(CshMoPaymentUsed dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    /**
     * @Description 根据公司（管理组织）获取启用的付款用途
     *
     * @Author Tagin
     * @Date 2019-03-04 20:54
     * @param request
     * @Return com.hand.hap.system.dto.ResponseData
     * @Version 1.0
     **/
    @RequestMapping(value = "/queryPaymentUsed")
    @ResponseBody
    public ResponseData queryPaymentUsed(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.queryPaymentUsed(iRequest));
    }
}

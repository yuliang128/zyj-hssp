package com.hand.hec.csh.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoRepaymentRegType;
import com.hand.hec.csh.service.ICshMoRepaymentRegTypeService;

/**
 * <p>
 * 
 * </p>
 * 
 * @author jialin.xing@hand-china.com 2019/04/26 10:42
 */

@Controller
public class CshMoRepaymentRegTypeController extends BaseController {

    @Autowired
    private ICshMoRepaymentRegTypeService service;

    @Autowired
    private IFndManagingOrganizationService magOrgService;

    @RequestMapping(value = "/csh/mo-repayment-reg-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoRepaymentRegType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/mo-repayment-reg-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoRepaymentRegType> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/mo-repayment-reg-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshMoRepaymentRegType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH7010/csh_mo_repayment_reg_types.screen")
    public ModelAndView cshMoRepaymentRegTypeView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH7010/csh_mo_repayment_reg_types");
        IRequest requestCtx = createRequestContext(request);
        FndManagingOrganization defaultMagOrg = magOrgService.defaultManageOrganizationQuery(requestCtx, requestCtx.getCompanyId());
        view.addObject("defaultMagOrg", defaultMagOrg);
        return view;
    }

    @RequestMapping(value="/csh/mo-repayment-reg-type/queryByCompanyId")
    public ResponseData queryByCompanyId(HttpServletRequest request) {
        IRequest requestCxt = createRequestContext(request);
        return new ResponseData(service.queryByCompanyId(requestCxt));
    }
}
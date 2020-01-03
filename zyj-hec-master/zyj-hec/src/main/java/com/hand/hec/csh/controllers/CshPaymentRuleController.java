package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentRule;
import com.hand.hec.csh.service.ICshPaymentRuleService;
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
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class CshPaymentRuleController extends BaseController{

    @Autowired
    private ICshPaymentRuleService service;
    @Autowired
    private IFndManagingOrganizationService iFndManagingOrganizationService;

    @RequestMapping(value = "/csh/payment-rule/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        /*System.out.println("2313123"+dto.getMagOrgId());*/
        if(dto.getMagOrgId()!=null) {
            criteria.where(new WhereField(CshPaymentRule.FIELD_MAG_ORG_ID, Comparison.EQUAL));
        }
        if(dto.getRuleCodeFrom()!=null) {
            criteria.where(new WhereField(CshPaymentRule.FIELD_RULE_CODE_FROM));
        }
        if(dto.getRuleCodeTo()!=null) {
            criteria.where(new WhereField(CshPaymentRule.FIELD_RULE_CODE_TO));
        }
        return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping(value = "/csh/payment-rule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentRule> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-rule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentRule> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1230/csh_payment_rule.screen")
    public ModelAndView cshPaymentRule(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<FndManagingOrganization> fndManagingOrganizationList = iFndManagingOrganizationService.magOrgOption(requestContext);
        ModelAndView view = new ModelAndView("csh/CSH1230/csh_payment_rule");
        view.addObject("fndManagingOrganizationList", fndManagingOrganizationList);
        return view;
    }
}
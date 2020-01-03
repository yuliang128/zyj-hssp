package com.hand.hec.csh.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentTrxRule;
import com.hand.hec.csh.service.ICshPaymentTrxRuleService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;

/**
 *
 * 付款事务生成规则定义控制器
 *
 * @author luhui 2019-03-04
 */

@Controller
public class CshPaymentTrxRuleController extends BaseController{

    @Autowired
    private ICshPaymentTrxRuleService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/csh/payment-trx-rule/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentTrxRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentTrxRule.FIELD_DESCRIPTION,Comparison.LIKE),
                new WhereField(CshPaymentTrxRule.FIELD_RULE_CODE,Comparison.LIKE),
                new WhereField(CshPaymentTrxRule.FIELD_MAG_ORG_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/csh/payment-trx-rule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentTrxRule> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitTrx(dto,requestCtx));
    }

    @RequestMapping(value = "/csh/payment-trx-rule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentTrxRule> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询默认管理组织返还给-付款事务生成规则定义页面
     *
     * @param request
     * @author ngls.luhui 2019-02-27 10:25
     * @return
     */
    @RequestMapping(value = "csh/CSH1250/csh_payment_trx_rules.screen")
    public ModelAndView queryDefaultMagOrgPolicy(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH1250/csh_payment_trx_rules");
        FndManagingOrganization defaultMag = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        view.addObject("defaultMag",defaultMag);
        return view;
    }
}
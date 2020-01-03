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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hec.csh.dto.CshPaymentBatchRule;
import com.hand.hec.csh.service.ICshPaymentBatchRuleService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;

/**
 *
 * 付款批決定規則定义控制器
 *
 * @author luhui 2019-02-28
 */

@Controller
public class CshPaymentBatchRuleController extends BaseController{

    @Autowired
    private ICshPaymentBatchRuleService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/csh/payment-batch-rule/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentBatchRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentBatchRule.FIELD_MAG_ORG_ID),
                new WhereField(CshPaymentBatchRule.FIELD_DESCRIPTION,Comparison.LIKE),
                new WhereField(CshPaymentBatchRule.FIELD_RULE_CODE,Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/csh/payment-batch-rule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentBatchRule> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-batch-rule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentBatchRule> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询默认管理组织返还给-付款批決定規則定义页面
     *
     * @param request
     * @author ngls.luhui 2019-02-28 10:25
     * @return
     */
    @RequestMapping(value = "csh/CSH1095/csh_payment_batch_rules.screen")
    public ModelAndView cshPaymentBatchRulesView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH1095/csh_payment_batch_rules");
        FndManagingOrganization defaultMag = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        view.addObject("defaultMag",defaultMag);
        return view;
    }
}
package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndBusinessRuleDetail;
import com.hand.hec.fnd.service.IFndBusinessRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import com.hand.hec.fnd.dto.FndBusinessRule;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FndBusinessRuleDetailController extends BaseController {

    @Autowired
    private IFndBusinessRuleDetailService service;
    @Autowired
    private IFndBusinessRuleService ruleService;


    @RequestMapping(value = "/fnd/business-rule-detail/query")
    @ResponseBody
    public ResponseData query(FndBusinessRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndBusinessRuleDetail.FIELD_BUSINESS_RULE_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/fnd/business-rule-detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndBusinessRuleDetail> dto, BindingResult result,
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

    @RequestMapping(value = "/fnd/business-rule-detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndBusinessRuleDetail> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"/fnd/FND5020/fnd_business_rule_detail"})
    public ModelAndView getDefaultBusinessRule(@RequestParam Long businessRuleId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndBusinessRule businessRule = new FndBusinessRule();
        businessRule.setBusinessRuleId(businessRuleId);
        businessRule = ruleService.selectByPrimaryKey(requestContext, businessRule);
        ModelAndView modelAndView = new ModelAndView("fnd/FND5020/fnd_business_rule_detail");
        modelAndView.addObject("businessInfo", businessRule);
        return modelAndView;
    }
}

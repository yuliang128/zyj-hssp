package com.hand.hec.csh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentRuleParameter;
import com.hand.hec.csh.service.ICshPaymentRuleParameterService;

/**
 * 付款批参数控制器
 *
 * @author luhui 2019-02-27
 */

@Controller
public class CshPaymentRuleParameterController extends BaseController {

    @Autowired
    private ICshPaymentRuleParameterService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/csh/payment-rule-parameter/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentRuleParameter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentRuleParameter.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(CshPaymentRuleParameter.FIELD_PARAMETER_CODE, Comparison.LIKE),
                new WhereField(CshPaymentRuleParameter.FIELD_MAG_ORG_ID, Comparison.LIKE),
                new WhereField(CshPaymentRuleParameter.FIELD_RULE_PARAMETER_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/csh/payment-rule-parameter/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentRuleParameter> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-rule-parameter/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshPaymentRuleParameter> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询默认管理组织返还给-支付权限规则参数代码定义页面
     *
     * @param request
     * @return
     * @author ngls.luhui 2019-02-27 10:25
     */
    @RequestMapping(value = "csh/CSH1220/csh_payment_rule_parameters.screen")
    public ModelAndView cshPaymentRuleParametersView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH1220/csh_payment_rule_parameters");
        FndManagingOrganization defaultMag = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        view.addObject("defaultMag", defaultMag);
        return view;
    }


    @RequestMapping(value = "/csh/payment-rule-parameter/checkSql", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData checkSql(CshPaymentRuleParameter dto, HttpServletRequest request) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.checkSql(requestContext, dto.getSqlContents()));
    }
}
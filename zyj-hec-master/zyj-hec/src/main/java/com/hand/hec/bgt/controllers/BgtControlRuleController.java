package com.hand.hec.bgt.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtControlRule;
import com.hand.hec.bgt.service.IBgtControlRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 预算控制规则Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:27
 */
@Controller
public class BgtControlRuleController extends BaseController {

    @Autowired
    private IBgtControlRuleService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/control-rule/query")
    @ResponseBody
    public ResponseData query(BgtControlRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtControlRule.FIELD_CONTROL_RULE_CODE, Comparison.LIKE),
                        new WhereField(BgtControlRule.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(BgtControlRule.FIELD_CONTROL_RULE_ID, Comparison.EQUAL),
                        new WhereField(BgtControlRule.FIELD_BGT_ORG_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bgt/control-rule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtControlRule> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/control-rule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtControlRule> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取预算项目默认预算组织
     *
     * @param request 获取管理组织id
     * @return 默认预算组织
     * @author guiyuting 2019/2/18 16:05
     */
    @RequestMapping(value = "/bgt/BGT2920/bgt_control_rule.screen")
    public ModelAndView bgtControlRuleView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2920/bgt_control_rule");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }
}

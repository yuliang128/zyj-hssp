package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpMoExpensePolicy;
import com.hand.hec.exp.service.IExpMoExpensePolicyService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 政策标准定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */

@Controller
public class ExpMoExpensePolicyController extends BaseController {

    @Autowired
    private IExpMoExpensePolicyService service;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-expense-policy/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpensePolicy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpensePolicy.FIELD_MAG_ORG_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpensePolicy.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(ExpMoExpensePolicy.FIELD_EXPENSE_POLICY_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpensePolicy.FIELD_EXPENSE_POLICY_CODE, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-expense-policy/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpensePolicy> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-expense-policy/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpensePolicy> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 政策标准获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author xiuxian.wu 2019/02/19 15:52
     */
    @RequestMapping(value = "/exp/EXP1170/exp_mo_expense_policy.screen")
    public ModelAndView expMoExpensePolicy(FndManagingOrganization dto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("exp/EXP1170/exp_mo_expense_policy");
        IRequest requestCtx = createRequestContext(request);
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }
}
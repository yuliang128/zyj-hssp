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

import com.hand.hec.exp.dto.ExpMoExpenseType;
import com.hand.hec.exp.service.IExpMoExpenseTypeService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 报销类型定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */

@Controller
public class ExpMoExpenseTypeController extends BaseController {

    @Autowired
    private IExpMoExpenseTypeService service;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-expense-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpenseType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpenseType.FIELD_MAG_ORG_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpenseType.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpMoExpenseType.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_CODE, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-expense-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpenseType> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-expense-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpenseType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 报销类型获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author xiuxian.wu 2019/02/26 15:52
     */
    @RequestMapping(value = "/exp/EXP4030/exp_mo_expense_types.screen")
    public ModelAndView expMoExpenseTypes(FndManagingOrganization dto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("exp/EXP4030/exp_mo_expense_types");
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
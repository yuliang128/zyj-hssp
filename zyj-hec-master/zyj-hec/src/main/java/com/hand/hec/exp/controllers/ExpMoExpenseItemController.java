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

import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.service.IExpMoExpenseItemService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 费用项目定义控制器
 *
 * @author zhongyu 2019-2-25 20:20
 */

@Controller
public class ExpMoExpenseItemController extends BaseController{

    @Autowired
    private IExpMoExpenseItemService service;
    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-expense-item/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpenseItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpenseItem.FIELD_MO_REQ_ITEM_DESC),
                new WhereField(ExpMoExpenseItem.FIELD_MO_REQ_ITEM_CODE),
                new WhereField(ExpMoExpenseItem.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_CODE,Comparison.LIKE),
                new WhereField(ExpMoExpenseItem.FIELD_BUDGET_ITEM_CODE,Comparison.LIKE),
                new WhereField(ExpMoExpenseItem.FIELD_BUDGET_ITEM_DESC,Comparison.LIKE),
                new WhereField(ExpMoExpenseItem.FIELD_MAG_ORG_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/exp/mo-expense-item/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpenseItem> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-expense-item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpenseItem> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author ZhongYu
     */
    @RequestMapping(value = "/expm/EXP2130/exp_mo_expense_item.screen")
    public ModelAndView expMoExpenseItemView(FndManagingOrganization dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP2130/exp_mo_expense_item");
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }

}
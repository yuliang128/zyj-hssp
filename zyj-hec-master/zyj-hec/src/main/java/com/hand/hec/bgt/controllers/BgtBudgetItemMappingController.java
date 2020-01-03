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
import com.hand.hec.bgt.dto.BgtBudgetItemMapping;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
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
 * 预算项决定规则Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:21
 * @author  zhongyu 2019-2-27
 */
@Controller
public class BgtBudgetItemMappingController extends BaseController{

    @Autowired
    private IBgtBudgetItemMappingService service;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    @RequestMapping(value = "/bgt/budget-item-mapping/query")
    @ResponseBody
    public ResponseData query(BgtBudgetItemMapping dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtBudgetItemMapping.FIELD_BUDGET_ITEM_NAME,Comparison.LIKE),
                new WhereField(BgtBudgetItemMapping.FIELD_BGT_ORG_ID, Comparison.EQUAL),
                new WhereField(BgtBudgetItemMapping.FIELD_BUSINESS_CATEGORY,Comparison.EQUAL),
                new WhereField(BgtBudgetItemMapping.FIELD_MAG_ORG_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_COMPANY_SHORT_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_UNIT_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_POSITION_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_EMPLOYEE_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_ACC_ENTITY_NAME),
                new WhereField(BgtBudgetItemMapping.FIELD_RESP_CNTER_NAME));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/bgt/budget-item-mapping/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetItemMapping> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/budget-item-mapping/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtBudgetItemMapping> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取默认管理组织下的预算组织
     * @param dto
     * @param request
     * @return
     * @author zhongyu 2019-2-27 11:02
     */
    @RequestMapping (value = "/bgt/BGT6200/bgt_budget_item_mapping.screen")
    public ModelAndView bgtBudgetItemMappingView(BgtOrganization dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        Long defaultMagOrgId = requestCtx.getMagOrgId();
        ModelAndView view = new ModelAndView("bgt/BGT6200/bgt_budget_item_mapping");
        List<BgtOrganization> organization = bgtOrganizationService.queryDefaultBgtOrganization(requestCtx);
        if (!organization.isEmpty()) {
            view.addObject("bgtOrgId",organization.get(0).getBgtOrgId());
            view.addObject("bgtOrgCodeName",organization.get(0).getBgtOrgCodeName());
        }
        return view;
    }
}
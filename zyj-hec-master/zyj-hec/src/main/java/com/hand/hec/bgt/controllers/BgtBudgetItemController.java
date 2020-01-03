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
import com.hand.hec.bgt.dto.BgtBudgetItem;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
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
 * 预算项目Controller
 * </p>
 *
 * @author mouse 2019/01/07 13:57
 */
@Controller
public class BgtBudgetItemController extends BaseController {

    @Autowired
    private IBgtBudgetItemService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/budget-item/query")
    @ResponseBody
    public ResponseData query(BgtBudgetItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtBudgetItem.FIELD_BUDGET_ITEM_ID, Comparison.EQUAL),
                        new WhereField(BgtBudgetItem.FIELD_BUDGET_ITEM_CODE, Comparison.LIKE),
                        new WhereField(BgtBudgetItem.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(BgtBudgetItem.FIELD_BGT_ORG_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    /**
     * 根据预算组织ID，查询汇总标志为Y，启用的预算项目信息
     *
     * @param dto 预算项目信息
     * @author guiyuting 2019-03-11 15:54
     * @return
     */
    @RequestMapping(value = "/bgt/budget-item/querySummary")
    @ResponseBody
    public ResponseData queryHierarchy(BgtBudgetItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryHierarchyByBgtOrgId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/budget-item/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetItem> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/budget-item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBudgetItem> dto) {
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
    @RequestMapping(value = "/bgt/BGT2010/bgt_budget_item.screen")
    public ModelAndView bgtBudgetItemView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2010/bgt_budget_item");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }

    /**
     * 预算项目批量分配管理组织
     *
     * @param dto
     * @author guiyuting 2019-03-13 16:31
     * @return
     */
    @RequestMapping(value = "/bgt/budget-item/batchAssignMagOrg")
    @ResponseBody
    public ResponseData batchAssignMagOrg(@RequestBody List<BgtBudgetItem> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssignMagOrg(requestCtx, dto));
    }

    /**
     * 根据预算组织ID查询不为汇总，未被分配的预算项目
     *
     * @param dto
     * @author guiyuting 2019-03-13 16:31
     * @return
     */
    @RequestMapping(value = "/bgt/budget-item/queryNoSummaryByBgtOrg")
    @ResponseBody
    public ResponseData queryNoSummaryByBgtOrg(BgtBudgetItem dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryNoSummaryByBgtOrg(requestCtx, dto));
    }

    /**
     * 根据预算组织ID，查询汇总标志为Y，启用的预算项目信息
     *
     * @param dto 预算项目信息
     * @author guiyuting 2019-03-11 15:54
     * @return
     */
    @RequestMapping(value = "/bgt/budget-item/bgtJournalBatchAssign")
    @ResponseBody
    public ResponseData bgtJournalBatchAssign(BgtBudgetItem dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.bgtJournalBatchAssign(requestContext, dto));
    }
}

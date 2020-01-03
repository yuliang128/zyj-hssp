package com.hand.hec.bgt.controllers;

import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBudgetItemHierarchy;
import com.hand.hec.bgt.service.IBgtBudgetItemHierarchyService;
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
 * 预算项目层次Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:21
 */
@Controller
public class BgtBudgetItemHierarchyController extends BaseController {

    @Autowired
    private IBgtBudgetItemHierarchyService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/budget-item-hierarchy/query")
    @ResponseBody
    public ResponseData query(BgtBudgetItemHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/budget-item-hierarchy/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetItemHierarchy> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/budget-item-hierarchy/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBudgetItemHierarchy> dto) {
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
    @RequestMapping(value = "/bgt/BGT2020/bgt_budget_item_hierarchy.screen")
    public ModelAndView bgtBudgetItemHierarchyView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2020/bgt_budget_item_hierarchy");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }


    @RequestMapping(value = "/bgt/budget-item-hierarchy/queryByParentItem")
    @ResponseBody
    public ResponseData queryByParentItem(BgtBudgetItemHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByParentItem(requestContext, dto, page, pageSize));
    }
}

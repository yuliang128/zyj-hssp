package com.hand.hec.bgt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtOrganizationService;

/**
 * <p>
 * 预算组织Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:56
 */
@Controller
public class BgtOrganizationController extends BaseController {

    @Autowired
    private IBgtOrganizationService service;

    @RequestMapping(value = "/bgt/organization/query")
    @ResponseBody
    public ResponseData query(BgtOrganization dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        Criteria criteria = new Criteria();
        criteria.where(new WhereField(BgtOrganization.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(BgtOrganization.FIELD_BGT_ORG_CODE, Comparison.LIKE));
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }


    /**
     * 获取所有预算组织
     *
     * @param dto 预算组织的查询条件
     * @return
     * @author ngls.luhui 2019-01-28 20:53
     */
    @RequestMapping(value = "/bgt/organization/queryBgtOrgAll")
    @ResponseBody
    public ResponseData queryBgtOrgAll(BgtOrganization dto, HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBgtOrgAll(dto.getBgtOrgCode(), dto.getDescription(), requestContext,page,pageSize));
    }

    @RequestMapping(value = "/bgt/organization/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtOrganization> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/organization/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtOrganization> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 预算组织的下拉框
     *
     * @param organization 预算组织
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author YHL 2019-02-15 14:56
     */
    @RequestMapping(value = "/bgt/organization/bgtOrgOption")
    public ResponseData bgtOrgOption(BgtOrganization organization, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        HttpSession session = request.getSession();
        Long magOrgId = (Long) session.getAttribute(FndManagingOrganization.FIELD_MAG_ORG_ID);
        List<BgtOrganization> bgtOrganizationList = service.bgtOrgOption(requestCtx, magOrgId);
        return new ResponseData(bgtOrganizationList);
    }

    /**
     * 获取预算项目默认预算组织
     *
     * @param request 获取管理组织id
     * @return 默认预算组织
     * @author xiuxian.wu 2019/2/18 16:05
     */
    @RequestMapping(value = "/bgt/BGT1040/bgt_budget_item_types.screen")
    public ModelAndView bgtBudgetItemTypes(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<BgtOrganization> data = service.queryDefaultBudgetOrganizationByMagOrgId(requestContext);
        ModelAndView view = new ModelAndView("bgt/BGT1040/bgt_budget_item_types");
        if (data != null && !data.isEmpty()) {
            view.addObject("bgtOrgId", data.get(0).getBgtOrgId());
            view.addObject("bgtOrgCodeName", data.get(0).getBgtOrgCodeName());
        }
        return view;
    }

    /**
     * 获取所有的预算组织
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return 返回所有的预算组织
     * @author xiuxian.wu 2019/2/18 16:06
     */
    @RequestMapping(value = "/bgt/organization/queryAll")
    @ResponseBody
    public ResponseData queryAll(BgtOrganization dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto));
    }

    /**
     * 预算组织的下拉框
     *
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author guiyuting 2019-02-15 14:56
     */
    @RequestMapping(value = "/bgt/organization/queryBgtOrganizationOptions")
    public ResponseData queryBgtOrganizationOptions(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryBgtOrganizationOptions(requestCtx));
    }

    /**
     * 预算余额查询页面预算组织下拉框
     *
     * @param request
     * @author YHL 2019-03-14 14:49
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/bgt/organization/getBgtOrgOptionForBgtQuery")
    public ResponseData getBgtOrgOptionForBgtQuery(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBgtOrgOptionForBgtQuery(requestCtx));
    }
}

package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.dto.SysStandardTimezone;
import com.hand.hap.sys.service.ISysStandardTimezoneService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 管理公司定义Controller
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 * @author xiuxian.wu 2019/01/21 13:45
 */

@Controller
public class FndCompanyController extends BaseController {

    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;
    @Autowired
    ISysStandardTimezoneService sysStandardTimezoneService;
    @Autowired
    private IFndCompanyService service;

    @RequestMapping(value = "/fnd-company/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCompany.FIELD_COMPANY_ID, Comparison.EQUAL),
                        new WhereField(FndCompany.FIELD_MAG_ORG_ID), new WhereField(FndCompany.FIELD_PARENT_COMPANY_ID),
                        new WhereField(FndCompany.FIELD_COMPANY_CODE, Comparison.LIKE),
                        new WhereField(FndCompany.FIELD_COMPANY_SHORT_NAME, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/fnd-company/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCompany> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/fnd-company/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCompany> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询符合条件的管理公司
     *
     * @param dto 各种查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回管理公司
     * @author xiuxian.wu 2019/01/25 15:13
     */
    @RequestMapping(value = "/fnd-company/queryFndCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFndCompany(FndCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFndCompany(requestContext, dto, page, pageSize));
    }

    /**
     * 修改页面中查询父级公司且不包含自己的公司列表下拉框
     *
     * @param dto 管理组织ID及自己公司ID信息
     * @return 不含自己的公司下拉框数据
     * @author xiuxian.wu 2019/1/25 15:13
     */
    @RequestMapping(value = "/fnd-company/queryFndCompanyBox", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFndCompanyBox(FndCompany dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFndCompanyBox(dto, requestContext));
    }

    /**
     * 新增或修改公司信息及公司所关联的默认核算主体及预算实体
     *
     * @param dto 公司信息
     * @param result
     * @param request
     * @return 返回新增或修改的公司信息
     * @author xiuxian.wu 2019/1/25 14:27
     */
    @RequestMapping(value = "/fnd-company/submitFndCompany")
    @ResponseBody
    public ResponseData submitFndCompany(@RequestBody List<FndCompany> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitFndCompany(requestCtx, dto));
    }

    /**
     * 管理公司获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author xiuxian.wu 2019/02/19 15:52
     */
    @RequestMapping(value = "/fnd/FND1020/fnd_companies.screen")
    public ModelAndView fndCompanies(FndManagingOrganization dto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("fnd/FND1020/fnd_companies");
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

    /**
     * 管理公司获取选定的管理组织及默认时区
     *
     * @param request
     * @param timezone
     * @param dto
     * @return 返回默认组织信息
     * @author xiuxian.wu 2019/02/19 15:52
     */
    @RequestMapping(value = "/fnd/FND1020/fnd_companies_create.screen")
    public ModelAndView fndCompaniesCreate(FndManagingOrganization dto, SysStandardTimezone timezone,
                    HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<SysStandardTimezone> zones = sysStandardTimezoneService
                        .queryDefaultTimezoneByCode(timezone.getStandardTimezoneCode(), requestCtx);
        ModelAndView view = new ModelAndView("fnd/FND1020/fnd_companies_create");
        List<FndManagingOrganization> organization = fndManagingOrganizationService.select(requestCtx, dto, 0, 0);
        if (zones != null && !zones.isEmpty()) {
            view.addObject("systemTimezoneIdDisplay", zones.get(0).getDescription());
            view.addObject("systemTimezoneId", zones.get(0).getStandardTimezoneId());
        }
        if (organization != null && !organization.isEmpty()) {
            view.addObject("magOrgId2", organization.get(0).getMagOrgId());
            view.addObject("magOrgCode", organization.get(0).getMagOrgCode());
            view.addObject("description", organization.get(0).getDescription());
            view.addObject("magOrgCodeName",
                            organization.get(0).getMagOrgCode() + "-" + organization.get(0).getDescription());
        }
        return view;
    }

    /**
     * 公司级维值定义页面，根据公司ID和管理组织ID 查询默认公司
     *
     * @param company
     * @return
     * @author guiyuting 2019-02-28 16:03
     */
    @RequestMapping(value = "/fnd-company/queryDefaultCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryDefaultCompany(FndCompany company, HttpServletRequest request,
                    HttpServletResponse response) {
        IRequest requestCtx = createRequestContext(request);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(service.queryDefaultCompany(requestCtx, company));
        return new ResponseData(list);
    }

    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 公司信息
     * @param assignMoId 预算表分配公司ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    @RequestMapping(value = "/fnd-company/queryForBgtStructureAssign", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryForBgtStructureAssign(Long magOrgId, Long assignMoId, HttpServletRequest request,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryForBgtStructureAssign(requestCtx, magOrgId, assignMoId));
    }


    /**
     * 查询当前预算项目未被分配的公司
     *
     * @param magOrgId 公司信息
     * @param assignMoId 预算项目分配管理组织ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    @RequestMapping(value = "/fnd-company/bgtItemAssignCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData bgtItemAssignCompany(Long magOrgId, Long assignMoId, HttpServletRequest request,
                    HttpServletResponse response) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.bgtItemAssignCompany(requestCtx, magOrgId, assignMoId));
    }

    /**
     * 查询部门未被分配的公司
     *
     * @param magOrgId 公司信息
     * @param unitTypeId 部门类型ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    @RequestMapping(value = "/fnd-company/unitTypeAssignCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData unitTypeAssignCompany(Long magOrgId, Long unitTypeId, HttpServletRequest request,
                    HttpServletResponse response) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.unitTypeAssignCompany(requestCtx, magOrgId, unitTypeId));
    }


    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 公司信息
     * @param assignMoId 预算表分配公司ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    @RequestMapping(value = "/fnd-company/bgtJournalBatchAssign", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData bgtJournalBatchAssign(Long magOrgId, Long assignMoId,
                    HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.bgtJournalBatchAssign(requestCtx, magOrgId, assignMoId, page, pageSize));
    }

    /**
     * 查询当前公司相关信息 (单据页面)
     *
     * @param request
     * @author LJK 2019-03-28 14:33
     * @return
     */
	@RequestMapping(value = "/fnd-company/queryCurrentCompany", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseData queryCurrentCompany(HttpServletRequest request) {
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(service.queryCurrentCompany(requestCtx));
	}
}

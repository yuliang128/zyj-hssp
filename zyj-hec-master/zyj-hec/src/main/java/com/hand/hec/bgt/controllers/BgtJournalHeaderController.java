package com.hand.hec.bgt.controllers;

import com.hand.hap.cache.impl.SysCodeCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.interceptor.SecurityTokenInterceptor;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.service.*;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账头Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:40
 */
@Controller
public class BgtJournalHeaderController extends BaseController {

    @Autowired
    private IBgtJournalHeaderService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @Autowired
    private IExpDocumentAuthorityService authorityService;

    @Autowired
    private SysCodeCache codeCache;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IBgtStructureService structureService;

    @Autowired
    private IBgtJournalTypeService journalTypeService;

    @Autowired
    private IFndDimensionService dimensionService;

    @Autowired
    private IBgtJournalObjectService objectService;

    @RequestMapping(value = "/bgt/journal-header/query")
    @ResponseBody
    public ResponseData query(BgtJournalHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/journal-header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalHeader> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        BgtJournalHeader header = dto.get(0);
        if (header == null) {
            return new ResponseData(true);
        }

        header = service.saveJournalHeader(requestCtx, header);
        List resList = new ArrayList();
        resList.add(header);

        return new ResponseData(resList);
    }

    @RequestMapping(value = "/bgt/journal-header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtJournalHeader> dto) {
        service.setRequest(createRequestContext(request));
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/bgt/journal-header/query-main")
    @ResponseBody
    public ResponseData queryMain(BgtJournalHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        if (dto.getBgtOrgId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        IRequest requestContext = createRequestContext(request);
        if (dto.getCreationDateScope() != null) {
            dto.setCreationDateScopeFrom(DateUtils.getDateScopeFrom(dto.getCreationDateScope()));
            dto.setCreationDateScopeTo(DateUtils.getDateScopeTo(dto.getCreationDateScope()));
        }
        return new ResponseData(service.queryMain(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/BGT5120/bgt_journal_main.screen")
    public ModelAndView bgtJournalMainView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("/bgt/BGT5120/bgt_journal_main");

        // 获取预算组织
        List<BgtOrganization> organizationList = organizationService.queryBgtOrgCurrentMagOrg(requestCtx);

        // 获取默认预算组织
        BgtOrganization organization = organizationService.getBgtOrgByMagOrgId(requestCtx, requestCtx.getMagOrgId());

        // 获取授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory("BGT_JOURNAL");
        authority.setEmployeeId(requestCtx.getEmployeeId());
        List<ExpEmployee> authEmployeeList = authorityService.queryDocAuthEmployee(requestCtx, authority);

        // 获取默认日期范围
        String defaultDateScope = "000-007";
        String defaultDateScopeName = null;
        CodeValue value = codeService.getCodeValue(requestCtx, "DATE_SCOPE", defaultDateScope);
        if (value != null) {
            defaultDateScopeName = value.getMeaning();
        }


        view.addObject("bgtOrg", organizationList);
        view.addObject("authEmployee", authEmployeeList);
        view.addObject("defaultBgtOrg", organization);

        if (defaultDateScopeName != null) {
            view.addObject("defaultDateScope", defaultDateScope);
            view.addObject("defaultDateScopeName", defaultDateScopeName);
        }

        return view;
    }


    @RequestMapping(value = "/bgt/BGT5120/bgt_journal_type_choice.screen")
    public ModelAndView bgtJournalTypeChoiceView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("/bgt/BGT5120/bgt_journal_type_choice");

        // 获取预算组织
        List<BgtOrganization> organizationList = organizationService.queryBgtOrgCurrentMagOrg(requestCtx);

        // 获取默认预算组织
        BgtOrganization organization = organizationService.getBgtOrgByMagOrgId(requestCtx, requestCtx.getMagOrgId());


        view.addObject("bgtOrg", organizationList);
        view.addObject("defaultBgtOrg", organization);

        return view;
    }

    @RequestMapping(value = {"/bgt/BGT5120/bgt_journal_maintain.screen", "/bgt/BGT5120/bgt_journal_maintain.screen.ftl",
            "/bgt/BGT5120/bgt_journal_view.screen", "/bgt/BGT5120/bgt_journal_view.screen.ftl"})
    public ModelAndView bgtJournalMaintainView(HttpServletRequest request, BgtJournalHeader dto) {
        if (dto.getJournalHeaderId() == null && request.getAttribute("journalHeaderId") != null) {
            dto.setJournalHeaderId(Long.parseLong(request.getAttribute("journalHeaderId").toString()));
        }

        IRequest requestCtx = createRequestContext(request);
        String servletPath = request.getServletPath().substring(1).replace(".screen", "").replace(".ftl", "");
        ModelAndView view = new ModelAndView(servletPath);

        BgtJournalHeader header = service.queryJournalHeader(requestCtx, dto);

        //
        // 设置Token，否则数据保存的时候提示无token
        // ------------------------------------------------------------------------------
        TokenUtils.generateAndSetToken(SecurityTokenInterceptor.LOCAL_SECURITY_KEY.get(), header);

        Map headerMap = BeanUtil.convert2Map(header);

        List<Map> headerDimList = structureService.getStructureDimInfo(requestCtx, header.getStructureId(),
                        BgtStructureLayout.POSITION_HEADER);

        //
        // 设置维度相关的信息，如果是公司、部门、岗位、人员，则配置指定维度信息
        // ------------------------------------------------------------------------------
        headerDimList.forEach(headerDim -> setDimInfo(requestCtx, headerDim));

        List<Map> lineDimList = structureService.getStructureDimInfo(requestCtx, header.getStructureId(),
                        BgtStructureLayout.POSITION_LINE);


        //
        // 设置维度相关的信息，如果是公司、部门、岗位、人员，则配置指定维度信息
        // ------------------------------------------------------------------------------
        lineDimList.forEach(lineDim -> setDimInfo(requestCtx, lineDim));

        List<Map> headerObjList = journalTypeService.getJournalTypeObjInfo(requestCtx, header.getBgtJournalTypeId(),
                        BgtJournalTypeRefObj.POSITION_HEADER);
        List<Map> lineObjList = journalTypeService.getJournalTypeObjInfo(requestCtx, header.getBgtJournalTypeId(),
                        BgtJournalTypeRefObj.POSITION_LINE);

        //
        // 设置头上的费用对象信息
        // ------------------------------------------------------------------------------
        if (dto.getJournalHeaderId() != null) {
            BgtJournalObject queryObj = new BgtJournalObject();
            queryObj.setJournalHeaderId(dto.getJournalHeaderId());
            List<BgtJournalObject> objList = objectService.select(requestCtx, queryObj, 0, 0);

            for (BgtJournalObject obj : objList) {
                if (obj.getJournalLineId() == null) {
                    headerMap.put("^#" + obj.getExpenseObjectTypeId(), obj.getExpenseObjectDesc());
                    headerMap.put("#" + obj.getExpenseObjectTypeId(), obj.getExpenseObjectId());
                }
            }
        }

        view.addObject("header", headerMap);
        view.addObject("headerDimList", headerDimList);
        view.addObject("lineDimList", lineDimList);
        view.addObject("headerObjList", headerObjList);
        view.addObject("lineObjList", lineObjList);

        return view;
    }

    /**
     * 设置维度的页面属性
     *
     * @param request
     * @param dim
     * @author mouse 2019-03-27 17:48
     * @return void
     */
    private void setDimInfo(IRequest request, Map dim) {

        if (new Long(21L).equals((Long) dim.get("dimension_sequence"))) {
            // 公司维度
            dim.put("lovCode", "FND_COMPANY_LOV");
            dim.put("prompt", "fnd_company.company_name");
            dim.put("title", "fnd_company.company_name");
            dim.put("fromName", "companyName");
            dim.put("fromId", "companyId");
            dim.put("display_field", "companyName");
        } else if (new Long(22L).equals((Long) dim.get("dimension_sequence"))) {
            // 部门维度
            dim.put("lovCode", "LOV_EXP_ORG_UNIT");
            dim.put("prompt", "exp_org_unit.unit_name");
            dim.put("title", "exp_org_unit.unit_name");
            dim.put("fromName", "unitName");
            dim.put("fromId", "unitId");
            dim.put("display_field", "unitName");
        } else if (new Long(23L).equals((Long) dim.get("dimension_sequence"))) {
            // 岗位维度
            dim.put("lovCode", "LOV_ORG_POSITION");
            dim.put("prompt", "exp_org_position.position_name");
            dim.put("title", "exp_org_position.position_name");
            dim.put("fromName", "positionName");
            dim.put("fromId", "positionId");
            dim.put("display_field", "positionName");
        } else if (new Long(24L).equals((Long) dim.get("dimension_sequence"))) {
            // 员工维度
            dim.put("lovCode", "LOV_EXP_EMPLOYEE_IN_ORG");
            dim.put("prompt", "exp_employee.name");
            dim.put("title", "exp_employee.name");
            dim.put("fromName", "employeeName");
            dim.put("fromId", "employeeId");
            dim.put("display_field", "employeeName");
        } else {
            dim.put("prompt", dim.get("dimension_name"));
            dim.put("title", dim.get("dimension_name"));
            dim.put("fromName", "dimensionValueName");
            dim.put("fromId", "dimensionValueId");

            FndDimension dimension = new FndDimension();
            dimension.setDimensionId((Long) dim.get("dimension_id"));
            dimension = dimensionService.selectByPrimaryKey(request, dimension);
            if (dimension != null) {
                if (dimension.getCompanyLevel() == null) {
                    dim.put("companyLevel", dimension.getSystemLevel());
                } else {
                    dim.put("companyLevel", dimension.getCompanyLevel());
                }
            }

            dim.put("lovCode", "LOV_BGT_DIMENSION_VALUE?dimensionId=" + dim.get("dimension_id") + "&amp;companyLevel="
                            + dim.get("companyLevel"));
        }
    }

    @RequestMapping(value = "/bgt/journal-header/queryForApprove")
    @ResponseBody
    public ResponseData queryForApprove(BgtJournalHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        if (dto.getBgtOrgId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryJournalForApprove(requestContext, dto, page, pageSize));
    }
}

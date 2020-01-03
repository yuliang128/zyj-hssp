package com.hand.hec.exp.controllers;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpOrgUnitLevel;
import com.hand.hec.exp.service.IExpOrgUnitLevelService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * ExpOrgUnitLevelController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */

@Controller
public class ExpOrgUnitLevelController extends BaseController {

    @Autowired
    private IExpOrgUnitLevelService service;

    @Autowired
    private IFndManagingOrganizationService managingOrganizationService;

    @RequestMapping(value = "/exp/org-unit-level/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgUnitLevel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpOrgUnitLevel.FIELD_ORG_UNIT_LEVEL_CODE, Comparison.LIKE),
                        new WhereField(ExpOrgUnitLevel.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(ExpOrgUnitLevel.FIELD_MAG_ORG_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    /**
     * 根据公司ID查询部门级别信息
     *
     * @param companyId 公司ID
     * @author guiyuting 2019-02-26 18:56
     * @return
     */
    @RequestMapping(value = "/exp/org-unit-level/queryByCompany")
    @ResponseBody
    public ResponseData queryByCompany(Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization map =
                        managingOrganizationService.defaultManageOrganizationQuery(requestContext, companyId);
        List<ExpOrgUnitLevel> list = new ArrayList<>();
        if (map != null) {
            Long magOrgId = map.getMagOrgId();
            if (magOrgId != null) {
                ExpOrgUnitLevel level = new ExpOrgUnitLevel();
                level.setMagOrgId(magOrgId);
                level.setEnabledFlag(BaseConstants.YES);
                Criteria criteria = new Criteria(level);
                criteria.where(new WhereField(ExpOrgUnitLevel.FIELD_MAG_ORG_ID, Comparison.EQUAL));
                criteria.where(new WhereField(ExpOrgUnitLevel.FIELD_ENABLED_FLAG,Comparison.EQUAL));
                list = service.selectOptions(requestContext, level, criteria);
            }
        }

        return new ResponseData(list);
    }

    @RequestMapping(value = "/exp/org-unit-level/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgUnitLevel> dto, BindingResult result,
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

    @RequestMapping(value = "/exp/org-unit-level/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpOrgUnitLevel> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"exp/EXP1120/exp_org_unit_level.screen", "exp/EXP1110/exp_mo_unit_groups.screen"})
    public ModelAndView expOrgUnitLevelView(HttpServletRequest request) {
        String servletPath = request.getServletPath().substring(1).replace(".screen", "");
        IRequest requestCtx = createRequestContext(request);
        HttpSession session = request.getSession(false);
        ModelAndView view = new ModelAndView(servletPath);
        Long companyId = (Long) session.getAttribute(FndCompany.FIELD_COMPANY_ID);
        FndManagingOrganization defaultMag =
                        managingOrganizationService.defaultManageOrganizationQuery(requestCtx, companyId);
        view.addObject("defaultMagList", defaultMag);
        return view;
    }
}

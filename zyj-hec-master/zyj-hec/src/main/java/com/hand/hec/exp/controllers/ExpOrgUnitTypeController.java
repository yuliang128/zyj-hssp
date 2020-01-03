package com.hand.hec.exp.controllers;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpOrgUnitType;
import com.hand.hec.exp.service.IExpOrgUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ExpOrgUnitTypeController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */

@Controller
public class ExpOrgUnitTypeController extends BaseController {

    @Autowired
    private IExpOrgUnitTypeService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    private IFndCompanyService companyService;

    @RequestMapping(value = "/exp/org-unit-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgUnitType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpOrgUnitType.FIELD_UNIT_TYPE_CODE, Comparison.LIKE),
                        new WhereField(ExpOrgUnitType.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(ExpOrgUnitType.FIELD_MAG_ORG_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/org-unit-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgUnitType> dto, BindingResult result,
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

    @RequestMapping(value = "/exp/org-unit-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpOrgUnitType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    @RequestMapping(value = "/exp/EXP1012/exp_org_unit_type.screen")
    public ModelAndView expOrgUnitTypeView(HttpServletRequest request, Long accountSetId, Long accountId) {
        IRequest requestContext = createRequestContext(request);
        List<FndManagingOrganization> fndManagingOrganizationList =
                        fndManagingOrganizationService.magOrgOption(requestContext);
        FndManagingOrganization defaultFndManagingOrganization = fndManagingOrganizationService
                        .defaultManageOrganizationQuery(requestContext, requestContext.getCompanyId());

        ModelAndView view = new ModelAndView("exp/EXP1012/exp_org_unit_type");
        view.addObject("fndManagingOrganizationList", fndManagingOrganizationList);
        view.addObject("defaultFndManagingOrganization", defaultFndManagingOrganization);
        return view;
    }

    @RequestMapping(value = "/exp/org-unit-type/queryByCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByCompany(Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndCompany company = new FndCompany();
        company.setCompanyId(companyId);
        company = companyService.selectByPrimaryKey(requestContext, company);
        List<ExpOrgUnitType> list = new ArrayList<>();
        if (company != null && company.getMagOrgId() != null) {
            ExpOrgUnitType unitType = new ExpOrgUnitType();
            unitType.setMagOrgId(company.getMagOrgId());
            unitType.setEnabledFlag(BaseConstants.YES);
            Criteria criteria = new Criteria(unitType);
            criteria.where(new WhereField(ExpOrgUnitType.FIELD_MAG_ORG_ID, Comparison.EQUAL),
                            new WhereField(ExpOrgUnitType.FIELD_ENABLED_FLAG, Comparison.EQUAL));
            list = service.selectOptions(requestContext, unitType, criteria);
        }
        return new ResponseData(list);
    }
}

package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.hand.hap.excel.annotation.ExcelExport;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 管理组织控制器
 * </p>
 *
 * @author MouseZhou 2019/01/04 18:53
 */
@Controller
public class FndManagingOrganizationController extends BaseController {

    @Autowired
    IGldCurrencyService gldCurrencyService;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "fnd/FND2010/fnd_managing_organizations.screen")
    public ModelAndView fndManagingOrganizationView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("fnd/FND2010/fnd_managing_organizations");

        IRequest requestCtx = createRequestContext(request);
        List<GldCurrency> currencyList = gldCurrencyService.queryEnabledCurrency(requestCtx);

        view.addObject("currencyList", currencyList);

        return view;
    }

    @RequestMapping(value = "/fnd/fnd-managing-organizations/query")
    public ResponseData query(FndManagingOrganization organization, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria();
        criteria.where(new WhereField(FndManagingOrganization.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(FndManagingOrganization.FIELD_MAG_ORG_CODE, Comparison.LIKE),
                new WhereField(FndManagingOrganization.FIELD_MAG_ORG_ID, Comparison.EQUAL));
        return new ResponseData(fndManagingOrganizationService.selectOptions(requestContext, organization, criteria, page,
                pageSize));
    }

    /**
     * 查询管理组织及其默认预算组织，账套的信息
     *
     * @param organization
     * @return
     * @author ngls.luhui 2019-02-15 12:17
     */
    @RequestMapping(value = "/fnd/fnd-managing-organizations/queryMain")
    @ExcelExport(table = FndManagingOrganization.class)
    @ResponseBody
    public ResponseData queryMain(FndManagingOrganization organization,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.queryMain(organization, requestContext,page,pageSize));
    }


    @RequestMapping(value = "/fnd/fnd-managing-organizations/submit")
    public ResponseData submit(@RequestBody List<FndManagingOrganization> organizations, BindingResult result,
                               HttpServletRequest request) {
        getValidator().validate(organizations, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.batchUpdate(requestCtx, organizations));
    }

    @RequestMapping(value = "/fnd/fnd-managing-organizations/remove")
    public ResponseData remove(@RequestBody List<FndManagingOrganization> organizations) {
        fndManagingOrganizationService.batchDelete(organizations);
        return new ResponseData();
    }

    /**
     * <p>
     * 管理组织的下拉框
     * </p>
     */
    @RequestMapping(value = "/fnd-managing-organization/magOrgOption")
    public ResponseData magOrgOption(FndManagingOrganization organization, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<FndManagingOrganization> magOrgs = fndManagingOrganizationService.magOrgOption(requestCtx);
        return new ResponseData(magOrgs);
    }

    @RequestMapping(value = "/fnd-managing-organization/queryByBgtOrg")
    public ResponseData queryByBgtOrg(Long bgtOrgId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.queryByBgtOrgId(requestCtx,bgtOrgId));
    }

    @RequestMapping(value="/fnd/mag-org/queryMagOrg")
    public ResponseData queryMagOrg(HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.queryMagOrg(requestCtx));
    }
}

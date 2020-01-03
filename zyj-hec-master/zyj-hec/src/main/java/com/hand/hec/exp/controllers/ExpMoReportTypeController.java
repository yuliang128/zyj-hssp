package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.impl.CodeServiceImpl;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.service.IExpMoReportTypeService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpMoReportTypeController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:41
 */

@Controller
@RequestMapping(value = "")
public class ExpMoReportTypeController extends BaseController {

    @Autowired
    private IExpMoReportTypeService service;

    @Autowired
    private IFndManagingOrganizationService managingOrganizationService;

    @Autowired
    private CodeServiceImpl codeService;

    @RequestMapping(value = "/exp/mo-report-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReportType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoReportType.FIELD_MAG_ORG_ID, Comparison.EQUAL),
                        new WhereField(ExpMoReportType.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(ExpMoReportType.FIELD_MO_EXP_REPORT_TYPE_CODE, Comparison.LIKE),
                        new WhereField(ExpMoReportType.FIELD_PAYMENT_METHOD_NAME));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-report-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReportType> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-report-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReportType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/EXP4060/exp_mo_report_types.screen")
    public ModelAndView expMoReportTypesView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization managingOrganizations = managingOrganizationService.queryDefaultMagOrg(requestContext);
        List<CodeValue> codeValues = codeService.selectCodeValuesByCodeName(requestContext, "DOCUMENT_PAGE_TYPE");
        for (CodeValue cv : codeValues) {
            if (!cv.getValue().equals("STANDARD")) {
                codeValues.remove(cv);
            }
        }
        ModelAndView view = new ModelAndView("exp/EXP4060/exp_mo_report_types");
        view.addObject("EXP4060_defaultMagList", managingOrganizations);
        view.addObject("page_types", codeValues);
        return view;
    }

    @RequestMapping(value = "/exp/mo-report-type/queryExpMoReportTypeList")
    @ResponseBody
    public ResponseData queryExpMoReportTypeList(@RequestParam Long employeeId,
                    @RequestParam(defaultValue = "") Long companyId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if ("".equals(companyId)) {
            companyId = requestContext.getCompanyId();
        }
        return new ResponseData(
                        service.queryExpMoReportTypeList(requestContext, employeeId, companyId, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-report-type/queryExpReportTypeByCom")
    @ResponseBody
    public ResponseData queryExpReportTypeByCom(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpReportTypeByCom(requestContext, requestContext.getCompanyId()));
    }

}

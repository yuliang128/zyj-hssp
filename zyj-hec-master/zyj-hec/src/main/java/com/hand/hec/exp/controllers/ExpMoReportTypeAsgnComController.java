package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.dto.ExpMoReportTypeAsgnCom;
import com.hand.hec.exp.service.IExpMoReportTypeAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpMoReportTypeAsgnComController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:41
 */

@Controller
@RequestMapping(value = "/exp/mo-report-type-asgn-com")
public class ExpMoReportTypeAsgnComController extends BaseController {

    @Autowired
    private IExpMoReportTypeAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReportTypeAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoReportTypeAsgnCom.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL),
                        new WhereField(ExpMoReportTypeAsgnCom.FIELD_COMPANY_CODE),
                        new WhereField(ExpMoReportTypeAsgnCom.FIELD_COMPANY_SHORT_NAME));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReportTypeAsgnCom> dto, BindingResult result,
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReportTypeAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryCompanyInfo")
    @ResponseBody
    public ResponseData queryCompanyInfo(HttpServletRequest request, ExpMoReportType dto) {
        return new ResponseData(service.queryCompanyInfo(dto));
    }

    @RequestMapping(value = "/batchAssignComOneToMany")
    @ResponseBody
    public ResponseData batchAssignComOneToMany(HttpServletRequest request, @RequestBody List<FndCompany> companies) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAsgnCompanyOneToMany(requestCtx, companies));
    }

    @RequestMapping(value = "/batchAssignComManyToMany")
    @ResponseBody
    public ResponseData batchAssignComManyToMany(HttpServletRequest request,
                    @RequestBody List<ExpMoReportType> reportTypes) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAsgnCompanyManyToMany(requestCtx, reportTypes));
    }
}

package com.hand.hec.expm.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import com.hand.hec.expm.dto.ExpReportPageElement;
import com.hand.hec.expm.service.IExpReportPageElementService;


/**
 * <p>
 * 费用报销单页面元素Controller
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 * @author xiuxian.wu 2019/01/19 25:25
 */

@Controller
@RequestMapping(value = "/exp/report-page-element")
public class ExpReportPageElementController extends BaseController {

    @Autowired
    private IExpReportPageElementService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpReportPageElement dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpReportPageElement.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(ExpReportPageElement.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpReportPageElement.FIELD_REPORT_PAGE_ELEMENT_CODE, Comparison.LIKE)
                , new WhereField(ExpReportPageElement.FIELD_REPORT_PAGE_ELEMENT_ID, Comparison.EQUAL)
                , new WhereField(ExpReportPageElement.FIELD_SERVICE_ID, Comparison.EQUAL)
                , new WhereField(ExpReportPageElement.FIELD_READONLY_SERVICE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportPageElement> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpReportPageElement> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
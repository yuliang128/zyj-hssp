package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpRequisitionLine;
import com.hand.hec.exp.service.IExpRequisitionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/exp/requisition-line")
public class ExpRequisitionLineController extends BaseController {

    @Autowired
    private IExpRequisitionLineService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpRequisitionLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/expReqLineQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData expReqLineQuery(@RequestParam("expRequisitionHeaderId") long expRequisitionHeaderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.expReqLineQuery(requestContext, expRequisitionHeaderId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpRequisitionLine> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpRequisitionLine> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /*
     *费用申请财务关闭 行明细查询
     *
     * @author weikun.wang 2019/3/11
     * @param headId 头id
     */
    @RequestMapping(value = "/queryDetailLine", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryDetailLine(Long headId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryDetailLine(requestContext, headId));
    }

    @RequestMapping(value = "/expReportFromReqQuery")
    @ResponseBody
    public ResponseData expReportFromReqQuery(HttpServletRequest request, @RequestParam(required = false) String reportPageElementCode, @RequestParam Long moExpReportTypeId, @RequestParam(required = false) Long expRequisitionHeaderId, @RequestParam(required = false) String uncompletelyReleasedFlag, @RequestParam String paymentCurrencyCode, @RequestParam(required = false) String monopolizeFlag) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.expReportFromReqQuery(requestContext, reportPageElementCode, moExpReportTypeId, expRequisitionHeaderId, uncompletelyReleasedFlag, paymentCurrencyCode, monopolizeFlag));
    }
}
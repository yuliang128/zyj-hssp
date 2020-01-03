package com.hand.hec.acp.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.acp.dto.AcpRequisitionDtl;
import com.hand.hec.acp.service.IAcpRequisitionDtlService;

/**
 * <p>
 * 付款申请单明细 控制器
 * </p>
 * 
 * @author guiyuting 2019/04/30 11:15
 */

@Controller
@RequestMapping(value = "/acp/requisition-dtl")
public class AcpRequisitionDtlController extends BaseController {

    @Autowired
    private IAcpRequisitionDtlService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpRequisitionDtl dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpRequisitionDtl> dto, BindingResult result,
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpRequisitionDtl> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryByLineId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByLine(AcpRequisitionDtl acpRequisitionDtl, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByLineId(requestContext, acpRequisitionDtl));
    }

    @RequestMapping(value = "/queryFromReport", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFromReport(AcpRequisitionDtl dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFromReport(requestContext, dto, page, pageSize));
    }
}

package com.hand.hec.pur.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.pur.dto.PurVenderTaxRefAe;
import com.hand.hec.pur.service.IPurVenderTaxRefAeService;

/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/pur/vender-tax-ref-ae")
public class PurVenderTaxRefAeController extends BaseController {

    @Autowired
    private IPurVenderTaxRefAeService service;

    @RequestMapping(value = "/query/{taxId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(@PathVariable Long taxId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByTaxId(requestContext, taxId, page, pageSize));
    }

    @RequestMapping(value = "/query/{venderId}/{taxId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAccEntityByVenderIdAndTaxId(@PathVariable Long taxId, @PathVariable Long venderId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccEntityByVenderIdAndTaxId(requestContext, venderId, taxId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderTaxRefAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/batch-assign/submit")
    @ResponseBody
    public ResponseData batchAssign(@RequestBody List<PurVenderTaxRefAe> dto, BindingResult result, HttpServletRequest request) throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssign(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderTaxRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
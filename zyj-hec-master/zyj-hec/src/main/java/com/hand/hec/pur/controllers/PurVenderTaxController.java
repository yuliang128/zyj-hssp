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
import com.hand.hec.pur.dto.PurVenderTax;
import com.hand.hec.pur.service.IPurVenderTaxService;

/**
 * <p>
 * 供应商税务信息控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/25 17:23
 */

@Controller
@RequestMapping(value = "/pur")
public class PurVenderTaxController extends BaseController {

    @Autowired
    private IPurVenderTaxService service;

    @RequestMapping(value = "/vender-tax/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(PurVenderTax dto,Long venderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setVenderId(venderId);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/vender-tax/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderTax> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderTax> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/ae-vender-tax/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAeTax(Long accEntityId, Long venderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByVenderIdAndAccEntityId(requestContext, accEntityId, venderId, page, pageSize));
    }

    @RequestMapping(value = "/ae-vender-tax/submit")
    @ResponseBody
    public ResponseData updateAeTax(@RequestBody List<PurVenderTax> dto, BindingResult result, HttpServletRequest request) throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateTax(requestCtx, dto));
    }
}
package com.hand.hec.pur.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.pur.dto.PurVenderAccountRefAe;
import com.hand.hec.pur.exception.PurVenderAccountRefAeException;
import com.hand.hec.pur.service.IPurVenderAccountRefAeService;

/**
 * <p>
 * 供应商银行账户核算主体分配控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */

@Controller
@RequestMapping(value = "/pur/vender-account-ref-ae")
public class PurVenderAccountRefAeController extends BaseController {

    @Autowired
    private IPurVenderAccountRefAeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long accountId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByAccountId(requestContext, accountId, page, pageSize));
    }

    @RequestMapping(value = "/query/{accountId}/{venderId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAccEntityByVenderIdAndAccountId(@PathVariable Long accountId, @PathVariable Long venderId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccEntityByVenderIdAndAccountId(requestContext, accountId, venderId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderAccountRefAe> dto, BindingResult result, HttpServletRequest request) throws PurVenderAccountRefAeException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.assignVendorAccountAccEntity(requestCtx, dto));
    }

    @RequestMapping(value = "/batch-assign/submit")
    @ResponseBody
    public ResponseData batchAssignUpdate(@RequestBody List<PurVenderAccountRefAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        for (PurVenderAccountRefAe record : dto) {
            service.insertSelective(requestCtx, record);
        }
        return new ResponseData(dto);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderAccountRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
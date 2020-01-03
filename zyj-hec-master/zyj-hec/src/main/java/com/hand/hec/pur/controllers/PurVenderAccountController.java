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
import com.hand.hec.pur.dto.PurVenderAccount;
import com.hand.hec.pur.service.IPurVenderAccountService;

/**
 * <p>
 * 供应商银行账户控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:21
 */

@Controller
@RequestMapping(value = "/pur")
public class PurVenderAccountController extends BaseController {

    @Autowired
    private IPurVenderAccountService service;

    @RequestMapping(value = "/vender-account/query/{venderId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(@PathVariable Long venderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByVenderId(requestContext, venderId, page, pageSize));
    }

    @RequestMapping(value = "/vender-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderAccount> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/vender-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/ae-vender-account/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAeAccount(Long venderId, Long accEntityId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByVenderIdAndAccEntityId(requestContext, venderId, accEntityId, page, pageSize));
    }

    @RequestMapping(value = "/ae-vender-account/submit")
    @ResponseBody
    public ResponseData updateAeAccount(@RequestBody List<PurVenderAccount> dto, BindingResult result, HttpServletRequest request) throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.updateAeAccount(requestCtx, dto));
    }

}
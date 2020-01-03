package com.hand.hec.fnd.controllers;

import com.hand.hec.fnd.exception.OrdSystemCustomerException;
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
import com.hand.hec.fnd.dto.OrdCustomerAccount;
import com.hand.hec.fnd.service.IOrdCustomerAccountService;

/**
 * <p>
 * OrdCustomerAccountController
 * </p>
 * 
 * @author guiyuting 2019/02/20 15:39
 */

@Controller
@RequestMapping(value = "/ord/customer-account")
public class OrdCustomerAccountController extends BaseController{

    @Autowired
    private IOrdCustomerAccountService service;
    
    /**
     * 根据核算主体查询账户信息
     *
     * @param dto
     * @author guiyuting 2019-02-22 14:07
     * @return ResponseData
     */
    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(OrdCustomerAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByAccEntity(requestContext,dto,page,pageSize));
    }

    /**
     * 系统级客户查询账户信息
     *
     * @param dto
     * @author guiyuting 2019-02-22 14:07
     * @return ResponseData
     */
    @RequestMapping(value = "/queryBySysCustomer",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryBySysCustomer(OrdCustomerAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBySysCustomer(requestContext,dto,page,pageSize));
    }

    /**
     * 客户主文件定义银行账户保存
     *
     * @param dto
     * @param result
     * @param request
     * @author guiyuting 2019-02-22 13:55
     * @return ResponseData
     */
    @RequestMapping(value = "/submitByCustomer")
    @ResponseBody
    public ResponseData update(@RequestBody List<OrdCustomerAccount> dto, BindingResult result, HttpServletRequest request) throws OrdSystemCustomerException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitByCustomer(requestCtx, dto));
    }

    /**
     * 系统级客户主文件定义银行账户保存
     *
     * @param dto
     * @param result
     * @param request
     * @author guiyuting 2019-02-22 13:55
     * @return ResponseData
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<OrdCustomerAccount> dto, BindingResult result, HttpServletRequest request) throws OrdSystemCustomerException {
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OrdCustomerAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
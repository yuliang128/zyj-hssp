package com.hand.hec.csh.controllers;

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
import com.hand.hec.csh.dto.CshPaymentTrxRuleBiz;
import com.hand.hec.csh.service.ICshPaymentTrxRuleBizService;

/**
 *
 * 付款事务生成规则定义-分配业务范围控制器
 *
 * @author luhui 2019-03-04
 */

@Controller
@RequestMapping(value = "/csh/payment-trx-rule-biz")
public class CshPaymentTrxRuleBizController extends BaseController{

    @Autowired
    private ICshPaymentTrxRuleBizService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentTrxRuleBiz dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentTrxRuleBiz> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentTrxRuleBiz> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
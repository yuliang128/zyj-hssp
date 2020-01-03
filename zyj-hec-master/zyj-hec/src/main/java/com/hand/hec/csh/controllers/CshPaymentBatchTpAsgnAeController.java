package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentBatchTpAsgnAe;
import com.hand.hec.csh.service.ICshPaymentBatchTpAsgnAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 付款批类型定义控制器
 *
 * @author zhaohui 2019/03/21 14:35
 */
@Controller
@RequestMapping(value = "/csh/payment-batch-tp-asgn-ae")
public class CshPaymentBatchTpAsgnAeController extends BaseController{

    @Autowired
    private ICshPaymentBatchTpAsgnAeService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentBatchTpAsgnAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(CshPaymentBatchTpAsgnAe.FIELD_TYPE_ID);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentBatchTpAsgnAe> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentBatchTpAsgnAe> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/query-acc-entity")
    @ResponseBody
    public ResponseData queryAccEntity(CshPaymentBatchTpAsgnAe dto,Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.queryCanAsgEntity(iRequest,dto.getTypeId(),magOrgId,page,pageSize));
    }
}
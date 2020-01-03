package com.hand.hec.csh.controllers;

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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoPayUsdRefFlowIt;
import com.hand.hec.csh.service.ICshMoPayUsdRefFlowItService;

/**
 * <p>
 * 付款用途与现金流量项联系Controller
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */

@Controller
@RequestMapping(value = "/csh/mo-pay-usd-ref-flow-it")
public class CshMoPayUsdRefFlowItController extends BaseController{

    @Autowired
    private ICshMoPayUsdRefFlowItService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoPayUsdRefFlowIt dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshMoPayUsdRefFlowIt.FIELD_PAYMENT_USEDE_ID, Comparison.EQUAL),
                new WhereField(CshMoPayUsdRefFlowIt.FIELD_PAYMENT_USEDE_CODE, Comparison.LIKE),
                new WhereField(CshMoPayUsdRefFlowIt.FIELD_CASH_FLOW_ITEM_DESC, Comparison.LIKE),
                new WhereField(CshMoPayUsdRefFlowIt.FIELD_SET_OF_BOOKS_NAME, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));

    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoPayUsdRefFlowIt> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoPayUsdRefFlowIt> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryIncludeSetOfBooks",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryIncludeSetOfBooks(CshMoPayUsdRefFlowIt dto,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryIncludeSetOfBooks(requestContext,dto,page,pageSize));
    }
}
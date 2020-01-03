package com.hand.hec.csh.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.apache.ibatis.annotations.Param;
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
import com.hand.hec.csh.dto.CshMoPayUsdAsgnCom;
import com.hand.hec.csh.service.ICshMoPayUsdAsgnComService;

/**
 * <p>
 * 付款用途分配公司Controller
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */

@Controller
@RequestMapping(value = "/csh/mo-pay-usd-asgn-com")
public class CshMoPayUsdAsgnComController extends BaseController{

    @Autowired
    private ICshMoPayUsdAsgnComService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoPayUsdAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.query(requestContext,dto,page,pageSize));

    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoPayUsdAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoPayUsdAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryLov",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryLov(Long magOrgId, Long paymentUsedeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryLov(requestContext,magOrgId,paymentUsedeId,page,pageSize));
    }

}
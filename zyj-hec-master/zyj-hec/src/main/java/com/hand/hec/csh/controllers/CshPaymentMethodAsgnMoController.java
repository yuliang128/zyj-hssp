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
import com.hand.hec.csh.dto.CshPaymentMethodAsgnMo;
import com.hand.hec.csh.service.ICshPaymentMethodAsgnMoService;

/**
 * <p>
 * 付款方式分配管理组织Controller
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 11:15
 */

@Controller
@RequestMapping(value = "/csh/payment-method-asgn-mo")
public class CshPaymentMethodAsgnMoController extends BaseController{

    @Autowired
    private ICshPaymentMethodAsgnMoService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentMethodAsgnMo dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.query(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentMethodAsgnMo> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentMethodAsgnMo> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.csh.dto.CshPaymentMethod;
import com.hand.hec.csh.service.ICshPaymentMethodService;

/**
 * <p>
 * 付款方式Controller
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 11:15
 */

@Controller
@RequestMapping(value = "/csh/payment-method")
public class CshPaymentMethodController extends BaseController {

    @Autowired
    private ICshPaymentMethodService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentMethod dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        if (dto.getDescription() != null && !"undefined".equals(dto.getDescription())
                        && !"".equals(dto.getDescription())) {
            criteria.where(new WhereField(CshPaymentMethod.FIELD_DESCRIPTION, Comparison.LIKE));
        }
        if (dto.getPaymentMethodCode() != null && !"undefined".equals(dto.getPaymentMethodCode())
                        && !"".equals(dto.getPaymentMethodCode())) {
            criteria.where(new WhereField(CshPaymentMethod.FIELD_PAYMENT_METHOD_CODE, Comparison.LIKE));
        }
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentMethod> dto, BindingResult result,
                    HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshPaymentMethod> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * @Description 根据公司（管理组织）获取对应付款方式
     *
     * @Author Tagin
     * @Date 2019-03-04 20:25
     * @param request
     * @Return com.hand.hap.system.dto.ResponseData
     * @Version 1.0
     **/
    @RequestMapping(value = "/queryPaymentMethod")
    @ResponseBody
    public ResponseData queryPaymentMethod(HttpServletRequest request,Long magOrgId,@RequestParam(required = false) Long companyId) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.queryPaymentMethod(iRequest,magOrgId,companyId));
    }

}

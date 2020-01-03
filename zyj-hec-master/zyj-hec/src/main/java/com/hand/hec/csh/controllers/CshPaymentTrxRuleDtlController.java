package com.hand.hec.csh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentTrxRuleDtl;
import com.hand.hec.csh.exception.CshPaymentTrxRuleDtlException;
import com.hand.hec.csh.service.ICshPaymentTrxRuleDtlService;

/**
 * 管理组织级-付款事务生成规则定义-分组规则控制器
 *
 * @author luhui 2019-03-04
 */

@Controller
@RequestMapping(value = "/csh/payment-trx-rule-dtl")
public class CshPaymentTrxRuleDtlController extends BaseController {

    @Autowired
    private ICshPaymentTrxRuleDtlService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentTrxRuleDtl dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentTrxRuleDtl.FIELD_RULE_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }


    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentTrxRuleDtl> dto, BindingResult result, HttpServletRequest request) throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshPaymentTrxRuleDtl> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
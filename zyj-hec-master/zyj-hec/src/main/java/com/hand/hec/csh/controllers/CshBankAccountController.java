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

import com.hand.hec.csh.dto.CshBankAccount;
import com.hand.hec.csh.service.ICshBankAccountService;
import com.hand.hec.csh.service.ICshTransactionHeaderService;

/**
 * <p>
 * 公司付款账户分配账户Controller
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */

@Controller
@RequestMapping(value = "/csh/bank-account")
public class CshBankAccountController extends BaseController {

    @Autowired
    private ICshBankAccountService service;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshBankAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshBankAccount.FIELD_BANK_ACCOUNT_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_BANK_BRANCH_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_ACCOUNT_TYPE, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_COUNTRY_CODE, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_TIMEZONE_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_RESPONSIBILITY_CENTER_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_CUSTOMER_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_VENDER_ID, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_RECEIPT_FLAG, Comparison.EQUAL), new WhereField(CshBankAccount.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshBankAccount> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshBankAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询指定核算主体的银行账户的相关信息(汇率类型,汇率等)
     *
     * @param dto
     * @return List<CshBankAccount>
     * @author ngls.luhui 2019-03-05 17:38
     */
    @RequestMapping(value = "/queryBankAccounts", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryBankAccounts(CshBankAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        //根据需要可以加分页
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBankAccounts(dto.getAccEntityId(), dto.getBankAccountName(), dto.getBankAccountNum(), dto.getCurrencyCode(), requestContext));
    }


    @RequestMapping(value = "/queryBankAccountByAe")
    @ResponseBody
    public ResponseData queryBankAccountByAe(HttpServletRequest request, Long accEntityId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBankAccountByAe(requestContext, accEntityId));
    }
}
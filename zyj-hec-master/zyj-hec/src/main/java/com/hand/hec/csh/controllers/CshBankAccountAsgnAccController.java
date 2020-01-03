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
import com.hand.hec.csh.dto.CshBankAccountAsgnAcc;
import com.hand.hec.csh.service.ICshBankAccountAsgnAccService;

/**
 * <p>
 * 公司付款账户分配核算科目Controller
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Controller
@RequestMapping(value = "/csh/bank-account-asgn-acc")
public class CshBankAccountAsgnAccController extends BaseController {

    @Autowired
    private ICshBankAccountAsgnAccService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshBankAccountAsgnAcc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshBankAccountAsgnAcc.FIELD_ASSIGN_ACC_ID,Comparison.EQUAL)
        ,new WhereField(CshBankAccountAsgnAcc.FIELD_BANK_ACCOUNT_ID,Comparison.EQUAL)
        ,new WhereField(CshBankAccountAsgnAcc.FIELD_SET_OF_BOOKS_ID,Comparison.EQUAL)
        ,new WhereField(CshBankAccountAsgnAcc.FIELD_CASH_ACCOUNT_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto,criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshBankAccountAsgnAcc> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshBankAccountAsgnAcc> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
package com.hand.hec.csh.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshWriteOffAccount;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.csh.service.ICshWriteOffAccountService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;

/**
 * <p>
 * 核销凭证表Controller
 * </p>
 *
 * @author Tagin 2019/01/22 10:00
 */

@Controller
public class CshWriteOffAccountController extends BaseController {

    @Autowired
    private ICshWriteOffAccountService service;

    @RequestMapping(value = "/csh/write-off-account/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshWriteOffAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshWriteOffAccount.FIELD_WRITE_OFF_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/csh/write-off-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshWriteOffAccount> dto, BindingResult result,
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

    @RequestMapping(value = "/csh/write-off-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshWriteOffAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value="/csh/write-off-account/cshPaymentFinanceInfo")
    @ResponseBody
    public ResponseData cshPaymentFinanceInfo(HttpServletRequest request,@RequestParam Long transactionHeaderId,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.cshPaymentFinanceInfo(requestCtx,transactionHeaderId,page,pageSize));
    }


	@RequestMapping(value="/csh/write-off-account/cshPrepaymentFinanceInfo")
	@ResponseBody
	public ResponseData cshPrepaymentFinanceInfo(HttpServletRequest request,@RequestParam Long writeOffId,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize){
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(service.cshPrepaymentFinanceInfo(requestCtx,writeOffId,page,pageSize));
	}
}

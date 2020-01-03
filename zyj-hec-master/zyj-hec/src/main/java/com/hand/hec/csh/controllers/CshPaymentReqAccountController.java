package com.hand.hec.csh.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.annotation.IgnoreToken;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.exception.CshPaymentReqAccountException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentReqAccount;
import com.hand.hec.csh.service.ICshPaymentReqAccountService;
import org.springframework.web.servlet.ModelAndView;


/**
 * <p>
 * 借款单凭证表Controller
 * </p>
 * 
 * @author Tagin 2019/02/21 22:32
 */
@Controller
@IgnoreToken
public class CshPaymentReqAccountController extends BaseController {

    @Autowired
    private ICshPaymentReqAccountService service;

    @RequestMapping(value = "/csh/payment-req-account/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentReqAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/payment-req-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentReqAccount> dto, BindingResult result,
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

    @RequestMapping(value = "/csh/payment-req-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshPaymentReqAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/csh/payment-req-account/queryByHeaderId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByHeaderId(Long paymentRequisitionHeaderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByHeaderId(requestContext, paymentRequisitionHeaderId, page, pageSize));
    }

    @RequestMapping(value = "/csh/payment-req-account/queryByCompanyId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByCompanyId(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByCompanyId(requestContext));
    }

    @RequestMapping(value = "/csh/payment-req-account/queryCurrentTime", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCurrentTime(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCurrentTime(requestContext));
    }

    @RequestMapping(value = "csh/CSH5070/csh_payment_req_audit_journal_adjust.screen")
    public ModelAndView jumpCshMoPaymentReqTypes(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH5070/csh_payment_req_audit_journal_adjust");
        IRequest requestContext = createRequestContext(request);
        List<Map> dftAccEntityList = service.queryByCompanyId(requestContext);

        view.addObject("dftAccEntity", dftAccEntityList);
        return view;
    }

    @RequestMapping(value = "/csh/payment-req-account/createAccount", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData createAccount(HttpServletRequest request, @RequestBody List<CshPaymentRequisitionHd> list) {
        IRequest requestContext = createRequestContext(request);
        String journalDate = list.get(0).getInnerMap().get("auditAccountDate").toString();
        String periodName = list.get(0).getInnerMap().get("auditPeriodName").toString();
        try{
            return new ResponseData(service.createAccount(requestContext,list,journalDate,periodName));
        }catch (CshPaymentReqAccountException e){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @RequestMapping(value = "/csh/payment-req-account/auditPayReq", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData auditPayReq(HttpServletRequest request, @RequestBody List<CshPaymentRequisitionHd> list) {
        IRequest requestContext = createRequestContext(request);
        try{
            return new ResponseData(service.auditPayReq(requestContext,list));

        }catch (CshPaymentReqAccountException e){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @RequestMapping(value = "/csh/payment-req-account/rejectPayReq", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData rejectPayReq(HttpServletRequest request, @RequestBody List<CshPaymentRequisitionHd> list, String description) {
        IRequest requestContext = createRequestContext(request);
        try{
            return new ResponseData(service.rejectPayReq(requestContext,list,description));
        }catch (CshPaymentReqAccountException e){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @RequestMapping(value = "/csh/payment-req-account/queryPeriodName", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPeriodName(HttpServletRequest request, @RequestParam(value = "journalDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date journalDate) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPeriodName(requestContext,journalDate,"O"));
    }

    @RequestMapping(value = "/csh/payment-req-account/updateAccount", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData updateAccount(HttpServletRequest request, @RequestBody List<CshPaymentReqAccount> list) {
        IRequest requestContext = createRequestContext(request);
        try {
            return new ResponseData(service.updateAccount(requestContext,list));
        }catch (CshPaymentReqAccountException e){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }


}

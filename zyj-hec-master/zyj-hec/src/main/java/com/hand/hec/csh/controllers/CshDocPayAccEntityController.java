package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshDocPayAccEntity;
import com.hand.hec.csh.dto.CshDocPayment;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.csh.service.ICshBankAccountService;
import com.hand.hec.csh.service.ICshDocPayAccEntityService;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 付款信息表Controller
 *
 * @author Tagin 2019-02-28
 */

@Controller
public class CshDocPayAccEntityController extends BaseController {

    @Autowired
    private ICshDocPayAccEntityService service;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @Autowired
    private ICshBankAccountService cshBankAccountService;


    @Autowired
    private IFndCompanyRefAccEntityService fndCompanyRefAccEntityService;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;


    @RequestMapping(value = "/csh/doc-pay-acc-entity/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshDocPayAccEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/doc-pay-acc-entity/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshDocPayAccEntity> dto, BindingResult result,
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

    @RequestMapping(value = "/csh/doc-pay-acc-entity/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshDocPayAccEntity> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 单据支付
     * 
     * @Author Tagin
     * @Date 2019-03-14 14:37
     * @param request 请求
     * @Return com.hand.hap.system.dto.ResponseData
     * @Version 1.0
     **/
    @RequestMapping(value = "/csh/doc-pay-acc-entity/payment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData payment(HttpServletRequest request, @RequestBody List<CshDocPayment> paymentList)
                    throws CshWriteOffException, CshTransactionException {
        IRequest iRequest = createRequestContext(request);
        service.executePayment(iRequest, paymentList.get(0), paymentList.get(0).getData());
        return new ResponseData(paymentList);
    }

    /**
     * 退回
     *
     * @param request
     * @author guiyuting 2019-06-17 10:24
     * @return 
     */
    @RequestMapping(value = "/csh/doc-pay-acc-entity/payBack", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData payBack(HttpServletRequest request, @RequestBody List<CshDocPayAccEntity> dto)
            throws CshWriteOffException, CshTransactionException {
        IRequest iRequest = createRequestContext(request);
        service.payBack(iRequest, dto);
        return new ResponseData(dto);
    }

    /**
     * 单据支付-支付数据查询
     * 
     * @Author Tagin
     * @Date 2019-03-05 19:35
     * @param request 请求
     * @param accEntityId 核算主体ID
     * @param docCatetory 单据类别
     * @param docNumber 单据编号
     * @param payeeCategory 收款方对象
     * @param payeeId 收款方
     * @param requisitionDateFrom 申请日期从
     * @param requisitionDateTo 申请日期到
     * @param paymentMethodId 付款方式ID
     * @param paymentUsedeId 付款用途ID
     * @param schedulePaymentDateFrom 计划付款日期从
     * @param schedulePaymentDateTo 计划付款日期到
     * @param currencyCode 币种
     * @param page 页数
     * @param pageSize 每页大小
     * @Return java.util.List<com.hand.hec.csh.dto.CshDocPayAccEntity>
     * @Version 1.0
     **/
    @RequestMapping(value = "/csh/doc-pay-acc-entity/queryPayment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPayment(HttpServletRequest request, Long accEntityId, String docCategory, String docNumber,
                    String payeeCategory, Long payeeId, Date requisitionDateFrom, Date requisitionDateTo,
                    Long paymentMethodId, Long paymentUsedeId, Date schedulePaymentDateFrom, Date schedulePaymentDateTo,
                    String currencyCode, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryPayment(requestCtx, accEntityId, docCategory, docNumber, payeeCategory,
                        payeeId, requisitionDateFrom, requisitionDateTo, paymentMethodId, paymentUsedeId,
                        schedulePaymentDateFrom, schedulePaymentDateTo, currencyCode, page, pageSize));
    }

    /**
     * 获取付款银行账号
     * 
     * @Author Tagin
     * @Date 2019-03-11 14:57
     * @param request 请求
     * @param accEntityId 核算主体ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshBankAccount>
     * @Version 1.0
     **/
    @RequestMapping(value = "/csh/doc-pay-acc-entity/queryPaymentBank",
                    method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPaymentBank(HttpServletRequest request, Long accEntityId, String bankAccountName,
                    String bankAccountNum, String currencyCode) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(cshBankAccountService.queryBankAccounts(accEntityId, bankAccountName, bankAccountNum,
                        currencyCode, iRequest));
    }

    /**
     * 
     * 单据支付-默认核算主体
     * 
     * @Author Tagin
     * @Date 2019-03-04 19:16
     * @param request 请求
     * @Return org.springframework.web.servlet.ModelAndView
     * @Version 1.0
     **/
    @RequestMapping(value = "csh/CSH5040/csh_document_payment.screen")
    public ModelAndView cshDocumentPaymentView(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH5040/csh_document_payment");
        // 默认核算主体
        GldAccountingEntity defaultAccEntityList =
                        gldAccountingEntityService.queryDefaultAccEntity(iRequest, iRequest.getCompanyId());
        view.addObject("defaultAccEntity", defaultAccEntityList);
        return view;
    }

    /**
     * 单据支付-默认币种及日期
     * 
     * @Author Tagin
     * @Date 2019-03-07 20:10
     * @param request 请求
     * @param accEntityId 核算主体ID
     * @Return org.springframework.web.servlet.ModelAndView
     * @Version 1.0
     **/
    @RequestMapping(value = "csh/CSH5040/csh_document_payment_pay.screen")
    public ModelAndView cshDocumentPaymentPayView(HttpServletRequest request, Long accEntityId) {
        Map<String, String> map = new HashMap<>();
        IRequest iRequest = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH5040/csh_document_payment_pay");
        // 核算主体对应本位币
        String funCurrency = gldCurrencyService.getAccEntityFunCurrCode(accEntityId);
        map.put("funcCurrency", funCurrency);
        // 系统当前日期
        map.put("defaultDate", DateUtils.getCurrentDate().toString());
        // 系统日期对应期间
        String periodName = gldPeriodService.getPeriodName(iRequest, new Date(), accEntityId, "O");
        map.put("periodName", periodName);
        view.addObject("defaultValue", map);
        return view;
    }

    /**
     * 查询当前公司的关联核算主体信息-预付款核销页面
     *
     * @param request
     * @return
     * @author LJK 2019-03-20 14:25
     */
    @RequestMapping(value = "/expm/EXP5201/exp_to_be_paid_doc_confirm.screen")
    public ModelAndView expToBePaidDocConfirmView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP5201/exp_to_be_paid_doc_confirm");
        List<Map> accEntityList = gldAccountingEntityService.queryByCompanyId(null, requestCtx);
        List<Map> dftAcc = gldAccountingEntityService.queryForDftAccEntity(requestCtx);
        view.addObject("accEntityList", accEntityList);
        view.addObject("dftAcc", dftAcc);
        return view;
    }

    @RequestMapping(value = "/csh/CSH5230/csh_payment_reverse.screen")
    public ModelAndView cshPaymentReverseView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH5230/csh_payment_reverse");
        List<Map> currentDate = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("currentDate", DateUtils.getCurrentDate());
        Long accEntityId = fndCompanyRefAccEntityService.queryDftAccByComId(requestCtx);
        String currentOpenPeriodName =
                        gldPeriodService.getPeriodName(requestCtx, DateUtils.getCurrentDate(), accEntityId, "O");
        map.put("currentOpenPeriodName", currentOpenPeriodName);
        currentDate.add(map);
        view.addObject("current_date", currentDate);
        return view;
    }

    @RequestMapping(value = "/csh/CSH5230/csh_payment_reverse_detail.screen")
    public ModelAndView cshPaymentReverseDetailView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH5230/csh_payment_reverse_detail");
        List<Map> currentDate = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("currentDate", DateUtils.getCurrentDate());
        Long accEntityId = fndCompanyRefAccEntityService.queryDftAccByComId(requestCtx);
        String currentOpenPeriodName =
                        gldPeriodService.getPeriodName(requestCtx, DateUtils.getCurrentDate(), accEntityId, "O");
        map.put("currentOpenPeriodName", currentOpenPeriodName);
        currentDate.add(map);
        view.addObject("current_date", currentDate);
        return view;
    }

    @RequestMapping(value = "/csh/payment/reverse/query")
    @ResponseBody
    public ResponseData queryPaymentReverse(@RequestParam(required = false) String docNumber,
                    @RequestParam(required = true) Long accEntityId,
                    @RequestParam(required = false) String payeeCategory, @RequestParam(required = false) Long payeeId,
                    @RequestParam(required = false) String currencyCode,
                    @RequestParam(required = false) String transactionNum,
                    @RequestParam(required = false) String transactionDateFrom,
                    @RequestParam(required = false) String transactionDateTo,
                    @RequestParam(required = false) Long paymentMethodId,
                    @RequestParam(required = false) String paymentEmployeeName,
                    @RequestParam(required = false) String agentEmployeeName,
                    @RequestParam(required = false) String transactionAmountFrom,
                    @RequestParam(required = false) String transactionAmountTo, HttpServletRequest request,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(cshTransactionHeaderService.queryPaymentReverse(requestCtx, page, pageSize, docNumber,
                        accEntityId, payeeCategory, payeeId, currencyCode, transactionNum, transactionDateFrom,
                        transactionDateTo, paymentMethodId, paymentEmployeeName, agentEmployeeName,
                        transactionAmountFrom, transactionAmountTo));
    }
}

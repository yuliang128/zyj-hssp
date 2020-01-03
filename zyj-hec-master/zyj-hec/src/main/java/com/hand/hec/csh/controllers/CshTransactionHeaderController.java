package com.hand.hec.csh.controllers;


import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.service.ICshTransactionAccountService;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * <p>
 * 现金事务头表Controller
 * </p>
 *
 * @author Tagin 2019/01/22 10:27
 */

@Controller
@RequestMapping(value = "")
public class CshTransactionHeaderController extends BaseController {

    @Autowired
    private ICshTransactionHeaderService service;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private ICshTransactionAccountService cshTransactionAccountService;



    @RequestMapping(value = "/csh/transaction-header/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshTransactionHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/transaction-header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshTransactionHeader> dto, BindingResult result,
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

    @RequestMapping(value = "/csh/transaction-header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshTransactionHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/csh/transaction-header/queryByPayReqHeaderId",
            method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByPayReqHeaderId(Long headerId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByPayReqHeaderId(requestContext, headerId, page, pageSize));
    }

    @RequestMapping(value = "/csh/transaction-header/queryWriteOffByPaYReqHeaderId",
            method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryWriteOffByPaYReqHeaderId(Long headerId,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryWriteOffByPaYReqHeaderId(requestContext, headerId, page, pageSize));
    }

    /**
     * 查询预付款核销过账页面的头信息
     *
     * @param request 上下文环境
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map> 过账页面的头信息
     * @author ngls.luhui 2019-03-07 19:44
     */
    @RequestMapping(value = "/csh/transaction-header/queryPrePayWtfByHId",
            method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPrePayWtfByHId(@RequestParam Long transactionHeaderId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPrePayWtfByHId(requestContext, transactionHeaderId));
    }


    @RequestMapping(value = "/csh/transaction-header/cshTransactionInfoQuery")
    @ResponseBody
    public ResponseData cshTransactionInfoQuery(@RequestParam Long transactionHeaderId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.cshTransactionInfoQuery(requestContext, transactionHeaderId));
    }


    @RequestMapping(value = "/csh/transaction-header/cshTransactionQuery")
    @ResponseBody
    public ResponseData cshTransactionQuery(HttpServletRequest request, @RequestParam(required = true) Long accEntityId,
                                            @RequestParam(required = true) String transactionDateFrom,
                                            @RequestParam(required = true) String transactionDateTo, String payeeCategory, Long payeeId,
                                            String currencyCode, String transactionNum, Long bankAccountId, Long paymentMethodId,
                                            String paymentEmployeeName, String agentEmployeeName, String contactNumber, String transactionType,
                                            String transactionAmountFrom, String transactionAmountTo, String docNumber, String docCategory,
                                            String sourceTransactionNum, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.cshTransactionQuery(requestContext, accEntityId, transactionDateFrom,
                transactionDateTo, payeeCategory, payeeId, currencyCode, transactionNum, bankAccountId,
                paymentMethodId, paymentEmployeeName, agentEmployeeName, contactNumber, transactionType,
                transactionAmountFrom, transactionAmountTo, docNumber, docCategory, sourceTransactionNum, page,
                pageSize));
    }


    @RequestMapping(value = "/csh/CSH3200/csh_transaction_query.screen")
    public ModelAndView cshTransactionQueryView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH3200/csh_transaction_query");
        // 获取当前公司下默认的核算主体
        List<GldAccountingEntity> dftAccountingEntityList = new ArrayList<>();
        GldAccountingEntity accountingEntity =
                gldAccountingEntityService.queryDefaultAccEntity(requestContext, requestContext.getCompanyId());
        if (accountingEntity != null) {
            dftAccountingEntityList.add(accountingEntity);
        }

        // 获取当前公司关联的核算主体
        List<GldAccountingEntity> accountingEntityList = gldAccountingEntityService
                .queryAccEntityByCompany(requestContext, requestContext.getCompanyId());

        List<Map> currentDate = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("currentDate", DateUtils.getCurrentDate());
        map.put("currentDate30", DateUtils.date2Str(DateUtils.addMonth(DateUtils.getCurrentDate(), -1), null));
        currentDate.add(map);

        view.addObject("default_acc_entity", dftAccountingEntityList);
        view.addObject("acc_entity", accountingEntityList);
        view.addObject("current_date", currentDate);
        return view;

    }

    @RequestMapping(value = "/csh/transaction-header/paymentFinanceInfor")
    @ResponseBody
    public ResponseData paymentFinanceInforQuery(@RequestParam Long transactionHeaderId, HttpServletRequest request,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(cshTransactionAccountService.paymentFinanceInforQuery(requestContext,
                transactionHeaderId, page, pageSize));
    }

    @RequestMapping(value = "/csh/CSH3200/postReverseTransaction")
    @ResponseBody
    public ResponseData postReverseTransaction(HttpServletRequest request,
                                               @RequestBody List<CshTransactionHeader> dto) {
        IRequest requestContext = createRequestContext(request);
        service.postReverseTransaction(requestContext, dto);
        return new ResponseData();
    }

	@RequestMapping(value = "/csh/public/csh_pay_req_trans_detail.screen")
	public ModelAndView cshPayReqTransDetailView(HttpServletRequest request,Long transactionHeaderId) {
		IRequest requestContext = createRequestContext(request);
		ModelAndView view = new ModelAndView("csh/public/csh_pay_req_trans_detail");

		List<Map> paymentHeaderDetail = service.cshTransInfoQuery(requestContext, transactionHeaderId);

		view.addObject("payment_header_detail", paymentHeaderDetail);
		return view;
	}
}
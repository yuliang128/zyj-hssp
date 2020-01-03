package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshWriteOff;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.csh.service.ICshWriteOffService;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核销表Controller
 * </p>
 *
 * @author Tagin 2019-01-22 09:44
 */

@Controller
public class CshWriteOffController extends BaseController {

    @Autowired
    private ICshWriteOffService service;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @RequestMapping(value = "/csh/write-off/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshWriteOff dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/write-off/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshWriteOff> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/write-off/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshWriteOff> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 报销单核销借款-单据信息-查询
     *
     * @author Tagin
     * @date 2019-06-05 15:50
     * @param request 请求
     * @param paymentScheduleLineId 计划付款行 ID
     * @return com.hand.hap.system.dto.ResponseData
     * @version 1.0
     **/
    @RequestMapping(value = "/csh/write-off/docQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData docQuery(HttpServletRequest request, Long paymentScheduleLineId) {
        IRequest requestCtx = createRequestContext(request);
        // 调用查询单据基础信息
        return new ResponseData(service.docQuery(requestCtx, paymentScheduleLineId));
    }

    /**
     * 报销单核销借款-未核销记录-查询
     *
     * @author Tagin
     * @date 2019-06-05 15:51
     * @param request 请求
     * @param paymentScheduleLineId 计划付款行 ID
     * @param requisitionNumber 借款申请单编号
     * @param dateFrom 日期从
     * @param dateTo 日期至
     * @return com.hand.hap.system.dto.ResponseData
     * @version 1.0
     **/
    @RequestMapping(value = "/csh/write-off/unWriteQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData unWriteQuery(HttpServletRequest request, Long paymentScheduleLineId, String requisitionNumber,
                    String dateFrom, String dateTo) {
        IRequest requestCtx = createRequestContext(request);
        // 调用查询费用报销单可核销的记录
        return new ResponseData(
                        service.unWriteQuery(requestCtx, paymentScheduleLineId, requisitionNumber, dateFrom, dateTo));
    }

    /**
     * 报销单核销借款-已核销记录-查询
     * 
     * @author Tagin 2019-02-14 17:59
     * @param request 请求
     * @param paymentScheduleLineId 计划付款行 ID
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/csh/write-off/writeQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData writeQuery(HttpServletRequest request, Long paymentScheduleLineId) {
        IRequest requestCtx = createRequestContext(request);
        // 调用查询费用报销单已核销的记录
        return new ResponseData(service.writeQuery(requestCtx, paymentScheduleLineId));
    }

    /**
     * 报销单核销借款-销记录-创建
     * 
     * @author Tagin 2019-02-19 17:03
     * @param request 请求
     * @param cshWriteOffList 核销集合
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/csh/write-off/createWrite", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData createWrite(HttpServletRequest request, @RequestBody List<CshWriteOff> cshWriteOffList)
                    throws CshWriteOffException {
        // 调用创建核销记录逻辑
        service.createWrite(createRequestContext(request), cshWriteOffList);
        return new ResponseData(cshWriteOffList);
    }

    /**
     * @param request
     * @param cshWriteOffList
     * @return com.hand.hap.system.dto.ResponseData
     * @description 报销单核销借款-销记录-删除
     * @author Tagin 2019-02-18 13:27
     */
    @RequestMapping(value = "/csh/write-off/deleteWrite", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData deleteWrite(HttpServletRequest request, @RequestBody List<CshWriteOff> cshWriteOffList) {
        // 调用删除核销记录逻辑
        service.deleteWrite(createRequestContext(request), cshWriteOffList);
        return new ResponseData(cshWriteOffList);
    }

    /**
     * 查询当前公司的关联核算主体信息-预付款核销页面
     *
     * @param request
     * @return
     * @author ngls.luhui 2019-03-05 19:25
     */
    @RequestMapping(value = "csh/CSH5220/csh_prepayment_write_off.screen")
    public ModelAndView cshPrepaymentWriteOffView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("csh/CSH5220/csh_prepayment_write_off");
        List<Map> accEntityList = gldAccountingEntityService.queryByCompanyId(null, requestCtx);
        List<Map> dftAcc = gldAccountingEntityService.queryForDftAccEntity(requestCtx);
        view.addObject("accEntityList", accEntityList);
        view.addObject("dftAcc", dftAcc);
        return view;
    }

    /**
     * 预付款核销页面信息
     *
     * @param accEntityId 核算主体ID
     * @param payeeCategory 收款方类别(EMPLOYEE,VENDER,CUSTOMER)
     * @param payeeId 收款方ID
     * @param currencyCode 币种
     * @param transactionNum 现金事务编号
     * @param transactionDateFrom 付款日期从
     * @param transactionDateTo 付款日期到
     * @param paymentMethodId 付款方式ID
     * @param userName 用户名
     * @param agentEmployeeId 代理员工
     * @param contactNumber 合同编号
     * @param amountFrom 付款金额从
     * @param amountTo 付款金额到
     * @param requisitionNumber 单据编号
     * @param bankAccountId 银行账号
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-06 16:16
     */
    @RequestMapping(value = "/csh/write-off/queryPrePay", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPrePay(@RequestParam Long accEntityId,
                    @RequestParam(required = false) String payeeCategory, @RequestParam(required = false) Long payeeId,
                    @RequestParam(required = false) String currencyCode,
                    @RequestParam(required = false) String transactionNum, @RequestParam String transactionDateFrom,
                    @RequestParam String transactionDateTo, @RequestParam(required = false) Long paymentMethodId,
                    @RequestParam(required = false) String userName,
                    @RequestParam(required = false) Long agentEmployeeId,
                    @RequestParam(required = false) String contactNumber,
                    @RequestParam(required = false) String amountFrom, @RequestParam(required = false) String amountTo,
                    @RequestParam(required = false) String requisitionNumber,
                    @RequestParam(required = false) Long bankAccountId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(cshTransactionHeaderService.queryPrePay(requestContext, page, pageSize, accEntityId,
                        payeeCategory, payeeId, currencyCode, transactionNum, transactionDateFrom, transactionDateTo,
                        paymentMethodId, userName, agentEmployeeId, contactNumber, amountFrom, amountTo,
                        requisitionNumber, bankAccountId));
    }

    /**
     * 预付款核销明细信息
     *
     * @param transactionHeaderId
     * @param request
     * @param page
     * @param pageSize
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-08 13:52
     */
    @RequestMapping(value = "/csh/write-off/queryPrePayDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPrePayDetail(@RequestParam Long transactionHeaderId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPrePayDetail(transactionHeaderId, requestContext, page, pageSize));

    }

    /**
     * 预付款过账核销
     *
     * @param transactionHeaderId 预付款现金事务头ID
     * @param dto 待核销记录集合
     * @param request 请求
     * @return com.hand.hap.system.dto.ResponseData
     * @author ngls.luhui 2019-03-12 10:37
     */
    @RequestMapping(value = "/csh/write-off/post")
    @ResponseBody
    public ResponseData post(@RequestParam Long transactionHeaderId, @RequestBody List<CshWriteOff> dto,
                    HttpServletRequest request) throws BaseException {
        IRequest requestCtx = createRequestContext(request);
        cshTransactionHeaderService.postTransaction(requestCtx, transactionHeaderId, null, dto);
        return new ResponseData(dto);
    }

    /**
     * 获取核销历史数据
     *
     * @param transactionHeaderId
     * @param request
     * @param page
     * @param pageSize
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-12 14:58
     */
    @RequestMapping(value = "/csh/write-off/getWriteOffHistory", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getWriteOffHistory(@RequestParam Long transactionHeaderId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getWriteOffHistory(transactionHeaderId, requestContext, page, pageSize));

    }

    /**
     * 预付款核销-核销记录-财务信息
     *
     * @param transactionHeaderId
     * @param request
     * @param page
     * @param pageSize
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-13 18:58
     */
    @RequestMapping(value = "/csh/write-off/queryFinance", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFinance(@RequestParam Long transactionHeaderId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFinance(transactionHeaderId, requestContext, page, pageSize));
    }

    @RequestMapping(value = "/csh/write-off/cshWriteOffCheck")
    @ResponseBody
    public ResponseData cshWriteOffCheck(HttpServletRequest request, String payeeCategory, Long payeeId, Long companyId,
                    Long accEntityId) {
        IRequest requestContext = createRequestContext(request);
        List<String> list = new ArrayList<>();
        list.add(service.submitCshWriteOffCheck(requestContext, payeeCategory, payeeId, companyId, accEntityId));
        return new ResponseData(list);
    }


    @RequestMapping(value = "/csh/write-off/queryCshWriteOffHistory")
    @ResponseBody
    public ResponseData queryCshWriteOffHistory(HttpServletRequest request, @RequestParam Long transactionHeaderId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCshWriteOffHistory(requestContext, transactionHeaderId, page, pageSize));
    }

	@RequestMapping(value = "/csh/write-off/queryPrePayForReverse", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryPrePayForReverse(@RequestParam(required = false) String payeeCategory, @RequestParam(required = false) Long payeeId, @RequestParam(required = false) String currencyCode, @RequestParam(required = false) String transactionNum, String transactionDateFrom, String transactionDateTo, @RequestParam(required = false) String requisitionNumber, String contractNumber, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(cshTransactionHeaderService.queryPrePayForReverse(requestContext, payeeCategory, payeeId, currencyCode, transactionNum, transactionDateFrom, transactionDateTo, requisitionNumber, contractNumber, page, pageSize));
	}

	@RequestMapping(value = "/csh/write-off/queryPrePayWriteForReverse")
	@ResponseBody
	public ResponseData queryPrePayWriteForReverse(HttpServletRequest request, @RequestParam Long transactionHeaderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryPrePayWriteForReverse(requestContext, transactionHeaderId, page, pageSize));
	}

	@RequestMapping(value = "/csh/write-off/reversePrePayWrite")
	@ResponseBody
	public ResponseData reversePrePayWrite(@RequestBody List<CshWriteOff> dto, BindingResult result, HttpServletRequest request) {
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
		IRequest requestCtx = createRequestContext(request);
		service.reversePrePayWrite(requestCtx, dto);
		return new ResponseData();
	}
}

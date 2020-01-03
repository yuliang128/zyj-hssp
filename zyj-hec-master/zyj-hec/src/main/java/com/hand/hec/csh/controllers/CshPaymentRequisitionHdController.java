package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.IgnoreToken;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.service.ICshPaymentRequisitionHdService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@IgnoreToken
public class CshPaymentRequisitionHdController extends BaseController{

    @Autowired
    private ICshPaymentRequisitionHdService service;
    @Autowired
    private IGldSobSegmentService segmentService;
	@Autowired
	private IFndCompanyService fndCompanyService;

    @RequestMapping(value = "/csh/payment-requisition-hd/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

	@RequestMapping(value = "/csh/payment-requisition-hd/queryPayReq",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryPayReq(CshPaymentRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryPayReq(requestContext,dto,page,pageSize));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/queryDetail",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryDetail(Long paymentRequisitionHeaderId, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.selectDetailByHdId(requestContext,paymentRequisitionHeaderId));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/closePaymentRequisition")
	@ResponseBody
	public ResponseData closePaymentRequisition(@RequestBody List<CshPaymentRequisitionHd> dto, BindingResult result,
			HttpServletRequest request) throws BaseException {
		IRequest requestCtx = createRequestContext(request);
		service.closePaymentRequisition(requestCtx, dto);
		return new ResponseData(true);
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/approvePaymentRequisition")
	@ResponseBody
	public ResponseData approvePaymentRequisition(@RequestBody List<CshPaymentRequisitionHd> dto, BindingResult result,
			HttpServletRequest request) throws BaseException {
		IRequest requestCtx = createRequestContext(request);
		service.approvePaymentRequisition(requestCtx, dto);
		return new ResponseData();
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/reject")
	@ResponseBody
	public ResponseData reject(@RequestBody List<CshPaymentRequisitionHd> dto, BindingResult result,
			HttpServletRequest request) {
		IRequest requestCtx = createRequestContext(request);
		service.reject(requestCtx, dto);
		return new ResponseData();
	}

    @RequestMapping(value = "/csh/payment-requisition-hd/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentRequisitionHd> dto, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.savePaymentRequisition(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-requisition-hd/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentRequisitionHd> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/csh/payment-requisition-hd/queryForFinance",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForFinance(String allCompanyFlag, String requisitionNumber, Long employeeId
                                       , String description, Date requisitionDateFrom, Date requisitionDateTo
                                       , BigDecimal amountFrom, BigDecimal amountTo, String payeeCategory
                                       , Long payeeId, String currencyCode, String status
                                       , Long paymentMethodId, Long cshPaymentRequisitionTypeId
                                       , @RequestParam(defaultValue = DEFAULT_PAGE) int page
                                       , @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForFinance(requestContext,allCompanyFlag,requisitionNumber,employeeId,description,requisitionDateFrom,requisitionDateTo,amountFrom,amountTo,payeeCategory,payeeId,currencyCode,status,paymentMethodId,cshPaymentRequisitionTypeId,page,pageSize));
    }

    @RequestMapping(value = "/csh/payment-requisition-hd/queryForAudit",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForAudit(Long accEntityId, Long employeeId
                                    , String requisitionNumber, Date requisitionDateFrom
                                    , Date requisitionDateTo, String currencyCode
                                    , String payeeCategory, Long payeeId
                                    , @RequestParam(defaultValue = DEFAULT_PAGE) int page
                                    , @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForAudit(requestContext,accEntityId,employeeId,requisitionNumber,requisitionDateFrom,requisitionDateTo,currencyCode,payeeCategory,payeeId,page,pageSize));
    }
	@RequestMapping(value = "/csh/payment-requisition-hd/getPeriodName", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData getPeriodName(@RequestParam(value = "reverseDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date reverseDate, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.getPeriodName(requestContext, reverseDate));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/queryForReverse",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryForReverse(CshPaymentRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		dto.setStatus("COMPLETELY_APPROVED");
		dto.setReversedFlag("N");
		dto.setAuditFlag("Y");
		return new ResponseData(service.queryPayReq(requestContext,dto,page,pageSize));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/reverse")
	@ResponseBody
	public ResponseData reverse(@RequestBody List<CshPaymentRequisitionHd> dto, BindingResult result,
			HttpServletRequest request) throws BaseException {
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
		IRequest requestCtx = createRequestContext(request);
		service.reversePayReq(requestCtx, dto);
		return new ResponseData();
	}

	/**
	 *费用申请财务关闭查询
	 * @para CshPaymentRequisitionHd dto,request , int page ,int pageSize
	 * @author weikun.wang
	 * @time 2019/03/19
	 */
	@RequestMapping(value = "/csh/payment-requisition-hd/queryForRequisitionReverse",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryForRequisitionReverse(CshPaymentRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
												   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		dto.setCompanyId(requestContext.getCompanyId());
		return new ResponseData(service.queryForRequisitionReverse(requestContext,dto,page,pageSize));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/queryPayRequisitionMain",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryPayRequisitionMain(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, CshPaymentRequisitionHd dto,
			HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		List<Map> headers = service.queryPayRequisitionMain(requestContext,dto, page, pageSize);
		return new ResponseData(headers);
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/queryPayReqHd",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryPayRequisitionMain(CshPaymentRequisitionHd dto,HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		List<CshPaymentRequisitionHd> headers = service
				.queryPaymentRequisitionHeader(requestContext, dto.getPaymentRequisitionHeaderId(), dto.getPaymentReqTypeId(), dto.getEmployeeId(),
						dto.getAccEntityId());
		return new ResponseData(headers);
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/submitPayReqHd",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData submitPayReqHd(@RequestBody CshPaymentRequisitionHd dto,HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		service.submitPaymentRequisition(requestContext, dto.getPaymentRequisitionHeaderId());
		return new ResponseData();
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/deletePaymentRequisition")
	@ResponseBody
	public ResponseData deletePaymentRequisition(@RequestBody CshPaymentRequisitionHd dto, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		service.deletePaymentRequisition(requestContext, dto.getPaymentRequisitionHeaderId());
		return new ResponseData();
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/selectPayReqRefReport",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData selectPayReqRefReport(Long paymentRequisitionHeaderId, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.selectPayReqRefReport(requestContext,paymentRequisitionHeaderId));
	}

	@RequestMapping(value = "/csh/payment-requisition-hd/selectPayReqRefRegister",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData selectPayReqRefRegister(Long paymentRequisitionHeaderId, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.selectPayReqRefRegister(requestContext,paymentRequisitionHeaderId));
	}

	@RequestMapping(value = "csh/CSH5010/csh_payment_requisition_type_choice.screen")
	public ModelAndView cshPaymentRequisitionTypeChoiceView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("csh/CSH5010/csh_payment_requisition_type_choice");
		IRequest requestCtx = createRequestContext(request);
		List<FndCompany> currentCompany = fndCompanyService.queryCurrentCompany(requestCtx);
		view.addObject("currentCompany", currentCompany);
		return view;
	}

	@RequestMapping(value = "csh/CSH5010/csh_payment_requisition_maintain_main.screen")
	public ModelAndView cshPaymentRequisitionMaintainMainView(HttpServletRequest request,
			Long paymentRequisitionHeaderId, Long paymentReqTypeId, Long employeeId, Long accEntityId) {
		ModelAndView view = new ModelAndView("csh/CSH5010/csh_payment_requisition_maintain_main");
		IRequest requestCtx = createRequestContext(request);
		List<CshPaymentRequisitionHd> headerInfo = service
				.queryPaymentRequisitionHeader(requestCtx, paymentRequisitionHeaderId, paymentReqTypeId, employeeId,
						accEntityId);
		view.addObject("headerInfo", headerInfo);
		return view;
	}

	@RequestMapping(value = "csh/CSH3110/csh_payment_requisition_finance_query.screen")
	public ModelAndView jumpCshMoPaymentReqTypes(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("csh/CSH3110/csh_payment_requisition_finance_query");
		IRequest requestContext = createRequestContext(request);
		List<Map> accountSegment = segmentService.querySegmentDesc(requestContext);

		view.addObject("accountSegment", accountSegment);
		return view;
	}
}
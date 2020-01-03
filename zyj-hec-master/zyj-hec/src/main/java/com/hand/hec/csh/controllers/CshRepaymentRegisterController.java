package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.interceptor.SecurityTokenInterceptor;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;
import com.hand.hec.csh.service.*;
import com.hand.hec.gld.service.IGldSobSegmentService;
import com.hand.hec.gld.service.impl.GldSobSegmentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款单控制器
 * </p>
 *
 * @author mouse 2019/05/08 16:07
 */
@Controller
public class CshRepaymentRegisterController extends BaseController {

    private static final String HEAD_INFO = "headInfo";

    private final ICshRepaymentRegisterService service;

    private final ICshRepaymentRegisterHdService headerService;

    private final ICshRepaymentRegisterLnService lineService;

    private final ISysParameterService parameterService;

    private final ICshPaymentMethodService paymentMethodService;

    private final ICshTransactionAccountService transactionAccountService;

    private final GldSobSegmentServiceImpl sobSegmentService;

    private final IGldSobSegmentService segmentService;

    private final IFndCompanyService companyService;

    public CshRepaymentRegisterController(ICshRepaymentRegisterService service,
                                          ICshRepaymentRegisterHdService headerService,
                                          ICshRepaymentRegisterLnService lineService,
                                          ISysParameterService parameterService,
                                          ICshPaymentMethodService paymentMethodService,
                                          ICshTransactionAccountService transactionAccountService,
                                          GldSobSegmentServiceImpl sobSegmentService,
                                          IGldSobSegmentService segmentService,
                                          IFndCompanyService companyService) {
        this.service = service;
        this.headerService = headerService;
        this.lineService = lineService;
        this.parameterService = parameterService;
        this.paymentMethodService = paymentMethodService;
        this.transactionAccountService = transactionAccountService;
        this.sobSegmentService = sobSegmentService;
        this.segmentService = segmentService;
        this.companyService = companyService;
    }



    /*===============================================   通用 url  ==============================================*/

    /**
     * 还款申请单保存
     *
     * @param dto     头行明细
     * @param result
     * @param request http报文
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 16:59
     */
    @RequestMapping(value = "/csh/repayment-register/save")
    @ResponseBody
    public ResponseData save(@RequestBody List<CshRepaymentRegisterHd> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(headerService.batchUpdate(requestCtx, dto));
    }

    /**
     * 还款单行查询
     *
     * @param registerHdsId 还款单头ID
     * @param page          页数
     * @param pageSize      页大小
     * @param request       http报文
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 16:45
     */
    @RequestMapping(value = "/csh/repayment-register/line/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long registerHdsId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lineService.queryLinesByHeaderId(requestContext, registerHdsId, page, pageSize));
    }

    /*===============================================   CSH 7020  ==============================================*/

    /**
     * 还款单行关联借款单查询
     *
     * @param currencyCode      币种代码
     * @param employeeId        员工ID
     * @param requisitionNumber 借款单据编号
     * @param request           http报文
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 16:41
     */
    @RequestMapping(value = "/csh/repayment-register/line/queryPaymentRequisition", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPaymentRequisition(String currencyCode, Long employeeId, String requisitionNumber, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lineService.queryPaymentRequisition(requestContext, currencyCode, employeeId, requisitionNumber));
    }

    /**
     * 还款行删除
     *
     * @param dto 行dto
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 16:21
     */
    @RequestMapping(value = "/csh/repayment-register/line/remove")
    @ResponseBody
    public ResponseData delete(@RequestBody List<CshRepaymentRegisterLn> dto) {
        lineService.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 还款申请单页面查询
     *
     * @param request  http报文
     * @param page     页码
     * @param pageSize 页大小
     * @param params   查询条件
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 17:01
     */
    @RequestMapping(value = "/csh/repayment-register/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(HttpServletRequest request,
                              @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              @RequestParam Map<String, Object> params) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.baseSelect(requestContext, params, page, pageSize));
    }

    /**
     * 还款申请单提交审批
     *
     * @param registerHdsId
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 16:59
     */
    @RequestMapping(value = "/csh/repayment-register/submit")
    @ResponseBody
    public ResponseData submit(Long registerHdsId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(headerService.batchSubmit(requestCtx, registerHdsId));
    }

    /**
     * 还款单行更新
     *
     * @param dto     行dto列表
     * @param result
     * @param request http报文
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 16:23
     */
    @RequestMapping(value = "/csh/repayment-register/line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshRepaymentRegisterLn> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(lineService.batchUpdate(requestCtx, dto));
    }

    /**
     * 还款单删除
     *
     * @param request
     * @param registerHdsId
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 17:47
     */
    @RequestMapping(value = "/csh/repayment-register/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, Long registerHdsId) {
        IRequest requestCtx = createRequestContext(request);
        headerService.delete(requestCtx, CshRepaymentRegisterHd.builder().registerHdsId(registerHdsId).build());
        return new ResponseData();
    }

    /**
     * 还款单关联借款单基础查询
     *
     * @param request
     * @param registerHdsId
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 17:01
     */
    @RequestMapping(value = "/csh/repayment-register/pay-req/query")
    @ResponseBody
    public ResponseData queryRelatedPaymentRequisition(HttpServletRequest request, Long registerHdsId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(headerService.selectRelatedPaymentRequisition(requestCtx, registerHdsId));
    }
    /*===============================================   CSH7030  ==============================================*/

    /**
     * 还款申请出纳确认基础查询
     *
     * @param request  http报文
     * @param params   查询条件
     * @param page     页数
     * @param pageSize 页大小
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 16:56
     */
    @RequestMapping(value = "/csh/repayment-register/cashier/query")
    @ResponseBody
    public ResponseData queryRepaymentCashier(HttpServletRequest request,
                                              @RequestParam Map<String, Object> params,
                                              @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryRepaymentCashier(requestContext, params, page, pageSize));
    }

    /**
     * 还款出纳还款单明细基础头查询
     *
     * @param request
     * @param registerHdsId 还款申请单头ID
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 16:55
     */
    @RequestMapping(value = "/csh/repayment-register/header/detail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRepaymentHd(HttpServletRequest request, Long registerHdsId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(Collections.singletonList(headerService.queryRepaymentHd(requestContext, registerHdsId)));
    }

    /**
     * 出纳确认
     *
     * @param request       http报文
     * @param registerHdsId 还款单头Id
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 14:47
     */
    @RequestMapping(value = "/csh/repayment-register/cashier/confirm")
    @ResponseBody
    public ResponseData cashierConfirm(HttpServletRequest request, Long registerHdsId) {
        IRequest requestContext = createRequestContext(request);
        service.cashierConfirm(requestContext, getRegisterHd(requestContext, registerHdsId));
        return new ResponseData(true);
    }

    /**
     * 出纳拒绝
     *
     * @param request       http报文
     * @param registerHdsId 还款单头Id
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 14:47
     */
    @RequestMapping(value = "/csh/repayment-register/cashier/reject")
    @ResponseBody
    public ResponseData cashierReject(HttpServletRequest request, Long registerHdsId) {
        IRequest requestContext = createRequestContext(request);
        service.cashierReject(requestContext, getRegisterHd(requestContext, registerHdsId));
        return new ResponseData(true);
    }

    /*===============================================   CSH7040  ==============================================*/

    /**
     * 还款申请会计确认基础查询
     *
     * @param request  http报文
     * @param params   查询条件
     * @param page     页数
     * @param pageSize 页大小
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-13 16:56
     */
    @RequestMapping(value = "/csh/repayment-register/accounting/query")
    @ResponseBody
    public ResponseData queryRepaymentAccounting(HttpServletRequest request
            , @RequestParam Map<String, Object> params
            , @RequestParam(defaultValue = DEFAULT_PAGE) int page
            , @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryRepaymentAccounting(requestContext, params, page, pageSize));
    }

    /**
     * 会计确认
     *
     * @param request       http报文
     * @param registerHdsId 还款单头Id
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 14:47
     */
    @RequestMapping(value = "/csh/repayment-register/accounting/confirm")
    @ResponseBody
    public ResponseData accountingConfirm(HttpServletRequest request, Long registerHdsId) {
        IRequest requestContext = createRequestContext(request);
        service.accountingConfirm(requestContext, getRegisterHd(requestContext, registerHdsId));
        return new ResponseData(true);
    }

    /**
     * 会计拒绝
     *
     * @param request       http报文
     * @param registerHdsId 还款单头Id
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-05-31 14:47
     */
    @RequestMapping(value = "/csh/repayment-register/accounting/reject")
    @ResponseBody
    public ResponseData accountingReject(HttpServletRequest request, Long registerHdsId) {
        IRequest requestContext = createRequestContext(request);
        service.accountingReject(requestContext, getRegisterHd(requestContext, registerHdsId));
        return new ResponseData(true);
    }

    /*===============================================   CSH7070  ==============================================*/

    @RequestMapping(value = "/csh/repayment-register/queryForFinance")
    @ResponseBody
    public ResponseData queryForFinance(HttpServletRequest request, CshRepaymentRegisterHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryForFinance(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/repayment-register/queryAccountForFinance")
    @ResponseBody
    public ResponseData queryAccountForFinance(HttpServletRequest request, Long registerHdsId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(transactionAccountService.queryAccountForFinance(requestContext, registerHdsId, page, pageSize));
    }

    /*===============================================   Private Method  ==============================================*/

    private CshRepaymentRegisterHd getRegisterHd(IRequest request, Long registerHdId) {
        return headerService.selectByPrimaryKey(request, CshRepaymentRegisterHd.builder().registerHdsId(registerHdId).build());
    }

    /**
     * 处理还款登记单主页面与只读主页面的携带参数逻辑
     */
    private ModelAndView processMaintainMainView(HttpServletRequest request,
                                                 String viewName,
                                                 Long registerHdsId,
                                                 Long moRepaymentRegTypeId,
                                                 Long employeeId,
                                                 Long accEntityId) {
        ModelAndView view = new ModelAndView(viewName);
        IRequest requestCxt = createRequestContext(request);
        CshRepaymentRegisterHd record = new CshRepaymentRegisterHd();
        TokenUtils.generateAndSetToken(SecurityTokenInterceptor.LOCAL_SECURITY_KEY.get(), record);
        if (registerHdsId != null) {
            record = headerService.queryRepaymentHd(requestCxt, registerHdsId);
            view.addObject(HEAD_INFO, BeanUtil.convert2Map(record));
            return view;
        }
        record = headerService.queryHdWithIdIsNull(requestCxt, moRepaymentRegTypeId, employeeId, accEntityId);
        view.addObject(HEAD_INFO, BeanUtil.convert2Map(record));
        return view;
    }

    /*===============================================   ModelAndView  ================================================*/

    /**
     * CSH7020: csh_repayment_register_maintain_type_choice
     */
    @RequestMapping(value = "csh/CSH7020/csh_repayment_register_maintain_type_choice.screen")
    public ModelAndView cshRepaymentRegisterMaintainTypeChoiceView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH7020/csh_repayment_register_maintain_type_choice");
        IRequest requestCxt = createRequestContext(request);
        view.addObject("currentCompany", companyService.queryCurrentCompany(requestCxt));
        return view;
    }

    /**
     * CSH7020: csh_repayment_register_maintain_main
     */
    @RequestMapping(value = "csh/CSH7020/csh_repayment_register_maintain_main.screen")
    public ModelAndView cshRepaymentRegisterMaintainMainView(HttpServletRequest request,
                                                             Long registerHdsId,
                                                             Long moRepaymentRegTypeId,
                                                             Long employeeId,
                                                             Long accEntityId) {
        return processMaintainMainView(request, "csh/CSH7020/csh_repayment_register_maintain_main"
                , registerHdsId, moRepaymentRegTypeId, employeeId, accEntityId);
    }

    /**
     * CSH7020: csh_repayment_register_view_maintain_main
     */
    @RequestMapping(value = "csh/CSH7020/csh_repayment_register_view_maintain_main.screen")
    public ModelAndView cshRepaymentRegisterViewMaintainMainView(HttpServletRequest request, Long registerHdsId) {
        return processMaintainMainView(request, "csh/CSH7020/csh_repayment_register_view_maintain_main"
                , registerHdsId, null, null, null);
    }

    /**
     * CSH7020: csh_repayment_register_query_csh_payment_req_readonly
     */
    @RequestMapping(value = "csh/CSH7020/csh_repayment_register_query_csh_payment_req_readonly.screen")
    public ModelAndView cshPaymentReqReadonlyView(HttpServletRequest request, Long registerHdsId) {
        ModelAndView view = new ModelAndView("csh/CSH7020/csh_repayment_register_query_csh_payment_req_readonly");
        IRequest requestCxt = createRequestContext(request);
        view.addObject(HEAD_INFO, headerService.queryNumberAndType(requestCxt, registerHdsId));
        return view;
    }

    /**
     * CSH7030: csh_repayment_register_maintain
     */
    @RequestMapping(value = "/csh/CSH7030/csh_repayment_register_maintain.screen")
    public ModelAndView needAccounting(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/csh/CSH7030/csh_repayment_register_maintain");
        view.addObject("flag", parameterService.queryParamValueByCode("CSH_REPAYMENT_ACCOUNTING_FLAG"
                , null, null, null, null, null, null, null));
        return view;
    }

    /**
     * CSH7040: csh_repayment_register_maintain
     */
    @RequestMapping(value = "csh/CSH7040/csh_repayment_register_maintain.screen")
    public ModelAndView cshRepaymentRegisterMaintainView(HttpServletRequest request, Long registerHdsId) {
        ModelAndView view = new ModelAndView("csh/CSH7040/csh_repayment_register_maintain");
        IRequest requestCxt = createRequestContext(request);
        // 获取段值描述
        List<Map> segmentDescs = segmentService.querySegmentDesc(requestCxt);
        view.addObject("segmentDescs", segmentDescs);
        return view;
    }

    /**
     * CSH7070: csh_repayment_register_for_finance_query
     */
    @RequestMapping(value = "csh/CSH7070/csh_repayment_register_for_finance_query.screen")
    public ModelAndView cshRepaymentRegisterForFinanceQueryView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH7070/csh_repayment_register_for_finance_query");
        IRequest requestCtx = createRequestContext(request);
        view.addObject("payMethodList", paymentMethodService.selectAll(requestCtx));
        // 获取段值描述
        List<Map> segmentDescs = sobSegmentService.querySegmentDesc(requestCtx);
        view.addObject("segmentDescs", segmentDescs);
        return view;
    }

}

package com.hand.hec.acp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.interceptor.SecurityTokenInterceptor;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.service.IAcpRequisitionAccountService;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import com.hand.hec.csh.service.ICshMoPaymentReqTypeService;
import com.hand.hec.csh.service.ICshPaymentReqAccountService;
import com.hand.hec.csh.service.ICshTransactionLineService;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 付款申请单头信息控制器
 * </p>
 * 
 * @author guiyuting 2019/04/28 15:36
 */

@Controller
public class AcpRequisitionHdController extends BaseController {

    @Autowired
    private IExpDocumentAuthorityService authorityService;

    @Autowired
    private IAcpRequisitionHdService service;

    @Autowired
    private IAcpRequisitionAccountService acpRequisitionAccountService;

    @Autowired
    private IExpEmployeeService expEmployeeService;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;
    @Autowired
    private ICshPaymentReqAccountService cshPaymentReqAccountService;
    @Autowired
    private ICshMoPaymentReqTypeService cshMoPaymentReqTypeService;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private ICshTransactionLineService cshTransactionLineService;

    @RequestMapping(value = "/acp/requisition-hd/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryMain(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/acp/requisition-hd/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpRequisitionHd> dto, BindingResult result,
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

    @RequestMapping(value = "/acp/requisition-hd/delete")
    @ResponseBody
    public ResponseData delete(@RequestBody AcpRequisitionHd dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);

        return new ResponseData(service.deleteAcpRequisition(requestCtx, dto.getRequisitionHdsId()));
    }

    @RequestMapping(value = "/acp/requisition-hd/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpRequisitionHd> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/acp/ACP3100/acp_requisition_main.screen")
    public ModelAndView acpRequisitionMainView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("/acp/ACP3100/acp_requisition_main");

        // 获取授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory("ACP_REQUISITION");
        authority.setEmployeeId(requestCtx.getEmployeeId());
        List<ExpEmployee> authEmployeeList = authorityService.queryDocAuthEmployee(requestCtx, authority);

        view.addObject("authEmployee", authEmployeeList);

        return view;
    }

    @RequestMapping(value = "/acp/ACP3100/acp_requisition_maintain_type_choice.screen")
    public ModelAndView acpRequisitionMaintainTypeChoiceView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("acp/ACP3100/acp_requisition_maintain_type_choice");

        // 获取当前员工信息
        ExpEmployee employee = ExpEmployee.builder().employeeId(requestCtx.getEmployeeId()).build();
        employee = expEmployeeService.selectByPrimaryKey(requestCtx, employee);
        view.addObject("employee", employee);

        // 获取当前公司信息
        FndCompany company = FndCompany.builder().companyId(requestCtx.getCompanyId()).build();
        company = fndCompanyService.queryCurrentCompany(requestCtx).get(0);
        view.addObject("currentCompany", company);

        // 获取授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory("ACP_REQUISITION");
        authority.setEmployeeId(requestCtx.getEmployeeId());
        List<ExpEmployee> authEmployeeList = authorityService.queryDocAuthEmployee(requestCtx, authority);

        view.addObject("authEmployee", authEmployeeList);


        return view;
    }


    @RequestMapping(value = {"/acp/ACP3100/acp_requisition_maintain_main.screen",
            "/acp/ACP3100/acp_requisition_view_maintain_main.screen"})
    public ModelAndView acpRequisitionMaintainMainView(AcpRequisitionHd dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        String servletPath = request.getServletPath().substring(1).replace(".screen", "");
        Long magOrgId = requestContext.getMagOrgId();
        ModelAndView view = new ModelAndView(servletPath);
        AcpRequisitionHd acpRequisitionHd = service.selectByHeaderId(requestContext, dto);

        //
        // 设置Token，否则数据保存的时候提示无token
        // ------------------------------------------------------------------------------
        TokenUtils.generateAndSetToken(SecurityTokenInterceptor.LOCAL_SECURITY_KEY.get(), acpRequisitionHd);

        Map headerMap = BeanUtil.convert2Map(acpRequisitionHd);

        view.addObject("headerInfo", headerMap);

        return view;
    }


    @RequestMapping(value = "/acp/requisition-hd/submitAcpRequisition")
    @ResponseBody
    public ResponseData submitAcpRequisition(@RequestBody AcpRequisitionHd dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitAcpRequisition(requestCtx, dto.getRequisitionHdsId()));
    }

    @RequestMapping(value = "/acp/ACP5065/acp_requistion_audit.screen")
    public ModelAndView acpRequisitionAuditMainView(AcpRequisitionHd dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("/acp/ACP5065/acp_requistion_audit");

        // 获取默认公司默认的核算主体
        GldAccountingEntity gldAccountingEntity =
                        gldAccountingEntityService.queryDefaultAccEntity(requestCtx, requestCtx.getCompanyId());
        view.addObject("defaultAccEntity", gldAccountingEntity);

        // 获取段值描述
        List<Map> segmentDescs = gldSobSegmentService.querySegmentDesc(requestCtx);
        view.addObject("segmentDescs", segmentDescs);

        // 获取当前时间和期间
        List<Map> currentTime = cshPaymentReqAccountService.queryCurrentTime(requestCtx);
        view.addObject("currentTime", currentTime);
        return view;
    }

    /**
     * 通过公司查询核算主体
     * 
     * @param request
     * @return
     * @author zhognyu 2019-5-7
     */

    @RequestMapping(value = "/acp/requisition-hd/queryAccEntityByCompanyId",
                    method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAccEntity(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(
                        gldAccountingEntityService.queryAccEntityByCompany(requestCtx, requestCtx.getCompanyId()));
    }

    /**
     * 调用Ma的有效币种查询
     * 
     * @param request
     * @return
     * @author zhongyu 2019-5-7
     */
    @RequestMapping(value = "/acp/requisition-hd/queryCurrency", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCurrency(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(cshMoPaymentReqTypeService.currencyQuery(requestCtx));
    }


    @RequestMapping(value = "/acp/requisition-hd/getPeriodName")
    @ResponseBody
    public ResponseData getPeriodName(HttpServletRequest request, Date newDate, Long accEntityId) {
        IRequest requestCtx = createRequestContext(request);
        List<String> list = new ArrayList<String>();
        list.add(gldPeriodService.getPeriodName(requestCtx, newDate, accEntityId, null));
        return new ResponseData(list);
    }



    @RequestMapping(value = "/acp/requisition-hd/queryAuditResult", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAuditResult(AcpRequisitionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryAuditResult(requestCtx, dto, page, pageSize));
    }

    /**
     * 
     * 付款申请单创建凭证
     * 
     * @param accEntityId 核算主体ID
     * @param journalDate 凭证日期的期间
     * @author guiyuting 2019-05-08 10:19
     * @return
     */
    @RequestMapping(value = "/acp/requisition-hd/createAcpRequisitionAccount")
    @ResponseBody
    public ResponseData createAcpRequisitionAccount(HttpServletRequest request, Long accEntityId, Date journalDate,
                    @RequestBody List<AcpRequisitionHd> details) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.createAcpRequisitionAccount(requestCtx, accEntityId, journalDate, details));
    }

    /**
     * 付款申请单审核
     * 
     * @param request
     * @param list
     * @author zhongyu 2019-5-9
     * @return
     */

    @RequestMapping(value = "/acp/requisition-hd/auditAcpReq", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData auditAcpReq(HttpServletRequest request, @RequestBody List<AcpRequisitionHd> list) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(acpRequisitionAccountService.auditAcpReq(requestContext, list));


    }

    /**
     * 付款申请单拒绝
     * 
     * @param request
     * @param list
     * @author zhongyu 2019-5-9
     * @return
     */

    @RequestMapping(value = "/acp/requisition-hd/rejectAcpReq", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData rejectAcpReq(HttpServletRequest request, @RequestBody List<AcpRequisitionHd> list) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(acpRequisitionAccountService.rejectAcpReq(requestContext, list));


    }

    @RequestMapping(value = "/acp/requisition-hd/queryByHeadId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByHeadId(HttpServletRequest request,AcpRequisitionHd dto){
        IRequest requestContext = createRequestContext(request);
        List<AcpRequisitionHd> list = new ArrayList<AcpRequisitionHd>();
        list.add(service.selectByHeaderId(requestContext, dto));
        return new ResponseData(list);
    }

    @RequestMapping(value = "/acp/requisition-hd/queryCshTrx",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCshTrx(HttpServletRequest request,long requisitionHdsId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(cshTransactionLineService.queryByReqHdsId(requestContext,requisitionHdsId,page,pageSize));
    }

}

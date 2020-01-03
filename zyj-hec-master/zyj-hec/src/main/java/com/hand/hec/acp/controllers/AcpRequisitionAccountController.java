package com.hand.hec.acp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.acp.dto.AcpMoPayReqType;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.service.IAcpMoPayReqTypeService;
import com.hand.hec.acp.service.IAcpRequisitionAccountService;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 付款申请单凭证控制器
 * </p>
 * 
 * @author guiyuting 2019/05/23 16:32
 */

@Controller
@RequestMapping(value = "/acp/requisition-account")
public class AcpRequisitionAccountController extends BaseController{

    @Autowired
    private IAcpRequisitionAccountService service;

    @Autowired
    private IAcpMoPayReqTypeService acpMoPayReqTypeService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @Autowired
    private IAcpRequisitionHdService acpRequisitionHdService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpRequisitionAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpRequisitionAccount> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<AcpRequisitionAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryReqTypes",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryReqTypes(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        AcpMoPayReqType acpMoPayReqType = new AcpMoPayReqType();
        acpMoPayReqType.setEnabledFlag("Y");
        Criteria criteria = new Criteria(acpMoPayReqType);
        criteria.where(new WhereField(AcpMoPayReqType.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(acpMoPayReqTypeService. selectOptions(requestContext, acpMoPayReqType, criteria));
    }

    @RequestMapping(value = "/queryCurrency",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCurrency(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(gldCurrencyService.queryEnabledCurrency(requestContext));
    }

    /**
     * 付款申请单财务审核，不包括子公司
     * @param request
     * @param allCompanyFlag
     * @param page
     * @param pageSize
     * @param dto
     * @return
     */

    @RequestMapping(value = "/queryRequisition",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRequisition(HttpServletRequest request,String allCompanyFlag,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, AcpRequisitionHd dto){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(acpRequisitionHdService.queryRequisition(requestContext,dto,allCompanyFlag,page,pageSize));
    }

    /**
     * 付款申请单财务审核，包括子公司
     * @param request
     * @param page
     * @param pageSize
     * @param dto
     * @return
     */
    @RequestMapping(value = "/queryComRequisition",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryComRequisition(HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, AcpRequisitionHd dto){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(acpRequisitionHdService.queryComRequisition(requestContext,dto,page,pageSize));
    }
}
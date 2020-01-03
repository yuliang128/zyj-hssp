package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoPaymentReqType;
import com.hand.hec.csh.service.ICshMoPaymentReqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author dingwei.ma@hand-china.com
 */

@Controller
public class CshMoPaymentReqTypeController extends BaseController{

    @Autowired
    private ICshMoPaymentReqTypeService service;
    @Autowired
    private IFndManagingOrganizationService iFndManagingOrganizationService;

    @RequestMapping(value = "/csh/mo-payment-req-type/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoPaymentReqType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshMoPaymentReqType.FIELD_MAG_ORG_ID,Comparison.EQUAL)
                      ,new WhereField(CshMoPaymentReqType.FIELD_MO_PAYMENT_REQ_TYPE_CODE,Comparison.LIKE)
                      ,new WhereField(CshMoPaymentReqType.FIELD_DESCRIPTION,Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/csh/mo-payment-req-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoPaymentReqType> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/mo-payment-req-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoPaymentReqType> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/csh/mo-payment-req-type/magOrgQuery")
    @ResponseBody
    public ResponseData magOrgQuery(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.magOrgQuery(requestContext));
    }

    @RequestMapping(value = "/csh/mo-payment-req-type/currencyQuery")
    @ResponseBody
    public ResponseData currencyQuery(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.currencyQuery(requestContext));
    }

    @RequestMapping(value = "csh/CSH2011/csh_mo_payment_req_types.screen")
    public ModelAndView jumpCshMoPaymentReqTypes(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH2011/csh_mo_payment_req_types");
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganization = iFndManagingOrganizationService.queryDefaultMagOrg(requestContext);

        view.addObject("managingOrganization", fndManagingOrganization);
        return view;
    }

    @RequestMapping(value = "/csh/mo-payment-req-type/queryDftPayReqType")
    @ResponseBody
    public ResponseData queryDftPayReqType(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryDftPayReqType(requestContext));
    }

	@RequestMapping(value = "/csh/mo-payment-req-type/queryTypeForPayReq")
	@ResponseBody
	public ResponseData queryTypeForPayReq(HttpServletRequest request, Long employeeId, String moPaymentReqTypeCode,
			String description) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(
				service.queryTypeForPayReq(requestContext, employeeId, moPaymentReqTypeCode, description));
	}
}
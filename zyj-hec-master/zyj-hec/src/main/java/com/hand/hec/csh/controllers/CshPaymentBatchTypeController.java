package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentBatchType;
import com.hand.hec.csh.service.ICshPaymentBatchTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 付款批定义控制器
 *
 * @author zhaohui 2019/03/04 10:53
 */

@Controller
public class CshPaymentBatchTypeController extends BaseController{

    @Autowired
    private ICshPaymentBatchTypeService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "csh/CSH1260/csh_payment_batch_types.screen")
    public ModelAndView cshPaymentBatchTypesView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("csh/CSH1260/csh_payment_batch_types");
        FndManagingOrganization fndManagingOrganization = new FndManagingOrganization();
        fndManagingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(iRequest,iRequest.getCompanyId());
        modelAndView.addObject("CSH1260DefaultMagList",fndManagingOrganization);
        return modelAndView;
    }

    @RequestMapping(value = "/csh/payment-batch-type/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentBatchType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentBatchType.FIELD_TYPE_CODE, Comparison.LIKE),new WhereField(CshPaymentBatchType.FIELD_DESCRIPTION,Comparison.LIKE),CshPaymentBatchType.FIELD_MAG_ORG_ID);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/csh/payment-batch-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentBatchType> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-batch-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentBatchType> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
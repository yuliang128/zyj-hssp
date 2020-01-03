package com.hand.hec.vat.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.vat.dto.VatInvoiceCategory;
import com.hand.hec.vat.service.IVatInvoiceCategoryService;
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
 * @author xxx YYYY-mm-dd
 */

@Controller
public class VatInvoiceCategoryController extends BaseController{

    @Autowired
    private IVatInvoiceCategoryService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "vat/VAT1020/vat_invoice_category.screen")
    public ModelAndView vatInvoiceCategoryView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("vat/VAT1020/vat_invoice_category");
        FndManagingOrganization fndManagingOrganization = new FndManagingOrganization();
        fndManagingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(iRequest,iRequest.getCompanyId());
        modelAndView.addObject("VAT1020_defaultMagList",fndManagingOrganization);
        return modelAndView;
    }

    @RequestMapping(value = "/vat/invoice-category/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(VatInvoiceCategory dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(VatInvoiceCategory.FIELD_MAG_ORG_ID,new WhereField(VatInvoiceCategory.FIELD_DESCRIPTION, Comparison.LIKE),new WhereField(VatInvoiceCategory.FIELD_INVOICE_CATEGORY_CODE, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/vat/invoice-category/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<VatInvoiceCategory> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/vat/invoice-category/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<VatInvoiceCategory> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.fnd.dto.FndCustomerType;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.service.IFndCustomerTypeService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.fnd.service.IOrdSystemCustomerService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * OrdSystemCustomerController
 * </p>
 *
 * @author guiyuting 2019/02/20 15:38
 */

@Controller
public class OrdSystemCustomerController extends BaseController {

    @Autowired
    private IOrdSystemCustomerService service;

    @Autowired
    private IFndCustomerTypeService customerTypeService;

    @Autowired
    private IGldAccountingEntityService accountingEntityService;

    /**
     * 客户文件查询
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @author guiyuting 2019-02-21 16:37
     * @return
     */
    @RequestMapping(value = "/ord/system-customer/ordAeQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(OrdSystemCustomer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByAccEntity(requestContext, dto, page, pageSize));
    }

    /**
     * 系统级客户文件查询
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @author guiyuting 2019-02-21 16:37
     * @return
     */
    @RequestMapping(value = "/ord/system-customer/ordSysQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData ordSysQuery(OrdSystemCustomer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(OrdSystemCustomer.FIELD_CUSTOMER_TYPE_ID, Comparison.EQUAL),
                        new WhereField(OrdSystemCustomer.FIELD_CUSTOMER_CODE, Comparison.LIKE),
                        new WhereField(OrdSystemCustomer.FIELD_DESCRIPTION, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    /**
     * 客户文件提交
     *
     * @param dto
     * @param result
     * @param request
     * @author guiyuting 2019-02-21 16:36
     * @return
     */
    @RequestMapping(value = "/ord/system-customer/submitOrdAeCustomer")
    @ResponseBody
    public ResponseData update(@RequestBody List<OrdSystemCustomer> dto, BindingResult result,
                    HttpServletRequest request) throws OrdSystemCustomerException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitOrdAeCustomer(requestCtx, dto));
    }

    /**
     * 系统级客户文件提交
     *
     * @param dto
     * @param result
     * @param request
     * @author guiyuting 2019-02-21 16:36
     * @return
     */
    @RequestMapping(value = "/ord/system-customer/submitOrdSysCustomer")
    @ResponseBody
    public ResponseData submitOrdSysCustomer(@RequestBody List<OrdSystemCustomer> dto, BindingResult result,
                    HttpServletRequest request) throws OrdSystemCustomerException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitOrdSysCustomer(requestCtx, dto));
    }

    @RequestMapping(value = "/ord/system-customer/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<OrdSystemCustomer> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"fnd/FND2520/ord_ae_customer.screen", "fnd/FND2515/ord_system_customer.screen"})
    public ModelAndView ordAeCustomerView(HttpServletRequest request, Long accEntityId) {
        String servletPath = request.getServletPath().substring(1).replace(".screen", "");
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView(servletPath);
        Long companyId = (Long) request.getSession().getAttribute("companyId");
        List<FndCustomerType> aeCustomerTypeList = customerTypeService.queryByAccEntity(requestContext, accEntityId);
        GldAccountingEntity dftAccEntity = accountingEntityService.queryDefaultAccEntity(requestContext, companyId);
        view.addObject("aeCustomerTypeList", aeCustomerTypeList);
        view.addObject("dftAccEntity", dftAccEntity);
        return view;
    }

    /**
     * 系统级客户文件批量分配核算主体
     *
     * @param dto
     * @param result
     * @param request
     * @author guiyuting 2019-02-21 16:36
     * @return
     */
    @RequestMapping(value = "/ord/system-customer/batchAssignAccEntity")
    @ResponseBody
    public ResponseData batchAssignAccEntity(@RequestBody List<OrdSystemCustomer> dto, BindingResult result,
                    HttpServletRequest request) throws OrdSystemCustomerException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssignAccEntity(requestCtx, dto));
    }

}

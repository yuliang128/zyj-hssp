package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import com.hand.hec.fnd.service.IOrdCustomerAccountRefAeService;

/**
 * <p>
 * OrdCustomerAccountRefAeController
 * </p>
 * 
 * @author guiyuting 2019/02/22 13:44
 */

@Controller
@RequestMapping(value = "/ord/customer-account-ref-ae")
public class OrdCustomerAccountRefAeController extends BaseController {

    @Autowired
    private IOrdCustomerAccountRefAeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(OrdCustomerAccountRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(OrdCustomerAccountRefAe.FIELD_ACCOUNT_ID, Comparison.EQUAL),
                        new WhereField(OrdCustomerAccountRefAe.FIELD_ACC_ENTITY_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OrdCustomerAccountRefAe> dto, BindingResult result,
                    HttpServletRequest request) throws OrdSystemCustomerException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitSysCustomerAccountRefAe(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<OrdCustomerAccountRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

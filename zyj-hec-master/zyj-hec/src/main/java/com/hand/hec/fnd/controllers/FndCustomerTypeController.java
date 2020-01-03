package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.fnd.dto.FndCustomerType;
import com.hand.hec.fnd.service.IFndCustomerTypeService;

/**
 * <p>
 * 客户类型定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */

@Controller
@RequestMapping(value = "/ord/customer-type")
public class FndCustomerTypeController extends BaseController {

    @Autowired
    private IFndCustomerTypeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCustomerType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCustomerType.FIELD_CUSTOMER_TYPE_CODE, Comparison.LIKE)
                , new WhereField(FndCustomerType.FIELD_CUSTOMER_TYPE_ID, Comparison.EQUAL)
                , new WhereField(FndCustomerType.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(FndCustomerType.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCustomerType> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCustomerType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
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


import com.hand.hec.fnd.dto.FndCustomerTypeRefAe;
import com.hand.hec.fnd.service.IFndCustomerTypeRefAeService;

/**
 * <p>
 * 客户类型关联核算主体Controller
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */

@Controller
@RequestMapping(value = "/ord/customer-type-ref-ae")
public class FndCustomerTypeRefAeController extends BaseController {

    @Autowired
    private IFndCustomerTypeRefAeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCustomerTypeRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCustomerTypeRefAe.FIELD_REF_ID, Comparison.EQUAL)
                , new WhereField(FndCustomerTypeRefAe.FIELD_ACC_ENTITY_ID, Comparison.EQUAL)
                , new WhereField(FndCustomerTypeRefAe.FIELD_CUSTOMER_TYPE_ID, Comparison.EQUAL)
                , new WhereField(FndCustomerTypeRefAe.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCustomerTypeRefAe> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCustomerTypeRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询所有符合条件可以分配的核算主体
     *
     * @param dto      条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回符合条件的核算主体
     * @author xiuxian.wu 2019/2/15 10:16
     */
    @RequestMapping(value = "/queryAllAccountingEntity")
    @ResponseBody
    public ResponseData queryAllAccountingEntity(FndCustomerTypeRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAllAccountingEntity(dto,requestContext,page,pageSize));
    }

    /**
     * 批量插入客户类型与核算主体关联关系
     *
     * @param dto     关联关系
     * @param result
     * @param request
     * @return 插入数据
     * @author xiuxian.wu 2019/2/15 16:16
     */
    @RequestMapping(value = "/insertAccEntities")
    @ResponseBody
    public ResponseData insertAccEntities(@RequestBody List<FndCustomerTypeRefAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.insertAccEntities(requestCtx, dto));
    }

}
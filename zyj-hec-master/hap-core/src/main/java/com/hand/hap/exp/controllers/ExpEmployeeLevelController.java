package com.hand.hap.exp.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.exp.service.IExpEmployeeLevelService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 员工级别控制器
 *
 * @author zhongyu
 */

@Controller
@RequestMapping(value = "/exp/employee-level")
public class ExpEmployeeLevelController extends BaseController {

    @Autowired
    private IExpEmployeeLevelService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpEmployeeLevel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria();
        if (dto.getDescription() != null) {
            criteria.where(new WhereField(ExpEmployeeLevel.FIELD_DESCRIPTION, Comparison.LIKE));
        }
        if (dto.getEmployeeLevelsCode() != null) {
            criteria.where(new WhereField(ExpEmployeeLevel.FIELD_EMPLOYEE_LEVELS_CODE, Comparison.LIKE));
        }

        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/queryEmployeeLevel", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryEmployeeLevel(ExpEmployeeLevel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpEmpLevel(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpEmployeeLevel> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpEmployeeLevel> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


}
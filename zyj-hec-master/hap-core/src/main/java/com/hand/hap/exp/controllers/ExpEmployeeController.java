package com.hand.hap.exp.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 员工定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */

@Controller
@RequestMapping(value = "/exp/employee")
public class ExpEmployeeController extends BaseController {

    @Autowired
    private IExpEmployeeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpEmployee dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAllEmployeeByCondition(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpEmployee> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpEmployee> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryCurrentEmployee", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCurrentEmployee(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCurrentEmployee(requestContext));
    }

    @RequestMapping(value = "/queryEmployeeByAuth", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryEmployeeByAuth(HttpServletRequest request,String docCategory) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryEmployeeByAuth(requestContext,docCategory));
	}

	@RequestMapping(value = "/queryEmployeeByCompAuth", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryEmployeeByCompAuth(HttpServletRequest request,String docCategory) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryEmployeeByCompAuth(requestContext,docCategory));
	}
}
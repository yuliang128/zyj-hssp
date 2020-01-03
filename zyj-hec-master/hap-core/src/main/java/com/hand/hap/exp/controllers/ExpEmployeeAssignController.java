package com.hand.hap.exp.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 员工分配Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */


@Controller
@RequestMapping(value = "/exp/employee-assign")
public class ExpEmployeeAssignController extends BaseController {

    @Autowired
    private IExpEmployeeAssignService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpEmployeeAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpEmployeeAssign> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpEmployeeAssign> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询员工具体信息
     *
     * @param dto      员工信息（员工ID）
     * @param request
     * @param page
     * @param pageSize
     * @return 返回员工具体信息
     * @author xiuxian.wu 2019-02-25 14:43
     */
    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(ExpEmployeeAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpEmployeeAssign(requestContext, dto, page, pageSize));
    }
}
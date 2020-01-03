package com.hand.hec.exp.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpComEmpGroupRefEmp;
import com.hand.hec.exp.service.IExpComEmpGroupRefEmpService;

/**
 * <p>
 * 员工分配员工组Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */

@Controller
@RequestMapping(value = "/exp/com-emp-group-ref-emp")
public class ExpComEmpGroupRefEmpController extends BaseController {

    @Autowired
    private IExpComEmpGroupRefEmpService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpComEmpGroupRefEmp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpComEmpGroupRefEmp> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpComEmpGroupRefEmp> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询所有已分配给员工的员工组
     *
     * @param dto      查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回员工组
     * @author xiuxian.wu 2019-02-26 16:21
     */
    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(ExpComEmpGroupRefEmp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<ExpComEmpGroupRefEmp> list =service.queryAll(requestContext, dto, page, pageSize);
        return new ResponseData(list);
    }
}
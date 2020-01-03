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
import com.hand.hec.exp.dto.ExpMoExpPolicyAsgnCom;
import com.hand.hec.exp.service.IExpMoExpPolicyAsgnComService;

/**
 * <p>
 * 政策标准关联管理公司Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-policy-asgn-com")
public class ExpMoExpPolicyAsgnComController extends BaseController {

    @Autowired
    private IExpMoExpPolicyAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompanyByExpensePolicyId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyAsgnCom> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpPolicyAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查找剩余可以分配给政策标准的管理公司
     * @param dto 查找条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回剩余管理公司信息
     * @author xiuxian.wu 2019/02/20 15:14
     */
    @RequestMapping(value = "/queryRemainingCompanyByExpensePolicyId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRemainingCompanyByExpensePolicyId(ExpMoExpPolicyAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryRemainingCompanyByExpensePolicyId(requestContext, dto, page, pageSize));
    }

}
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

import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;
import com.hand.hec.exp.service.IExpMoExpPolicyCondLnsService;

/**
 * <p>
 * 政策标准明细条件行Controller
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-policy-cond-lns")
public class ExpMoExpPolicyCondLnsController extends BaseController {

    @Autowired
    private IExpMoExpPolicyCondLnsService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyCondLns dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyCondLns> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpPolicyCondLns> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询标准明细条件所有的数据
     *
     * @param request
     * @param pageSize
     * @param page
     * @param dto      查询条件
     * @return 返回所有明细条件数据
     * @author xiuxian.wu 2019-02-21 16:26
     */
    @RequestMapping(value = "/queryPolicyCondLns", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPolicyCondLns(ExpMoExpPolicyCondLns dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPolicyCondLns(requestContext, dto, page, pageSize));
    }

    /**
     * 保存政策标准标准明细条件数据
     *
     * @param dto
     * @param result
     * @param request
     * @return 已保存的政策标准标准明细条件数据
     * @author xiuxian.wu 2019/02/22 15:14
     */

    @RequestMapping(value = "/submitCondLns")
    @ResponseBody
    public ResponseData submitCondLns(@RequestBody List<ExpMoExpPolicyCondLns> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

}
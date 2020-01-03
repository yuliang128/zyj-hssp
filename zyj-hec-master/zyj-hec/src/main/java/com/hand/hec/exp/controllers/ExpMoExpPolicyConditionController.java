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

import com.hand.hec.exp.dto.ExpMoExpPolicyCondition;
import com.hand.hec.exp.service.IExpMoExpPolicyConditionService;

/**
 * <p>
 * 政策标准明细条件Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-policy-condition")
public class ExpMoExpPolicyConditionController extends BaseController {

    @Autowired
    private IExpMoExpPolicyConditionService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyCondition dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyCondition> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpPolicyCondition> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取所有匹配条件代码
     *
     * @param request
     * @param page
     * @param pageSize
     * @return 返回所有匹配条件代码
     * @author xiuxian.wu 2019/02/21 10:31
     */
    @RequestMapping(value = "/queryAllMatchingCondition", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAllMatchingCondition(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAllMatchingCondition(requestContext, page, pageSize));
    }

    /**
     * 增加修改数据
     *
     * @param dto     数据
     * @param result
     * @param request
     * @return 已经改变的数据
     * @author xiuxian.wu 2019-02-26 16:14
     */
    @RequestMapping(value = "/submitHds")
    @ResponseBody
    public ResponseData submitHds(@RequestBody List<ExpMoExpPolicyCondition> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitHds(requestCtx, dto));
    }
}
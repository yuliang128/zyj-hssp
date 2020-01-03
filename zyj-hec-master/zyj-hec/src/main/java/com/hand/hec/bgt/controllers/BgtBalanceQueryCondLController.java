package com.hand.hec.bgt.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondL;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondHService;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondLService;

/**
 * <p>
 * 预算余额查询方案行控制器
 * </p>
 *
 * @author YHL 2019/03/13 18:21
 */
@Controller
@RequestMapping(value = "/bgt/balance-query-cond-l")
public class BgtBalanceQueryCondLController extends BaseController {

    @Autowired
    private IBgtBalanceQueryCondLService service;

    @Autowired
    private IBgtBalanceQueryCondHService condHService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(BgtBalanceQueryCondL dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBalanceQueryCondL> dto, BindingResult result,
            HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBalanceQueryCondL> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取取值范围
     *
     * @param request
     * @author YHL 2019-03-18 18:37
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getControlRuleRange")
    public ResponseData getControlRuleRange(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getControlRuleRange(requestCtx));
    }

    /**
     * 根据预算余额查询方案头ID和预算组织ID查询预算余额查询方案（预算参数）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 18:36
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBalanceQueryCondLBgt")
    public ResponseData getBalanceQueryCondLBgt(HttpServletRequest request, Long balanceQueryCondHId, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBalanceQueryCondLBgt(requestCtx, balanceQueryCondHId, bgtOrgId));
    }

    /**
     * 根据预算余额查询方案头ID、公司ID和管理组织ID获取预算余额查询方案（组织相关）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 18:45
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBalanceQueryCondLOrg")
    public ResponseData getBalanceQueryCondLOrg(HttpServletRequest request, Long balanceQueryCondHId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBalanceQueryCondLOrg(requestCtx, balanceQueryCondHId));
    }

    /**
     * 根据预算余额查询方案头ID和公司ID获取预算余额查询方案（维度相关）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 19:05
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBalanceQueryCondLDim")
    public ResponseData getBalanceQueryCondLDim(HttpServletRequest request, Long balanceQueryCondHId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBalanceQueryCondLDim(requestCtx, balanceQueryCondHId));
    }

    /**
     * 保存预算余额查询方案行（获取对应的balanceQueryCondHId和sessionId）
     *
     * @param dto 预算余额查询方案行
     * @param result
     * @param request
     * @author YHL 2019-03-20 16:48
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/saveBalanceQueryCondL")
    @ResponseBody
    public ResponseData saveBalanceQueryCondL(@RequestBody List<BgtBalanceQueryCondL> dto, BindingResult result,
            HttpServletRequest request) throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors() || dto.isEmpty()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        for (BgtBalanceQueryCondL condL : dto) {
            service.insertBalanceQueryCondL(requestCtx, condL.getBalanceQueryConditionCode(),
                    request.getSession().getId(), condL.getParameter(), condL.getControlRuleRange(),
                    condL.getParameterCode(), condL.getParameterLowerLimit(), condL.getParameterUpperLimit());
        }
        return new ResponseData();
    }
}
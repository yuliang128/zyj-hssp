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
import com.hand.hec.bgt.dto.BgtBalanceQueryCondH;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondL;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondHService;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondLService;

/**
 * <p>
 * 预算余额查询方案头控制器
 * </p>
 *
 * @author YHL 2019/03/13 18:20
 */
@Controller
@RequestMapping(value = "/bgt/balance-query-cond-h")
public class BgtBalanceQueryCondHController extends BaseController {

    @Autowired
    private IBgtBalanceQueryCondHService service;

    @Autowired
    private IBgtBalanceQueryCondLService condLService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(BgtBalanceQueryCondH dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBalanceQueryCondH> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBalanceQueryCondH> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询预算余额查询方案
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 10:49
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBalanceQueryCondH")
    public ResponseData getBalanceQueryCondH(HttpServletRequest request, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBalanceQueryCondH(requestCtx, bgtOrgId));
    }

    /**
     * 保存预算余额查询方案头（将预算表代码、预算场景代码、预算版本代码设为null）
     *
     * @param bgtBalanceQueryCondH 预算余额查询方案头
     * @param result
     * @param request
     * @author YHL 2019-03-20 16:46
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/saveBalanceQueryCondH")
    @ResponseBody
    public ResponseData saveBalanceQueryCondH(@RequestBody BgtBalanceQueryCondH bgtBalanceQueryCondH,
            BindingResult result, HttpServletRequest request) throws BaseException {
        getValidator().validate(bgtBalanceQueryCondH, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        bgtBalanceQueryCondH.setBudgetStrcCode(null);
        bgtBalanceQueryCondH.setScenarioCode(null);
        bgtBalanceQueryCondH.setVersionCode(null);
        service.insertSelective(requestCtx, bgtBalanceQueryCondH);
        return new ResponseData();
    }

    @RequestMapping(value = "/deleteBalanceQueryCond")
    @ResponseBody
    public ResponseData deleteBalanceQueryCond(HttpServletRequest request,
            @RequestBody BgtBalanceQueryCondH bgtBalanceQueryCondH) {
        service.deleteByPrimaryKey(bgtBalanceQueryCondH);
        List<BgtBalanceQueryCondL> condLList = condLService
                .getBalanceQueryCondLByCondHId(bgtBalanceQueryCondH.getBalanceQueryCondHId());
        BgtBalanceQueryCondL bgtBalanceQueryCondL = new BgtBalanceQueryCondL();
        bgtBalanceQueryCondL.setBalanceQueryCondHId(bgtBalanceQueryCondH.getBalanceQueryCondHId());
        condLService.batchDelete(condLList);
        return new ResponseData();
    }

}
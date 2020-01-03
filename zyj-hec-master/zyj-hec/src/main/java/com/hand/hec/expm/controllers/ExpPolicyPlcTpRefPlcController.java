package com.hand.hec.expm.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.expm.dto.ExpPolicyPlcTpRefPlc;
import com.hand.hec.expm.service.IExpPolicyPlcTpRefPlcService;

/**
 * <p>
 * 费用政策地点类型分配政策地点Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */

@Controller
@RequestMapping(value = "/exp/policy-plc-tp-ref-plc")
public class ExpPolicyPlcTpRefPlcController extends BaseController {

    @Autowired
    private IExpPolicyPlcTpRefPlcService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpPolicyPlcTpRefPlc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpPolicyPlcTpRefPlc> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpPolicyPlcTpRefPlc> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查找所有已分配给费用政策类型的地点
     *
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 返回地点
     * @author xiuxian.wu 2019-02-27
     */
    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(ExpPolicyPlcTpRefPlc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    /**
     * 查询剩余费用政策类型的地点
     *
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 费用政策类型的地点
     * @author xiuxian.wu 2019-02-27
     */
    @RequestMapping(value = "/queryRemainPolicyPlace", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRemainPolicyPlace(ExpPolicyPlcTpRefPlc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryRemainPolicyPlace(requestContext, dto, page, pageSize));
    }
}
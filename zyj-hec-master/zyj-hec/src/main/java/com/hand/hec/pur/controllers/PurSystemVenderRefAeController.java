package com.hand.hec.pur.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.pur.dto.PurSystemVenderRefAe;
import com.hand.hec.pur.service.IPurSystemVenderRefAeService;

/**
 * <p>
 * 供应商分配核算主体控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/20 15:47
 */

@Controller
@RequestMapping(value = "/pur/system-vender-ref-ae")
public class PurSystemVenderRefAeController extends BaseController {

    @Autowired
    private IPurSystemVenderRefAeService service;

    /**
     * 基础查询
     *
     * @param venderId
     * @param page
     * @param pageSize
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author jialin.xing@hand-china.com 2019-02-20 17:48
     */
    @RequestMapping(value = "/query/{venderId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByVenderId(@PathVariable Long venderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByVenderId(requestContext, venderId, page, pageSize));
    }

    @RequestMapping(value = "/batch-assign/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAllAccEntity(HttpServletRequest request,Long venderId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryUnallocatedAccEntity(requestContext,venderId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurSystemVenderRefAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/batch-assign/submit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData submitBatchAssignment(HttpServletRequest request, @RequestBody List<PurSystemVenderRefAe> dto) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssign(requestContext, dto));
    }

    @RequestMapping(value = "/batch-assign/multiSubmit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData multiSubmitBatchAssignment(HttpServletRequest request, @RequestBody List<PurSystemVenderRefAe> accEntity) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.multiBatchAssign(requestContext, accEntity));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurSystemVenderRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

}
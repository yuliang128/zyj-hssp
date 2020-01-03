package com.hand.hec.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe;
import com.hand.hec.fnd.service.IGldExchangerateTpAsgnAeService;

/**
 * <p>
 * 汇率类型分配核算主体控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/08 16:11
 */

@Controller
@RequestMapping(value = "/gld/exchangerate-tp-asgn-ae")
public class GldExchangerateTpAsgnAeController extends BaseController {

    @Autowired
    private IGldExchangerateTpAsgnAeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldExchangerateTpAsgnAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/batch-assign/query")
    @ResponseBody
    public ResponseData queryByTypeId(HttpServletRequest request, Long typeId) {
        IRequest ctxRequest = createRequestContext(request);
        return new ResponseData(this.service.queryUnallocatedAccEntity(ctxRequest, typeId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldExchangerateTpAsgnAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/batch-assign/submit")
    @ResponseBody
    public ResponseData batchAssign(@RequestBody List<GldExchangerateTpAsgnAe> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        for (GldExchangerateTpAsgnAe eta:dto) {
            eta.setEnabledFlag(BaseConstants.YES);
            this.service.insertSelective(requestCtx,eta);
        }
        return new ResponseData(dto);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldExchangerateTpAsgnAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
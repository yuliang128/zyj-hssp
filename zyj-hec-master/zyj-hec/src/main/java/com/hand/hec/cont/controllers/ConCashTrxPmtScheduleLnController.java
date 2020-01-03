package com.hand.hec.cont.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.cont.dto.ConCashTrxPmtScheduleLn;
import com.hand.hec.cont.service.IConCashTrxPmtScheduleLnService;

/**
 *
 * 现金事务与合同资金计划行关联控制器
 *
 * @author Tagin 2019-04-03
 */

@Controller
@RequestMapping(value = "/con/cash-trx-pmt-schedule-ln")
public class ConCashTrxPmtScheduleLnController extends BaseController {

    @Autowired
    private IConCashTrxPmtScheduleLnService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ConCashTrxPmtScheduleLn dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ConCashTrxPmtScheduleLn> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ConCashTrxPmtScheduleLn> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBalanceDetailInit;
import com.hand.hec.bgt.service.IBgtBalanceDetailInitService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 预算保留临时表 控制器
 * </p>
 * 
 * @author guiyuting 2019/05/22 16:00
 */
@Controller
public class BgtBalanceDetailInitController extends BaseController {

    @Autowired
    private IBgtBalanceDetailInitService service;

    @RequestMapping(value = "/bgt/balance-detail-init/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(BgtBalanceDetailInit dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBgtBalanceInit(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/balance-detail-init/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBalanceDetailInit> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/balance-detail-init/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBalanceDetailInit> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/bgt/balance-detail-init/downDeal")
    @ResponseBody
    public ResponseData downDeal(HttpServletRequest request, @RequestBody List<BgtBalanceDetailInit> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.downDeal(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/public/bgt_budget_balance_details_down.screen")
    public ModelAndView bgtBudgetBalanceDetailsDownV(HttpServletRequest request, String reserveFlag) {
        ModelAndView mv = new ModelAndView("/bgt/public/bgt_budget_balance_details_down");
        mv.addObject("reserveFlag",reserveFlag);
        return mv;
    }
}

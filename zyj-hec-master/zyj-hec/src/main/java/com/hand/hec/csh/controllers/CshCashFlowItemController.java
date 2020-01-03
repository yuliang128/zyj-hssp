package com.hand.hec.csh.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshCashFlowItem;
import com.hand.hec.csh.service.ICshCashFlowItemService;
import com.hand.hec.gld.dto.GldSetOfBook;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class CshCashFlowItemController extends BaseController {

    @Autowired
    private ICshCashFlowItemService service;

    @RequestMapping(value = "/csh/cash-flow-item/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshCashFlowItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/cash-flow-item/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshCashFlowItem> dto, BindingResult result,
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

    @RequestMapping(value = "/csh/cash-flow-item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshCashFlowItem> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1100/csh_cash_flow_items.screen")
    public ModelAndView jumpCshCashFlowItem(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("csh/CSH1100/csh_cash_flow_items");
        IRequest requestContext = createRequestContext(request);
        GldSetOfBook setOfBook = service.defaultSetOfBook(requestContext);

        view.addObject("setOfBook", setOfBook);
        return view;
    }

    @RequestMapping(value = "/csh/cash-flow-item/queryForSob", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForSob(CshCashFlowItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForSob(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/cash-flow-item/queryCashItem", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCashItem(CshCashFlowItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCashItem(requestContext, dto, page, pageSize));
    }

    /**
     * @Description 根据核算主体（账套）获取现金流量项
     *
     * @Author Tagin
     * @Date 2019-03-07 16:39
     * @param setOfBooksId 账套ID
     * @param accEntityId 核算主体ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshCashFlowItem>
     * @Version 1.0
     **/
    @RequestMapping(value = "/csh/cash-flow-item/getCashFlowItem", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getCashFlowItem(HttpServletRequest request, Long setOfBooksId, Long accEntityId) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.getCashFlowItme(iRequest, setOfBooksId, accEntityId));
    }
}

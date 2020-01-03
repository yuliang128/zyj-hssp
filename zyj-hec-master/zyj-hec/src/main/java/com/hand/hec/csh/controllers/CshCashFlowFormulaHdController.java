package com.hand.hec.csh.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.csh.dto.CshCashFlowFormulaHd;
import com.hand.hec.csh.service.ICshCashFlowFormulaHdService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class CshCashFlowFormulaHdController extends BaseController{

    @Autowired
    private ICshCashFlowFormulaHdService service;

    @RequestMapping(value = "/csh/cash-flow-formula-hd/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshCashFlowFormulaHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/csh/cash-flow-formula-hd/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshCashFlowFormulaHd> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/cash-flow-formula-hd/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshCashFlowFormulaHd> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1100/csh_cash_flow_item_formula.screen")
    public ModelAndView jumpCshCashFlowItem(HttpServletRequest request,Long cashFlowItemId) {
        ModelAndView view = new ModelAndView("csh/CSH1100/csh_cash_flow_item_formula");
        IRequest requestContext = createRequestContext(request);
        CshCashFlowFormulaHd cashFlowFormulaHd = service.queryFormulaByItemId(requestContext,cashFlowItemId);

        view.addObject("cashFlowFormulaHd", cashFlowFormulaHd);
        return view;
    }

    @RequestMapping(value = "/csh/cash-flow-formula-hd/queryCashFlowLineNumber",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCashFlowLineNumber(Long cashFlowItemId, Long setOfBooksId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCashFlowLineNumber(requestContext,cashFlowItemId,setOfBooksId));
    }

}
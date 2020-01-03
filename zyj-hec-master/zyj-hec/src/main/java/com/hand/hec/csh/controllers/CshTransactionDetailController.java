package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshTransactionDetail;
import com.hand.hec.csh.service.ICshTransactionDetailService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * CshTransactionDetail控制器
 *
 * @author bo.zhang 2019-03-08
 */

@Controller
public class CshTransactionDetailController extends BaseController{

    @Autowired
    private ICshTransactionDetailService service;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @RequestMapping(value = "/csh/transaction-detail/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshTransactionDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/csh/transaction-detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshTransactionDetail> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/transaction-detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshTransactionDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1090/csh_offer_format_detail.screen")
    public ModelAndView cshOfferFormatDetailView(HttpServletRequest request ){
        List<CshTransactionDetail> cshTransactionDetailList =
                service.queryCshTransactionDetail();
        ModelAndView view = new ModelAndView("csh/CSH1090/csh_offer_format_detail");
        view.addObject("cshTransactionDetailList",cshTransactionDetailList);
        return view;
    }

    @RequestMapping(value = "csh/public/csh_payment_transaction_detail.screen")
    public ModelAndView cshPaymentTraDetailView(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        ModelAndView view=new ModelAndView("csh/public/csh_payment_transaction_detail");
        List<Map> segmentDescs=gldSobSegmentService.querySegmentDesc(requestContext);
        view.addObject("segmentDescs", segmentDescs);
        return view;
    }

}
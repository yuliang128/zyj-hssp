package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalBalanceExtend;
import com.hand.hec.bgt.service.IBgtJournalBalanceExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;
/**
 * <p>
 * 预算日记账余额扩展Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:40
 */
@Controller
public class BgtJournalBalanceExtendController extends BaseController{

    @Autowired
    private IBgtJournalBalanceExtendService service;

    @RequestMapping(value = "/bgt/journal/balance/extend/query")
    @ResponseBody
    public ResponseData query(BgtJournalBalanceExtend dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/journal/balance/extend/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalBalanceExtend> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/journal/balance/extend/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtJournalBalanceExtend> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
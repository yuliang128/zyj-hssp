package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalBalanceExtendBak;
import com.hand.hec.bgt.service.IBgtJournalBalanceExtendBakService;
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
 * 预算日记账余额扩展备份Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:40
 */
@Controller
public class BgtJournalBalanceExtendBakController extends BaseController{

    @Autowired
    private IBgtJournalBalanceExtendBakService service;

    @RequestMapping(value = "/bgt/journal/balance/extend/bak/query")
    @ResponseBody
    public ResponseData query(BgtJournalBalanceExtendBak dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/journal/balance/extend/bak/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalBalanceExtendBak> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/journal/balance/extend/bak/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtJournalBalanceExtendBak> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
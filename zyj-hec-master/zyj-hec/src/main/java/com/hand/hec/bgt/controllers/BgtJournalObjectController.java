package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalObject;
import com.hand.hec.bgt.service.IBgtJournalObjectService;
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
 * 预算日记账费用对象Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:29
 */
@Controller
public class BgtJournalObjectController extends BaseController{

    @Autowired
    private IBgtJournalObjectService service;

    @RequestMapping(value = "/bgt/journal/object/query")
    @ResponseBody
    public ResponseData query(BgtJournalObject dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/journal/object/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalObject> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/journal/object/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtJournalObject> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBudgetStrcAuthority;
import com.hand.hec.bgt.service.IBgtBudgetStrcAuthorityService;
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
 * 预算表授权Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:27
 */
@Controller
public class BgtBudgetStrcAuthorityController extends BaseController{

    @Autowired
    private IBgtBudgetStrcAuthorityService service;

    @RequestMapping(value = "/bgt/budget/strc/authority/query")
    @ResponseBody
    public ResponseData query(BgtBudgetStrcAuthority dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/budget/strc/authority/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetStrcAuthority> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/budget/strc/authority/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtBudgetStrcAuthority> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
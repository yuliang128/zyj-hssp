package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
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
 * 预算占用Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:27
 */
@Controller
public class BgtBudgetReserveController extends BaseController{

    @Autowired
    private IBgtBudgetReserveService service;

    @RequestMapping(value = "/bgt/budget/reserve/query")
    @ResponseBody
    public ResponseData query(BgtBudgetReserve dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/budget/reserve/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetReserve> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/budget/reserve/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtBudgetReserve> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
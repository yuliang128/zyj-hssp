package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnCom;
import com.hand.hec.bgt.service.IBgtBudgetItemAsgnComService;
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
 * 预算项目分配公司Controller
 * </p>
 *
 * @author mouse 2019/01/07 14:56
 */
@RequestMapping(value = "/bgt/budget-item-asgn-com")
@Controller
public class BgtBudgetItemAsgnComController extends BaseController{

    @Autowired
    private IBgtBudgetItemAsgnComService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtBudgetItemAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetItemAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtBudgetItemAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
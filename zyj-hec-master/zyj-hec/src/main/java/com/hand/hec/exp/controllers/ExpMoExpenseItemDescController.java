package com.hand.hec.exp.controllers;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpMoExpenseItemDesc;
import com.hand.hec.exp.service.IExpMoExpenseItemDescService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 费用项目说明定义Controllrt
 * </p>
 *
 * @author yang.cai 2019/02/28 18:19
 */

@Controller
@RequestMapping(value = "/exp/mo-expense-item-desc")
public class ExpMoExpenseItemDescController extends BaseController{

    @Autowired
    private IExpMoExpenseItemDescService service;

    @Autowired
    private IFndManagingOrganizationService managingOrganizationService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpenseItemDesc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpenseItemDesc> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpenseItemDesc> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganization = managingOrganizationService
                .queryDefaultMagOrg(requestContext);
        ModelAndView view = new ModelAndView("exp/EXP2160/exp_mo_expense_item_desc");
        view.addObject("EXP2160_defaultMag", fndManagingOrganization);
        return view;
    }

    @RequestMapping(value = "/queryAll",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(ExpMoExpenseItemDesc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext,dto,page,pageSize));
    }
}
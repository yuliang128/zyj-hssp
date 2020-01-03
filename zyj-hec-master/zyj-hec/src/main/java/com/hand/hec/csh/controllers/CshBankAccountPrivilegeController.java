package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshBankAccountPrivilege;
import com.hand.hec.csh.service.ICshBankAccountPrivilegeService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * CshBankAccountPrivilege
 *
 * @author zhangbo 2019/02/22 14:21
 */

@Controller
public class CshBankAccountPrivilegeController extends BaseController{

    @Autowired
    private ICshBankAccountPrivilegeService service;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;


    @RequestMapping(value = "/csh/bank-account-privilege/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshBankAccountPrivilege dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectCshBankAccount(dto.getAccEntityId(),dto.getBankCode(),dto.getBankName(),requestContext));
    }

    @RequestMapping(value = "/csh/bank-account-privilege/queryPrivilege",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPrivilege(CshBankAccountPrivilege dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectCshBankAccountPrivilege(dto.getBankAccountId(),requestContext));
    }

    @RequestMapping(value = "/csh/bank-account-privilege/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshBankAccountPrivilege> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));

    }

    @RequestMapping(value = "/csh/bank-account-privilege/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshBankAccountPrivilege> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1035/csh_bank_account_privilege_main.screen")
    public ModelAndView cshBankAccountPrivilegeMainView(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        List<GldAccountingEntity> gldAccountingEntityList =
                gldAccountingEntityService.selectAccEntityName(requestContext);
        ModelAndView view = new ModelAndView("csh/CSH1035/csh_bank_account_privilege_main");
        view.addObject("gldAccountingEntityList",gldAccountingEntityList);
        return view;
    }
}
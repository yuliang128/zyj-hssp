package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAccountSet;
import com.hand.hec.fnd.mapper.GldAccountSetMapper;
import com.hand.hec.gld.dto.GldSapReconAccount;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.service.IGldSapReconAccountService;
import com.hand.hec.gld.service.IGldSetOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * SAP统驭科目定义控制器
 *
 * @author zhaohui 2019/02/28 14:45
 */

@Controller
public class GldSapReconAccountController extends BaseController{

    @Autowired
    private IGldSapReconAccountService service;

    @Autowired
    private IGldSetOfBookService gldSetOfBookService;

    @Autowired
    private GldAccountSetMapper gldAccountSetMapper;

    @RequestMapping(value = "/gl/GL3001/gld_sap_recon_accounts_set_select_detail.screen")
    public ModelAndView gldSapReconAccountsSetSelectDetailView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("gl/GL3001/gld_sap_recon_accounts_set_select_detail");
        List<GldSetOfBook> gldSetOfBooks = gldSetOfBookService.selectDefaultSobByMagOrgId(iRequest);
        modelAndView.addObject("defaultSob",gldSetOfBooks);
        GldAccountSet gldAccountSet = gldAccountSetMapper.getInitAccountSet();
        modelAndView.addObject("initQuery",gldAccountSet);
        return modelAndView;
    }

    @RequestMapping(value = "/gld/sap-recon-account/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSapReconAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(GldSapReconAccount.FIELD_SET_OF_BOOKS_ID,GldSapReconAccount.FIELD_ACCOUNT_SET_ID,GldSapReconAccount.FIELD_ACC_ENTITY_ID);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/gld/sap-recon-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSapReconAccount> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/sap-recon-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSapReconAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
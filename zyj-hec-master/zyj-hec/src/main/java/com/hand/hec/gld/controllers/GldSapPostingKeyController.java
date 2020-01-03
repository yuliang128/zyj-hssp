package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAccountSet;
import com.hand.hec.fnd.mapper.GldAccountSetMapper;
import com.hand.hec.gld.dto.GldSapPostingKey;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.service.IGldSapPostingKeyService;
import com.hand.hec.gld.service.IGldSetOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * SAP记账码设置控制器
 *
 * @author zhaohui 2019/02/28 14:45
 */

@Controller
public class GldSapPostingKeyController extends BaseController{

    @Autowired
    private IGldSapPostingKeyService service;

    @Autowired
    private IGldSetOfBookService gldSetOfBookService;

    @Autowired
    private GldAccountSetMapper gldAccountSetMapper;

    @RequestMapping(value = "gl/GL3002/gld_sap_posting_key_set_select_detail.screen")
    public ModelAndView gldSapPostingKeySetSelectDetailView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("gl/GL3002/gld_sap_posting_key_set_select_detail");
        List<GldSetOfBook> gldSetOfBooks = gldSetOfBookService.selectDefaultSobByMagOrgId(iRequest);
        modelAndView.addObject("defaultSob",gldSetOfBooks);
        GldAccountSet gldAccountSet = gldAccountSetMapper.getInitAccountSet();
        modelAndView.addObject("initQuery",gldAccountSet);
        return modelAndView;
    }

    @RequestMapping(value = "/gld/sap-posting-key/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSapPostingKey dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/gld/sap-posting-key/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSapPostingKey> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/sap-posting-key/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSapPostingKey> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshOfferFormatHds;
import com.hand.hec.csh.service.ICshOfferFormatHdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author zhangbo 2019-03-21
 */

@Controller
public class CshOfferFormatHdsController extends BaseController{

    @Autowired
    private ICshOfferFormatHdsService service;

    @RequestMapping(value = "/csh/offer-format-hds/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshOfferFormatHds dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCshOfferFormatHds(dto.getFormatCode(),dto.getDescription(),requestContext));
    }

    @RequestMapping(value = "/csh/offer-format-hds/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshOfferFormatHds> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/offer-format-hds/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshOfferFormatHds> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "csh/CSH1090/csh_offer_format.screen")
    public ModelAndView cshOfferFormat(HttpServletRequest request ){
        IRequest requestContext = createRequestContext(request);
        List<CshOfferFormatHds> cshOfferFormatHdsList =
               service.querySysCode(requestContext);
        ModelAndView view = new ModelAndView("csh/CSH1090/csh_offer_format");
        view.addObject("cshOfferFormatHdsList",cshOfferFormatHdsList);
        return view;
    }
}
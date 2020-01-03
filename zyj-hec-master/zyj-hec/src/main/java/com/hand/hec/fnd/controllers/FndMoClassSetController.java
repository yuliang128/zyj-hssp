package com.hand.hec.fnd.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hec.fnd.dto.FndMoClassSet;
import com.hand.hec.fnd.service.IFndMoClassSetService;

/**
 *
 * 管理组织级分类集控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class FndMoClassSetController extends BaseController{

    @Autowired
    private IFndMoClassSetService service;

    @Autowired
    private IFndManagingOrganizationService magOrgService;

    @RequestMapping(value = "fnd/mo-class-set/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndMoClassSet dto,Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        dto.setMagOrgId(magOrgId);
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.baseSelect(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "fnd/mo-class-set/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndMoClassSet> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "fnd/mo-class-set/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndMoClassSet> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value="fnd/FND1200/fnd_mo_class_sets.screen")
    public ModelAndView fndMoClassSetsView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("fnd/FND1200/fnd_mo_class_sets");
        IRequest requestCtx = createRequestContext(request);
        FndManagingOrganization magOrg = magOrgService.defaultManageOrganizationQuery(requestCtx,requestCtx.getCompanyId());
        view.addObject("defaultMagOrg",magOrg);
        return view;
    }

}
package com.hand.hec.expm.controllers;

import com.hand.hec.expm.dto.ExpPolicyDistrict;
import com.hand.hec.expm.service.IExpPolicyDistrictService;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.expm.dto.ExpPolicyPlace;
import com.hand.hec.expm.service.IExpPolicyPlaceService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller

public class ExpPolicyPlaceController extends BaseController{

    @Autowired
    private IExpPolicyPlaceService service;
    @Autowired
    private IExpPolicyDistrictService expPolicyDistrictService;

    @RequestMapping(value = "/exp/policy-place/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpPolicyPlace dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value ="/exp/policy-place/queryPolicyPlace",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPolicyPlace(ExpPolicyPlace dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryPolicyPlace(requestCtx, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/policy-place/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpPolicyPlace> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/policy-place/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpPolicyPlace> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 获取费用政策
     *
     * @param request
     * @param expPolicyDistrict
     * @return
     * @author ZhongYu 2019-2-20 15:40
     */
    @RequestMapping(value = "/expm/EXP1320/exp_policy_place.screen")
    public ModelAndView expPolicyPlaceView(HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    ExpPolicyDistrict expPolicyDistrict,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP1320/exp_policy_place");
        List<ExpPolicyDistrict>  expPolicyDistricts = expPolicyDistrictService.select(requestCtx,expPolicyDistrict,page,pageSize);
        view.addObject("expPolicyDistricts",expPolicyDistricts);
        return view;
    }
}
package com.hand.hec.exp.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpMoExpPolicyCondHds;
import com.hand.hec.exp.service.IExpMoExpPolicyCondHdsService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 政策标准明细条件头Controller
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */

@Controller
public class ExpMoExpPolicyCondHdsController extends BaseController {

    @Autowired
    private IExpMoExpPolicyCondHdsService service;

    @RequestMapping(value = "/exp/mo-exp-policy-cond-hds/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyCondHds dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-exp-policy-cond-hds/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyCondHds> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-exp-policy-cond-hds/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpPolicyCondHds> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询组件是否显示
     * @param dto
     * @param request
     * @return 返回需要显示的组件的状态
     * @author xiuxian.wu 2019-02-22 16:01
     */
    @RequestMapping(value = "/exp/EXP1170/exp_mo_exp_policy_cond_lns.screen")
    public ModelAndView expMoExpPolicyCondLns(ExpMoExpPolicyCondHds dto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("exp/EXP1170/exp_mo_exp_policy_cond_lns");
        view.addAllObjects(service.queryAllMatchItemCode(dto.getConditionId()));
        view.addObject("magOrgId", dto.getMagOrgId());
        view.addObject("detailId", dto.getDetailId());
        view.addObject("conditionId", dto.getConditionId());
        return view;
    }
}
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssign;
import com.hand.hec.exp.service.IExpMoExpPolicyAssignService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 费用政策分配控制器
 *
 * @author zhongyu
 */

@Controller
public class ExpMoExpPolicyAssignController extends BaseController{

    @Autowired
    private IExpMoExpPolicyAssignService service;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-exp-policy-assign/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/exp/mo-exp-policy-assign/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyAssign> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-exp-policy-assign/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpPolicyAssign> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 获取默认公司下的管理组织的值
     * @param request
     * @return
     * @author zhongyu 2019-02-28 15:36
     */
    @RequestMapping(value="/exp/EXP1160/exp_mo_exp_policy_assign.screen")
    public ModelAndView expMoExpPolicyAssignView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("exp/EXP1160/exp_mo_exp_policy_assign");
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(requestContext,requestContext.getCompanyId());
        if (fndManagingOrganization != null) {
            view.addObject("magOrgId",fndManagingOrganization.getMagOrgId());
            view.addObject("magOrgName",fndManagingOrganization.getDescription());
        }
        return view;
    }

    /**
     * 单据主页面管理组织查询条件【根据默认管理组织和参数 FND_ALL_ORGANIZATIONAL限制查询条件】
     * @param dto
     * @param request
     * @return
     * @author zhongyu 2019-2-28 15:11
     */
    @RequestMapping(value = "/exp/mo-exp-policy-assign/queryFndMagOra",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFndMagOra(FndManagingOrganization dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.queryFndMagOra(requestContext));
    }
}
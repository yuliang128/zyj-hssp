package com.hand.hec.exp.controllers;

import com.hand.hec.exp.dto.ExpLevelSeries;
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

import com.hand.hec.exp.dto.ExpEmployeeJob;
import com.hand.hec.exp.service.IExpEmployeeJobService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 员工职务控制器
 *
 * @author zhongyu
 */

@Controller
public class ExpEmployeeJobController extends BaseController{

    @Autowired
    private IExpEmployeeJobService service;

    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;


    @RequestMapping(value = "/exp/employee-job/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpEmployeeJob dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryEmpJobs(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/exp/employee-job/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpEmployeeJob> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/employee-job/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpEmployeeJob> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/employee-job/queryForCb",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForCb(ExpEmployeeJob dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForCb(requestContext,dto));
    }

    /**
     * 级别系列查询
     *
     * @param request
     * @param dto
     * @return 返回级别系列信息
     * @author ZhongYu
     */

    @RequestMapping(value = "/exp/employee-job/queryForLevelService",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForLevelService(ExpLevelSeries dto,HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForLevelService(requestContext,dto));
    }

    /**
     * 获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author ZhongYu
     */
    @RequestMapping(value = "/exp/EXP1040/exp_employee_jobs.screen")
    public ModelAndView expEmployeeJobsView(FndManagingOrganization dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("exp/EXP1040/exp_employee_jobs");
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }

    /**
     * 获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author ZhongYu
     */
    @RequestMapping(value = "/exp/employee-job/queryForManagingOrganization",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForManagingOrganization(FndManagingOrganization dto, HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        Long roleId = requestContext.getRoleId();
        Long userId = requestContext.getUserId();
        Long magOrgId = requestContext.getMagOrgId();
        return new ResponseData(service.queryForManagingOrganization(requestContext,dto,roleId,userId,magOrgId));
    }
}
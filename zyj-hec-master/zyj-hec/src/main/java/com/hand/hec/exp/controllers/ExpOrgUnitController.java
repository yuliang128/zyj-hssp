package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpOrgUnitService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * ExpOrgUnitController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */

@Controller
public class ExpOrgUnitController extends BaseController {

    @Autowired
    private IExpOrgUnitService service;

    @Autowired
    private IGldAccountingEntityService accountingEntityService;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @RequestMapping(value = "/exp/org-unit/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgUnit dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    /**
     * 根据公司查询部门信息
     *
     * @param dto
     * @return
     * @author guiyuting 2019-01-26 18:37
     */
    @RequestMapping(value = "/exp/org-unit/queryByCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByCompany(ExpOrgUnit dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByCompany(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/org-unit/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgUnit> dto, BindingResult result, HttpServletRequest request) throws UnitAccOrBgtDfMultiException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitExpOrgUnit(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/org-unit/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpOrgUnit> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/org-unit/queryAssignUnit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAssignUnit(ExpOrgUnit dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAssignUnit(requestContext, dto));
    }

    @RequestMapping(value = "exp/EXP1010/exp_org_unit.screen")
    public ModelAndView expOrgUnitView(HttpServletRequest request, Long accountSetId, Long accountId) {
        ModelAndView view = new ModelAndView("exp/EXP1010/exp_org_unit");
        IRequest requestContext = createRequestContext(request);
        GldAccountingEntity defaultAccEntity = accountingEntityService.queryDefaultAccEntity(requestContext, requestContext.getCompanyId());
        BgtEntity defaultBgtEntity = bgtEntityService.queryDefaultBgtEntity(requestContext, requestContext.getCompanyId());
        view.addObject("defaultAccEntity", defaultAccEntity);
        view.addObject("defaultBgtEntity", defaultBgtEntity);
        return view;
    }

    @RequestMapping(value = "/exp/org-unit/getUnitForEmployeeAssign")
    @ResponseBody
    public ResponseData getUnitForEmployeeAssign(@RequestParam(required = true) Long employeeId,
            @RequestParam(required = true) Long companyId,
            @RequestParam(required = false) String unitCode, @RequestParam(required = false) String unitName,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getUnitForEmployeeAssign(requestContext, employeeId, companyId, unitCode,unitName));
    }
}

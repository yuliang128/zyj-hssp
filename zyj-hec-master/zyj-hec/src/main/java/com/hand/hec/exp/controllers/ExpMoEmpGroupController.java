package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
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

import com.hand.hec.exp.dto.ExpMoEmpGroup;
import com.hand.hec.exp.service.IExpMoEmpGroupService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 员工组定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */

@Controller
public class ExpMoEmpGroupController extends BaseController {

    @Autowired
    private IExpMoEmpGroupService service;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-emp-group/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoEmpGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoEmpGroup.FIELD_MO_EMP_GROUP_ID, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroup.FIELD_MAG_ORG_ID, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroup.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroup.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(ExpMoEmpGroup.FIELD_MO_EMP_GROUP_CODE, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-emp-group/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoEmpGroup> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-emp-group/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoEmpGroup> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 员工组获取默认管理组织或获取选定的管理组织
     *
     * @param request
     * @param dto
     * @return 返回默认组织信息
     * @author xiuxian.wu 2019/03/06 15:52
     */
    @RequestMapping(value = "/exp/EXP2410/exp_mo_emp_groups.screen")
    public ModelAndView expMoEmpGroups(FndManagingOrganization dto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("exp/EXP2410/exp_mo_emp_groups");
        IRequest requestCtx = createRequestContext(request);
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }
}
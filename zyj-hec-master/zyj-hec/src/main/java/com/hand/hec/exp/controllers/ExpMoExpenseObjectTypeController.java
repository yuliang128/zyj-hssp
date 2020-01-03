package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoExpenseObjectType;
import com.hand.hec.exp.mapper.ExpMoExpenseObjectTypeMapper;
import com.hand.hec.exp.service.IExpMoExpenseObjectTypeService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 费用对象类型控制器
 *
 * @author jiangxz 2019-03-01
 */

@Controller
public class ExpMoExpenseObjectTypeController extends BaseController {

    @Autowired
    private IExpMoExpenseObjectTypeService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    ExpMoExpenseObjectTypeMapper typeMapper;

    @RequestMapping(value = "/exp/mo-expense-object-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpenseObjectType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_ID),
                        new WhereField(ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_CODE, Comparison.LIKE),
                        new WhereField(ExpMoExpenseObjectType.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(ExpMoExpenseObjectType.FIELD_MAG_ORG_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-expense-object-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpenseObjectType> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-expense-object-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpenseObjectType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/EXP1086/exp_mo_expense_object_type.screen")
    public ModelAndView queryExpMoExpenseObjectTypeController(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("exp/EXP1086/exp_mo_expense_object_type");
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }

    @RequestMapping(value = "/exp/mo-expense-object-type/sqlValidate", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData sqlValidate(@RequestBody ExpMoExpenseObjectType dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.sqlValidate(dto, requestContext));
    }

    @RequestMapping(value = "/exp/mo-expense-object-type/lov-query")
    @ResponseBody
    public ResponseData lovQuery(@RequestParam(required = false) Long moExpObjTypeId,
                    @RequestParam(required = false) String code, @RequestParam(required = false) String name,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);

        ExpMoExpenseObjectType type = typeMapper.getExpMoExpenseObjectType(moExpObjTypeId);
        if (type == null) {
            return new ResponseData(Collections.emptyList());
        }

        return new ResponseData(service.queryMoExpObjValueLov(requestCtx, type, code, name));
    }
}

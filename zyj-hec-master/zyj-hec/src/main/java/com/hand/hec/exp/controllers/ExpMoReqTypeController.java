package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.service.IExpMoReqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/19 15:36
 * Description:申请单类型控制器
 */

@Controller
public class ExpMoReqTypeController extends BaseController {

    @Autowired
    private IExpMoReqTypeService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-req-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReqType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoReqType.FIELD_MO_EXP_REQ_TYPE_CODE, Comparison.LIKE), new WhereField(ExpMoReqType.FIELD_DESCRIPTION, Comparison.LIKE), new WhereField(ExpMoReqType.FIELD_MAG_ORG_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-req-type/queryChoiceRequisitionTypeInfor", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryChoiceRequisitionTypeInfor(ExpMoReqType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryChoiceRequisitionTypeInfor(requestContext));
    }

    @RequestMapping(value = "/exp/mo-req-type/queryExpReqTypeChoiceCurrentInfor", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryExpReqTypeChoiceCurrentInfor(Long employeeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpReqTypeChoiceCurrentInfor(employeeId, requestContext));
    }

    @RequestMapping(value = "/exp/mo-req-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReqType> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-req-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReqType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/EXP4020/exp_mo_req_type.screen")
    public ModelAndView queryMagReqTypeController(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("exp/EXP4020/exp_mo_req_type");
        FndManagingOrganization organization = fndManagingOrganizationService.queryDefaultMagOrg(requestCtx);
        if (organization != null) {
            view.addObject("magOrgCodeName", organization.getMagOrgCodeName());
            view.addObject("magOrgId", organization.getMagOrgId());
            view.addObject("magOrgCode", organization.getMagOrgCode());
            view.addObject("description", organization.getDescription());
        }
        return view;
    }
}
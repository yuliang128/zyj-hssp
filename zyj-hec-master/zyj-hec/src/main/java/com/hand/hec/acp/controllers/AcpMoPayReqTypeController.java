package com.hand.hec.acp.controllers;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
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
import com.hand.hec.acp.dto.AcpMoPayReqType;
import com.hand.hec.acp.service.IAcpMoPayReqTypeService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 管理组织级付款申请单类型控制器
 *
 * @author luhui 2019-03-01
 */

@Controller
public class AcpMoPayReqTypeController extends BaseController {

    @Autowired
    private IAcpMoPayReqTypeService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/acp/mo-pay-req-type/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpMoPayReqType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(AcpMoPayReqType.FIELD_MO_PAY_REQ_TYPE_CODE, Comparison.LIKE),
                        new WhereField(AcpMoPayReqType.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(AcpMoPayReqType.FIELD_MAG_ORG_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/acp/mo-pay-req-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpMoPayReqType> dto, BindingResult result,
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

    @RequestMapping(value = "/acp/mo-pay-req-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpMoPayReqType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "acp/ACP1135/acp_mo_pay_req_type.screen")
    public ModelAndView acpMoPayReqTypeView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganizationList = fndManagingOrganizationService
                        .defaultManageOrganizationQuery(requestContext, requestContext.getCompanyId());
        ModelAndView view = new ModelAndView("acp/ACP1135/acp_mo_pay_req_type");
        view.addObject("defaultMagList", fndManagingOrganizationList);
        return view;

    }

    @RequestMapping(value = "/acp/mo-pay-req-type/queryByCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByCompany(Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByCompany(requestContext, companyId));
    }

    @RequestMapping(value = "/acp/mo-pay-req-type/getAcpMoPayReqTypeList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getAcpMoPayReqTypeList(Long employeeId, Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getAcpMoPayReqTypeList(requestContext,employeeId, companyId));
    }
}

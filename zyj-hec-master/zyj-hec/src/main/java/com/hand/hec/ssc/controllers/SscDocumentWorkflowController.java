package com.hand.hec.ssc.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscDocumentWorkflow;
import com.hand.hec.ssc.service.ISscDocumentWorkflowService;

/**
 * 单据分配工作流程控制器
 *
 * @author luhui 2019-03-20
 */

@Controller
public class SscDocumentWorkflowController extends BaseController {

    @Autowired
    private ISscDocumentWorkflowService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/ssc/document-workflow/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscDocumentWorkflow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/ssc/document-workflow/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscDocumentWorkflow> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/ssc/document-workflow/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SscDocumentWorkflow> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询当前公司的默认管理组织→共享业务类型分配页面
     *
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author ngls.luhui 2019-03-20 15:34
     */
    @RequestMapping(value = "ssc/SSC1050/ssc_document_workflow.screen")
    @ResponseBody
    public ModelAndView sscDocumentWorkflowView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("ssc/SSC1050/ssc_document_workflow");
        FndManagingOrganization defaultMag = fndManagingOrganizationService.defaultManageOrganizationQuery(requestCtx,requestCtx.getCompanyId());
        view.addObject("defaultMag",defaultMag);
        return view;
    }

    /**
     * 共享业务类型分配主页面查询
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author ngls.luhui 2019-03-20 15:34
     */
    @RequestMapping(value = "/ssc/document-workflow/queryDocument", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryDocument(SscDocumentWorkflow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryDocument(dto.getMagOrgId(), dto.getDocCategory(), page, pageSize, requestContext));
    }
}
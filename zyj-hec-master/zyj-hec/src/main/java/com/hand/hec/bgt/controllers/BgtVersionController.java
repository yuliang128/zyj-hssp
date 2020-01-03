package com.hand.hec.bgt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.dto.BgtVersion;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import com.hand.hec.bgt.service.IBgtVersionService;

/**
 * <p>
 * 预算版本Controller
 * </p>
 *
 * @author mouse 2019/01/07 16:14
 */
@RequestMapping(value = "/bgt/version")
@Controller
public class BgtVersionController extends BaseController {

    @Autowired
    private IBgtVersionService service;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    /**
     * 查询预算版本（实现模糊查询功能）
     *
     * @param dto 预算版本
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-02-18 10:00
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtVersion dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        if (dto.getBgtOrgId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(BgtVersion.FIELD_VERSION_ID),
                new WhereField(BgtVersion.FIELD_VERSION_CODE, Comparison.LIKE),
                new WhereField(BgtVersion.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(BgtVersion.FIELD_BGT_ORG_ID), new WhereField(BgtVersion.FIELD_STATUS),
                new WhereField(BgtVersion.FIELD_BGT_JOURNAL_TYPE_ID),
                new WhereField(BgtVersion.FIELD_ENABLED_FLAG, Comparison.EQUAL)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtVersion> dto, BindingResult result, HttpServletRequest request)
                    throws BaseException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtVersion> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 预算版本定义-查询预算组织
     *
     * @param request
     * @author YHL 2019-02-15 11:05
     * @return org.springframework.web.servlet.ModelAndView 预算版本定义页面
     */
    @RequestMapping(value = "/bgtVersionView")
    public ModelAndView bgtVersionView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long magOrgId = (Long) session.getAttribute(FndManagingOrganization.FIELD_MAG_ORG_ID);
        BgtOrganization bgtOrganization = bgtOrganizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT1030/bgt_versions");
        view.addObject("bgtOrganization", bgtOrganization);
        return view;
    }

    /**
     * 预算版本下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 16:09
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBgtVersionOption")
    public ResponseData getBgtVersionOption(HttpServletRequest request, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBgtVersionOption(requestCtx, bgtOrgId));
    }
}

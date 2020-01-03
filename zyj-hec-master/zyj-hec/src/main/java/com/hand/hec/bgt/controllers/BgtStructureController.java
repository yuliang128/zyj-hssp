package com.hand.hec.bgt.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.dto.BgtStructure;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import com.hand.hec.bgt.service.IBgtStructureService;

/**
 * <p>
 * 预算表Controller
 * </p>
 *
 * @author mouse 2019/01/07 16:07
 */
@Controller
public class BgtStructureController extends BaseController {

    @Autowired
    private IBgtStructureService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/structure/query")
    @ResponseBody
    public ResponseData query(BgtStructure dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        if (dto.getBgtOrgId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[]{new WhereField(BgtStructure.FIELD_STRUCTURE_ID),
                new WhereField(BgtStructure.FIELD_STRUCTURE_CODE, Comparison.LIKE),
                new WhereField(BgtStructure.FIELD_BGT_ORG_ID),
                new WhereField(BgtStructure.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(BgtStructure.FIELD_NOTE, Comparison.LIKE),
        new WhereField(BgtStructure.FIELD_BGT_JOURNAL_TYPE_ID)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bgt/structure/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtStructure> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/structure/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtStructure> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 获取预算项目默认预算组织
     *
     * @param request 获取管理组织id
     * @return 默认预算组织
     * @author guiyuting 2019/2/18 16:05
     */
    @RequestMapping(value = "/bgt/BGT2110/bgt_budget_structure.screen")
    public ModelAndView jumpBgtStructure(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2110/bgt_budget_structure");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }

    /**
     * 预算表下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 15:53
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/bgt/structure/getBgtStructureByBgtOrgId")
    public ResponseData getBgtStructureByBgtOrgId(HttpServletRequest request, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBgtStructureByBgtOrgId(requestCtx, bgtOrgId));
    }

    @RequestMapping(value = "/bgt/structure/queryAll")
    @ResponseBody
    public ResponseData queryAll(BgtStructure dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    /**
     * 根据预算组织ID 查询与预算日记账类型相关联的预算表信息
     *
     * @param bgtOrgId 预算组织ID
     * @param bgtJournalTypeId 预算日记账类型ID
     * @author guiyuting 2019-03-26 16:48
     * @return 符合条件的预算表信息
     */
    @RequestMapping(value = "/bgt/structure/queryForBgtJournal")
    public ResponseData queryForBgtJournal(HttpServletRequest request, Long bgtOrgId,Long bgtJournalTypeId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryForBgtJournal(requestCtx, bgtOrgId,bgtJournalTypeId));
    }
}
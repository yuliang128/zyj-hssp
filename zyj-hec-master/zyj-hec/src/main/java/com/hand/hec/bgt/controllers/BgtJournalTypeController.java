package com.hand.hec.bgt.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalType;
import com.hand.hec.bgt.service.IBgtJournalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 预算日记账类型Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:45
 */
@Controller
public class BgtJournalTypeController extends BaseController {

    @Autowired
    private IBgtJournalTypeService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/journal-type/query")
    @ResponseBody
    public ResponseData query(BgtJournalType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        if (dto.getBgtOrgId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(BgtJournalType.FIELD_BGT_JOURNAL_TYPE_ID),
                new WhereField(BgtJournalType.FIELD_BGT_JOURNAL_TYPE_CODE, Comparison.LIKE),
                new WhereField(BgtJournalType.FIELD_BGT_ORG_ID), new WhereField(BgtJournalType.FIELD_BGT_BUSINESS_TYPE),
                new WhereField(BgtJournalType.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(BgtJournalType.FIELD_ENABLED_FLAG, Comparison.EQUAL)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bgt/journal-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalType> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/journal-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtJournalType> dto) {
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
    @RequestMapping(value = "/bgt/BGT2200/bgt_journal_type.screen")
    public ModelAndView bgtBudgetItemView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2200/bgt_journal_type");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }
}

package com.hand.hec.bgt.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hec.bgt.dto.BgtJournalHeader;
import com.hand.hec.bgt.service.IBgtJournalHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtControlRule;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtControlRuleService;
import com.hand.hec.bgt.service.IBgtOrganizationService;

/**
 * <p>
 * 预算日记账过账Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:27
 */
@Controller
public class BgtJournalStatusController extends BaseController {

    @Autowired
    private IBgtJournalHeaderService journalHeaderService;

    @Autowired
    private IBgtOrganizationService organizationService;

    /**
     * 获取预算项目默认预算组织
     *
     * @param request 获取管理组织id
     * @return 默认预算组织
     * @author guiyuting 2019/2/18 16:05
     */
    @RequestMapping(value = {"/bgt/BGT5310/bgt_journal_posted.screen", "/bgt/BGT5410/bgt_journal_reverse.screen",
            "/bgt/BGT5210/bgt_journal_approved.screen"})
    public ModelAndView bgtJournalPostedView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        String servletPath = request.getServletPath().substring(1).replace(".screen", "");
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView(servletPath);
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }

    @RequestMapping(value = "/bgt/journal-header/setStatus/{status}")
    @ResponseBody
    public ResponseData update(@PathVariable String status, @RequestBody List<BgtJournalHeader> dto,
                    BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(journalHeaderService.setBgtJournalStatus(requestCtx, dto, status));
    }
}

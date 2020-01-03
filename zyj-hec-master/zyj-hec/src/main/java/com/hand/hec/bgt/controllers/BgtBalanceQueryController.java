package com.hand.hec.bgt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import com.hand.hec.bgt.exception.BgtBalanceQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.service.*;

/**
 * <p>
 * 预算余额查询控制器
 * </p>
 *
 * @author YHL 2019/03/14 10:52
 */
@Controller
public class BgtBalanceQueryController extends BaseController {

    @Autowired
    private IBgtBalanceQueryService service;

    @Autowired
    private IFndCompanyService companyService;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    @Autowired
    private IBgtStructureService bgtStructureService;

    @Autowired
    private IBgtScenarioService bgtScenarioService;

    @Autowired
    private IBgtVersionService bgtVersionService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    /**
     * 预算余额查询主页面
     *
     * @param request 请求
     * @author YHL 2019-03-25 18:58
     * @return org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping(value = "/bgt/BGT3010/bgt_budget_balance_query.screen")
    @ResponseBody
    public ModelAndView bgtBudgetBalanceQueryView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        // 获取session中的公司信息
        FndCompany fndCompany = new FndCompany();
        Long companyId = (Long) session.getAttribute(FndCompany.FIELD_COMPANY_ID);
        fndCompany.setCompanyId(companyId);
        fndCompany = companyService.selectByPrimaryKey(requestContext, fndCompany);
        // 获取session中的管理组织ID
        Long magOrgId = (Long) session.getAttribute(FndManagingOrganization.FIELD_MAG_ORG_ID);
        // 获取管理组织下的默认预算组织
        BgtOrganization bgtOrganization = bgtOrganizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        // 获取默认预算组织的预算表
        List<BgtStructure> bgtStructureList =
                        bgtStructureService.getBgtStructureByBgtOrgId(requestContext, bgtOrganization.getBgtOrgId());
        // 获取默认预算组织的默认预算场景
        BgtScenario bgtScenario = bgtScenarioService.getDefaultBgtScenarioByBgtOrgId(requestContext,
                        bgtOrganization.getBgtOrgId());
        // 获取默认预算组织的当前预算版本
        BgtVersion currentBgtVersion =
                        bgtVersionService.getCurrentBgtVersionByBgtOrgId(requestContext, bgtOrganization.getBgtOrgId());

        ModelAndView view = new ModelAndView("bgt/BGT3010/bgt_budget_balance_query");
        view.addObject("fndCompany", fndCompany);
        view.addObject("bgtOrganization", bgtOrganization);
        if (bgtStructureList != null && !bgtStructureList.isEmpty()) {
            view.addObject("bgtStructure", bgtStructureList.get(0));
        }
        view.addObject("bgtScenario", bgtScenario);
        view.addObject("currentBgtVersion", currentBgtVersion);
        return view;
    }

    /**
     * 预算余额查询——查询
     *
     * @param dto 预算余额查询分组
     * @param request 请求
     * @author MouseZhou 2019年4月17日14:47:22
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/bgt/balance-query/do-query")
    @ResponseBody
    public ResponseData doQuery(@RequestBody List<BgtBalanceQueryGroup> dto, HttpServletRequest request) {
        if (dto == null || dto.size() == 0) {
            throw new BgtBalanceQueryException("BGT", "参数传递错误", null);
        }
        IRequest requestCtx = createRequestContext(request);
        service.doQuery(requestCtx, dto.get(0));
        return new ResponseData(true);
    }
}

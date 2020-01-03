package com.hand.hec.bgt.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtControlStrategyGroup;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtControlStrategyGroupService;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 预算控制策略组Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:36
 */
@Controller
public class BgtControlStrategyGroupController extends BaseController {

    @Autowired
    private IBgtControlStrategyGroupService service;

    @Autowired
    private IBgtOrganizationService organizationService;

    @RequestMapping(value = "/bgt/control-strategy-group/query")
    @ResponseBody
    public ResponseData query(BgtControlStrategyGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtControlStrategyGroup.FIELD_BGT_ORG_ID, Comparison.EQUAL),
                        new WhereField(BgtControlStrategyGroup.FIELD_CONTROL_STRATEGY_GROUP_CODE, Comparison.LIKE),
                        new WhereField(BgtControlStrategyGroup.FIELD_CONTROL_STRATEGY_GROUP_DESC, Comparison.LIKE),
                        new WhereField(BgtControlStrategyGroup.FIELD_CONTROL_STRATEGY_GROUP_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bgt/control-strategy-group/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtControlStrategyGroup> dto, BindingResult result,
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

    @RequestMapping(value = "/bgt/control-strategy-group/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtControlStrategyGroup> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/bgt/control-strategy-group/queryByBgtOrgId")
    @ResponseBody
    public ResponseData queryByBgtOrgId(Long bgtOrgId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        return new ResponseData(service.queryByBgtOrgId(requestContext, bgtOrgId, magOrgId));
    }

    /**
     * 获取预算项目默认预算组织
     *
     * @param request 获取管理组织id
     * @return 默认预算组织
     * @author guiyuting 2019/2/18 16:05
     */
    @RequestMapping(value = "/bgt/BGT2910/bgt_control_strategy_group.screen")
    public ModelAndView bgtControlStrategyGroupView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long magOrgId = requestContext.getMagOrgId();
        BgtOrganization defaultBgtOrgOfMagOrg = organizationService.getBgtOrgByMagOrgId(requestContext, magOrgId);
        ModelAndView view = new ModelAndView("bgt/BGT2910/bgt_control_strategy_group");
        view.addObject("defaultBgtOrgOfMagOrg", defaultBgtOrgOfMagOrg);
        return view;
    }
}

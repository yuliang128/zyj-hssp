package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtCenterService;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 预算中心Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:27
 */
@Controller
public class BgtCenterController extends BaseController {

    @Autowired
    private IBgtCenterService service;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    @Autowired
    IGldCurrencyService gldCurrencyService;

    @RequestMapping(value = "/bgt/center/query")
    @ResponseBody
    public ResponseData query(BgtCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria();
        criteria.where(new WhereField(BgtCenter.FIELD_CENTER_CODE, Comparison.LIKE),
                        new WhereField(BgtCenter.FIELD_DESCRIPTION, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));

    }

    @RequestMapping(value = "/bgt/center/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtCenter> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/center/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtCenter> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "bgt/BGT1100/bgt_centers.screen")
    public ModelAndView bgtCenterView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bgt/BGT1100/bgt_centers");

        IRequest requestCtx = createRequestContext(request);
        List<GldCurrency> currencyList = gldCurrencyService.queryEnabledCurrency(requestCtx);
        List<BgtOrganization> bgtOrgList = bgtOrganizationService.queryDefaultBgtOrganization(requestCtx);

        view.addObject("currencyList", currencyList);
        view.addObject("bgtOrgList", bgtOrgList);

        return view;
    }

    /**
     * 预算中心定义页面主查询
     *
     * @param dto 包含预算组织来源类型(决定关联哪张表查询)，预算组织id，以及常规查询条件预算中心code和预算中心描述
     * @author ngls.luhui 2019-02-21 10:47
     * @return
     */
    @RequestMapping(value = "/bgt/center/queryMain")
    @ResponseBody
    public ResponseData queryMain(BgtCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querytMain(dto.getSourceTypeCode(), dto.getBgtOrgId(), dto.getCenterCode(),
                        dto.getDescription(), requestContext, page, pageSize));

    }

    @RequestMapping(value = "/bgt/center/manual")
    @ResponseBody
    public ResponseData manual(@RequestBody BgtCenter dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.manual(dto.getBgtOrgId(), dto.getSourceTypeCode(), requestCtx));
    }

    /**
     * 预算日记账类型定义指定范围页面，分配预算中心
     *
     * @param bgtOrgId 预算组织ID
     * @param rangeEtsId 预算日记账类型定义分配预算实体ID
     * @author guiyuting 2019-03-20 11:00
     * @return 
     */
    @RequestMapping(value = "/bgt/center/bgtJournalBatch")
    @ResponseBody
    public ResponseData bgtJournalBatch(Long bgtOrgId, Long rangeEtsId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.bgtJournalBatch(requestContext, bgtOrgId, rangeEtsId));

    }
}

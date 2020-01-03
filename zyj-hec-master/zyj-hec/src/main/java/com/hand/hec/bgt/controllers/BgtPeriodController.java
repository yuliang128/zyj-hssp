package com.hand.hec.bgt.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtPeriod;
import com.hand.hec.bgt.service.IBgtPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 预算期间Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:57
 */
@Controller
@RequestMapping(value = "/bgt/period")
public class BgtPeriodController extends BaseController {

    @Autowired
    private IBgtPeriodService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtPeriod dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(BgtPeriod.FIELD_PERIOD_ID),
                new WhereField(BgtPeriod.FIELD_PERIOD_NAME), new WhereField(BgtPeriod.FIELD_INTERNAL_PERIOD_NUM),
                new WhereField(BgtPeriod.FIELD_PERIOD_SET_ID), new WhereField(BgtPeriod.FIELD_BGT_ORG_ID),});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/checkPeriodExist")
    @ResponseBody
    public ResponseData checkPeriodExist(BgtPeriod dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long yearFrom = dto.getYearFrom() != null ? dto.getYearFrom() : cal.get(Calendar.YEAR);
        Long yearTo = dto.getYearTo() != null ? dto.getYearTo() : cal.get(Calendar.YEAR);
        int result = service.checkPeriodExist(dto.getPeriodSetId(), yearFrom, yearTo);
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("total", result);
        list.add(map);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/checkPeriodUsed")
    @ResponseBody
    public ResponseData checkPeriodUsed(BgtPeriod dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long yearFrom = dto.getYearFrom() != null ? dto.getYearFrom() : cal.get(Calendar.YEAR);
        Long yearTo = dto.getYearTo() != null ? dto.getYearTo() : cal.get(Calendar.YEAR);
        int result = service.checkPeriodUsed(dto.getPeriodSetId(), yearFrom, yearTo);
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("total", result);
        list.add(map);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtPeriod> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/submitByYear")
    @ResponseBody
    public void submitByYear(@RequestBody List<BgtPeriod> list, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        BgtPeriod dto = list.get(0);
        service.batchSubmit(requestCtx, dto);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtPeriod> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 根据预算组织ID获取预算期间下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织
     * @author YHL 2019-03-15 13:37
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getBgtPeriodOption")
    public ResponseData getBgtPeriodOption(HttpServletRequest request, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getBgtPeriodOption(requestCtx, bgtOrgId));
    }

    /**
     * 季度下拉框
     *
     * @param request
     * @author YHL 2019-03-15 13:38
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getQuarterOption")
    public ResponseData getQuarterOption(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getQuarterOption(requestCtx));
    }

    /**
     * 根据预算组织ID获取预算年度下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织
     * @author YHL 2019-03-15 13:58
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getPeriodYearOption")
    public ResponseData getPeriodYearOption(HttpServletRequest request, Long bgtOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getPeriodYearOption(requestCtx, bgtOrgId));
    }
}

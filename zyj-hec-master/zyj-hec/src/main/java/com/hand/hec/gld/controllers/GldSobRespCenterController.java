package com.hand.hec.gld.controllers;


import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;

import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.gld.dto.GldSobRespCenter;
import com.hand.hec.gld.service.IGldSobRespCenterService;

/**
 * <p>
 * 账套级成本中心定义控制器
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */

@Controller
@RequestMapping(value = "/gld-sob-resp-center")
public class GldSobRespCenterController extends BaseController {

    @Autowired
    private IGldSobRespCenterService service;


    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobRespCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_ID, Comparison.EQUAL), new WhereField(GldSobRespCenter.FIELD_SET_OF_BOOKS_ID, Comparison.EQUAL), new WhereField(GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_NAME, Comparison.LIKE), new WhereField(GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_CODE, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobRespCenter> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldSobRespCenter> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 将账套级成本中心分配给核算主体级成本中心
     *
     * @param request
     * @param dto     前台给出的核算主体数据
     * @return 无数据
     */
    @RequestMapping(value = "/submitResponsibilityCenter")
    @ResponseBody
    public ResponseData submitResponsibilityCenter(HttpServletRequest request, @RequestBody List<GldResponsibilityCenter> dto) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.submitResponsibilityCenter(requestContext,dto ));
    }

    /**
     * 查询被分配账套级成本中心的核算主体
     *
     * @param dto
     * @param page     每页数据量
     * @param pageSize 数据大小
     * @param request
     * @return 返回被分配的核算主体
     */
    @RequestMapping(value = "/queryResponsibilityCenter")
    @ResponseBody
    public ResponseData queryAccountingEntity(GldResponsibilityCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectAccountingEntity(requestContext, dto, page, pageSize));
    }

    /**
     * 查询符合被分配条件的核算主体
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return 返回核算主体
     * @author wuxiuxian 2019/2/18
     */
    @RequestMapping(value = "/queryAccountingEntity")
    @ResponseBody
    public ResponseData queryAccountingEntity(GldSobRespCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccountingEntity(requestContext, dto, page, pageSize));
    }


}
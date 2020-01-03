package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.CodeValue;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;

/**
 * 维度定义Controller
 *
 * @author xiuxian.wu 2019/02/18 20:09
 */

@Controller
@RequestMapping(value = "/fnd/dimension")
public class FndDimensionController extends BaseController {

    @Autowired
    private IFndDimensionService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndDimension dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndDimension.FIELD_DIMENSION_ID, Comparison.EQUAL),
                new WhereField(FndDimension.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(FndDimension.FIELD_DIMENSION_CODE, Comparison.LIKE),
                new WhereField(FndDimension.FIELD_ENABLED_FLAG, Comparison.EQUAL),
                new WhereField(FndDimension.FIELD_SYSTEM_FLAG, Comparison.EQUAL),
                new WhereField(FndDimension.FIELD_DIMENSION_SEQUENCE, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(FndDimension dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndDimension> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndDimension> dto) {
        service.batchDelete(dto);
        return new ResponseData();

    }

    /**
     * 根据组织级别查询公司级别
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return 公司级别
     * @author xiuxian.wu 2019/02/18 19:56
     */
    @RequestMapping(value = "/queryCodeValue")
    @ResponseBody
    public ResponseData queryCodeValue(CodeValue dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryCodeValue(dto, requestCtx));

    }

    /**
     * 系统级维值定义，查询维度代码
     *
     * @param dto
     * @return
     * @author guiyuting 2019-02-26 16:44
     */
    @RequestMapping(value = "/queryForDimensionValue", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForDimensionValue(FndDimension dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForDimensionValue(requestContext, dto));
    }

    /**
     * 公司级维值定义，查询维度代码
     *
     * @param dto
     * @return
     * @author guiyuting 2019-02-26 16:44
     */
    @RequestMapping(value = "/queryForCompanyDimValue", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForCompanyDimValue(FndDimension dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForCompanyDimensionValue(requestContext, dto));
    }

}

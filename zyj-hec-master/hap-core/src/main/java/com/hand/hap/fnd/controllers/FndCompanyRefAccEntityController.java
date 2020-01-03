package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 管理公司分配核算主体Controller
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 * @author xiuxian.wu 2019/1/25 14:37
 */

@Controller
@RequestMapping(value = "/fnd/company-ref-acc-entity")
public class FndCompanyRefAccEntityController extends BaseController {

    @Autowired
    private IFndCompanyRefAccEntityService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCompanyRefAccEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCompanyRefAccEntity.FIELD_COMPANY_ID, Comparison.EQUAL)
                , new WhereField(FndCompanyRefAccEntity.FIELD_ACC_ENTITY_ID, Comparison.EQUAL)
                , new WhereField(FndCompanyRefAccEntity.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(FndCompanyRefAccEntity.FIELD_DEFAULT_FLAG, Comparison.EQUAL)
                , new WhereField(FndCompanyRefAccEntity.FIELD_REF_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCompanyRefAccEntity> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCompanyRefAccEntity> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询可以批量分配给管理公司的剩余核算主体
     *
     * @param dto      查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回剩余核算主体信息
     * @author xiuxian.wu 2019/1/25 14:39
     */

    @RequestMapping(value = "/queryAccEntity", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAccEntity(FndCompanyRefAccEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccEntity(dto, requestContext, page, pageSize));
    }

    /**
     * 查询已经分配给管理公司的核算主体信息
     *
     * @param dto      查询条件 公司ID
     * @param page     数据数
     * @param pageSize 页数
     * @param request
     * @return 核算主体信息
     * @author xiuxian.wu 2019/1/25 14:42
     */
    @RequestMapping(value = "/queryAllFndCompanyRefAccEntity", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAllFndCompanyRefAccEntity(FndCompanyRefAccEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAllFndCompanyRefAccEntity(requestContext, dto, page, pageSize));
    }

    /**
     * 新增或修改管理公司关联的核算主体信息及所关联的默认预算实体信息
     *
     * @param dto     新增或修改的数据
     * @param result
     * @param request
     * @return 返回已经新增或修改的数据
     */
    @RequestMapping(value = "/submitCompanyRefAccEntity")
    @ResponseBody
    public ResponseData submitCompanyRefAccEntity(@RequestBody List<FndCompanyRefAccEntity> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submitCompanyRefAccEntity(requestCtx, dto));
    }


}
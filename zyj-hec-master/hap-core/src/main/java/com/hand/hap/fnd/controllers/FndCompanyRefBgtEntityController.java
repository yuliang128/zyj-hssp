package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 管理公司关联预算实体Controller
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 * @author xiuxian.wu 2019/01/25 14:49
 */

@Controller
@RequestMapping(value = "/fnd/company-ref-bgt-entity")
public class FndCompanyRefBgtEntityController extends BaseController {

    @Autowired
    private IFndCompanyRefBgtEntityService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCompanyRefBgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompanyRefBgtEntityByCompanyId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCompanyRefBgtEntity> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCompanyRefBgtEntity> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询可以批量分配给管理公司的剩余预算实体
     *
     * @param dto      查询条件 公司ID
     * @param page
     * @param pageSize
     * @param request
     * @return 返回剩余预算实体信息
     * @author xiuxian.wu 2019/01/25 14:50
     */
    @RequestMapping(value = "/queryBgtEntity", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryBgtEntity(FndCompanyRefBgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBgtEntity(dto, requestContext, page, pageSize));
    }


}
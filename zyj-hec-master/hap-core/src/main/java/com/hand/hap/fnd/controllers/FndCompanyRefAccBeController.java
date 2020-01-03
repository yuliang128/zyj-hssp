package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.fnd.service.IFndCompanyRefAccBeService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 管理公司分配核算主体关联预算实体Controller
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 * @author xiuxian.wu 2019/1/25 14:35
 */

@Controller
@RequestMapping(value = "/fnd/company-ref-acc-be")
public class FndCompanyRefAccBeController extends BaseController {

    @Autowired
    private IFndCompanyRefAccBeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCompanyRefAccBe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompanyAccRefBgtByAccRefId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCompanyRefAccBe> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCompanyRefAccBe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询批量管理公司与核算主体分配预算实体剩余可以分配的预算实体
     *
     * @param dto      查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回可以分配的预算实体
     * @author xiuxian.wu 2019/1/25 14:35
     */

    @RequestMapping(value = "/queryBgtEntity", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryBgtEntity(FndCompanyRefAccBe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBgtEntity(dto, requestContext, page, pageSize));
    }
}
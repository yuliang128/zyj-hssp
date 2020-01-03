package com.hand.hap.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndCompanyLevel;
import com.hand.hap.fnd.service.IFndCompanyLevelService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * <p>
 * 公司级别控制器
 * </p>
 *
 * @author YHL 2019/01/10 11:05
 */
@Controller
@RequestMapping(value = "/fnd/company-level")
public class FndCompanyLevelController extends BaseController {

    @Autowired
    private IFndCompanyLevelService service;

    /**
     * 查询公司级别（实现模糊查询功能）
     *
     * @param dto      公司级别
     * @param page
     * @param pageSize
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author YHL 2019-01-19 0:17
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCompanyLevel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCompanyLevel.FIELD_COMPANY_LEVEL_CODE, Comparison.LIKE),
                new WhereField(FndCompanyLevel.FIELD_DESCRIPTION, Comparison.LIKE),
                new WhereField(FndCompanyLevel.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCompanyLevel> dto, BindingResult result, HttpServletRequest request)
            throws BaseException {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCompanyLevel> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
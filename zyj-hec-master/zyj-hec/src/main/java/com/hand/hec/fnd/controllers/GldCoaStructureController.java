package com.hand.hec.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldCoaStructure;
import com.hand.hec.fnd.service.IGldCoaStructureService;

/**
 * <p>
 * 科目结构控制器
 * </p>
 *
 * @author YHL 2019/01/08 16:08
 */
@Controller
@RequestMapping(value = "/gld/coa-structure")
public class GldCoaStructureController extends BaseController {

    @Autowired
    private IGldCoaStructureService service;

    /**
     * 查询科目结构（实现模糊查询功能）
     *
     * @param dto 科目结构
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-19 0:20
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldCoaStructure dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldCoaStructure.FIELD_COA_STRUCTURE_CODE, Comparison.LIKE),
                new WhereField(GldCoaStructure.FIELD_DESCRIPTION, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldCoaStructure> dto, BindingResult result, HttpServletRequest request)
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldCoaStructure> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
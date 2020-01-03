package com.hand.hec.gld.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.gld.dto.GldSobRespCenterHierarchy;
import com.hand.hec.gld.service.IGldSobRespCenterHierarchyService;

/**
 * <p>
 * 子成本中心层级控制器
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */

@Controller
@RequestMapping(value = "/gld-sob-resp-center-hierarchy")
public class GldSobRespCenterHierarchyController extends BaseController {

    @Autowired
    private IGldSobRespCenterHierarchyService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobRespCenterHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldSobRespCenterHierarchy.FIELD_HIERARCHY_ID), new WhereField(GldSobRespCenterHierarchy.FIELD_PARENT_RESP_CENTER_ID), new WhereField(GldSobRespCenterHierarchy.FIELD_RESPONSIBILITY_CENTER_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobRespCenterHierarchy> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldSobRespCenterHierarchy> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
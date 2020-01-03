package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.service.IExpOrgUnitRefAccOrgService;

/**
 * <p>
 * ExpOrgUnitRefAccOrgController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */

@Controller
@RequestMapping(value = "/exp/org-unit-ref-acc-org")
public class ExpOrgUnitRefAccOrgController extends BaseController {

    @Autowired
    private IExpOrgUnitRefAccOrgService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgUnitRefAccOrg dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpOrgUnitRefAccOrg.FIELD_UNIT_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgUnitRefAccOrg> dto, BindingResult result,
                    HttpServletRequest request) throws UnitAccOrBgtDfMultiException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpOrgUnitRefAccOrg> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoRepEleRefExpIt;
import com.hand.hec.exp.service.IExpMoRepEleRefExpItService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpMoRepEleRefExpItController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:40
 */

@Controller
@RequestMapping(value = "/exp/mo-rep-ele-ref-exp-it")
public class ExpMoRepEleRefExpItController extends BaseController {

    @Autowired
    private IExpMoRepEleRefExpItService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoRepEleRefExpIt dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepEleRefExpIt.FIELD_EXP_TYPE_REF_ID, Comparison.EQUAL),
                        new WhereField(ExpMoRepEleRefExpIt.FIELD_MO_EXPENSE_ITEM_CODE),
                        new WhereField(ExpMoRepEleRefExpIt.FIELD_MO_EXPENSE_ITEM_NAME));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoRepEleRefExpIt> dto, BindingResult result,
                    HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoRepEleRefExpIt> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

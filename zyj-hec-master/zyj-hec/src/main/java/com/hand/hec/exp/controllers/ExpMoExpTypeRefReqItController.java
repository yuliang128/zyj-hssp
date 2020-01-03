package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
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
import com.hand.hec.exp.dto.ExpMoExpTypeRefReqIt;
import com.hand.hec.exp.service.IExpMoExpTypeRefReqItService;

/**
 * <p>
 * 报销类型关联申请项目Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-type-ref-req-it")
public class ExpMoExpTypeRefReqItController extends BaseController {

    @Autowired
    private IExpMoExpTypeRefReqItService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpTypeRefReqIt dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpTypeRefReqIt.FIELD_MAG_ORG_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeRefReqIt.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeRefReqIt.FIELD_MO_EXPENSE_TYPE_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeRefReqIt.FIELD_MO_REQ_ITEM_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeRefReqIt.FIELD_REF_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpTypeRefReqIt> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpTypeRefReqIt> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
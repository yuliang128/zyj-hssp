package com.hand.hec.bgt.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtBudgetItemType;
import com.hand.hec.bgt.service.IBgtBudgetItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * <p>
 * 预算项目类型Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:26
 */
@Controller
@RequestMapping("/bgt/budget-item-type")
public class BgtBudgetItemTypeController extends BaseController {

    @Autowired
    private IBgtBudgetItemTypeService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtBudgetItemType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtBudgetItemType.FIELD_BUDGET_ITEM_TYPE_ID, Comparison.EQUAL)
                , new WhereField(BgtBudgetItemType.FIELD_BGT_ORG_ID, Comparison.EQUAL)
                , new WhereField(BgtBudgetItemType.FIELD_BUDGET_ITEM_TYPE_CODE, Comparison.LIKE)
                , new WhereField(BgtBudgetItemType.FIELD_DESCRIPTION, Comparison.LIKE)
                , new WhereField(BgtBudgetItemType.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtBudgetItemType> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtBudgetItemType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryByBgtOrgId")
    @ResponseBody
    public ResponseData queryByBgtOrgId(BgtBudgetItemType dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByBgtOrgId(requestContext, dto));
    }
}
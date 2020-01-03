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

import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;
import com.hand.hec.exp.service.IExpMoExpPolicyDetailService;

/**
 * <p>
 * 政策标准明细定义Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-policy-detail")
public class ExpMoExpPolicyDetailController extends BaseController{

    @Autowired
    private IExpMoExpPolicyDetailService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpPolicyDetail.FIELD_DETAIL_ID,Comparison.EQUAL)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_EXPENSE_POLICY_ID,Comparison.EQUAL)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_DETAIL_CODE,Comparison.LIKE)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_DESCRIPTION,Comparison.LIKE)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_EXPENSE_STANDARD,Comparison.EQUAL)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_UPPER_LIMIT,Comparison.LESS_THAN_OR_EQUALTO)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_LOWER_LIMIT,Comparison.GREATER_THAN_OR_EQUALTO)
        ,new WhereField(ExpMoExpPolicyDetail.FIELD_CURRENCY_CODE,Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyDetail> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpPolicyDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 根据交通工具查询舱位/席别
     * @param request
     * @param transportation 交通工具代码
     * @return 舱位/席别
     * @author xiuxian.wu 2019/02/18 19:56
     */
    @RequestMapping(value = "/querySeatClass")
    @ResponseBody
    public ResponseData querySeatClass(String transportation,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySeatClass(transportation,requestContext));
    }
}
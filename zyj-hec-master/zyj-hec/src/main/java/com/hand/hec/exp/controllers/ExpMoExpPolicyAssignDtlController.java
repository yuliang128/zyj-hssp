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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssignDtl;
import com.hand.hec.exp.service.IExpMoExpPolicyAssignDtlService;

/**
 *
 * 费用政策分配明细控制器
 *
 * @author zhongyu
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-policy-assign-dtl")
public class ExpMoExpPolicyAssignDtlController extends BaseController{

    @Autowired
    private IExpMoExpPolicyAssignDtlService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpPolicyAssignDtl dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpPolicyAssignDtl.FIELD_ASSIGN_ID, Comparison.EQUAL),
                new WhereField(ExpMoExpPolicyAssignDtl.FIELD_EXPENSE_POLICY_CODE),
                new WhereField(ExpMoExpPolicyAssignDtl.FIELD_EXPENSE_POLICY_NAME));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpPolicyAssignDtl> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpPolicyAssignDtl> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
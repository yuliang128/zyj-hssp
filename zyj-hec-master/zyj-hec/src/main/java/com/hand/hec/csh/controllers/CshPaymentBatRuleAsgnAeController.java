package com.hand.hec.csh.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
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
import com.hand.hec.csh.dto.CshPaymentBatRuleAsgnAe;
import com.hand.hec.csh.service.ICshPaymentBatRuleAsgnAeService;

/**
 *
 * 付款批规则定义-分配核算主体控制器
 *
 * @author luhui 2019-02-28
 */

@Controller
@RequestMapping(value = "/csh/payment-bat-rule-asgn-ae")
public class CshPaymentBatRuleAsgnAeController extends BaseController{

    @Autowired
    private ICshPaymentBatRuleAsgnAeService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentBatRuleAsgnAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentBatRuleAsgnAe.FIELD_RULE_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentBatRuleAsgnAe> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentBatRuleAsgnAe> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 付款批规则定义-分配核算主体-批量分配 查询当前规则可分配的核算主体
     *
     * @param dto
     * @author ngls.luhui 2019-02-28 19:00
     * @return 可分配的核算主体list
     */
    @RequestMapping(value = "/quertAccCanAsgn",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData quertAccCanAsgn(CshPaymentBatRuleAsgnAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.quertAccCanAsgn(dto.getMagOrgId(),dto.getRuleId(),requestContext,page,pageSize));
    }
}
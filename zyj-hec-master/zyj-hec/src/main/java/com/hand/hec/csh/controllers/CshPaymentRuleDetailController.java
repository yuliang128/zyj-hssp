package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshPaymentRuleDetail;
import com.hand.hec.csh.service.ICshPaymentRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/csh/payment-rule-detail")
public class CshPaymentRuleDetailController extends BaseController{

    @Autowired
    private ICshPaymentRuleDetailService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        if(dto.getRuleId()!=null) {
            criteria.where(new WhereField(CshPaymentRuleDetail.FIELD_RULE_ID, Comparison.EQUAL));
        }
        return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentRuleDetail> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentRuleDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     *
     * 校验函数
     *
     * 检验其中的左右括号和and or是否匹配，匹配则返回true 不匹配返回false
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public ResponseData check(HttpServletRequest request,@RequestBody List<CshPaymentRuleDetail> dto){
        int temp=0;
        IRequest requestContext = createRequestContext(request);
        System.out.println("**************************");
        String str="select 1 from dual where 1 = 1 ";
        for(int i=0;i<dto.size();i++){
            str=str+dto.get(i).getAndOr()+ " "+dto.get(i).getLeftParenthesis()+"  1=1  "+dto.get(i).getRightParenthesis()+" ";
        }
        try {
            System.out.println(str);
            temp = service.check(requestContext,str);
            System.out.println(temp);
        }catch (Exception e) {
            e.printStackTrace();
            temp = 0;
        };
        System.out.println(temp);
        if(temp!=0) return new ResponseData(true);
        else return new ResponseData(false);

    }
}
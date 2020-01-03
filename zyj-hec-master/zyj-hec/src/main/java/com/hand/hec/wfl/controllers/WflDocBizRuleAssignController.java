package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflDocBizRuleAssign;
import com.hand.hec.wfl.service.IWflDocBizRuleAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/wfl/doc-biz-rule-assign")
public class WflDocBizRuleAssignController extends BaseController {

    @Autowired
    private IWflDocBizRuleAssignService service;


    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(WflDocBizRuleAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果docProcessAssignId为空，返回空值
        if(dto.getDocProcessAssignId() == null){
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflDocBizRuleAssign.FIELD_DOC_PROCESS_ASSIGN_ID),
                new WhereField(WflDocBizRuleAssign.FIELD_BUSINESS_RULE_ID)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflDocBizRuleAssign> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflDocBizRuleAssign> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
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
import com.hand.hec.fnd.dto.FndCodingRuleDetail;
import com.hand.hec.fnd.service.IFndCodingRuleDetailService;

/**
 *
 * 编码规则明细控制器
 *
 * @author zhongyu 2019-2-22
 */

@Controller
@RequestMapping(value = "/fnd/coding-rule-detail")
public class FndCodingRuleDetailController extends BaseController{

    @Autowired
    private IFndCodingRuleDetailService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCodingRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndCodingRuleDetail.FIELD_SEQUENCE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    /**
     * 按照顺序号排序查询结果
     * @param dto
     * @param request
     * @return
     * @author zhongyu 2019-2-25 15:08
     */

    @RequestMapping(value="/queryBySequence",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryBySequence(FndCodingRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBySequence(requestContext,dto,page,pageSize));
    }


    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCodingRuleDetail> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndCodingRuleDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
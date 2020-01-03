package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldSobRuleSegment;
import com.hand.hec.gld.service.IGldSobRuleSegmentService;
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
@RequestMapping(value = "/gld/sob-rule-segment")
public class GldSobRuleSegmentController extends BaseController{

    @Autowired
    private IGldSobRuleSegmentService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobRuleSegment dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(GldSobRuleSegment.FIELD_RULE_ID),
				new WhereField(GldSobRuleSegment.FIELD_ACCOUNT_SEGMENT_CODE),
				new WhereField(GldSobRuleSegment.FIELD_ACCOUNT_SEGMENT_NAME));
		return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobRuleSegment> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSobRuleSegment> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
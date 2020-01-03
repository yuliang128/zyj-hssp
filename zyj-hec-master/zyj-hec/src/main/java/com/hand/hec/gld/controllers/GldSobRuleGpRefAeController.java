package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldSobRuleGpRefAe;
import com.hand.hec.gld.service.IGldSobRuleGpRefAeService;
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
@RequestMapping(value = "/gld/sob-rule-gp-ref-ae")
public class GldSobRuleGpRefAeController extends BaseController{

    @Autowired
    private IGldSobRuleGpRefAeService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobRuleGpRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(GldAccountingEntity.FIELD_ACC_ENTITY_NAME),
				new WhereField( GldAccountingEntity.FIELD_ACC_ENTITY_NAME),
				new WhereField( GldSobRuleGpRefAe.FIELD_RULE_GROUP_ID));
		return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

	@RequestMapping(value = "/query-not-ref-ae",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryNotRefAE(GldSobRuleGpRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryNotRefAE(requestContext, dto, page, pageSize));
	}




    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobRuleGpRefAe> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSobRuleGpRefAe> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
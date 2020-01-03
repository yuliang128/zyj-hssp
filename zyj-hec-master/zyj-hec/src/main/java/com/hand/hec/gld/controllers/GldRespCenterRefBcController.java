package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldRespCenterRefBc;
import com.hand.hec.gld.service.IGldRespCenterRefBcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * GldRespCenterRefBcController
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:44
 */

@Controller
@RequestMapping(value = "/gld/resp-center-ref-bc")
public class GldRespCenterRefBcController extends BaseController{

    @Autowired
    private IGldRespCenterRefBcService service;


	@RequestMapping("/query")
	@ResponseBody
	public ResponseData query(GldRespCenterRefBc dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(GldRespCenterRefBc.FIELD_BGT_ENTITY_NAME),
				new WhereField(GldRespCenterRefBc.FIELD_BGT_CENTER_NAME),
				new WhereField(GldRespCenterRefBc.FIELD_RESP_CENTER_ID));
		return new ResponseData(service.selectOptions(requestContext,dto,criteria));
	}

	@RequestMapping("/submit")
	@ResponseBody
	public ResponseData update(@RequestBody List<GldRespCenterRefBc> dto, BindingResult result,
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

	@RequestMapping("/remove")
	@ResponseBody
	public ResponseData delete(HttpServletRequest request, @RequestBody List<GldRespCenterRefBc> dto) {
		service.batchDelete(dto);
		return new ResponseData();
	}
}
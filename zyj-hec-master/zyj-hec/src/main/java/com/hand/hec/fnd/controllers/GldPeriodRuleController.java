package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldPeriodRule;
import com.hand.hec.fnd.service.IGldPeriodRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 会计期间规则控制器
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:40
 */

@Controller
@RequestMapping(value = "/fnd/gld-period-rule")
public class GldPeriodRuleController extends BaseController {

	@Autowired
	private IGldPeriodRuleService service;

	@RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData query(GldPeriodRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.select(requestContext, dto, page, pageSize));
	}

	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseData update(@RequestBody List<GldPeriodRule> dto, BindingResult result, HttpServletRequest request)
			throws BaseException {
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
		if (dto.size() > 0) {
			service.periodAdditionalNameCheck(dto);
			service.periodRuleCheck(dto);
		}

		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(service.batchUpdate(requestCtx, dto));
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public ResponseData delete(HttpServletRequest request, @RequestBody List<GldPeriodRule> dto) {
		service.batchDelete(dto);
		return new ResponseData();
	}
}
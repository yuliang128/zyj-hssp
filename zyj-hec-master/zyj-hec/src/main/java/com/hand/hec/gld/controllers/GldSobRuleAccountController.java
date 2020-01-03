package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldSobRuleAccount;
import com.hand.hec.gld.service.IGldSobRuleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *账套级会计规则明细-分配科目
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/01/08 13:50
 */

@Controller
@RequestMapping(value = "/gld/sob-rule-account")
public class GldSobRuleAccountController extends BaseController {

	@Autowired
	private IGldSobRuleAccountService service;

	@RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData query(GldSobRuleAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryGldSobRuleAccount(requestContext, dto,  page,  pageSize));
	}


	/**
	 * 批量分配-查询未分配的科目
	 * @param dto  查询条件
	 * @param page 页数
	 * @param pageSize  pageSize
	 * @param request request
	 * @return 未分配的科目
	 */
	@RequestMapping(value = "/query-not-ref-account", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryNotRefAccount(GldSobRuleAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryNotRefAccount(requestContext, dto, page, pageSize));
	}





	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseData update(@RequestBody List<GldSobRuleAccount> dto, BindingResult result,
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
	public ResponseData delete(HttpServletRequest request, @RequestBody List<GldSobRuleAccount> dto) {
		service.batchDelete(dto);
		return new ResponseData();
	}
}
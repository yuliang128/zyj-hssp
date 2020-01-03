package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.exception.GldPeriodException;
import com.hand.hec.fnd.service.IGldPeriodService;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 会计期间规则控制器
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:40
 */

@Controller
@RequestMapping(value = "/fnd/gld-period")
public class GldPeriodController extends BaseController {

	@Autowired
	private IGldPeriodService service;

	@RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData query(GldPeriod dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.select(requestContext, dto, page, pageSize));
	}

	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseData createPeriod(@RequestBody List<GldPeriod> dto, BindingResult result, HttpServletRequest request)
			throws BaseException {
		IRequest requestCtx = createRequestContext(request);
		dto = service.createPeriod(requestCtx, dto);
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
		return new ResponseData(dto);
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public ResponseData delete(HttpServletRequest request, @RequestBody List<GldPeriod> dto) {
		service.batchDelete(dto);
		return new ResponseData();
	}

	@RequestMapping(value = "/checkPeriodExists", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData checkPeriodExists(HttpServletRequest request, GldPeriod dto) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.checkPeriodExists(requestContext, dto));
	}

	@RequestMapping(value = "/checkPeriodUsed", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData checkPeriodUsed(HttpServletRequest request, GldPeriod dto) {

		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.checkPeriodUsed(requestContext, dto));
	}

	/**
	 * @Description 获取会计期间
	 *
	 * @Author Tagin
	 * @Date 2019-03-07 14:20
	 * @param request
	 * @param accEntityId
	 * @param date
	 * @param status
	 * @Return com.hand.hap.system.dto.ResponseData
	 * @Version 1.0
	 **/
	@RequestMapping(value = "/getPeriodName", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData getPeriodName(HttpServletRequest request, @RequestBody Map<String, String> map)
			throws GldPeriodException {
		IRequest iRequest = createRequestContext(request);
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("date"));
		} catch (Exception e) {
			throw new GldPeriodException(GldPeriodException.DATE_FORMAT_ERROR, GldPeriodException.DATE_FORMAT_ERROR,
					null);
		}
		return new ResponseData(Collections.singletonList(
				service.getPeriodName(iRequest, date, Long.valueOf(map.get("accEntityId")), map.get("status"))));
	}
}

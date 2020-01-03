package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import com.hand.hec.fnd.service.IGldAePeriodStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 核算主体级会计期间控制controller
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 16:42
 */

@Controller
public class GldAePeriodStatusController extends BaseController {

	@Autowired
	private IGldAePeriodStatusService service;

	@RequestMapping(value = "/fnd/gld-ae-period-status/queryUnOpenedPeriod", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryUnOpenedPeriod(@RequestParam Long periodSetId, @RequestParam Long accEntityId,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
		return new ResponseData(service.unOpenedPeriodQuery(periodSetId, accEntityId, page, pageSize));
	}

	@RequestMapping(value = "/fnd/gld-ae-period-status/queryOpenedPeriod", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData queryOpenedPeriod(@RequestParam Long periodSetId, @RequestParam Long accEntityId,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
		return new ResponseData(service.OpenedPeriodQuery(periodSetId, accEntityId, page, pageSize));
	}

	@RequestMapping(value = "/fnd/gld-ae-period-status/initPeriod")
	@ResponseBody
	public ResponseData initPeriod(@RequestBody GldAePeriodStatus dto, HttpServletRequest request) {

		IRequest requestCtx = createRequestContext(request);
		service.initPeriod(requestCtx, dto);
		return new ResponseData();
	}

	@RequestMapping(value = "/fnd/gld-ae-period-status/openPeriod")
	@ResponseBody
	public ResponseData openPeriod(@RequestBody GldAePeriodStatus dto, Long periodSetId, HttpServletRequest request)
			throws BaseException {
		IRequest requestCtx = createRequestContext(request);
		service.openPeriod(requestCtx, dto, periodSetId);
		return new ResponseData();
	}

	@RequestMapping(value = "/fnd/gld-ae-period-status/closePeriod")
	@ResponseBody
	public ResponseData closePeriod(@RequestBody GldAePeriodStatus dto, Long periodSetId, HttpServletRequest request)
			throws BaseException {
		IRequest requestCtx = createRequestContext(request);
		service.closePeriod(requestCtx, dto, periodSetId);
		return new ResponseData();
	}

	@RequestMapping(value = "/fnd/FND5800/gld_ae_period_select.screen")
	public ModelAndView gldAePeriodSelectView() {
		ModelAndView view = new ModelAndView("fnd/FND5800/gld_ae_period_select");
		List<GldAePeriodStatus> accEntityList = service.accEntityListQuery();
		view.addObject("accEntityList", accEntityList);
		return view;
	}
}
package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldSobPeriodStatus;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldSobPeriodStatusService;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hap.sys.dto.SysParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 账套级会计期间控制
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class GldSobPeriodStatusController extends BaseController{

    @Autowired
    private IGldSobPeriodStatusService service;

    @Autowired
	IGldSetOfBookService gldSetOfBookService;

	@Autowired
	 GldPeriodMapper mapper;


    @RequestMapping(value = "/gld/sob-period-status/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);


        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }


    @RequestMapping(value = "/gld/sob-period-status/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobPeriodStatus> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/sob-period-status/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSobPeriodStatus> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }


	@RequestMapping(value = "/gld/sob-period-status/index",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ModelAndView index(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);

		List<GldSetOfBook> defaultGldSetOfBookList = gldSetOfBookService.selectDefaultSobByMagOrgId(requestContext);

		List<GldSetOfBook> gldSetOfBookList  = gldSetOfBookService.querySetOfBookOptionsByParamValue(requestContext, SysParameter.PARAM_FND_ALL_ORGANIZATIONAL);


		ModelAndView view = new ModelAndView("fnd/FND2440/fnd_sob_period_status");
		view.addObject("defaultGldSetOfBookList", defaultGldSetOfBookList);
		view.addObject("gldSetOfBookList", gldSetOfBookList);


		return view;
	}

	/**
	 *  查询打开的期间
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 */
	@RequestMapping(value = "/gld/sob-period-status/query/o",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryForStatusOpen(GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryForStatusOpen(requestContext,dto,page,pageSize));
	}



	/**
	 *  查询关闭的期间
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 */
	@RequestMapping(value = "/gld/sob-period-status/query/u",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryForStatusClosed(GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryForStatusClosed(requestContext,dto,page,pageSize));
	}


	/**
	 *  初始化期间
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 */
	@RequestMapping(value = "/gld/sob-period-status/init-period",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData initPeriod(@RequestBody GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) throws BaseException {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.initPeriod(requestContext, dto, page, pageSize));
	}


	/**
	 *  打开期间
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 */
	@RequestMapping(value = "/gld/sob-period-status/open-period",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData openPeriod(@RequestBody GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request)  throws BaseException{
		IRequest requestContext = createRequestContext(request);

		return new ResponseData(service.openPeriod(requestContext, dto, page, pageSize));
	}


	/**
	 *  关闭期间
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 */
	@RequestMapping(value = "/gld/sob-period-status/close-period",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData closePeriod(@RequestBody GldSobPeriodStatus dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) throws BaseException {
		IRequest requestContext = createRequestContext(request);

		return new ResponseData(service.closePeriod(requestContext, dto, page, pageSize));
	}

}
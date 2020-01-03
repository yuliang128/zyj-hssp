package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldPeriodSet;
import com.hand.hec.fnd.service.IGldPeriodSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 会计期控制器
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:38
 */

@Controller
@RequestMapping(value = "/fnd/gld-period-set")
public class GldPeriodSetController extends BaseController {

	@Autowired
	private IGldPeriodSetService service;

	@RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData query(GldPeriodSet dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(GldPeriodSet.FIELD_PERIOD_SET_CODE, Comparison.LIKE),
				new WhereField(GldPeriodSet.FIELD_PERIOD_SET_NAME, Comparison.LIKE));
		return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
	}

	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseData update(@RequestBody List<GldPeriodSet> dto, BindingResult result, HttpServletRequest request) {
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
	public ResponseData delete(HttpServletRequest request, @RequestBody List<GldPeriodSet> dto) throws BaseException {
		service.periodSetDeleteCheck(dto);
		service.batchDelete(dto);
		return new ResponseData();
	}
}
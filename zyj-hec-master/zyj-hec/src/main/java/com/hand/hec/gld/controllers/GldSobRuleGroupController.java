package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.dto.GldSobRuleGroup;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hec.gld.service.IGldSobRuleGroupService;
import com.hand.hap.sys.dto.SysParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *会计规则定义
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/03/04 13:50
 */
@Controller
@RequestMapping(value = "/gld/sob-rule-group")
public class GldSobRuleGroupController extends BaseController{

    @Autowired
    private IGldSobRuleGroupService service;

	@Autowired
	IGldSetOfBookService gldSetOfBookService;

	@Autowired
	GldSetOfBookMapper gldSetOfBookMapper;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobRuleGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);

		criteria.where(new WhereField(GldSobRuleGroup.FIELD_SET_OF_BOOK_ID),
				new WhereField(GldSobRuleGroup.FIELD_RULE_GROUP_CODE, Comparison.LIKE),
				new WhereField(GldSobRuleGroup.FIELD_RULE_GROUP_NAME, Comparison.LIKE));

		return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobRuleGroup> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldSobRuleGroup> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);

		List<GldSetOfBook> defaultGldSetOfBookList = gldSetOfBookService.selectDefaultSobByMagOrgId(requestContext);

		List<GldSetOfBook> gldSetOfBookList  = gldSetOfBookService.querySetOfBookOptionsByParamValue(requestContext, SysParameter.PARAM_FND_ALL_ORGANIZATIONAL);


		ModelAndView view = new ModelAndView("gld/GLD1020/gld_sob_rule_group");
		view.addObject("defaultGldSetOfBookList", defaultGldSetOfBookList);
		view.addObject("gldSetOfBookList", gldSetOfBookList);

		return view;
	}
}
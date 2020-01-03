package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoTransactionClass;
import com.hand.hec.csh.service.ICshMoTransactionClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 现金事务分类定义控制器
 *
 * @author LJK 2019/02/19 10:42
 */

@Controller
public class CshMoTransactionClassController extends BaseController{

    @Autowired
    private ICshMoTransactionClassService service;

	@Autowired
    private IFndManagingOrganizationService managingOrganizationService;


	@RequestMapping(value = "/csh/mo-transaction-class/query", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseData query(CshMoTransactionClass dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(CshMoTransactionClass.FIELD_DESCRIPTION, Comparison.LIKE),
				new WhereField(CshMoTransactionClass.FIELD_MO_CSH_TRX_CLASS_CODE, Comparison.LIKE),
				new WhereField(CshMoTransactionClass.FIELD_CSH_TRANSACTION_TYPE_CODE, Comparison.EQUAL),
				new WhereField(CshMoTransactionClass.FIELD_MAG_ORG_ID, Comparison.EQUAL));
		return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/csh/mo-transaction-class/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoTransactionClass> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/mo-transaction-class/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoTransactionClass> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

	@RequestMapping(value = "/csh/CSH1051/csh_mo_transaction_classes.screen")
	public ModelAndView cshMoTransactionClassesView(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
        FndManagingOrganization managingOrganization = managingOrganizationService
				.defaultManageOrganizationQuery(requestContext, requestContext.getCompanyId());
		ModelAndView view = new ModelAndView("csh/CSH1051/csh_mo_transaction_classes");
		view.addObject("managingOrganization", managingOrganization);
		return view;
	}
}
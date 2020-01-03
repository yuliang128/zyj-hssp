package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.acp.mapper.AcpMoPayReqTypeMapper;
import com.hand.hec.csh.dto.CshPaymentEntityRule;
import com.hand.hec.csh.mapper.CshMoPaymentReqTypeMapper;
import com.hand.hec.csh.service.ICshPaymentEntityRuleService;
import com.hand.hec.exp.mapper.ExpMoReportTypeMapper;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.hand.hap.system.dto.Code.CODE_CSH_PAYMENT_DOCUMENT_CATEGORY;
import static com.hand.hap.system.dto.Code.CODE_PAYMENT_OBJECT;

/**
 * <p>
 *付款核算主体配置
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/03/04 13:50
 */
@Controller
public class CshPaymentEntityRuleController extends BaseController{

    @Autowired
    private ICshPaymentEntityRuleService service;

    @Autowired
	private IFndManagingOrganizationService fndManagingOrganizationService;

	@Autowired
	private ICodeService codeService;

	@Autowired
	ExpMoReportTypeMapper expMoReportTypeMapper;

	@Autowired
	CshMoPaymentReqTypeMapper cshMoPaymentReqTypeMapper;

	@Autowired
	AcpMoPayReqTypeMapper acpMoPayReqTypeMapper;

    @RequestMapping(value = "/csh/payment-entity-rule/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshPaymentEntityRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

		Criteria criteria = new Criteria(dto);
		criteria.where(
				new WhereField(CshPaymentEntityRule.FIELD_MAG_ORG_ID),
				new WhereField(CshPaymentEntityRule.FIELD_DOCUMENT_CATEGORY),
				new WhereField(CshPaymentEntityRule.FIELD_ACC_ENTITY_NAME),
				new WhereField(CshPaymentEntityRule.FIELD_COMPANY_NAME),
				new WhereField(CshPaymentEntityRule.FIELD_PAYMENT_METHOD_DESC)
		);

		List<CshPaymentEntityRule> cshPaymentEntityRuleList= service.selectOptions(requestContext,dto,criteria,page,pageSize);

		cshPaymentEntityRuleList.stream().map(cshPaymentEntityRule -> {
			String documentCategory = cshPaymentEntityRule.getDocumentCategory();
		    switch(documentCategory){
				case CshPaymentEntityRule.DOCUMENT_CATEGORY_EXP_REPORT :
					cshPaymentEntityRule.setDocumentTypeDesc(expMoReportTypeMapper.selectByPrimaryKey(cshPaymentEntityRule.getDocumentTypeId()).getDescription());
					break;
				case CshPaymentEntityRule.DOCUMENT_CATEGORY_PAYMENT_REQUISITION :
					cshPaymentEntityRule.setDocumentTypeDesc(cshMoPaymentReqTypeMapper.selectByPrimaryKey(cshPaymentEntityRule.getDocumentTypeId()).getDescription());
					break;
				case CshPaymentEntityRule.DOCUMENT_CATEGORY_ACP_REQUISITION :
					cshPaymentEntityRule.setDocumentTypeDesc(acpMoPayReqTypeMapper.selectByPrimaryKey(cshPaymentEntityRule.getDocumentTypeId()).getDescription());
					break;
				default:
					break;
			}

			return cshPaymentEntityRule;

		}).collect(Collectors.toList());



		return new ResponseData(cshPaymentEntityRuleList);
    }

    @RequestMapping(value = "/csh/payment-entity-rule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshPaymentEntityRule> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/payment-entity-rule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshPaymentEntityRule> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }


	@RequestMapping(value = "/csh/payment-entity-rule/index")
	public ModelAndView index(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);

		FndManagingOrganization defaultMagOrg = fndManagingOrganizationService.defaultManageOrganizationQuery(requestContext,requestContext.getCompanyId());

		List<FndManagingOrganization> fndManagingOrganizationList = fndManagingOrganizationService.magOrgOption(requestContext);

		List<CodeValue> cshPaymentDocumentCategoryList = codeService.getCodeValuesByCode(requestContext, CODE_CSH_PAYMENT_DOCUMENT_CATEGORY);
		List<CodeValue> paymentObjectList = codeService.getCodeValuesByCode(requestContext, CODE_PAYMENT_OBJECT);

		ModelAndView view = new ModelAndView("csh/CSH1200/csh_payment_entity_rule");
		view.addObject("defaultMagOrg", defaultMagOrg);
		view.addObject("fndManagingOrganizationList", fndManagingOrganizationList);
		view.addObject("cshPaymentDocumentCategoryList", cshPaymentDocumentCategoryList);
		view.addObject("paymentObjectList", paymentObjectList);


		return view;
	}
}
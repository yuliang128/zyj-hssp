package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *核算主体级成本中心
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/01/08 13:50
 */
@Controller
@RequestMapping("/gld/responsibility-center")
public class GldResponsibilityCenterController extends BaseController{

    @Autowired
    private IGldResponsibilityCenterService service;

	@Autowired
	private IGldAccountingEntityService gldAccountingEntityService;

    @RequestMapping("/query")
    @ResponseBody
    public ResponseData query(GldResponsibilityCenter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		if(dto.getResponsibilityCenterCode()!=null){
			criteria.where(new WhereField(GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_CODE, Comparison.LIKE));
		}
		if(dto.getResponsibilityCenterName()!=null){
			criteria.where(new WhereField(GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_NAME, Comparison.LIKE));
		}
		if(dto.getAccEntityId()!=null){
			criteria.where(new WhereField(GldResponsibilityCenter.FIELD_ACC_ENTITY_ID));
		}
        return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping("/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldResponsibilityCenter> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldResponsibilityCenter> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		GldAccountingEntity gldAccountingEntity =new GldAccountingEntity();


        GldAccountingEntity defaultAccEntity = gldAccountingEntityService.queryDefaultAccEntity(requestContext,requestContext.getCompanyId());
		List<GldAccountingEntity> gldAccountingEntityList = gldAccountingEntityService.queryAccEntityByCompany(requestContext,requestContext.getCompanyId());

		ModelAndView view = new ModelAndView("gld/GLD2030/gld_responsibility_center");
		view.addObject("gldAccountingEntityList", gldAccountingEntityList);
		view.addObject("defaultAccEntity", defaultAccEntity);
		return view;
	}
}
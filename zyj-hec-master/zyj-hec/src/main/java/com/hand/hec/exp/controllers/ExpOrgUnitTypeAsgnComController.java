package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpOrgUnitTypeAsgnCom;
import com.hand.hec.exp.service.IExpOrgUnitTypeAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  管理组织级部门类型定义
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/01/10 11:16
 */

@Controller
public class ExpOrgUnitTypeAsgnComController extends BaseController{

    @Autowired
    private IExpOrgUnitTypeAsgnComService service;


    @RequestMapping(value = "/exp/org-unit-type-asgn-com/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgUnitTypeAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(ExpOrgUnitTypeAsgnCom.FIELD_COMPANY_CODE),
				new WhereField(ExpOrgUnitTypeAsgnCom.FIELD_COMPANY_SHORT_NAME),
				new WhereField(ExpOrgUnitTypeAsgnCom.FIELD_UNIT_TYPE_ID));
		return new ResponseData(service.selectOptions(requestContext,dto,criteria));
    }

    @RequestMapping(value = "/exp/org-unit-type-asgn-com/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgUnitTypeAsgnCom> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }



    @RequestMapping(value = "/exp/org-unit-type-asgn-com/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpOrgUnitTypeAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }



	/**
	 * 批量分配管理组织下的所有所有公司
	 * @param dto ExpOrgUnitTypeAsgnCom
	 * @author rui.shi@hand-china.com 2019-03-18 15:46
	 * @return ResponseData
	 */
	@RequestMapping(value = "/exp/org-unit-type-asgn-com/batchAssignComAll")
	@ResponseBody
	public ResponseData batchAssignComAll(@RequestBody List<ExpOrgUnitTypeAsgnCom> dto, BindingResult result, HttpServletRequest request){
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(service.batchAssignComAll(requestCtx, dto));
	}
}
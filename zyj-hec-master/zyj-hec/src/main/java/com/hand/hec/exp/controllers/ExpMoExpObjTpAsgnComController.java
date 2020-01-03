package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoExpObjTpAsgnCom;
import com.hand.hec.exp.service.IExpMoExpObjTpAsgnComService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 费用对象分配公司控制器
 *
 * @author jiangxz 2019-02-26
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-obj-tp-asgn-com")
public class ExpMoExpObjTpAsgnComController extends BaseController {

    @Autowired
    private IExpMoExpObjTpAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpObjTpAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpObjTpAsgnCom.FIELD_MO_EXP_OBJ_TYPE_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpObjTpAsgnCom> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpObjTpAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryFndCompanyTypeId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryFndCompanyTypeId(@RequestParam Long moExpObjTypeId, @RequestParam Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFndCompanyTypeId(magOrgId, moExpObjTypeId, page, pageSize, requestContext));
    }


    @RequestMapping(value = "/batchAssignCompany")
    @ResponseBody
    public ResponseData batchAssignCompany(@RequestBody JSONObject jsonObject, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            new ResponseData();
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssignCompany(jsonObject, requestCtx));
    }
}
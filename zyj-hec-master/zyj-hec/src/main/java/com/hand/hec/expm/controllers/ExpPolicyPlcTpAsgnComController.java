package com.hand.hec.expm.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.expm.dto.ExpPolicyPlcTpAsgnCom;
import com.hand.hec.expm.service.IExpPolicyPlcTpAsgnComService;

/**
 * <p>
 * 费用政策地点类型分配公司Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */


@Controller
@RequestMapping(value = "/exp/policy-plc-tp-asgn-com")
public class ExpPolicyPlcTpAsgnComController extends BaseController {

    @Autowired
    private IExpPolicyPlcTpAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpPolicyPlcTpAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpPolicyPlcTpAsgnCom.FIELD_ASSIGN_ID, Comparison.EQUAL)
                , new WhereField(ExpPolicyPlcTpAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL)
                , new WhereField(ExpPolicyPlcTpAsgnCom.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpPolicyPlcTpAsgnCom.FIELD_PLACE_TYPE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto,criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpPolicyPlcTpAsgnCom> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpPolicyPlcTpAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
    /**
     * 查询剩余可以分配给费用政策类型的公司
     *
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 公司数据
     * @author xiuxian.wu 2019-02-27
     */
    @RequestMapping(value = "/queryRemainExpPolicyPlcTpAsgnCom", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRemainExpPolicyPlcTpAsgnCom(ExpPolicyPlcTpAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryRemainExpPolicyPlcTpAsgnCom(requestContext, dto, page, pageSize));
    }
}
package com.hand.hec.acp.controllers;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.acp.dto.AcpMoPayReqTpAsgnCom;
import com.hand.hec.acp.service.IAcpMoPayReqTpAsgnComService;

/**
 * <p>
 * 付款申请单类型关联公司控制器
 * </p>
 * 
 * @author guiyuting 2019/04/25 17:07
 */

@Controller
@RequestMapping(value = "/acp/mo-pay-req-tp-asgn-com")
public class AcpMoPayReqTpAsgnComController extends BaseController {

    @Autowired
    private IAcpMoPayReqTpAsgnComService service;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpMoPayReqTpAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(AcpMoPayReqTpAsgnCom.FIELD_MO_PAY_REQ_TYPE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpMoPayReqTpAsgnCom> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    /**
     *当前组织下-公司lov-不限制已分配的公司
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @author guiyuting 2019-04-26 10:53
     * @return 
     */
    @RequestMapping(value = "/queryBatchCompany")
    @ResponseBody
    public ResponseData queryBatchCompany(FndCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndCompanyService.queryBatchCompany(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/batchAssign")
    @ResponseBody
    public ResponseData batchAssign(@RequestBody List<AcpMoPayReqTpAsgnCom> dto, BindingResult result,
                               HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssign(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpMoPayReqTpAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

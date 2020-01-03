package com.hand.hec.acp.controllers;

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
import com.hand.hec.acp.dto.AcpMoPayReqTpRefEmpGp;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefEmpGpService;

/**
 * <p>
 * 付款申请单类型管理员工组控制器
 * </p>
 * 
 * @author guiyuting 2019/04/25 16:51
 */
@Controller
@RequestMapping(value = "/acp/mo-pay-req-tp-ref-emp-gp")
public class AcpMoPayReqTpRefEmpGpController extends BaseController {

    @Autowired
    private IAcpMoPayReqTpRefEmpGpService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpMoPayReqTpRefEmpGp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(AcpMoPayReqTpRefEmpGp.FIELD_MO_PAY_REQ_TYPE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpMoPayReqTpRefEmpGp> dto, BindingResult result,
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpMoPayReqTpRefEmpGp> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

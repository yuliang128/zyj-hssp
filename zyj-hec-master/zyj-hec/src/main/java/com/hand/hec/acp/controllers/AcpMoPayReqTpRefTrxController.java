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
import com.hand.hec.acp.dto.AcpMoPayReqTpRefTrx;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefTrxService;

/**
 * <p>
 * 付款申请单类型分配现金事务分类控制器
 * </p>
 * 
 * @author guiyuting 2019/04/25 10:17
 */

@Controller
@RequestMapping(value = "/acp/mo-pay-req-tp-ref-trx")
public class AcpMoPayReqTpRefTrxController extends BaseController {

    @Autowired
    private IAcpMoPayReqTpRefTrxService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(AcpMoPayReqTpRefTrx dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(AcpMoPayReqTpRefTrx.FIELD_MO_PAY_REQ_TYPE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AcpMoPayReqTpRefTrx> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AcpMoPayReqTpRefTrx> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryByTypeId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByTypeId(AcpMoPayReqTpRefTrx dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        return new ResponseData(service.queryByTypeId(requestContext, dto));
    }
}

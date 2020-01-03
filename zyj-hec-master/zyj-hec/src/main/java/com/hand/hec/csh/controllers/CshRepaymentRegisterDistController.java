package com.hand.hec.csh.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshRepaymentRegisterDist;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.mapper.CshRepaymentRegisterDistMapper;
import com.hand.hec.csh.service.ICshRepaymentRegisterDistService;

/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/csh/repayment-register-dist")
public class CshRepaymentRegisterDistController extends BaseController {

    @Autowired
    private ICshRepaymentRegisterDistService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long registerLnsId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.baseSelect(requestContext, registerLnsId));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshRepaymentRegisterDist> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CshRepaymentRegisterDist> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/trx")
    @ResponseBody
    public ResponseData queryTrxByLnId(Long reqLnId,
                                    @RequestParam(required = false) String transactionNum,
                                    @RequestParam(required = false) String paymentNum) {
        return new ResponseData(service.queryTrxById(reqLnId,transactionNum,paymentNum));
    }
}
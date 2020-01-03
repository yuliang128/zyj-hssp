package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpEmployeeAccount;
import com.hand.hec.exp.service.IExpEmployeeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 员工分配银行账户Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */

@Controller
@RequestMapping(value = "/exp/employee-account")
public class ExpEmployeeAccountController extends BaseController {

    @Autowired
    private IExpEmployeeAccountService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpEmployeeAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpEmployeeAccount> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        for (ExpEmployeeAccount d : dto) {
            if (DTOStatus.ADD.equals(d.get__status())) {
                d.setCreationDate(new Date());
                d.setCreatedBy(requestCtx.getUserId());
            }
        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpEmployeeAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryPayeeV")
    @ResponseBody
    public ResponseData queryPayeeV(HttpServletRequest request, Long payeeId, String payeeCategory, Long accEntityId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryGldPayeeV(requestContext, payeeCategory, payeeId, accEntityId));
    }
}

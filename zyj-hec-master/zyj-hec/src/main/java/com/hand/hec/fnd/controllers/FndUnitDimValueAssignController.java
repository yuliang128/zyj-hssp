package com.hand.hec.fnd.controllers;

import com.hand.hec.fnd.exception.FndUnitValueAssignException;
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
import com.hand.hec.fnd.dto.FndUnitDimValueAssign;
import com.hand.hec.fnd.service.IFndUnitDimValueAssignService;

/**
 * <p>
 * 部门维值分配控制器
 * </p>
 * 
 * @author guiyuting 2019/03/27 14:17
 */

@Controller
@RequestMapping(value = "/fnd/unit-dim-value-assign")
public class FndUnitDimValueAssignController extends BaseController {

    @Autowired
    private IFndUnitDimValueAssignService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndUnitDimValueAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByDimAssignId(requestContext, dto));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndUnitDimValueAssign> dto, BindingResult result,
                    HttpServletRequest request) throws FndUnitValueAssignException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndUnitDimValueAssign> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

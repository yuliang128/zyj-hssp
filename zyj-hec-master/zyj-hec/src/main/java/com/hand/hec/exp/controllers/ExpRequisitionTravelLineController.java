package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpRequisitionTravelLine;
import com.hand.hec.exp.service.IExpRequisitionTravelLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/exp/requisition-travel-line")
public class ExpRequisitionTravelLineController extends BaseController{

    @Autowired
    private IExpRequisitionTravelLineService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpRequisitionTravelLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpRequisitionTravelLine> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpRequisitionTravelLine> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
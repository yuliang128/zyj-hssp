package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtPeriodSetAsgnMo;
import com.hand.hec.bgt.service.IBgtPeriodSetAsgnMoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;
/**
 * <p>
 * 预算期间集分配管理组织Controller
 * </p>
 * 
 * @author mouse 2019/01/07 16:11
 */
@Controller
public class BgtPeriodSetAsgnMoController extends BaseController{

    @Autowired
    private IBgtPeriodSetAsgnMoService service;

    @RequestMapping(value = "/bgt/period/set/asgn/mo/query")
    @ResponseBody
    public ResponseData query(BgtPeriodSetAsgnMo dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/bgt/period/set/asgn/mo/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtPeriodSetAsgnMo> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/period/set/asgn/mo/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtPeriodSetAsgnMo> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}
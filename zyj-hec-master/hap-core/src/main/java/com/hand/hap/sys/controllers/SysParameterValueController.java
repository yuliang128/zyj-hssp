package com.hand.hap.sys.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.sys.service.ISysParameterValueService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 参数值Controller
 *
 * @author dingwei.ma@hand-china.com 2019-01-14
 */
@Controller
@RequestMapping(value = "/sys/parameter-value")
public class SysParameterValueController extends BaseController {

    @Autowired
    private ISysParameterValueService service;

    @Autowired
    private ISysParameterService parameterService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SysParameterValue dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(parameterService.queryParameterValue(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysParameter> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSave(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysParameterValue> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
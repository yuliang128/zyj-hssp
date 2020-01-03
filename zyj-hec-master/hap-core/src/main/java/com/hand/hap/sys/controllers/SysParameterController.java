package com.hand.hap.sys.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.exception.ParameterException;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;


/**
 * 参数Controller
 *
 * @author dingwei.ma@hand-china.com 2019-01-14
 * @author jialin.xing@hand-china.com
 */

@Controller
public class SysParameterController extends BaseController {

    @Autowired
    private ISysParameterService service;

    @RequestMapping(value = "/sys/SYS1110/sys_parameter_define.screen")
    public ModelAndView initScreenView(HttpServletRequest request, Long parameterId) {
        ModelAndView view = new ModelAndView("sys/SYS1110/sys_parameter_define");
        view.addObject("parameterId", parameterId);
        return view;
    }

    @RequestMapping(value = "/sys/parameter/query/{parameterId}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData query(@PathVariable String parameterId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        SysParameter parameter = new SysParameter();
        parameter.setParameterId(Long.parseLong(parameterId));
        Criteria criteria = new Criteria(parameter);
        return new ResponseData(service.selectOptions(requestContext, parameter, criteria, page, pageSize));
    }

    @RequestMapping(value = "/sys/parameter/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysParameter> dto, BindingResult result, HttpServletRequest request) throws ParameterException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSave(requestCtx, dto));
    }

    @RequestMapping(value = "/sys/parameter/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysParameter> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/parameter/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAll(SysParameter dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<SysParameter> list = service.queryAll(requestContext, dto, page, pageSize);
        return new ResponseData(list);
    }
}
package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.wfl.exception.WflProcessGenerateException;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.IWflProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WflProcessController extends BaseController {

    @Autowired
    private IWflProcessService service;

    @RequestMapping(value = "/wfl/process/query")
    @ResponseBody
    public ResponseData query(WflProcess dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflProcess.FIELD_PROCESS_ID),
                new WhereField(WflProcess.FIELD_PROCESS_CODE, Comparison.LIKE),
                new WhereField(WflProcess.FIELD_PROCESS_NAME, Comparison.LIKE),
                new WhereField(WflProcess.FIELD_DOC_CATEGORY)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/wfl/process/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflProcess> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/Process/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflProcess> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/wfl/process/queryWflProcess")
    @ResponseBody
    public ResponseData queryWflProcess(HttpServletRequest request, WflProcess dto) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryWflProcess(requestContext, dto));
    }

    @RequestMapping(value = "/wfl/process/generate-process")
    @ResponseBody
    public ResponseData generateProcess(HttpServletRequest request, @RequestBody WflProcess dto,
                    @RequestParam(required = false) String forceGenerate) {

        IRequest requestContext = createRequestContext(request);

        try {
            service.generateProcess(requestContext, dto, forceGenerate);
        } catch (WflProcessGenerateException e) {
            return new ResponseData(false);
        }

        // 修改process的configType为SIMPLE
        service.changeConfigType(requestContext, dto.getProcessId(), WflProcess.CONFIG_TYPE_SIMPLE);

        return new ResponseData(true);
    }

    @RequestMapping(value = "/wfl/process/publish-process")
    @ResponseBody
    public ResponseData publishProcess(HttpServletRequest request, @RequestBody List<WflProcess> dto) {
        IRequest requestContext = createRequestContext(request);
        for (WflProcess process : dto) {
            service.publishProcess(requestContext, process);
        }
        return new ResponseData(true);
    }

    @RequestMapping(value = {"/wfl/WFL1010/wfl_element", "wfl/WFL1010/wfl_process_action"})
    public ModelAndView getDefaultBusinessRule(@RequestParam Long processId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        WflProcess wflProcess = new WflProcess();
        wflProcess.setProcessId(processId);

        wflProcess = service.selectByPrimaryKey(requestContext, wflProcess);
        String viewName = request.getServletPath().substring(0, request.getServletPath().indexOf(".screen"));

        ModelAndView modelAndView = new ModelAndView(viewName);


        modelAndView.addObject("processInfo", wflProcess);

        return modelAndView;
    }
}

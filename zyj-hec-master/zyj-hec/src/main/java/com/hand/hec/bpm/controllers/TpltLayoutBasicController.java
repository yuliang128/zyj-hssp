package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.TpltLayout;
import com.hand.hec.bpm.service.ITpltLayoutBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TpltLayoutBasicController extends BaseController {

    @Autowired
    private ITpltLayoutBasicService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM101/tpltLayoutBasic")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM101/tpltLayoutBasic");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> layoutTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.LAYOUT_TYPE");
        view.addObject("layoutType", layoutTypeList);
        return view;
    }

    @RequestMapping(value = "/bpm/tpltLayoutBasic/query")
    @ResponseBody
    public ResponseData query(TpltLayout layout, HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryByTemplateId(requestCtx, layout));
    }


    @RequestMapping(value = "/bpm/tpltLayoutBasic/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<TpltLayout> layoutList, HttpServletRequest request, BindingResult result) {

        getValidator().validate(layoutList, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateLayout(requestCtx, layoutList));
    }
}
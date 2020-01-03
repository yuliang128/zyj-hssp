package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.PageLayout;
import com.hand.hec.bpm.service.IPageLayoutBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageLayoutBasicController extends BaseController {

    @Autowired
    private IPageLayoutBasicService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM102/pageLayoutBasic")
    public ModelAndView jumpPageLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM102/pageLayoutBasic");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> layoutTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.LAYOUT_TYPE");
        view.addObject("layoutType", layoutTypeList);
        return view;
    }

    @RequestMapping(value = "/bpm/pageLayoutBasic/query")
    @ResponseBody
    public ResponseData query(PageLayout layout, HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryByPageId(requestCtx, layout));
    }


    @RequestMapping(value = "/bpm/pageLayoutBasic/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageLayout> layoutList, HttpServletRequest request, BindingResult result) {

        getValidator().validate(layoutList, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateLayout(requestCtx, layoutList));
    }

    @RequestMapping(value = "/bpm/pageLayoutBasic/update")
    @ResponseBody
    public ResponseData updateByEngine(@RequestBody PageLayout layout, HttpServletRequest request, BindingResult result) {

        getValidator().validate(layout, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        layout = service.updateBasic(requestCtx, layout);
        List<PageLayout> list = new ArrayList<>();
        list.add(layout);
        return new ResponseData(list);
    }
}
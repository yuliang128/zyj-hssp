package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.PageLayout;
import com.hand.hec.bpm.dto.PageLayoutBasic;
import com.hand.hec.bpm.dto.PageTag;
import com.hand.hec.bpm.dto.TpltLayout;
import com.hand.hec.bpm.service.IPageTagService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageTagController extends BaseController {

    @Autowired
    private IPageTagService service;

    @RequestMapping(value = "/bpm/pageTag/query")
    @ResponseBody
    public ResponseData query(PageTag tag, HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryPageTag(requestCtx, tag.getLayoutId()));
    }


    @RequestMapping(value = "/bpm/pageTag/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageTag> tagList, HttpServletRequest request, BindingResult result) {

        getValidator().validate(tagList, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdatePageTag(requestCtx, tagList));
    }

    @RequestMapping(value = "/bpm/pageTag/queryByLayout")
    @ResponseBody
    public ResponseData queryByLayout(PageLayoutBasic layout, HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        PageTag tag = new PageTag();
        if(layout!=null){
            tag.setLayoutId(layout.getLayoutId());
        }
        return new ResponseData(service.queryPageTag(requestCtx, tag.getLayoutId()));
    }

    @RequestMapping(value = "/bpm/pageTag/update")
    @ResponseBody
    public ResponseData updateByEngine(@RequestBody PageTag tag, HttpServletRequest request, BindingResult result) {

        getValidator().validate(tag, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        tag = service.updatePageTag(requestCtx, tag);
        List<PageTag> list = new ArrayList<>();
        list.add(tag);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/bpm/pageTag/insert")
    @ResponseBody
    public ResponseData insertByEngine(@RequestBody PageTag tag, HttpServletRequest request, BindingResult result) {

        getValidator().validate(tag, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        tag = service.insertPageTag(requestCtx, tag);
        List<PageTag> list = new ArrayList<>();
        list.add(tag);
        return new ResponseData(list);
    }
}
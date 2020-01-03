package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.Page;
import com.hand.hec.bpm.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController extends BaseController {

    @Autowired
    private IPageService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/page/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, Page page, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(page);
        criteria.where(new WhereField(Page.FIELD_PAGE_ID, Comparison.EQUAL), new WhereField(Page.FIELD_GROUP_ID, Comparison.EQUAL), new WhereField(Page.FIELD_PAGE_CODE, Comparison.LIKE), new WhereField(Page.FIELD_PAGE_DESC, Comparison.LIKE), new WhereField(Page.FIELD_PAGE_TYPE, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, page, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/page/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<Page> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bpm/ENGINE/pageUpdate")
    public ModelAndView getPageTpltInfo(Long pageId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Map map = new HashMap();

        map = service.queryTemplateReference(requestContext, pageId);
        ModelAndView modelAndView = new ModelAndView("bpm/ENGINE/pageUpdate");


        modelAndView.addObject("pageTpltInfo", map);

        return modelAndView;
    }

    @RequestMapping(value = "/bpm/BPM102/pageCopy")
    public ResponseData pageCopy(Long pageId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        service.copyPage(pageId, requestCtx);
        return new ResponseData();
    }

}
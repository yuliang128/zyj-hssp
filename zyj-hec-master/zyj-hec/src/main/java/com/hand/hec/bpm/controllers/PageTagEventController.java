package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.service.*;
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
public class PageTagEventController extends BaseController {

    @Autowired
    private IPageTagEventService service;

    @Autowired
    private IPageTagBasicService tagBasicService;

    @Autowired
    private IPageLayoutBasicService layoutBasicService;

    @Autowired
    private IPageService pageService;

    @RequestMapping(value = "/bpm/BPM102/pageTagEvent")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request, @RequestParam Long tagId) {
        ModelAndView view = new ModelAndView("bpm/BPM102/pageTagEvent");
        IRequest requestContext = createRequestContext(request);

        PageTagBasic tag = new PageTagBasic();
        tag.setTagId(tagId);
        tag = tagBasicService.selectByPrimaryKey(requestContext, tag);

        PageLayoutBasic layout = new PageLayoutBasic();
        layout.setLayoutId(tag.getLayoutId());
        layout = layoutBasicService.selectByPrimaryKey(requestContext, layout);

        view.addObject("layout", layout);

        return view;
    }


    @RequestMapping(value = "/bpm/pageTagEvent/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageTagEvent tagEvent, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(tagEvent);
        criteria.where(new WhereField(PageTagEvent.FIELD_EVENT_ID, Comparison.EQUAL), new WhereField(PageTagEvent.FIELD_TAG_ID, Comparison.EQUAL), new WhereField(PageTagEvent.FIELD_GUIDE_ID, Comparison.EQUAL), new WhereField(PageTagEvent.FIELD_EVENT_TARGET, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, tagEvent, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/pageTagEvent/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageTagEvent> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }
}
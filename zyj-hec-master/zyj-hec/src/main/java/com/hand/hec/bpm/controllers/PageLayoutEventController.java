package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.Page;
import com.hand.hec.bpm.dto.PageLayoutEvent;
import com.hand.hec.bpm.service.IPageLayoutEventService;
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
public class PageLayoutEventController extends BaseController {

    @Autowired
    private IPageLayoutEventService service;


    @RequestMapping(value = "/bpm/pageLayoutEvent/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageLayoutEvent layoutEvent, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(layoutEvent);
        criteria.where(new WhereField(PageLayoutEvent.FIELD_EVENT_ID, Comparison.EQUAL), new WhereField(PageLayoutEvent.FIELD_LAYOUT_ID, Comparison.EQUAL), new WhereField(PageLayoutEvent.FIELD_GUIDE_ID, Comparison.EQUAL), new WhereField(PageLayoutEvent.FIELD_EVENT_TARGET, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, layoutEvent, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/pageLayoutEvent/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageLayoutEvent> dto, HttpServletRequest request, BindingResult result) {
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
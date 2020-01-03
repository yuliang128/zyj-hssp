package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.PageLayoutBasic;
import com.hand.hec.bpm.dto.PageTagBasic;
import com.hand.hec.bpm.dto.PageTagDataGuide;
import com.hand.hec.bpm.service.IPageLayoutBasicService;
import com.hand.hec.bpm.service.IPageService;
import com.hand.hec.bpm.service.IPageTagBasicService;
import com.hand.hec.bpm.service.IPageTagDataGuideService;
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
public class PageTagDataGuideController extends BaseController {

    @Autowired
    private IPageTagDataGuideService service;

    @Autowired
    private IPageTagBasicService tagBasicService;

    @Autowired
    private IPageLayoutBasicService layoutBasicService;

    @Autowired
    private IPageService pageService;

    @RequestMapping(value = "/bpm/BPM102/pageTagDataGuide")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request, @RequestParam Long tagId) {
        ModelAndView view = new ModelAndView("bpm/BPM102/pageTagDataGuide");
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

    @RequestMapping(value = "/bpm/pageTagDataGuide/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageTagDataGuide dataGuide, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(dataGuide);
        criteria.where(new WhereField(PageTagDataGuide.FIELD_GUIDE_ID, Comparison.EQUAL), new WhereField(PageTagDataGuide.FIELD_TAG_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, dataGuide, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/pageTagDataGuide/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageTagDataGuide> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        List<PageTagDataGuide> list = service.batchUpdate(requestCtx, dto);

        //根据数据向导生成对应的事件
        if (list != null && list.size() != 0) {
            PageTagDataGuide dataGuide = dto.get(0);
                PageTagBasic tag = new PageTagBasic();
                tag.setTagId(dataGuide.getTagId());
                tag = tagBasicService.selectByPrimaryKey(requestCtx, tag);
                service.clearDataGuideEvent(requestCtx, tag,list);
                service.generateDataGuideEvent(requestCtx, tag,list);
        }

        return new ResponseData(list);
    }
}
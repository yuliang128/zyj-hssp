package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.PageGroup;
import com.hand.hec.bpm.service.IPageGroupService;
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
public class PageGroupController extends BaseController {

    @Autowired
    private IPageGroupService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM102/pageGroup")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM102/pageGroup");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> docCategory = codeService.getCodeValuesByCode(requestCtx, "FND.DOC_CATEGORY");
        view.addObject("docCategory", docCategory);
        return view;
    }


    @RequestMapping(value = "/bpm/pageGroup/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageGroup group, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);


        Criteria criteria = new Criteria(group);
        criteria.where(new WhereField(PageGroup.FIELD_GROUP_ID, Comparison.EQUAL), new WhereField(PageGroup.FIELD_GROUP_CODE, Comparison.LIKE), new WhereField(PageGroup.FIELD_GROUP_DESC, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestCtx, group, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/pageGroup/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageGroup> dto, HttpServletRequest request, BindingResult result) {
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
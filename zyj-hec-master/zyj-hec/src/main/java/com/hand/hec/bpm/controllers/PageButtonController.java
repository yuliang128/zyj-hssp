package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.PageButton;
import com.hand.hec.bpm.service.IPageButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageButtonController extends BaseController {

    @Autowired
    private IPageButtonService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/pageButton/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageButton pageButton, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(pageButton);
        criteria.where(new WhereField(PageButton.FIELD_BUTTON_ID, Comparison.EQUAL), new WhereField(PageButton.FIELD_BUTTON_CODE, Comparison.LIKE), new WhereField(PageButton.FIELD_BUTTON_DESC, Comparison.LIKE), new WhereField(PageButton.FIELD_PAGE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, pageButton, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/pageButton/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageButton> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bpm/pageButton/update")
    @ResponseBody
    public ResponseData updateByEngine(@RequestBody PageButton dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        dto = service.updateByPrimaryKey(requestCtx, dto);
        List<PageButton> list = new ArrayList<>();
        list.add(dto);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/bpm/pageButton/insert")
    @ResponseBody
    public ResponseData insertByEngine(@RequestBody PageButton dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        dto = service.insert(requestCtx, dto);
        List<PageButton> list = new ArrayList<>();
        list.add(dto);
        return new ResponseData(list);
    }
}
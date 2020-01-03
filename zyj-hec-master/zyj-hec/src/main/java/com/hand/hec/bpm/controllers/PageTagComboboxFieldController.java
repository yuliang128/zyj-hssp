package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.PageTagComboboxField;
import com.hand.hec.bpm.service.IPageTagComboboxFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageTagComboboxFieldController extends BaseController {

    @Autowired
    private IPageTagComboboxFieldService service;


    @RequestMapping(value = "/bpm/pageTagComboboxField/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageTagComboboxField comboboxField, @RequestParam(defaultValue = DEFAULT_PAGE,name = "page") int pageNum,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(comboboxField);
        criteria.where(new WhereField(PageTagComboboxField.FIELD_TAG_ID, Comparison.EQUAL), new WhereField(PageTagComboboxField.FIELD_COMBOBOX_FIELD_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, comboboxField, criteria, pageNum, pageSize));
    }

    @RequestMapping(value = "/bpm/pageTagComboboxField/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageTagComboboxField> dto, HttpServletRequest request, BindingResult result) {
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
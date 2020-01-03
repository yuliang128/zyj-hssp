package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.DocHeader;
import com.hand.hec.bpm.dto.Page;
import com.hand.hec.bpm.dto.PageGroupField;
import com.hand.hec.bpm.service.IDocHeaderService;
import com.hand.hec.bpm.service.IPageGroupFieldService;
import com.hand.hec.bpm.service.IPageService;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DocHeaderController extends BaseController {

    @Autowired
    private IDocHeaderService service;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IPageGroupFieldService fieldService;


    @RequestMapping(value = "/bpm/docHeader/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, DocHeader docHeader,
                    @RequestParam(defaultValue = DEFAULT_PAGE, name = "page") int pageNum,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                    @RequestParam(defaultValue = "CREATION") String pageType) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(docHeader);
        criteria.where(new WhereField(DocHeader.FIELD_DOC_HEADER_ID, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_DOCUMENT_NUMBER, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_TEMPLATE_ID, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_PAGE_GROUP_ID, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_OA_FLOW_CATEGORY, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_OA_FLOW_SUBCATEGORY, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_DOCUMENT_DATE, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_TEMPLATE_ID, Comparison.EQUAL),
                        new WhereField(DocHeader.FIELD_PAGE_GROUP_ID, Comparison.EQUAL));

        // 设置动态查询条件
        addDynamicQueryField(requestCtx, criteria, docHeader);

        List<DocHeader> docHeaders = service.selectOptions(requestCtx, docHeader, criteria, pageNum, pageSize);
        for (DocHeader header : docHeaders) {
            Page page = new Page();
            page.setGroupId(header.getPageGroupId());
            page.setPageType(pageType);
            List<Page> pages = pageService.selectOptions(requestCtx, page, new Criteria(page));
            if (pages != null && pages.size() > 0) {
                page = pages.get(0);
                header.setPageId(page.getPageId());
            }
        }
        return new ResponseData(docHeaders);
    }

    @RequestMapping(value = "/bpm/docHeader/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<DocHeader> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    private void addDynamicQueryField(IRequest requestCtx, Criteria criteria, DocHeader docHeader) {
        // PageGroupField queryField = new PageGroupField();
        // queryField.setGroupId(docHeader.getPageGroupId());
        // queryField.setQueryFlag("Y");
        // Criteria fieldCriteria = new Criteria(queryField);
        // fieldCriteria.where(new WhereField(PageGroupField.FIELD_GROUP_ID), new
        // WhereField(PageGroupField.FIELD_QUERY_FLAG));
        // List<PageGroupField> fieldList = fieldService.selectOptions(requestCtx, queryField,
        // fieldCriteria, 0, 0);
        // if (fieldList != null) {
        // for (PageGroupField field : fieldList) {
        // criteria.where(new WhereField(field.getFieldName()));
        // }
        // }
    }

}

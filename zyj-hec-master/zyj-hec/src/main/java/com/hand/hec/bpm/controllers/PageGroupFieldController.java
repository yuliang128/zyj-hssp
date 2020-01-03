package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.PageGroupField;
import com.hand.hec.bpm.service.IPageGroupFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
public class PageGroupFieldController extends BaseController {

    @Autowired
    private IPageGroupFieldService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM102/pageGroupField")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM102/pageGroupField");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> dataTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.DATABASE_DATATYPE");
        view.addObject("dataType", dataTypeList);
        List<CodeValue> logicTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.DATABASE_LOGICTYPE");
        view.addObject("logicType", logicTypeList);
        return view;
    }


    @RequestMapping(value = "/bpm/pageGroupField/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageGroupField field,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        if (field.getEntityId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        Criteria criteria = new Criteria(field);
        criteria.where(new WhereField(PageGroupField.FIELD_FIELD_ID, Comparison.EQUAL),
                        new WhereField(PageGroupField.FIELD_FIELD_DESC, Comparison.LIKE),
                        new WhereField(PageGroupField.FIELD_ENTITY_ID, Comparison.EQUAL),
                        new WhereField(PageGroupField.FIELD_QUERY_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, field, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/pageGroupField/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageGroupField> dto, HttpServletRequest request,
                    BindingResult result) {
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

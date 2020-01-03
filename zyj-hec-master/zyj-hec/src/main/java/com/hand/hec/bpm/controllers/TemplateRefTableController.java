package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.TemplateRefTable;
import com.hand.hec.bpm.service.ITemplateRefTableService;
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
public class TemplateRefTableController extends BaseController {

    @Autowired
    private ITemplateRefTableService service;

    @RequestMapping(value = "/bpm/tpltRefTable/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, TemplateRefTable table, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(table);
        criteria.where(new WhereField(TemplateRefTable.FIELD_BPM_REF_TABLE_ID, Comparison.EQUAL), new WhereField(TemplateRefTable.FIELD_TEMPLATE_ID, Comparison.EQUAL), new WhereField(TemplateRefTable.FIELD_TABLE_DESCRIPTION, Comparison.LIKE), new WhereField(TemplateRefTable.FIELD_TABLE_NAME, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, table, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/tpltRefTable/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<TemplateRefTable> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }


    public List<TemplateRefTable> queryByTemplateId(IRequest request, TemplateRefTable TemplateRefTable) {
        return service.queryByTemplateId(request, TemplateRefTable);
    }
}
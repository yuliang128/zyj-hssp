package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.Template;
import com.hand.hec.bpm.service.ITemplateService;
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
public class TemplateController extends BaseController {

    @Autowired
    private ITemplateService service;

    @RequestMapping(value = "/bpm/template/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, Template template, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);

        Criteria criteria = new Criteria(template);
        criteria.where(new WhereField(Template.FIELD_TEMPLATE_ID, Comparison.EQUAL), new WhereField(Template.FIELD_TEMPLATE_CODE, Comparison.LIKE), new WhereField(Template.FIELD_TEMPLATE_DESC, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, template, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/template/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<Template> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    public List<Template> selectAllTemplates(IRequest request, Template template) {
        return service.selectAllByLike(template);
    }

    public Template setTemplateChange(IRequest request, Template template) {
        return service.setTemplateChange(request, template);
    }

    public List<Template> queryTemplateLov(IRequest request, Template template) {
        return service.queryTemplateLov(request, template);
    }

    public List<Template> queryDatesetFetch(IRequest request, Long pageId) {
        return service.queryDatesetFetch(request, pageId);
    }
}

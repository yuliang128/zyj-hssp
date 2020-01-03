package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflPage;
import com.hand.hec.wfl.service.IWflPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
public class WflPageController extends BaseController {

    @Autowired
    private IWflPageService service;


    @RequestMapping(value = "/wfl/page/query")
    @ResponseBody
    public ResponseData query(WflPage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflPage.FIELD_PAGE_CODE, Comparison.LIKE),
                new WhereField(WflPage.FIELD_PAGE_NAME, Comparison.LIKE)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/wfl/page/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflPage> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/page/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflPage> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

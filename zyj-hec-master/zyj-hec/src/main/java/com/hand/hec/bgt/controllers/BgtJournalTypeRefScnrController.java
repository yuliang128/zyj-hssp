package com.hand.hec.bgt.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalTypeRefScnr;
import com.hand.hec.bgt.service.IBgtJournalTypeRefScnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

/**
 * <p>
 * 预算日记账类型关联预算场景Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:54
 */
@RequestMapping(value = "/bgt/journal-type-ref-scnr")
@Controller
public class BgtJournalTypeRefScnrController extends BaseController {

    @Autowired
    private IBgtJournalTypeRefScnrService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtJournalTypeRefScnr dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(BgtJournalTypeRefScnr.FIELD_BGT_JOURNAL_TYPE_ID, Comparison.EQUAL);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalTypeRefScnr> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtJournalTypeRefScnr> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

package com.hand.hec.bgt.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtPeriodSet;
import com.hand.hec.bgt.service.IBgtPeriodSetService;
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
 * 预算期间集Controller
 * </p>
 * 
 * @author mouse 2019/01/07 16:11
 */
@Controller
@RequestMapping(value = "/bgt/period-set")
public class BgtPeriodSetController extends BaseController {

    @Autowired
    private IBgtPeriodSetService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtPeriodSet dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(BgtPeriodSet.FIELD_PERIOD_SET_CODE, Comparison.LIKE),
                        new WhereField(BgtPeriodSet.FIELD_PERIOD_SET_NAME, Comparison.LIKE),
                        new WhereField(BgtPeriodSet.FIELD_PERIOD_SET_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtPeriodSet> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtPeriodSet> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

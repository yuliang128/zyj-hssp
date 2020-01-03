package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.service.IFndDimensionValueService;

/**
 * <p>
 * FndDimensionValueController
 * </p>
 * 
 * @author guiyuting 2019/02/26 16:39
 */

@Controller
@RequestMapping(value = "/fnd/dimension-value")
public class FndDimensionValueController extends BaseController {

    @Autowired
    private IFndDimensionValueService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndDimensionValue dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndDimensionValue.FIELD_DIMENSION_ID, Comparison.EQUAL),
                        new WhereField(FndDimensionValue.FIELD_DIMENSION_VALUE_CODE, Comparison.LIKE),
                        new WhereField(FndDimensionValue.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(FndDimensionValue.FIELD_DIMENSION_VALUE_ID, Comparison.EQUAL),
                        new WhereField(FndDimensionValue.FIELD_SUMMARY_FLAG, Comparison.EQUAL),
                        new WhereField(FndDimensionValue.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndDimensionValue> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndDimensionValue> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}

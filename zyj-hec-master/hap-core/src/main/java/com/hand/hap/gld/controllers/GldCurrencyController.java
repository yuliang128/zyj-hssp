package com.hand.hap.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 币种控制器
 * </p>
 *
 * @author MouseZhou 2019/01/04 18:53
 */
@Controller
@RequestMapping(value = "/gld-currency")
public class GldCurrencyController extends BaseController {

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @RequestMapping(value = "/query")
    public ResponseData query(GldCurrency currency, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(currency);
        criteria.where(new WhereField(GldCurrency.FIELD_ENABLED_FLAG, Comparison.EQUAL),
                new WhereField(GldCurrency.FIELD_CURRENCY_CODE, Comparison.EQUAL),
                new WhereField(GldCurrency.FIELD_CURRENCY_CODE, Comparison.EQUAL));
        return new ResponseData(gldCurrencyService.selectOptions(requestContext, currency, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    public ResponseData submit(@RequestBody List<GldCurrency> currencies, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(currencies, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(gldCurrencyService.batchUpdate(requestCtx, currencies));
    }

    @RequestMapping(value = "/remove")
    public ResponseData remove(@RequestBody List<GldCurrency> currencies) {
        gldCurrencyService.batchDelete(currencies);
        return new ResponseData();
    }

    /**
     * 获取启用的货币下拉框
     *
     * @param request
     * @author YHL 2019-03-14 20:02
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/getGldCurrencyOption")
    public ResponseData getGldCurrencyOption(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(gldCurrencyService.queryEnabledCurrency(requestCtx));
    }

}

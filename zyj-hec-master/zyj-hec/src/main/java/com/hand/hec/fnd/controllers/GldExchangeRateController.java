package com.hand.hec.fnd.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.fnd.dto.GldExchangeRate;
import com.hand.hec.fnd.service.IGldExchangeRateService;

/**
 *
 * 汇率定义控制器
 *
 * @weikun.wang xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/gld/exchange-rate")
public class GldExchangeRateController extends BaseController {

    @Autowired
    private IGldExchangeRateService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldExchangeRate dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldExchangeRate.FIELD_TYPE_ID, Comparison.EQUAL),
                        new WhereField(GldExchangeRate.FIELD_FROM_CURRENCY, Comparison.EQUAL),
                        new WhereField(GldExchangeRate.FIELD_TO_CURRENCY, Comparison.EQUAL));
        if (dto.getPeriodFrom() != null && !"undefined".equals(dto.getPeriodFrom())) {

            criteria.where(new WhereField(GldExchangeRate.FIELD_PERIOD_FROM));
        }
        if (dto.getPeriodTo() != null && !"undefined".equals(dto.getPeriodTo())) {
            criteria.where(new WhereField(GldExchangeRate.FIELD_PERIOD_TO));
        }
        if (dto.getDateFrom() != null && !"undefined".equals(dto.getDateFrom())) {
            System.out.println("******" + dto.getDateFrom());
            criteria.where(new WhereField(GldExchangeRate.FIELD_DATE_FROM));
        }
        if (dto.getDateTo() != null && !"undefined".equals(dto.getDateTo())) {
            criteria.where(new WhereField(GldExchangeRate.FIELD_DATE_TO));
        }

        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldExchangeRate> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldExchangeRate> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * @Description
     *
     * @Author Tagin
     * @Date 2019-03-12 21:19
     * @param request
     * @param map Tips：其中 map 中需要传入的值如下：
     * @param accEntityId 核算主体ID
     * @param fromCurrency 需转换的币种
     * @param toCurrency 转换的币种
     * @param exchangeRateType 汇率类型
     * @param exchangeDate 汇率转换日期
     * @param periodName 汇率转换期间
     * @param oppositeFlag 反转标志 默认为"N" 不反转
     * @Return com.hand.hap.system.dto.ResponseData
     * @Version 1.0
     **/
    @RequestMapping(value = "/getExchangeRate", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getExchangeRate(HttpServletRequest request, @RequestBody Map<String, String> map)
                    throws CshWriteOffException {
        Date exchangeDate = null;
        List<BigDecimal> list = new ArrayList<>();
        try {
            exchangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("exchangeDate"));
        } catch (Exception e) {
            throw new CshWriteOffException(CshWriteOffException.DATE_FORMAT_ERROR,
                            CshWriteOffException.DATE_FORMAT_ERROR, null);
        }
        String oppoFlag = map.get("oppositeFlag") == null ? "N" : map.get("oppositeFlag");
        BigDecimal exchangeRate = service.getExchangeRate(Long.valueOf(map.get("accEntityId")), map.get("fromCurrency"),
                        map.get("toCurrency"), map.get("exchangeRateType"), exchangeDate, map.get("periodName"),
                        oppoFlag);
        list.add(exchangeRate);
        return new ResponseData(list);
    }
}

package com.hand.hec.fnd.service;

/**
 *
 * 汇率定义Service
 *
 * @weikun.wang xxx 2019-02-22
 */
import java.math.BigDecimal;
import java.util.Date;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldExchangeRate;

public interface IGldExchangeRateService extends IBaseService<GldExchangeRate>, ProxySelf<IGldExchangeRateService> {


    /**
     * @Description 获取汇率
     *
     * @Author Tagin
     * @Date 2019-03-06 14:44
     * @param accEntityId 核算主体ID
     * @param fromCurrency 需转换的币种
     * @param toCurrency 转换的币种
     * @param exchangeRateType 汇率类型
     * @param exchangeDate 汇率转换日期
     * @param periodName 汇率转换期间
     * @param oppositeFlag 反转标志 默认为"N" 不反转
     * @Return java.math.BigDecimal
     * @Version 1.0
     **/
    BigDecimal getExchangeRate(Long accEntityId, String fromCurrency, String toCurrency, String exchangeRateType,
                    Date exchangeDate, String periodName, String oppositeFlag);
}

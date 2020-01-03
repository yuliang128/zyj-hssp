package com.hand.hec.fnd.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldExchangeRate;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.mapper.GldExchangeRateMapper;
import com.hand.hec.fnd.mapper.GldExchangerateTypeMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;

/**
 *
 * 汇率定义ServiceImpl
 *
 * @weikun.wang xxx 2019-02-22
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GldExchangeRateServiceImpl extends BaseServiceImpl<GldExchangeRate> implements IGldExchangeRateService {

    @Autowired
    private GldExchangeRateMapper gldExchangeRateMapper;

    @Autowired
    private GldExchangerateTypeMapper gldExchangerateTypeMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    /**
     * @Description 获取汇率
     *
     * @Author Tagin
     * @Date 2019-03-06 16:39
     * @param fromCurrency 需转换的币种
     * @param toCurrency 转换的币种
     * @param exchangeRateType 汇率类型
     * @param exchangeDate 汇率转换日期
     * @param periodName 汇率转换期间
     * @Return java.math.BigDecimal
     * @Version 1.0
     **/
    public BigDecimal exchangeRate(String fromCurrency, String toCurrency, String exchangeRateType, Date exchangeDate,
                    String periodName, String accurateFlag) {
        List<GldExchangeRate> gldExchangeRateList = gldExchangeRateMapper.queryConversion(exchangeRateType,
                        fromCurrency, toCurrency, exchangeDate, periodName, accurateFlag);
        for (GldExchangeRate dto : gldExchangeRateList) {
            return dto.getCurrencyVolume().divide(dto.getConversionRate(), 6, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * @Description 计算汇率
     *
     * @Author Tagin
     * @Date 2019-03-06 14:44
     * @param fromCurrency 需转换的币种
     * @param toCurrency 转换的币种
     * @param exchangeRateType 汇率类型
     * @param exchangeDate 汇率转换日期
     * @param periodName 汇率转换期间
     * @param oppositeFlag 反转标志 默认为"N" 不反转
     * @Return java.math.BigDecimal
     * @Version 1.0
     **/
    public BigDecimal calcExchangeRate(String fromCurrency, String toCurrency, String exchangeRateType,
                    Date exchangeDate, String periodName, String oppositeFlag, String methodCode) {
        BigDecimal exchangeRate = null;
        // 1.0 同币种
        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.valueOf(1);
        }
        // 2.0 精确匹配
        exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, exchangeDate, periodName, "Y");
        if (exchangeRate != null) {
            return exchangeRate;
        }
        // 3.0 判断
        String oppoFlag = oppositeFlag == null ? "N" : oppositeFlag;
        if ("DAILY".equals(methodCode)) {
            // 3.0.1 每日汇率逻辑
            // Tips：每日汇率取小于等于当天的最大日期的汇率【正常兑换】
            Date maxExchangeDate = gldExchangeRateMapper.queryMaxDate(exchangeRateType, fromCurrency, toCurrency,
                            exchangeDate);
            // Tips：每日汇率取小于等于当天的最大日期的汇率【反转兑换】
            Date maxOppositeDate = gldExchangeRateMapper.queryMaxDate(exchangeRateType, toCurrency, fromCurrency,
                            exchangeDate);
            // Tips：判断是否反转，若反转则判断反转日期同正常日期的大小取最大日期对应的汇率
            if ("N".equals(oppoFlag)) {
                if (maxExchangeDate != null && maxOppositeDate != null) {
                    if (maxExchangeDate.compareTo(maxOppositeDate) >= 0) {
                        exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, maxExchangeDate, null,
                                        "N");
                    } else {
                        exchangeRate = BigDecimal.valueOf(1).divide(exchangeRate(toCurrency, fromCurrency,
                                        exchangeRateType, maxOppositeDate, null, "N"), 6, BigDecimal.ROUND_HALF_UP);
                    }
                } else if (maxExchangeDate != null) {
                    exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, maxExchangeDate, null, "N");
                } else if (maxOppositeDate != null) {
                    exchangeRate = BigDecimal.valueOf(1).divide(exchangeRate(toCurrency, fromCurrency, exchangeRateType,
                                    maxOppositeDate, null, "N"), 6, BigDecimal.ROUND_HALF_UP);
                }
            } else {
                if (maxExchangeDate != null) {
                    exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, maxExchangeDate, null, "N");
                }
            }
        } else if ("PERIOD".equals(methodCode)) {
            // 3.0.2 期间汇率
            // Tips：期间汇率取小于当期的最大期间的汇率【正常兑换】
            String maxExchangePeriod = gldExchangeRateMapper.queryMaxPeriod(exchangeRateType, fromCurrency, toCurrency,
                            periodName);
            // Tips：期间汇率取小于当期的最大期间的汇率【反转兑换】
            String maxOppositePeriod = gldExchangeRateMapper.queryMaxPeriod(exchangeRateType, toCurrency, fromCurrency,
                            periodName);
            // Tips：判断是否反转，若反转则判断反转期间同正常期间的大小取最大期间对应的汇率
            if ("N".equals(oppoFlag)) {
                if (maxExchangePeriod != null && maxOppositePeriod != null) {
                    if (maxExchangePeriod.compareTo(maxOppositePeriod) >= 0) {
                        exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, null, periodName, "N");
                    } else {
                        exchangeRate = BigDecimal.valueOf(1).divide(
                                        exchangeRate(toCurrency, fromCurrency, exchangeRateType, null, periodName, "N"),
                                        6, BigDecimal.ROUND_HALF_UP);
                    }
                } else if (maxExchangePeriod != null) {
                    exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, null, periodName, "N");
                } else if (maxOppositePeriod != null) {
                    exchangeRate = BigDecimal.valueOf(1).divide(
                                    exchangeRate(toCurrency, fromCurrency, exchangeRateType, null, periodName, "N"), 6,
                                    BigDecimal.ROUND_HALF_UP);
                }
            } else {
                if (maxExchangePeriod != null) {
                    exchangeRate = exchangeRate(fromCurrency, toCurrency, exchangeRateType, null, periodName, "N");
                }
            }
        }
        return exchangeRate;
    }

    /**
     * @Description 获取汇率-入口
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
    @Override
    public BigDecimal getExchangeRate(Long accEntityId, String fromCurrency, String toCurrency, String exchangeRateType,
                    Date exchangeDate, String periodName, String oppositeFlag) {
        // 1.0 获取汇率类型
        String methodCode = null;
        BigDecimal exchangeRate = null;
        GldExchangerateType dto = new GldExchangerateType();
        dto.setTypeCode(exchangeRateType);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldExchangerateType.FIELD_TYPE_CODE, Comparison.EQUAL));
        List<GldExchangerateType> gldExchangerateTypes = gldExchangerateTypeMapper.selectOptions(dto, criteria);
        if (gldExchangerateTypes != null && gldExchangerateTypes.size() > 0) {
            methodCode = gldExchangerateTypes.get(0).getMethodCode();
        }
        // 2.0 获取匹配或反转汇率
        exchangeRate = calcExchangeRate(fromCurrency, toCurrency, exchangeRateType, exchangeDate, periodName,
                        oppositeFlag, methodCode);
        if (exchangeRate != null) {
            return exchangeRate;
        } else {
            // 3.0 若正常兑换或反转兑换均未获取数据则按照中间转换币种进行转换
            String defaultCurrency = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_EXCHANGE_CURRENCY, null, null, null, accEntityId, null,
                            null, null);
            if (defaultCurrency != null && !"N".equals(defaultCurrency) && !defaultCurrency.equals(fromCurrency)
                            && !defaultCurrency.equals(toCurrency)) {
                BigDecimal defaultFromRate = calcExchangeRate(fromCurrency, defaultCurrency, exchangeRateType,
                                exchangeDate, periodName, "N", methodCode);
                BigDecimal defaultToRate = calcExchangeRate(toCurrency, defaultCurrency, exchangeRateType, exchangeDate,
                                periodName, "N", methodCode);
                if (defaultCurrency != null && defaultToRate != null) {
                    exchangeRate = defaultFromRate.divide(defaultToRate, 6, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return exchangeRate;
    }
}

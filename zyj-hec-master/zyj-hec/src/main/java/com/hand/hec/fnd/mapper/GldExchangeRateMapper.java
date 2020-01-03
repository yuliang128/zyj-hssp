package com.hand.hec.fnd.mapper;

/**
 *
 * 汇率定义mapper
 *
 * @weikun.wang xxx 2019-02-22
 */
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldExchangeRate;

public interface GldExchangeRateMapper extends Mapper<GldExchangeRate> {

    /**
     * @Description 获取汇率转换值及装换汇率
     *
     * @Author Tagin
     * @Date 2019-03-06 15:24
     * @param exchangeRateType 汇率类型
     * @param fromCurrency 需转换币种
     * @param toCurrency 转换币种
     * @param exchangeDate 转换日期
     * @param periodName 转换期间
     * @param accurateFlag 精确匹配标志
     * @Return java.util.List<com.hand.hec.fnd.dto.GldExchangeRate>
     * @Version 1.0
     **/
    List<GldExchangeRate> queryConversion(@Param("exchangeRateType") String exchangeRateType,
                    @Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency,
                    @Param("exchangeDate") Date exchangeDate, @Param("periodName") String periodName,
                    @Param("accurateFlag") String accurateFlag);

    /**
     * @Description
     *
     * @Author Tagin
     * @Date 2019-03-06 15:27
     * @param exchangeRateType 汇率类型
     * @param fromCurrency 需转换币种
     * @param toCurrency 转换币种
     * @param exchangeDate 转换日期
     * @Return java.util.Date
     * @Version 1.0
     **/
    Date queryMaxDate(@Param("exchangeRateType") String exchangeRateType, @Param("fromCurrency") String fromCurrency,
                    @Param("toCurrency") String toCurrency, @Param("exchangeDate") Date exchangeDate);

    /**
     * @Description 获取汇率转换值及装换汇率
     *
     * @Author Tagin
     * @Date 2019-03-06 15:24
     * @param exchangeRateType 汇率类型
     * @param fromCurrency 需转换币种
     * @param toCurrency 转换币种
     * @param periodName 转换期间
     * @Return java.util.List<com.hand.hec.fnd.dto.GldExchangeRate>
     * @Version 1.0
     **/
    String queryMaxPeriod(@Param("exchangeRateType") String exchangeRateType,
                    @Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency,
                    @Param("periodName") String periodName);

}

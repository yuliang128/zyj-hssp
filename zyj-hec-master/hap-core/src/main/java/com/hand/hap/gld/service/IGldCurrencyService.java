package com.hand.hap.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.gld.dto.GldCurrency;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 币种Service接口
 * </p>
 *
 * @author xingjialin 2019/01/07 10:38
 */
public interface IGldCurrencyService extends IBaseService<GldCurrency>, ProxySelf<IGldCurrencyService> {


    List<GldCurrency> queryEnabledCurrency(IRequest request);

    /**
     * 预算检查的预算币种符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param currencyCodeFrom 控制规则币种代码从
     * @param currencyCodeTo 控制规则币种代码到
     * @author YHL 2019-03-05 19:08
     * @return java.util.List<com.hand.hec.fnd.dto.GldCurrency> 符合的预算币种
     */
    List<GldCurrency> checkBgtCurrency(String controlType, String filtrateMethod, String currencyCodeFrom,
                    String currencyCodeTo);


    /**
     * <p>
     * 获取核算主体本位币
     * <p/>
     *
     * @param accEntityId 核算主体ID
     * @return String 本位币币种
     * @author yang.duan 2019/3/6 15:20
     */
    String getAccEntityFunCurrCode(@Param("accEntityId") Long accEntityId);

    /**
     * 根据精度计算金额
     *
     * @param amount
     * @param precision
     * @author mouse 2019-05-05 16:50
     * @return java.math.BigDecimal
     */
    BigDecimal calcAmountByPrecision(BigDecimal amount, int precision);

    /**
     * 根据币种计算交易精度金额
     * @param request
     * @param amount
     * @param currencyCode
     * @author mouse 2019-05-05 16:50
     * @return java.math.BigDecimal
     */
    BigDecimal calcTrxAmount(IRequest request, BigDecimal amount, String currencyCode);

    /**
     * 根据币种计算入账进度金额
     * @param request
     * @param amount
     * @param currencyCode
     * @author mouse 2019-05-05 16:50
     * @return java.math.BigDecimal
     */
    BigDecimal calcGldAmount(IRequest request, BigDecimal amount, String currencyCode);
}


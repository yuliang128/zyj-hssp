package com.hand.hap.gld.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * <p>
 * 币种ServiceImpl
 * </p>
 *
 * @author MouseZhou 2019/01/07 10:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldCurrencyServiceImpl extends BaseServiceImpl<GldCurrency> implements IGldCurrencyService {

    private static HashMap<String, Integer> trxPrecisionMap = new HashMap<>();
    private static HashMap<String, Integer> gldPrecisionMap = new HashMap<>();

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @Override
    public List<GldCurrency> queryEnabledCurrency(IRequest request) {
        GldCurrency currency = new GldCurrency();
        currency.setEnabledFlag("Y");
        Criteria criteria = new Criteria(currency);
        criteria.where(new WhereField(GldCurrency.FIELD_ENABLED_FLAG, Comparison.LIKE));
        return self().selectOptions(request, currency, criteria);
    }

    @Override
    public List<GldCurrency> checkBgtCurrency(String controlType, String filtrateMethod, String currencyCodeFrom,
                                              String currencyCodeTo) {
        return gldCurrencyMapper.checkBgtCurrency(controlType, filtrateMethod, currencyCodeFrom, currencyCodeTo);
    }

    /**
     * <p>
     * 获取核算主体本位币
     * <p/>
     *
     * @param accEntityId 核算主体ID
     * @return String 本位币币种
     * @author yang.duan 2019/3/6 15:20
     */
    @Override
    public String getAccEntityFunCurrCode(@Param("accEntityId") Long accEntityId) {
        return gldCurrencyMapper.getAccEntityFunCurrCode(accEntityId);
    }

    @Override
    public BigDecimal calcAmountByPrecision(BigDecimal amount, int precision) {

        return amount.setScale(precision,BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calcTrxAmount(IRequest request, BigDecimal amount, String currencyCode) {
        trxPrecisionMap.computeIfAbsent(currencyCode, k ->
                gldCurrencyMapper.selectOne(GldCurrency.builder().currencyCode(currencyCode).build()).getTransactionPrecision().intValue());
        return calcAmountByPrecision(amount, trxPrecisionMap.get(currencyCode));

    }

    @Override
    public BigDecimal calcGldAmount(IRequest request, BigDecimal amount, String currencyCode) {

        gldPrecisionMap.computeIfAbsent(currencyCode,k->
                gldCurrencyMapper.selectOne(GldCurrency.builder().currencyCode(currencyCode).build()).getFinancePrecision().intValue());
        return calcAmountByPrecision(amount, gldPrecisionMap.get(currencyCode));
    }

}

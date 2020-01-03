package com.hand.hec.cont.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.cont.dto.ConCashTrxPmtScheduleLn;

public interface ConCashTrxPmtScheduleLnMapper extends Mapper<ConCashTrxPmtScheduleLn> {

    /**
     * 根据现金事务收款对象获取资金计划
     *
     * @author Tagin
     * @date 2019-04-03 17:03
     * @param transactionLineId 现金事务行ID
     * @return List<ConCashTrxPmtScheduleLn>
     * @version 1.0
     **/
    List<ConCashTrxPmtScheduleLn> queryScheduleByTrx(@Param("transactionLineId") Long transactionLineId);

    /**
     * 根据现金事务获取资金计划合计金额
     *
     * @author Tagin
     * @date 2019-04-03 17:13
     * @param transactionLineId 现金事务行ID
     * @return java.math.BigDecimal
     * @version 1.0
     **/
    BigDecimal getTotalAmountByTrx(@Param("transactionLineId") Long transactionLineId);

}

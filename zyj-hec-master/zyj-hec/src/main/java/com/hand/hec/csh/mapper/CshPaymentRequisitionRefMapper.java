package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentRequisitionRef;

public interface CshPaymentRequisitionRefMapper extends Mapper<CshPaymentRequisitionRef>{

    /**
     * 校验引用申请的付款对象与付款事务的付款对象的一致性
     *
     * @param transactionLineId
     * @author ngls.luhui 2019-03-11 17:05
     * @return 返回结果大于0，则不一致
     */
    Long checkTranAndPayObj(@Param("transactionLineId") Long transactionLineId);

    /**
     * 校验引用申请的币种与付款事务的币种的一致性
     *
     * @param transactionLineId
     * @author ngls.luhui 2019-03-11 17:05
     * @return 返回结果大于0，则不一致
     */
    Long checkTranAndPayCurrency(@Param("transactionLineId") Long transactionLineId);
    
    /**
     *  已引用金额合计
     *
     * @param paymentReqLineId 借款申请行ID
     * @author ngls.luhui 2019-03-11 18:50
     * @return 已引用金额合计
     */
    BigDecimal querySumAmount(@Param("paymentReqLineId") Long paymentReqLineId);

    /**
     * 获取某个借款单行的已支付合计金额
     *
     * @param paymentReqLineId
     * @author mouse 2019-05-07 19:50
     * @return java.math.BigDecimal
     */
    BigDecimal getPaidAmount(@Param("paymentReqLineId") Long paymentReqLineId);
}
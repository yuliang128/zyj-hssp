package com.hand.hec.acp.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpRequisitionRef;

public interface AcpRequisitionRefMapper extends Mapper<AcpRequisitionRef> {

    /**
     * 获取付款申请单行已付金额
     *
     * @author Tagin
     * @date 2019-04-10 20:14
     * @param requisitionLnsId 付款申请单行ID
     * @return java.math.BigDecimal
     * @version 1.0
     **/
    BigDecimal getPaidAmount(@Param("requisitionLnsId") Long requisitionLnsId);


    /**
     * 根据核销获取付款申请支付信息
     * 
     * @author Tagin
     * @date 2019-05-29 15:58
     * @param writeOffId 核销 ID
     * @param transactionLineId 现金事务行 ID
     * @return List<AcpRequisitionRef>
     * @version 1.0
     **/
    List<AcpRequisitionRef> queryRefByWrite(@Param("writeOffId") Long writeOffId,
                    @Param("transactionLineId") Long transactionLineId);

}

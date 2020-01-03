package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentBatchRuleDtl;

public interface CshPaymentBatchRuleDtlMapper extends Mapper<CshPaymentBatchRuleDtl> {

    /**
     * 获取付款批决定规则明细
     *
     * @author Tagin
     * @date 2019-04-03 21:09
     * @param ruleLnsId 决定规则行ID
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentBatchRuleDtl>
     * @version 1.0
     **/
    List<CshPaymentBatchRuleDtl> getBatchRuleDtl(@Param("ruleLnsId") Long ruleLnsId);

}

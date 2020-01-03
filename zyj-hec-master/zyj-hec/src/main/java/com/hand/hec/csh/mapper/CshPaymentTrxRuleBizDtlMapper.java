package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBizDtl;

public interface CshPaymentTrxRuleBizDtlMapper extends Mapper<CshPaymentTrxRuleBizDtl> {

    /**
     * @Description 获取付款事务生成规则的业务范围明细
     *
     * @Author Tagin
     * @Date 2019-03-13 18:47
     * @param ruleBizId 业务范围ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshPaymentTrxRuleBizDtl>
     * @Version 1.0
     **/
    List<CshPaymentTrxRuleBizDtl> getTrxRuleBizDtl(@Param("ruleBizId") Long ruleBizId);

}

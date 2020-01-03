package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentBatchRuleLn;

public interface CshPaymentBatchRuleLnMapper extends Mapper<CshPaymentBatchRuleLn> {

    /**
     * 获取付款批决定规则明细
     *
     * @author Tagin
     * @date 2019-04-03 20:55
     * @param magOrgId 管理组织
     * @param accEntityId 核算主体
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentBatchRuleLn>
     * @version 1.0
     **/
    List<CshPaymentBatchRuleLn> getBatchRuleLn(@Param("magOrgId") Long magOrgId,
                    @Param("accEntityId") Long accEntityId);

}

package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentBatRuleAsgnAe;

public interface CshPaymentBatRuleAsgnAeMapper extends Mapper<CshPaymentBatRuleAsgnAe>{
    
    
    /**
     * 付款批规则定义-分配核算主体-批量分配 查询当前规则可分配的核算主体
     *
     * @param magOrgId 管理组织id
     * @param ruleId 付款批规则定义ID
     * @author ngls.luhui 2019-02-28 19:00
     * @return 可分配的核算主体list
     */
    List<CshPaymentBatRuleAsgnAeMapper> quertAccCanAsgn(@Param("magOrgId") Long magOrgId,@Param("ruleId") Long ruleId);
}
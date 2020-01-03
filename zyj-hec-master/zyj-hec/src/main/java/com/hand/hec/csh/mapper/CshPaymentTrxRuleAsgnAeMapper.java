package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentTrxRuleAsgnAe;

public interface CshPaymentTrxRuleAsgnAeMapper extends Mapper<CshPaymentTrxRuleAsgnAe>{

    /**
     * 管理组织级-付款事务生成规则定义-分配付款核算主体-批量分配 查询可分配的核算主体
     *
     * @param magOrgId 管理组织id
     * @param ruleId 事务生成规则id
     * @author ngls.luhui 2019-03-04 15:27
     * @return
     */
    List<CshPaymentTrxRuleAsgnAe> queryCanAsgn(@Param("magOrgId") Long magOrgId,@Param("ruleId") Long ruleId);
}
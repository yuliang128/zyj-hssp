package com.hand.hec.csh.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatRuleAsgnAe;
import com.hand.hec.csh.mapper.CshPaymentBatRuleAsgnAeMapper;

public interface ICshPaymentBatRuleAsgnAeService extends IBaseService<CshPaymentBatRuleAsgnAe>, ProxySelf<ICshPaymentBatRuleAsgnAeService>{

    /**
     * 付款批规则定义-分配核算主体-批量分配 查询当前规则可分配的核算主体
     *
     * @param magOrgId 管理组织id
     * @param ruleId 付款批规则定义ID
     * @author ngls.luhui 2019-02-28 19:00
     * @return 可分配的核算主体list
     */
    List<CshPaymentBatRuleAsgnAeMapper> quertAccCanAsgn(Long magOrgId,Long ruleId,IRequest request,Integer page,Integer pageSize);
}